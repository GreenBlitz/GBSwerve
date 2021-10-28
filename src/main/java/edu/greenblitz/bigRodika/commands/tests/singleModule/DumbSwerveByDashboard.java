package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.subsystems.SwerveModule;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.debug.RemoteCSVTarget;

public class DumbSwerveByDashboard extends DumbSwerveByConstants {

    public DumbSwerveByDashboard(SwerveModule swerve) {
        super(swerve, SmartDashboard.getNumber(String.format("SwerveModule%dDrive", swerve.getID()), 0), SmartDashboard.getNumber(String.format("SwerveModule%dTurn", swerve.getID()), 0));
    }
}
