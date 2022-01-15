package edu.greenblitz.bigRodika.commands.tests.chassis;

import edu.greenblitz.bigRodika.commands.chassis.ChassisCommand;
import edu.greenblitz.bigRodika.commands.swervemodule.OpMode;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;
import org.greenblitz.motion.pid.PIDObject;

public class ResetToZero extends ChassisCommand {
	private double epsilon;
	private int id;

	//TODO: Improve P config
	private final static double[] P = {0.1,0.1,0.07,0.06};

	public ResetToZero(double epsilon, int i){
		this.epsilon = epsilon;
		this.id = i;
	}

	@Override
	public void initialize() {
		chassis.getSwerveModules()[id].configureRotation(P[id], 0,0,0,0);
		chassis.getSwerveModules()[id].setOpMode(OpMode.BY_PID);
		chassis.getSwerveModules()[id].setIsLamprey(true);
		chassis.getSwerveModules()[id].setAngle(0);

	}

	@Override
	public void end(boolean interrupted) {
		chassis.stopMotor(id);
		chassis.getSwerveModules()[id].init();
		chassis.getSwerveModules()[id].setIsLamprey(false);
	}

	@Override
	public boolean isFinished() {
		SwerveModule s = Chassis.getInstance().getSwerveModules()[id];
		return Math.abs(s.getAngle()) < epsilon;
	}

}
