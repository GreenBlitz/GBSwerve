package edu.greenblitz.bigRodika.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.bigRodika.commands.swervemodule.OpMode;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.greenblitz.util.VersatileAngleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.pid.CollapsingPIDController;
import org.greenblitz.motion.pid.PIDObject;


import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.Modules.*;
import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.SwerveModule.*;

public class SwerveModule extends GBSubsystem {

	private final CANSparkMax rotationMotor;
	private final CANSparkMax driveMotor;
	private VersatileAngleEncoder angleEncoder;
	private final SparkEncoder driveEncoder;
	private CollapsingPIDController anglePID;
	private int ID;
	private boolean isDriveInverted, isRotateInverted;
	private RemoteCSVTarget logger;
	//	private long t0;
//	private double time;
	private double driveTarget = -1;
	private OpMode opMode;
	private int reverseFactor = 1;
	private final double EPSILON = 0.005;

	public double getAngleTarget() {
		return angleTarget;
	}

	private double angleTarget;

	SwerveModule(int ID) {
		this.ID = ID;
		this.rotationMotor = new CANSparkMax(RobotMap.Limbo2.Chassis.Modules.ROTATION_MOTOR_PORTS[ID], CANSparkMaxLowLevel.MotorType.kBrushless);
		this.rotationMotor.setInverted(RobotMap.Limbo2.Chassis.Modules.ROTATION_MOTORS_REVERSED[ID]);
		this.driveMotor = new CANSparkMax(RobotMap.Limbo2.Chassis.Modules.DRIVE_MOTOR_PORTS[ID], CANSparkMaxLowLevel.MotorType.kBrushless);
		this.driveMotor.setInverted(RobotMap.Limbo2.Chassis.Modules.DRIVE_MOTORS_REVERSED[ID]);
		this.driveMotor.setSmartCurrentLimit(40);
		this.rotationMotor.setSmartCurrentLimit(40);
		this.angleEncoder = new VersatileAngleEncoder(RobotMap.Limbo2.Chassis.Modules.LAMPREY_ANALOG_PORTS[ID], rotationMotor);
		this.driveEncoder = new SparkEncoder(RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SPARK, driveMotor);
	}

	public void init() {
		setOpMode(OpMode.BY_POWER);
		angleTarget = getAngle();
//		t0 = System.currentTimeMillis();
		configureDrive(DRIVE_P, DRIVE_I, DRIVE_D);
		configureRotation(ANGLE_P, ANGLE_I, ANGLE_D, 0.01, 0.01);
		this.logger = RemoteCSVTarget.initTarget(String.format("SwerveModule%d", ID), "time", "moduleAngle", "moduleSpeed", "target");
	}

	public void setOpMode(OpMode newOpMode) {
		this.opMode = newOpMode;
		newOpMode.getCommand(this).schedule(true);
	}

	public void setDrivePIDActive(boolean isActive) {
		if (isActive) {
			getDrivePID().setOutputRange(-0.3, 0.3);
		} else {
			getDrivePID().setOutputRange(0, 0);
		}
	}

	public void setIsLamprey(boolean isLamprey){
		angleEncoder.setLamprey(isLamprey);
	}

	public void configureDrive(double p, double i, double d) {
		CANPIDController controller = this.driveMotor.getPIDController();

		controller.setP(p);
		controller.setI(i);
		controller.setD(d);
	}

	public void configureRotation(double p, double i, double d, double tolerance, double thresh) {
		anglePID = new CollapsingPIDController(new PIDObject(p, i, d), thresh);
		anglePID.configure(getAngle(), angleTarget, -0.3, 0.3, 0);
		SmartDashboard.putNumber("angle target", getAngle());
		anglePID.setTolerance((goal, current) -> Math.abs(goal - current) < tolerance);
	}

	private CANPIDController getDrivePID() {
		return getDriveMotor().getPIDController();
	}

	public void setSpeed(double speed) {
		if(opMode != OpMode.BY_PID){
			System.out.println("trying to set speed but pid is disabled");
		}
		this.driveTarget = speed * reverseFactor;
		getDriveMotor().getPIDController().setReference(driveTarget, ControlType.kVelocity);
		System.out.println(SPEED_TO_FF.linearlyInterpolate(driveTarget)[0]);
		getDrivePID().setFF(SPEED_TO_FF.linearlyInterpolate(driveTarget)[0]);
	}

	public int decideSpinDirection() {
		double currentAngle = getAngle();
		double clockWise = currentAngle - angleTarget;
		double counterClockWise = Math.PI - clockWise;
		double newAngleTarget = angleTarget;
		int reverseFactor;
		if (clockWise >= counterClockWise) {
//			setAngle(Math.PI + newAngleTarget);
			reverseFactor = -1;
		} else {
//			setAngle(newAngleTarget);
			reverseFactor = 1;
		}
		return reverseFactor;
	}

	public void invertReverseFactor() {
		reverseFactor = -reverseFactor;
		setSpeed(driveTarget);
	}

	public int getReverseFactor() {
		return reverseFactor;
	}

	public void setAngle(double angle) {
		angle = angle % (2 * Math.PI);
		this.angleTarget = angle;
		this.anglePID.setGoal(angle);
		SmartDashboard.putNumber("angle target", angleTarget);
	}


	// Angle is measured in radians
	public double getAngle() {
		return angleEncoder.getAngle();
	}

	public int getRawLamprey(){
		return angleEncoder.getLampreyADCValue();
	}

	public boolean isOnAngle(){
		return anglePID.isFinished(getAngle());
	}

	public int getID() {
		return ID;
	}

	private CANSparkMax getRotationMotor() {
		return rotationMotor;
	}

	private CANSparkMax getDriveMotor() {
		return driveMotor;
	}

	private Object getAngleEncoder() {
		return angleEncoder;
	}

	private SparkEncoder getDriveEncoder() {
		return driveEncoder;
	}

	public double getLinVel() {
		return driveEncoder.getTickRate() * TICKS_TO_METERS * DRIVE_GEAR_RATIO;
	}

	public void setAsFollowerOf(CANSparkMax motor) {
		rotationMotor.follow(motor);
	}

	public void moveMotors(double drivePower, double anglePower) {
		setDrivePower(drivePower);
		setAnglePower(anglePower);
	}

	public void setDrivePower(double drivePower) {
		if(opMode != OpMode.BY_POWER){
			System.out.println("power is being set in non power opMode  (drive)");
		}
		getDriveMotor().set(drivePower * reverseFactor);
	}

	public void setAnglePower(double anglePower) {
		if(opMode != OpMode.BY_POWER){
			System.out.println("power is being set in non power opMode  (rotation)");
		}
		getRotationMotor().set(anglePower);
	}

	public void setAnglePowerByPID() {
		if(opMode != OpMode.BY_PID){
			System.out.println("pid is run not in pid opMode");
		}
		if (!angleEncoder.isLamprey()){
			int newReverseFactor = decideSpinDirection();
			if (newReverseFactor != reverseFactor) {
				setAngle(Math.PI + angleTarget);
			}
		}


		double currAngle = getAngle();
		double currAngleA = currAngle + 2 * Math.PI; //different representation of angle
		double currAngleB = currAngle - 2 * Math.PI; //different representation of angle
		currAngle = maxAngle(maxAngle(currAngle, currAngleA, getAngleTarget()), currAngleB, getAngleTarget());

		SmartDashboard.putNumber("min err angle", currAngle);
		getRotationMotor().set(Math.min(anglePID.calculatePID(currAngle), 0.1));
		SmartDashboard.putNumber("angle pid", anglePID.calculatePID(getAngle()));
	}




	/**
	 * finds the angle representation with lower difference compared to target
	 *
	 * @param angleA representation A of angle
	 * @param angleB representation B of angle
	 * @param target target
	 * @return the representation with lower difference
	 */
	private double maxAngle(double angleA, double angleB, double target) {
		if (Math.abs(target - angleA) < Math.abs(target - angleB)) {
			return angleA;
		} else return angleB;
	}

	public void driveInvert() {
		isDriveInverted = !isDriveInverted;
	}

	public void rotateInvert() {
		isRotateInverted = !isRotateInverted;
	}

	public void setDriveIdleMode(CANSparkMax.IdleMode mode) {
		getDriveMotor().setIdleMode(mode);
	}

	public void setRotationIdleMode(CANSparkMax.IdleMode mode) {
		getRotationMotor().setIdleMode(mode);
	}

	public void initDefaultCommand() {
	}

	public int getAngleEncoderValue() {
		return angleEncoder.getEncoderValue();
	}

	@Override
	public void periodic() {
		super.periodic();

//		SmartDashboard.putNumber(String.format("Drive Vel%d: ", this.ID), this.getLinVel());
//		SmartDashboard.putNumber(String.format("Angle%d: ", this.ID), this.getAngle());
//		SmartDashboard.putNumber(String.format("Encoder Voltage%d: ", this.ID), this.getAngleEncoderValue());
//		SmartDashboard.putString(String.format("opMode%d: ", this.ID), this.opMode.toString());
//		SmartDashboard.putNumber(String.format("Drive Encoder Ticks%d: ", this.ID), this.driveEncoder.getRawTicks());

//		this.time = System.currentTimeMillis() - t0;

//		logger.report(this.time / 1000.0, this.getAngle(), this.getLinVel(), angleTarget);

	}
}



