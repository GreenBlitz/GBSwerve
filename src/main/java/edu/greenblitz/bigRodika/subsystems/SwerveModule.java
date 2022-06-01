package edu.greenblitz.bigRodika.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;
import edu.greenblitz.bigRodika.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveModule extends GBSubsystem{

	private final TalonFX driveMotor;
	private final TalonFX rotationMotor;
	private final CANCoder rotationEncoder;
	private double targetAngle;
	private static SwerveModule instance;

	private SwerveModule(){
		this.driveMotor = new TalonFX(1);
		this.driveMotor.setInverted(false);
		configure(driveMotor);

		this.rotationMotor = new TalonFX(3);
		this.rotationMotor.setInverted(false);
		configure(rotationMotor);
		rotationMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
		rotationMotor.configSelectedFeedbackCoefficient(360/ RobotMap.Limbo2.Chassis.SwerveModule.TICKS_PER_ROTATION);

		this.rotationEncoder = new CANCoder(4);
		rotationMotor.setSelectedSensorPosition(rotationEncoder.getAbsolutePosition());
		setRotationalPID(3,0.005, 20,100, 0.5, 2);


	}

	private void configure(TalonFX motor){
		motor.configOpenloopRamp(0.75);
		motor.configClosedloopRamp(0.75);
		motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 35, 100));
		motor.configVelocityMeasurementPeriod(SensorVelocityMeasPeriod.Period_100Ms);
	}


	public static SwerveModule getInstance() {
		if (instance == null) {
			instance = new SwerveModule();
		}
		return instance;
	}




	public void moveMotors(double powerLin,double powerRot){
		this.driveMotor.set(TalonFXControlMode.PercentOutput,powerLin);
		this.rotationMotor.set(TalonFXControlMode.PercentOutput,powerRot);

	}

	/**
	 * sets pid and max speed max acceleration for motion magic
	 * @param kp
	 * @param ki
	 * @param kd
	 * @param peakOutput maximum percent out 0-1.0
	 * @param tolerance acceptable error
	 */

	public void setRotationalPID(double kp,double ki, double iZone, double kd, double peakOutput, double tolerance){
		this.rotationMotor.configMotionCruiseVelocity(RobotMap.Limbo2.Chassis.SwerveModule.MAX_SPEED);
		this.rotationMotor.configMotionAcceleration(RobotMap.Limbo2.Chassis.SwerveModule.MAX_ACC);
		this.rotationMotor.config_kP(0, kp);
		this.rotationMotor.config_kI(0, ki);
		this.rotationMotor.config_IntegralZone(0, iZone);
		this.rotationMotor.config_kD(0, kd);
		this.rotationMotor.configAllowableClosedloopError(0, tolerance);
		this.rotationMotor.configClosedLoopPeakOutput(0,peakOutput);
		SmartDashboard.putNumber("kp", kp);
		SmartDashboard.putNumber("ki", ki);
		SmartDashboard.putNumber("iZone", iZone);
		SmartDashboard.putNumber("kd", kd);
		SmartDashboard.putNumber("peak output", peakOutput);
		SmartDashboard.putNumber("tolerance", tolerance);
	}

	public double getAngle(){
		return rotationEncoder.getAbsolutePosition();
	}

	/**
	 * takes angle and sets it as the target.
	 * @param angle the angle target takes between 0 - 359
	 */
	public void moveToAngleByPID(double angle){
		angle = getClosestAngle(rotationMotor.getSelectedSensorPosition(),angle);
		this.targetAngle = angle;
		this.rotationMotor.set(TalonFXControlMode.Position,angle);
	}

	/**
	 * motion magic generates a trapezoid  profile and follows it
	 * @param angle the angle target takes between 0 - 359
	 */

	public void moveToAngleByMotionMagic(double angle){
		rotationMotor.set(TalonFXControlMode.MotionMagic, angle);
		rotationMotor.config_kF(0, 512/ RobotMap.Limbo2.Chassis.SwerveModule.MAX_SPEED);
	}


	/**
	 * @param curr current angle in continues terms
	 * @param target angle target between 0 and 359
	 * @return the angle target + k360 to be closest to current angle
	 */

	private double getClosestAngle(double curr, double target) {
		double currMod = curr % 360;
		double t1 = target + 360;
		double t2 = target - 360;
		double t3 = target;
		double e1 = Math.abs(currMod - t1);
		double e2 = Math.abs(currMod - t2);
		double e3 = Math.abs(currMod - t3);
		if (e2 > e1 && e3 > e1) {
			return t1 + curr - currMod;
		}
		else if (e1 > e2 && e3 > e2) {
			return t2 + curr - currMod;
		}
		else {
			return t3 + curr - currMod;
		}
	}

	private long t0;
	boolean tested = false;

	@Override
	public void periodic(){
		SmartDashboard.putNumber("angle",getAngle());
		SmartDashboard.putNumber("motor", rotationMotor.getSelectedSensorPosition());
		SmartDashboard.putNumber("speed", rotationMotor.getSelectedSensorVelocity());

		double kp = SmartDashboard.getNumber("kp", 0);
		double ki = SmartDashboard.getNumber("ki", 0);
		double iZone = SmartDashboard.getNumber("iZone", 0);
		double kd = SmartDashboard.getNumber("kd", 0);
		double peakOutput = SmartDashboard.getNumber("peak output", 1);
		double tolerance = SmartDashboard.getNumber("tolerance", 2);
		setRotationalPID(kp, ki, iZone, kd, peakOutput, tolerance);

	}
}
