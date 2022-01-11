package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.gblib.command.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetSingleModuleSpeed extends GBCommand {

	public SetSingleModuleSpeed() {
		super(SingleModule.getInstance());
	}

	@Override
	public void initialize() {
		super.initialize();


		SingleModule.getInstance().getModule().setSpeed(SmartDashboard.getEntry("referenceSpeed").getNumber(-1).intValue());
	}
}
