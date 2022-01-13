package edu.greenblitz.bigRodika.commands.tests.chassis;

import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.greenblitz.gblib.command.GBCommand;

public class ResetToZero extends GBCommand {
	private double epsilon;
	private int id;

	public ResetToZero(double epsilon, int i){
		this.epsilon = epsilon;
		this.id = i;
	}

	//TODO: Add P
	@Override
	public void execute() {
		SwerveModule s = Chassis.getInstance().getSwerveModules()[id];
		if(s.getAngle() > 0){
			s.moveMotors(0, -0.05);
		}else{
			s.moveMotors(0, 0.05);
		}
		System.out.println(s.getAngle());
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
}
