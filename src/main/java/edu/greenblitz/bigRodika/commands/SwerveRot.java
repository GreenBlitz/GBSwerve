package edu.greenblitz.bigRodika.commands;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;


public class SwerveRot extends GBCommand{
	private double powerRot;
	public SwerveRot(double powerRot){
		this.powerRot = powerRot;
	}

	@Override
	public void execute() {
		SwerveModule.getInstance().moveRotation(powerRot);
	}

	@Override
	public void end(boolean interrupted) {
		SwerveModule.getInstance().moveRotation(0);
	}
}

