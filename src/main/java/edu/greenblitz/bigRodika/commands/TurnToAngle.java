package edu.greenblitz.bigRodika.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToAngle extends SwerveCommand {
	double angle;

	public TurnToAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public void initialize() {
		super.initialize();
		swerveModule.moveToAngleByPID(angle);

	}

	@Override
	public void end(boolean interrupted) {
		swerveModule.moveMotors(0, 0);
	}

	/*@Override
	public boolean isFinished() {
		double R = swerveModule.getRelativePosition();
		double moe = marginOfError;
		if ((angle + moe > 360 )||(angle - moe < 0)) {
			return ((R < (angle + moe) % 360) || (R > (angle - moe) % 360)) ;
		} else
			return ((R < (angle + moe)) && (R > (angle - moe))) ;
	}*/
}


