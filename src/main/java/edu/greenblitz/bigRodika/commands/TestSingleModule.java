package edu.greenblitz.bigRodika.commands;

import edu.greenblitz.bigRodika.exceptions.MotorPowerOutOfRangeException;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.gblib.command.GBCommand;

public class TestSingleModule extends GBCommand {
	private int id;
	private double power;
	private double angle;
	private double deadline;

	public TestSingleModule(int id, double power, double angle, double waitTime) {
		this.id = id;
		this.power = power;
		this.angle = angle;
		this.deadline = System.currentTimeMillis() / 1000.0 + waitTime;
	}

	public TestSingleModule(int id, double power, double angle) {
		this(id, power, angle, 3);
	}

	@Override
	public void initialize() {
		try {
			Chassis.getInstance().moveMotor(id, power, angle);
		} catch (MotorPowerOutOfRangeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute() {
		try {
			Chassis.getInstance().moveMotor(id, power, angle);
		} catch (MotorPowerOutOfRangeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void end(boolean interrupted) {
		Chassis.getInstance().stopMotor(id);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
