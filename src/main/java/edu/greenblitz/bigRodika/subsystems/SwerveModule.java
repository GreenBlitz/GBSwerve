package edu.greenblitz.bigRodika.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveModule extends GBSubsystem{

	private final TalonFX driveMotor;
	private final TalonFX rotationMotor;
	private final CANCoder rotationEncoder;
	private final double zeroAnglePoint;

	private static SwerveModule instance;

	private SwerveModule(double zeroAnglePoint){
		this.driveMotor = new TalonFX(1);
		this.driveMotor.setInverted(false);
		configure(driveMotor);
		this.rotationMotor = new TalonFX(3);
		this.rotationMotor.setInverted(false);
		configure(rotationMotor);
		rotationMotor.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
		this.rotationEncoder = new CANCoder(4);

		this.zeroAnglePoint = zeroAnglePoint;

	}

	private void configure(TalonFX motor){
		motor.configOpenloopRamp(0.75);
		motor.configClosedloopRamp(0.75);
		motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 35, 100));
	}


	public static SwerveModule getInstance() {
		if (instance == null) {
			instance = new SwerveModule(45);
		}
		return instance;
	}




	public void moveMotors(double powerLin,double powerRot){
		this.driveMotor.set(TalonFXControlMode.PercentOutput,powerLin);
		this.rotationMotor.set(TalonFXControlMode.PercentOutput,powerRot);

	}


	public double getRelativePosition(){
		return (rotationEncoder.getAbsolutePosition()+zeroAnglePoint)%360;
	}


	@Override
	public void periodic(){
		SmartDashboard.putNumber("angle",rotationEncoder.getAbsolutePosition());
		SmartDashboard.putNumber("relative angle",getRelativePosition());
	}
}
