package edu.greenblitz.bigRodika;

import edu.greenblitz.bigRodika.commands.chassis.ChassisCommand;
import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.gblib.hid.SmartJoystick;
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
        SwerveModule swerve = SingleModule.getInstance().getModule();
        mainJoystick.A.whenPressed(new ChassisCommand() {
            @Override
            public void initialize() {
                super.initialize();
                chassis.printAllEncoderValues();
            }

            @Override
            public boolean isFinished() {
                return true;
            }
        });
    }

    private void initOfficialButtons() {

   }

    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSideStick()  {
        return secondJoyStick;
    }
}