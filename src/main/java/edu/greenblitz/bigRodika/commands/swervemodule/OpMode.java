package edu.greenblitz.bigRodika.commands.swervemodule;

import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;

public enum OpMode {
	BY_PID, BY_POWER;

	/*
	the code is built with the 2 modes of operation in mind either controlling the power of both drive and rotation motors
	or setting target angle and target speed to follow with set pid.
	while in BY_POWER the module pid should be disabled

	 */

	public GBCommand getCommand(SwerveModule module) {
		switch (this) {
			case BY_PID:
				return new SwerveOpMode(module) {
					@Override
					public void initialize() {
						module.setAngle(module.getAngle());
						module.setSpeed(0);
						module.setDrivePIDActive(true);
					}

					@Override
					public void execute() {
						module.setAnglePowerByPID();
					}

					@Override
					public void end(boolean interrupted) {
						//if (module.getReverseFactor() == -1) {
							//module.invertReverseFactor();
						//}
						module.setDrivePIDActive(false);
					}
				};
			case BY_POWER:
				return new SwerveOpMode(module) {
				};
			default:
				return new SwerveOpMode(module) {
				};
		}
	}
}

