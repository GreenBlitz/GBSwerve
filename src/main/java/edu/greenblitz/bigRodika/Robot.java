package edu.greenblitz.bigRodika;

import edu.greenblitz.bigRodika.commands.chassis.locazlier.LocalizerCommandRunner;
import edu.greenblitz.bigRodika.subsystems.*;
import edu.greenblitz.bigRodika.utils.VisionMaster;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.greenblitz.motion.Localizer;

public class Robot extends TimedRobot {

    @Override
    public void robotInit() {
        CommandScheduler.getInstance().enable();

//        Pneumatics.init();
//        Shifter.init();
        Funnel.init();
        Shooter.init();

//        Chassis.init(); // Must be last!

        OI.getInstance();


//        VisionMaster.getInstance();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("Shooter::Speed", Shooter.getInstance().getShooterSpeed());
        Shooter.getInstance().update();
//        VisionMaster.getInstance().update();
    }

    @Override
    public void teleopInit() {
        Shooter.getInstance().resetEncoder();
//        Chassis.getInstance().toBrake();
//        Localizer.getInstance().reset(Chassis.getInstance().getLeftMeters(), Chassis.getInstance().getRightMeters());

//        new LocalizerCommandRunner().schedule();

    }
}
