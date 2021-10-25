package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;

public class DumbSwerveByConstants extends GBCommand {
    SwerveModule swerve;
    private double forward, turn;
    public static final double turnFactor = 0.2;

    public DumbSwerveByConstants(SwerveModule swerve, double forward, double turn) {
        super(SingleModule.getInstance());
        this.swerve = swerve;
        this.forward = forward;
        this.turn = turn;
    }

    @Override
    public void execute() {
        super.execute();
        swerve.getRotationMotor().set(turn);
        swerve.getDriveMotor().set(forward);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);

        this.swerve.getDriveMotor().set(0);
        this.swerve.getRotationMotor().set(0);
    }
}
