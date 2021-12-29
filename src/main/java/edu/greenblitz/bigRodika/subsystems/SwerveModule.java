package edu.greenblitz.bigRodika.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.pid.CollapsingPIDController;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;


import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.Modules.*;
import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.SwerveModule.*;


public class SwerveModule extends GBSubsystem {

	private final CANSparkMax rotationMotor;
	private final CANSparkMax driveMotor;
	private final AnalogInput angleEncoder;
	private final SparkEncoder driveEncoder;
	private CollapsingPIDController anglePID;
	private int ID;
	private boolean isDriveInverted, isRotateInverted;
	private RemoteCSVTarget logger;
	private long t0 = -1;
	private double driveTarget = -1;
	private double angleTarget;


	SwerveModule(int ID) { // I'm not sure how to give port numbers in init' should i just add theme to init?
		this.ID = ID;
		isDriveInverted = false;
		isRotateInverted = false;
		rotationMotor = new CANSparkMax(RobotMap.Limbo2.Chassis.Modules.ROTATION_MOTOR_PORTS[ID], CANSparkMaxLowLevel.MotorType.kBrushless);
		driveMotor = new CANSparkMax(RobotMap.Limbo2.Chassis.Modules.DRIVE_MOTOR_PORTS[ID], CANSparkMaxLowLevel.MotorType.kBrushless); // TODO: check device type (2nd arg)
		angleEncoder = new AnalogInput(RobotMap.Limbo2.Chassis.Modules.LAMPREY_ANALOG_PORTS[ID]);// again, values from past code
		driveEncoder = new SparkEncoder(RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SPARK, driveMotor);

	}

	public void init() {
		angleTarget = getAngle();
		configureDrive(DRIVE_P, DRIVE_I, DRIVE_D);
		configureRotation(ANGLE_P, ANGLE_I, ANGLE_D, 0.2);
		this.logger = RemoteCSVTarget.initTarget(String.format("SwerveModule%d", ID), "time", "moduleAngle", "moduleSpeed", "target");
	}

	public void configureDrive(double p, double i, double d) {
		CANPIDController controller = this.driveMotor.getPIDController();

		controller.setP(p);
		controller.setI(i);
		controller.setD(d);
	}

	public PIDController getAnglePID(){
		return anglePID;
	}

	public void configureRotation(double p, double i, double d, double thresh) {
		anglePID = new CollapsingPIDController(new PIDObject(p, i, d), thresh);
		anglePID.configure(getAngle(), angleTarget, -0.15, 0.15, 0);
	}

	public CANPIDController getDrivePID() {
		return this.driveMotor.getPIDController();
	}

	public void setSpeed(double speed) {
		this.driveTarget = speed;
		this.driveMotor.getPIDController().setReference(speed, ControlType.kVelocity);
//        System.out.println(SPEED_TO_FF.linearlyInterpolate(speed)[0]);
		getDrivePID().setFF(SPEED_TO_FF.linearlyInterpolate(speed)[0]);
	}

	public void setAngle(double angle) {
		this.angleTarget = angle;
		this.anglePID.setGoal(angle);
	}

	public double getNormalizedAngle() {
		return getEncoderValue() % VOLTAGE_TO_ROTATIONS;
	}

	public double getEncoderValue() {
		return angleEncoder.getVoltage();
	}

	public double getAngle() {
		return getNormalizedAngle() / VOLTAGE_TO_ROTATIONS * 2 * Math.PI;
	}

	public int getID() {
		return ID;
	}

	public CANSparkMax getRotationMotor() {
		return rotationMotor;
	}

	public CANSparkMax getDriveMotor() {
		return driveMotor;
	}

	public AnalogInput getAngleEncoder() {
		return angleEncoder;
	}

	public SparkEncoder getDriveEncoder() {
		return driveEncoder;
	}

	public double getLinVel() {
		return driveEncoder.getTickRate() * TICKS_TO_METERS * DRIVE_GEAR_RATIO;
	}

	public boolean isDriveInverted() {
		return isDriveInverted;
	}

	public boolean isRotateInverted() {
		return isRotateInverted;
	}

	public void invertDrive() {
		isDriveInverted = !isDriveInverted;
	}

	public void invertRotate() {
		isRotateInverted = !isRotateInverted;
	}

	public void setAsFollowerOf(CANSparkMax motor) {
		rotationMotor.follow(motor);
	}

	public void setPower(double power) {
		driveMotor.set(power);
	}

	public void driveInvert() {
		isDriveInverted = !isDriveInverted;
	}

	public void rotateInvert() {
		isRotateInverted = !isRotateInverted;
	}

	public void initDefaultCommand() {
	}

	@Override
	public void periodic() {
		super.periodic();

		double currAngle = getAngle();
		double currAngleA = currAngle + 2*Math.PI; //different representation of angle
		double currAngleB = currAngle - 2*Math.PI; //different representation of angle
		if(angleTarget - currAngle <  angleTarget - currAngleA) //checking which representation has the lowest travel distance
			currAngle = angleTarget - currAngle <  angleTarget - currAngleB ? currAngle : currAngleB;
		else
			currAngle = angleTarget - currAngleA <  angleTarget - currAngleB ? currAngleA : currAngleB;

		getRotationMotor().set(anglePID.calculatePID(currAngle));
		SmartDashboard.putNumber("angle pid", anglePID.calculatePID(getAngle()));

		SmartDashboard.putNumber(String.format("DriveVel%d: ", this.ID), this.getLinVel());
		SmartDashboard.putNumber(String.format("Angle%d: ", this.ID), this.getAngle());

		double time;
		if (t0 == -1) {
			time = 0;
			t0 = System.currentTimeMillis();
		} else {
			time = System.currentTimeMillis() - t0;
		}

		logger.report(time / 1000.0, this.getAngle(), this.getLinVel(), driveTarget);
	}
}