package edu.greenblitz.bigRodika.commands;


import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;

public abstract class SwerveCommand extends GBCommand {
	protected SwerveModule swerveModule;

	public SwerveCommand() {
		this.swerveModule = SwerveModule.getInstance();
		require(swerveModule);
	}

}

