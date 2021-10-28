package edu.greenblitz.bigRodika;

import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.gblib.command.GBCommand;
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
        mainJoystick.X.whenPressed(new SetSingleModuleSpeed(300));
        mainJoystick.B.whenPressed(new SetSingleModuleSpeed(0));
        mainJoystick.A.whileHeld(new DumbSwerveByConstants(SingleModule.getInstance().getModule(), 0, 0));
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