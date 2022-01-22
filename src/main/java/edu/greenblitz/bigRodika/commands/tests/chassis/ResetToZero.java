/*
package edu.greenblitz.bigRodika.commands.tests.chassis;

import edu.greenblitz.bigRodika.commands.chassis.ChassisCommand;
import edu.greenblitz.bigRodika.commands.swervemodule.OpMode;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;
import org.greenblitz.motion.pid.PIDObject;

public class ResetToZero extends ChassisCommand {








	private double epsilon;

	//TODO: Improve P config
	private final static double[] P = {0.1, 0.1, 0.07, 0.06};
	private final static double[] I = {0.0002, 0.0002, 0.000035, 0.00003};

	public ResetToZero(double epsilon) {
		this.epsilon = epsilon;
	}

	@Override
	public void initialize() {
		for (int i = 0; i < 4; i++) {
			chassis.getSwerveModules()[i].configureRotation(P[i], I[i], 0, epsilon, epsilon);
			chassis.getSwerveModules()[i].setOpMode(OpMode.BY_PID);
			chassis.getSwerveModules()[i].setIsLamprey(true);
			chassis.getSwerveModules()[i].setAngle(0);
		}
	}

	@Override
	public void end(boolean interrupted) {
		for (int i = 0; i < 4; i++) {
			chassis.stopMotor(i);
			chassis.getSwerveModules()[i].init();
			chassis.getSwerveModules()[i].setIsLamprey(false);
		}
	}

	@Override
	public boolean isFinished() {
		boolean ret = true;
		for (int i = 0; i < 4; i++) {
			SwerveModule s = Chassis.getInstance().getSwerveModules()[i];
			ret = ret && Math.abs(s.getAngle()) < epsilon;
		}
		return ret;
	}

}
*/
