package edu.greenblitz.bigRodika;

import edu.greenblitz.bigRodika.commands.tests.GetLamNeoRelation;
import edu.greenblitz.bigRodika.commands.tests.chassis.ResetToZero;
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
		mainJoystick.A.whileHeld(new ParallelCommandGroup(
				new ResetToZero(0.25,0),
				new ResetToZero(0.25,1),
				new ResetToZero(0.25,2),
				new ResetToZero(0.25,3)
		));

		mainJoystick.B.whenPressed(new GetLamNeoRelation(0));
    }

    private void initOfficialButtons() {

    }

    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSideStick() {
        return secondJoyStick;
    }
}