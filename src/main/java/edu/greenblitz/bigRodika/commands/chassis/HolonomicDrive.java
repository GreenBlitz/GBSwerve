package edu.greenblitz.bigRodika.commands.chassis;

import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.gblib.hid.SmartJoystick;
import org.greenblitz.motion.base.Vector2D;

/**
 * @author Orel
 */

public class HolonomicDrive extends ChassisCommand {

    private final SmartJoystick joystick;
    private final Chassis chassis;
    private final double maxPower;
    private boolean fieldOriented = true;

    public HolonomicDrive(SmartJoystick joystick, boolean fieldOriented, double maxPower){
        this.joystick = joystick;
        chassis = Chassis.getInstance();
        this.fieldOriented = fieldOriented;
        this.maxPower = maxPower;
    }
    public HolonomicDrive(SmartJoystick joystick, boolean fieldOriented){
        this(joystick, fieldOriented, 1.0);
    }

    @Override
    public void execute() {
        double xVal = joystick.getAxisValue(SmartJoystick.Axis.LEFT_X);
        double yVal = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
        double omega = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X);
        Vector2D vel = new Vector2D(xVal, yVal);
        vel.scale(maxPower);
        if(fieldOriented){
            vel.rotate(-Chassis.getInstance().getAngle());
        }
        Chassis.getInstance().fullSwerve(vel.getX(), vel.getY(), omega);
    }


}
