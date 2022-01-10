package edu.greenblitz.bigRodika.commands.swervemodule;

import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;

public class AngleByPID extends GBCommand {

    private SwerveModule module;

    public AngleByPID(SwerveModule module) {
        this.module = module;
    }

    @Override
    public void execute() {
        module.setAnglePowerByPID();
    }
}
