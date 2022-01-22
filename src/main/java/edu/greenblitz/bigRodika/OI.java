package edu.greenblitz.bigRodika;


import edu.greenblitz.bigRodika.commands.TestSingleModule;
import edu.greenblitz.bigRodika.commands.tests.singleModule.FindLampreyValuesByNeo;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;


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
    private void initTestButtons() {
//	    mainJoystick.A.whenPressed(new ResetToZero(0.005));
//	    mainJoystick.B.whileHeld(new TestSingleModule(3,0,0.165));
	    mainJoystick.A.whileHeld(new FindLampreyValuesByNeo(0.0  , RobotMap.Limbo2.Chassis.SwerveModule.MEATBALL));

    }

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSideStick() {
		return secondJoyStick;
	}

}