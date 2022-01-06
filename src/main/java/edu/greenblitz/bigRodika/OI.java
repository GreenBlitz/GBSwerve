package edu.greenblitz.bigRodika;

import edu.greenblitz.bigRodika.commands.ChassisCommand;
import edu.greenblitz.bigRodika.exceptions.MotorPowerOutOfRangeException;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.bigRodika.commands.tests.singleModule.*;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

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

		mainJoystick.POV_UP.whenHeld(new ChassisCommand() {
			@Override
			public void initialize() {
				super.initialize();
				Chassis.getInstance().toBrake();
			}

			@Override
			public void execute() {
				super.execute();
				try {
					Chassis.getInstance().moveMotors(new double[]{0.2,0.2,0.2,0.2}, new double[]{0,0,0,0});
				} catch (MotorPowerOutOfRangeException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				try {
					Chassis.getInstance().moveMotors(new double[]{0,0,0,0}, new double[]{0,0,0,0});
				} catch (MotorPowerOutOfRangeException e) {
					e.printStackTrace();
				}
			}
		});

	    mainJoystick.POV_DOWN.whenHeld(new ChassisCommand() {
		    @Override
		    public void initialize() {
			    super.initialize();
			    Chassis.getInstance().toBrake();
		    }

		    @Override
		    public void execute() {
			    super.execute();
			    try {
				    Chassis.getInstance().moveMotors(new double[]{0,0,0,0}, new double[]{0.5,0.5,0.5,0.5});
			    } catch (MotorPowerOutOfRangeException e) {
				    e.printStackTrace();
			    }
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