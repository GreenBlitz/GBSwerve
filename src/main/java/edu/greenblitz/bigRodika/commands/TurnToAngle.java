package edu.greenblitz.bigRodika.commands;

public class TurnToAngle extends SwerveCommand {
	double angle;
	double marginOfError = 5;

	public TurnToAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute() {

			swerveModule.moveMotors(0, 0.08);

	}


	@Override
	public void end(boolean interrupted) {
		swerveModule.moveMotors(0,0);
	}

	@Override
	public boolean isFinished() {
		if((swerveModule.getRelativePosition()-marginOfError) % 360 < angle && (swerveModule.getRelativePosition()+marginOfError)%360 > angle){
			return true;
		}
		return false;
	}
}


