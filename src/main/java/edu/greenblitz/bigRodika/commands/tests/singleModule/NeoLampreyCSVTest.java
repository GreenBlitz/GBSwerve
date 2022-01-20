package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.commands.chassis.ChassisCommand;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.gblib.command.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NeoLampreyCSVTest extends GBCommand {
	@Override
	public void execute() {
		SmartDashboard.putNumber("", SingleModule.getInstance().getModule().getAngle());
	}
}
