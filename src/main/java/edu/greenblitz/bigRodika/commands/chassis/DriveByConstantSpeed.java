package edu.greenblitz.bigRodika.commands.chassis;

import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.bigRodika.commands.swervemodule.OpMode;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;

public class DriveByConstantSpeed extends ChassisCommand{
	private final double speed;
	public DriveByConstantSpeed(double speed){
		this.speed = speed;
	}


	@Override
	public void initialize() {
		chassis.setModuleOpMode(OpMode.BY_PID);
		chassis.setTargets(new double[]{speed,speed,speed,speed}, chassis.getAngles());
	}

	@Override
	public void execute() {
		chassis.setTargets(new double[]{speed,speed,speed,speed}, chassis.getAngles());
	}

	@Override
	public void end(boolean interrupted) {
		chassis.resetTargets();
	}

}
