package edu.greenblitz.bigRodika.subsystems;

import edu.greenblitz.bigRodika.OI;
import edu.greenblitz.bigRodika.commands.tests.singleModule.DumbSwerve;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SingleModule extends GBSubsystem {
    private SwerveModule module;
    private static SingleModule instance;

    private SingleModule() {
        module = new SwerveModule(0);
    }

    public static SingleModule getInstance() {
        if (instance == null) {
            instance = new SingleModule();
        }
        return instance;
    }

    public SwerveModule getModule() {
        return module;
    }

    public void setModule(SwerveModule module) {
        this.module = module;
    }

    @Override
    public void periodic() {
        super.periodic();
        SmartDashboard.putNumber("lamprey voltage", module.getEncoderValue());
        SmartDashboard.putNumber("lamprey angle", module.getAngle());
    }

    public void initDefaultCommand() {
        setDefaultCommand(new DumbSwerve(this.module, OI.getInstance().getMainJoystick()));
    }


}
