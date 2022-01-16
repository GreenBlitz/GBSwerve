package edu.greenblitz.bigRodika;

import edu.greenblitz.bigRodika.subsystems.Chassis;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

    @Override
    public void robotInit() {
	    Chassis.init();
        OI.getInstance();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().cancelAll();
        //Chassis.getInstance().initTestCommand();
//        SingleModule.getInstance().initDefaultCommand();
    }
}
