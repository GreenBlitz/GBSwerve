package edu.greenblitz.bigRodika;

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

    private void initTestButtons() {

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