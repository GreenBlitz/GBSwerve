package edu.greenblitz.bigRodika;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.bigRodika.commands.tests.singleModule.*;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;

public class OI {
    private static OI instance;

    private final SmartJoystick mainJoystick;
    private final SmartJoystick secondJoyStick;

    private OI() {
        mainJoystick = new SmartJoystick(RobotMap.Limbo2.Joystick.MAIN,
                RobotMap.Limbo2.Joystick.MAIN_DEADZONE);
        secondJoyStick = new SmartJoystick(RobotMap.Limbo2.Joystick.SIDE,
                RobotMap.Limbo2.Joystick.SIDE_DEADZONE);

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

        SwerveModule s = new SwerveModule(0);
        mainJoystick.B.whileHeld(new OneModuleTestByConstants(90, 0));
//        mainJoystick.A.whenPressed(new OneModuleTestByJoystick(mainJoystick, s));
    }

    private void initOfficalButtons() {

   }

    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSideStick()  {
        return secondJoyStick;
    }
}