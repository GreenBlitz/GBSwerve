package edu.greenblitz.bigRodika.commands;

import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.bigRodika.exceptions.MotorPowerOutOfRangeException;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.gblib.hid.SmartJoystick;

/**
 * @author Orel
 */

public class HolonomicDrive extends ChassisCommand {

    private final SmartJoystick joystick;
    private final Chassis chassis;
    private final double POWER_CONST = 1.0;
    private boolean fieldOriented = true;

    public HolonomicDrive(SmartJoystick joystick){
        this.joystick = joystick;
        chassis = Chassis.getInstance();
    }

    @Override
    public void execute() {
        double xVal = joystick.getAxisValue(SmartJoystick.Axis.LEFT_X);
        double yVal = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
        double power = getLinearPower(xVal, yVal);
        double angle = getDriveAngle(xVal, yVal);
        chassis.moveMotors(new double[]{power, power, power, power}, new double[]{angle, angle, angle, angle}, fieldOriented);
    }

    public double getLinearPower(double xVal, double yVal){
        return Math.sqrt(Math.pow(xVal, 2) + Math.pow(2, yVal));
    }

    public double getDriveAngle(double xVal, double yVal){
        return Math.atan(yVal/xVal);
    }

}
