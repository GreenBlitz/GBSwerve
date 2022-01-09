package edu.greenblitz.bigRodika.commands.chassis;

public abstract class ModuleAngleByPID extends ChassisCommand {
	
	@Override
	public void initialize() {
		super.initialize();
		chassis.setModuleAngleByPID();
	}
}
