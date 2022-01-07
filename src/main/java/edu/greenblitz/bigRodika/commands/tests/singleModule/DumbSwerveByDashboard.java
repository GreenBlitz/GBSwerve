package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.debug.RemoteCSVTarget;

public class DumbSwerveByDashboard extends DumbSwerveByConstants {
	
	public DumbSwerveByDashboard(SwerveModule swerve) {
		super(swerve, 0, 0);
		
		String key = String.format("SwerveModule%d", swerve.getID());
		SmartDashboard.putNumber(key + "Drive", 0);
		SmartDashboard.putNumber(key + "Turn", 0);
	}
	
	@Override
	public void initialize() {
		super.initialize();
		
		String key = String.format("SwerveModule%d", swerve.getID());
		this.drive = SmartDashboard.getNumber(key + "Drive", 0);
		this.turn = SmartDashboard.getNumber(key + "Turn", 0);
	}
}
