package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;

public class OneModuleTestByJoystick extends GBCommand {
    private SwerveModule module;
    private final SmartJoystick joystick;
    private final double POWER_SCALE = 0.5;

    public OneModuleTestByJoystick(SmartJoystick joystick, SwerveModule module) {
        this.joystick = joystick;
        this.module = module;
    }

    @Override
    public void initialize() {
        module.setPower(0);
    }

    @Override
    public void execute() {
        double xVal = joystick.getAxisValue(SmartJoystick.Axis.LEFT_X);
        double yVal = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
        module.setPower(yVal * POWER_SCALE);
        module.setAngle(xVal);
        module.putString("One Module Chassis", module.toString());
    }
}