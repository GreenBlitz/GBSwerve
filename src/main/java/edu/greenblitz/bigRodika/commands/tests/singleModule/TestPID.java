package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.Robot;
import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.gblib.command.GBCommand;

public class TestPID extends GBCommand {

    private double time;
    @Override
    public void initialize() {
        SingleModule.getInstance().getModule().setSpeed(1);
        time = System.currentTimeMillis()/1000.0;
    }

    @Override
    public void execute() {
        super.execute();

    }

    @Override
    public void end(boolean interrupted) {
        SingleModule.getInstance().getModule().setSpeed(0);
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return (time + 2.0 < System.currentTimeMillis()/1000.0);
    }
}
