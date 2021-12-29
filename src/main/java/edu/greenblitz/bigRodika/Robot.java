package edu.greenblitz.bigRodika;

import edu.greenblitz.bigRodika.commands.tests.singleModule.TestPID;
import edu.greenblitz.bigRodika.subsystems.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


public class Robot extends TimedRobot {

	@Override
	public void robotInit() {

//        Chassis.init();
		OI.getInstance();
		SingleModule.getInstance();
		SingleModule.getInstance().init();
//        SingleModule.getInstance().initDefaultCommand();
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
//        SingleModule.getInstance().initDefaultCommand();
	}
}
