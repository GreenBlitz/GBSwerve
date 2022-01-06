package edu.greenblitz.bigRodika.subsystems;

import edu.greenblitz.bigRodika.OI;
import edu.greenblitz.bigRodika.commands.tests.singleModule.DumbSwerve;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SingleModule extends GBSubsystem {
	private SwerveModule module;
	private static SingleModule instance; //TODO: Anda: Why is it singleton here? It should not be.

	private SingleModule() {
		SmartDashboard.putNumber("referenceSpeed", -1);
		module = new SwerveModule(0);

	}

	public static SingleModule getInstance() {
		if (instance == null) {
			instance = new SingleModule();
		}
		return instance;
	}

	public void init() {
		getModule().init();
	}

	public SwerveModule getModule() {
		return module;
	}

	public void setModule(SwerveModule module) {
		this.module = module;
	}

	@Override
	public void periodic() {
		super.periodic(); //TODO: Anda: Why here and not in SwerveModule, is it so general for all possible single modules?
		SmartDashboard.putNumber("lamprey voltage", module.getEncoderValue());
		SmartDashboard.putNumber("lamprey angle", module.getAngle());
		SmartDashboard.putNumber("drive ticks", module.getDriveEncoder().getRawTicks());
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DumbSwerve(this.module, OI.getInstance().getMainJoystick()));
	}


}
