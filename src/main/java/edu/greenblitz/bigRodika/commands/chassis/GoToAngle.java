package edu.greenblitz.bigRodika.commands.chassis;

import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.bigRodika.commands.swervemodule.OpMode;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;

public class GoToAngle extends ChassisCommand{
	private final double angle;
	public GoToAngle(double angle){
		this.angle = angle;
	}


	@Override
	public void initialize() {
		chassis.setModuleOpMode(OpMode.BY_PID);
		chassis.setTargets(new double[]{0,0,0,0}, new double[]{angle,angle,angle,angle});
	}

	@Override
	public void execute() {
		chassis.setTargets(new double[]{0,0,0,0}, new double[]{angle,angle,angle,angle});
	}

	@Override
	public void end(boolean interrupted) {
		chassis.resetTargets();
	}

	@Override
	public boolean isFinished() {
		boolean isFinished = true;
		for(SwerveModule module:chassis.getSwerveModules()){
			isFinished = isFinished && module.isOnAngle();
		}
		return isFinished;
	}
}
