package edu.greenblitz.bigRodika.commands.tests.chassis;

import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;

public class ResetToZero extends GBCommand {
	private double epsilon;
	private int id;

	//TODO: Improve P config
	private final static double[] P = {0.1,0.1,0.07,0.06};

	public ResetToZero(double epsilon, int i){
		this.epsilon = epsilon;
		this.id = i;
	}

	@Override
	public void execute() {
		SwerveModule s = Chassis.getInstance().getSwerveModules()[id];
		if(s.getAngle() > 0){
			s.moveMotors(0, -getP());
		}else{
			s.moveMotors(0, getP());
		}
	}

	@Override
	public void end(boolean interrupted) {
		SwerveModule s = Chassis.getInstance().getSwerveModules()[id];
		s.moveMotors(0, 0);
	}

	@Override
	public boolean isFinished() {
		SwerveModule s = Chassis.getInstance().getSwerveModules()[id];
		return Math.abs(s.getAngle()) < epsilon;
	}

	public double getP(){
		SwerveModule s = Chassis.getInstance().getSwerveModules()[id];
		double angle = s.getAngle(); //% Math.PI;
		//Robot should use % for the angle only when near 0 or PI radians
		//TODO: Add % for the needed case
		return angle * P[id];
	}
}
