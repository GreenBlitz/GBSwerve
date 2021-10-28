package edu.greenblitz.bigRodika.commands.tests.singleModule;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;
import org.greenblitz.debug.RemoteCSVTarget;

public class OneModuleTestByConstants extends GBCommand {

    private static SwerveModule module = SingleModule.getInstance().getModule();
    private double angle, velocity;

    public OneModuleTestByConstants(double angle, double velocity) {
        this.angle = angle;
        this.velocity = velocity;
    }

    @Override
    public void initialize() {
        module.setPower(0);
        module.getRotationMotor().set(0);
    }

    @Override
    public void execute() {
        module.getDriveMotor().set(this.velocity);
//        module.setAngle(this.angle); TODO: fix setAngle and uncomment this
    }

    public void end(boolean interrupted) {
        module.setPower(0);
        module.getRotationMotor().set(0);
    }
}
