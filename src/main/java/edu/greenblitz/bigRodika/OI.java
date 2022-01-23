package edu.greenblitz.bigRodika;


import edu.greenblitz.bigRodika.commands.TestSingleModule;
import edu.greenblitz.bigRodika.commands.chassis.ChassisCommand;
import edu.greenblitz.bigRodika.commands.chassis.DriveByConstantSpeed;
import edu.greenblitz.bigRodika.commands.chassis.GoToAngle;
import edu.greenblitz.bigRodika.commands.swervemodule.OpMode;
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
		mainJoystick.A.whileHeld(new GoToAngle(8*Math.PI/4));
	    mainJoystick.B.whileHeld(new GoToAngle(5*Math.PI/4));
		mainJoystick.X.whileHeld(new GoToAngle(3*Math.PI/4));
	    mainJoystick.Y.whileHeld(new GoToAngle(Math.PI/2));
    }

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSideStick() {
		return secondJoyStick;
	}

}