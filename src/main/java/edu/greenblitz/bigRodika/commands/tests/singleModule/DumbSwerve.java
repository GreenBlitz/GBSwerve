package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DumbSwerve extends GBCommand {
    SwerveModule swerve;
    SmartJoystick main;
    public static final double turnFactor = 0.4;

    public DumbSwerve(SwerveModule swerve, SmartJoystick main) {
        super(SingleModule.getInstance());
        this.swerve = swerve;
        this.main = main;
    }

    @Override
    public void execute() {
        super.execute();

        double drive = main.getAxisValue(SmartJoystick.Axis.LEFT_Y);
        double turn = main.getAxisValue(SmartJoystick.Axis.RIGHT_X) * turnFactor;

        swerve.getRotationMotor().set(turn);
        SmartDashboard.putNumber("power to angle", turn);
        swerve.getDriveMotor().set(drive);
    }
}
