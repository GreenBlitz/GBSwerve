package edu.greenblitz.bigRodika.commands;

import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;

public class SwerveLin extends GBCommand {
	private double powerLin;
	public SwerveLin(double powerLin){
		this.powerLin = powerLin;
	}

	@Override
	public void execute() {
		SwerveModule.getInstance().moveLiner(powerLin);
	}

	@Override
	public void end(boolean interrupted) {
		SwerveModule.getInstance().moveLiner(0);
	}
}

