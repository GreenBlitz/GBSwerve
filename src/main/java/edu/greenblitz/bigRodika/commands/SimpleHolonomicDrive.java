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
    private final double POWER_CONST = 1.0;
    private boolean fieldOriented = true;

    public SimpleHolonomicDrive(SmartJoystick joystick) {
        this.joystick = joystick;
        chassis = Chassis.getInstance();
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

        chassis.moveMotors(new double[]{power, power, power, power}, new double[]{angle, angle, angle, angle}, false);
        //TODO: change fieldOriented to be right
    }

    public double getLinearPower(double xVal, double yVal) {
        return Math.sqrt(Math.pow(xVal, 2) + Math.pow(2, yVal));
    }

    public double getDriveAngle(double xVal, double yVal) {
        return Math.atan(yVal / xVal);
    }

}
