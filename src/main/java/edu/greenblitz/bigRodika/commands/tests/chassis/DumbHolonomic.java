package edu.greenblitz.bigRodika.commands.tests.chassis;

import edu.greenblitz.bigRodika.OI;
import edu.greenblitz.bigRodika.exceptions.MotorPowerOutOfRangeException;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;

public class DumbHolonomic extends GBCommand {
    @Override
    public void initialize() {
        super.initialize();
    }

    public DumbHolonomic() {
        require(Chassis.getInstance());
    }

    @Override
    public void execute() {
        super.execute();

        double speed = OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_Y);
        double angleSpeed = OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X);

        double powers[] = {speed, speed, speed, speed};
        double angles[] = {angleSpeed, angleSpeed, angleSpeed, angleSpeed};

        Chassis.getInstance().moveMotors(powers, angles, false); //TODO: change fieldOriented to be right
    }

}
