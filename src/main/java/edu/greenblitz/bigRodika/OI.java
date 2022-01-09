package edu.greenblitz.bigRodika;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.bigRodika.commands.ChassisCommand;
import edu.greenblitz.bigRodika.commands.TestSingleModule;
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