package edu.greenblitz.bigRodika.commands;

public class MoveSwerve extends SwerveCommand{
	 private double powerRot;
	 private double powerLin;


	public MoveSwerve(double powerRot, double powerLin){
		this.powerLin = powerLin;
		this.powerRot = powerRot;
	}

	@Override
	public void execute() {
		swerveModule.moveMotors(powerLin,powerRot);
	}

	@Override
	public void end(boolean interrupted) {
		swerveModule.moveMotors(0,0);
	}
}
