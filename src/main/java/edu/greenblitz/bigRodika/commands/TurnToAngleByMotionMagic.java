package edu.greenblitz.bigRodika.commands;

import edu.greenblitz.bigRodika.subsystems.SwerveModule;

public class TurnToAngleByMotionMagic extends SwerveCommand {

	double angle;

	public TurnToAngleByMotionMagic(double angle) {
		this.angle = angle;
	}

	@Override
	public void initialize() {
		super.initialize();
		swerveModule.moveToAngleByMotionMagic(angle);

	}

	@Override
	public void execute() {
		super.execute();
	}

	@Override
	public void end(boolean interrupted) {
		swerveModule.moveMotors(0, 0);
	}
}
