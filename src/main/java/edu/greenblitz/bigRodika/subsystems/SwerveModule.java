package edu.greenblitz.bigRodika.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class SwerveModule {
	swerveRotation rotation;
	swerveLinear linear;



	private static SwerveModule instance;

	private SwerveModule(){
		rotation = new swerveRotation();
		linear = new swerveLinear();
	}

	public static SwerveModule getInstance() {
		if (instance == null) {
			instance = new SwerveModule();
		}
		return instance;
	}

	public void moveRotation(double power){
		this.rotation.move(power);
	}

	public void moveLiner(double power){
		this.linear.move(power);
	}

	public swerveLinear getLinear(){
		return linear;
	}

	public swerveRotation getRotation(){
		return rotation;
	}


	public class swerveLinear extends GBSubsystem{
		private final TalonFX driveMotor;

		private swerveLinear(){
			this.driveMotor = new TalonFX(1);
			this.driveMotor.setInverted(false);
		}

		private void move(double power){
			this.driveMotor.set(TalonFXControlMode.PercentOutput,power);
		}
	}

	public class swerveRotation{
		private final TalonFX rotationMotor;
		private swerveRotation(){
			this.rotationMotor = new TalonFX(3);
			this.rotationMotor.setInverted(false);
		}
		private void move(double power){
			this.rotationMotor.set(TalonFXControlMode.PercentOutput,power);
		}
	}
}
