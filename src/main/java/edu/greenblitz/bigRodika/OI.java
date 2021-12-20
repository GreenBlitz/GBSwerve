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
        SwerveModule swerve = SingleModule.getInstance().getModule();
        mainJoystick.A.whenPressed(new DumbSwerveByDashboard(swerve));
        mainJoystick.B.whenPressed(new GBCommand() {
            @Override
            public void initialize() {
                swerve.setAngle(swerve.getAngle() + Math.PI/2);
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