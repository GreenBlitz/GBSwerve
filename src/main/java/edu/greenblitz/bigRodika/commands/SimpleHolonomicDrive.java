package edu.greenblitz.bigRodika.commands;

import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.bigRodika.commands.chassis.ChassisCommand;
import edu.greenblitz.bigRodika.commands.swervemodule.OpMode;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.gblib.hid.SmartJoystick;

import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.SwerveModule.*;

/**
 * @author Orel
 */

public class SimpleHolonomicDrive extends ChassisCommand {

    private final SmartJoystick joystick;

    private static final double POWER_CONST = 1.0;
	private static final double EPSILON = 0.05;

    public SimpleHolonomicDrive(SmartJoystick joystick) {
		super();
        this.joystick = joystick;
    }

    @Override
    public void initialize() {
        chassis.toBrake();
		chassis.setModuleOpMode(OpMode.BY_PID);
    }

    @Override
    public void execute() {
        double xVal = joystick.getAxisValue(SmartJoystick.Axis.LEFT_X);
        double yVal = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
        double power = getLinearPower(xVal, yVal);
		double speed = power * MAX_SPEED;
        double angle = getDriveAngle(xVal, yVal);
		double[] angles = angle != -10 ? new double[]{angle, angle, angle, angle}: chassis.getAngles();
        chassis.setTargets(new double[]{speed, speed, speed, speed}, angles);
    }

    public double getLinearPower(double xVal, double yVal) {
		return Math.hypot(xVal, yVal);
    }

    public double getDriveAngle(double xVal, double yVal) {
		if(getLinearPower(xVal, yVal) < EPSILON){
			return -10;
		}
        return Math.atan(yVal / xVal);
    }

}
