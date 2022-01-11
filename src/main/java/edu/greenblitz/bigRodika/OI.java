package edu.greenblitz.bigRodika;

import edu.greenblitz.bigRodika.commands.TestSingleModule;
import edu.greenblitz.bigRodika.commands.chassis.ChassisCommand;
import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.hid.SmartJoystick;


public class OI {
	private static OI instance;

	private final SmartJoystick mainJoystick;
	private final SmartJoystick secondJoyStick;

	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Limbo2.Joystick.MAIN, RobotMap.Limbo2.Joystick.MAIN_DEADZONE);
		secondJoyStick = new SmartJoystick(RobotMap.Limbo2.Joystick.SIDE, RobotMap.Limbo2.Joystick.SIDE_DEADZONE);

		initTestButtons();
//        initOfficalButtons();
	}


	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}

	private void initOfficialButtons() {

	}

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSideStick() {
		return secondJoyStick;
	}

	private void initTestButtons() {
		SwerveModule swerve = SingleModule.getInstance().getModule();
		mainJoystick.A.whileHeld(new TestSingleModule(2, 0, 0.065));

	}
}