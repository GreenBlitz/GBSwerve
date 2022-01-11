package edu.greenblitz.bigRodika.commands.swervemodule;

import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;

public abstract class SwerveOpMode extends GBCommand {
	private SwerveModule module;
	
	public SwerveOpMode(SwerveModule module) {
		this.module = module;
		this.require(module);	}
}
