package edu.greenblitz.bigRodika.commands.tests.singleModule;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;
import org.greenblitz.debug.RemoteCSVTarget;

public class OneModuleTestByConstants extends GBCommand {

    private static SwerveModule module = SingleModule.getInstance().getModule();
    private double angle, velocity;
    private RemoteCSVTarget logger;
    private long t0;

    public OneModuleTestByConstants(double angle, double velocity) {
        this.angle = angle;
        this.velocity = velocity;
        this.logger = RemoteCSVTarget.initTarget("SwerveModuleByConstants", "time", "vel", "angle");
    }

    @Override
    public void initialize() {
        module.setPower(0);
        module.getRotationMotor().set(0);
        this.t0 = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        module.getDriveMotor().set(this.velocity);
        module.setAngle(this.angle);
        logger.report((System.currentTimeMillis() - this.t0) / 1000.0, module.getLinVel(), module.getDegrees());
    }

    public void end(boolean interrupted) {
        module.setPower(0);
    }
}
