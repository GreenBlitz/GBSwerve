package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import org.greenblitz.debug.RemoteCSVTarget;

public class DumbSwerveByConstants extends GBCommand {
	protected SwerveModule swerve;
	protected double drive, turn;
	protected RemoteCSVTarget logger;
	protected long t0;

	public DumbSwerveByConstants(SwerveModule swerve, double drive, double turn) {
		super(SingleModule.getInstance());
		this.swerve = swerve;
		this.drive = drive;
		this.turn = turn;

		String key = String.format("DumbSwerveByConstants%d", swerve.getID());
		logger = RemoteCSVTarget.initTarget(key, "time", "linVel", "angle");
	}

	@Override
	public void initialize() {
		super.initialize();

		this.t0 = System.currentTimeMillis();
	}

	@Override
	public void execute() {
		super.execute();
		swerve.moveMotors(drive, turn);

		logger.report((double) (System.currentTimeMillis() - t0) / 1000.0, this.swerve.getLinVel(), this.swerve.getAngle());
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		this.swerve.moveMotors(0, 0);
	}
}
