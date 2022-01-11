package edu.greenblitz.bigRodika.commands.swervemodule;

import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;

public enum OpMode {
	ANGLE_BY_PID, ANGLE_BY_POWER;

	public GBCommand getCommand(SwerveModule module) {
		switch (this) {
			case ANGLE_BY_PID:
				return new SwerveOpMode(module) {
					@Override
					public void initialize() {
						module.setAngle(module.getAngle());
					}

					@Override
					public void execute() {
						module.setAnglePowerByPID();
					}
				};
			case ANGLE_BY_POWER:
				return new SwerveOpMode(module) {};
			default: //Still redundant (and still equivalent to a basic if) but a switch is more modular
				return new SwerveOpMode(module) {};
		}
	}
}

