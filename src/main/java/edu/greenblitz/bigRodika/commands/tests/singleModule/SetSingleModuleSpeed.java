package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.gblib.command.GBCommand;

public class SetSingleModuleSpeed extends GBCommand {
    private int speed;

    public SetSingleModuleSpeed(int speed) {
        super(SingleModule.getInstance());

        this.speed = speed;
    }

    @Override
    public void initialize() {
        super.initialize();

        SingleModule.getInstance().getModule().setSpeed(speed);
    }
}
