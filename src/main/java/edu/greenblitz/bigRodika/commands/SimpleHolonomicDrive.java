package edu.greenblitz.bigRodika.commands;

import edu.greenblitz.bigRodika.commands.chassis.ChassisCommand;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.gblib.hid.SmartJoystick;

/**
 * @author Orel
 */

public class SimpleHolonomicDrive extends ChassisCommand {

    private final SmartJoystick joystick;
    private final Chassis chassis;
    private boolean fieldOriented = true;

    private static final double POWER_CONST = 1.0;

    public SimpleHolonomicDrive(SmartJoystick joystick) {
        this.joystick = joystick;
        this.chassis = Chassis.getInstance();
    }

    @Override
    public void initialize() {
        chassis.toBrake();
    }

    @Override
    public void execute() {
        double xVal = joystick.getAxisValue(SmartJoystick.Axis.LEFT_X);
        double yVal = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
        double power = getLinearPower(xVal, yVal);
        double angle = getDriveAngle(xVal, yVal);
	    if (fieldOriented) {
		        // TODO: 14/10/2020 check clockwise = positive in gyro
//                angle = angle - chassis.getAngle();
	    }

        chassis.moveMotors(power, angle, false);
        //TODO: change fieldOriented to be right
    }

    public double getLinearPower(double xVal, double yVal) {
        return Math.sqrt(Math.pow(xVal, 2) + Math.pow(2, yVal));
    }

    public double getDriveAngle(double xVal, double yVal) {
        return Math.atan(yVal / xVal);
    }

}
