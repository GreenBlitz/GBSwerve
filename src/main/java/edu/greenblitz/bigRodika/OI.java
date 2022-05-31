package edu.greenblitz.bigRodika;

import edu.greenblitz.bigRodika.commands.MoveSwerve;
import edu.greenblitz.bigRodika.commands.TurnToAngle;
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
		mainJoystick.A.whileHeld(new MoveSwerve(0.1,0));
		mainJoystick.B.whileHeld(new MoveSwerve(-0.06,0));
		mainJoystick.X.whileHeld(new MoveSwerve(0.3,0));
		mainJoystick.Y.whileHeld(new MoveSwerve(0.15,0));
		mainJoystick.POV_UP.whenPressed(new TurnToAngle(90));
		mainJoystick.POV_DOWN.whenPressed(new TurnToAngle(180));
		mainJoystick.POV_LEFT.whenPressed(new TurnToAngle(359));
	}
}