package edu.greenblitz.bigRodika.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.gblib.encoder.IEncoder;
import edu.greenblitz.gblib.encoder.TalonEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Turret extends GBSubsystem {
    private static final double MAX_TICKS = 16400;
    private static final double MIN_TICKS = -12400;
    private static Turret instance;
    private WPI_TalonSRX motor;
    private IEncoder encoder;
    private DigitalInput microSwitch;
    private double lastPower = 0;

    private Turret() {
        motor = new WPI_TalonSRX(RobotMap.Limbo2.Turret.MOTOR_PORT);
        motor.setInverted(RobotMap.Limbo2.Turret.IS_INVERTED);
        encoder = new TalonEncoder(RobotMap.Limbo2.Turret.NORMALIZER, motor);
        microSwitch = new DigitalInput(RobotMap.Limbo2.Turret.SWITCH_PORT);
    }

    public static void init() {
        if (instance == null) {
            instance = new Turret();
        }
    }

    public static Turret getInstance() {
        return instance;
    }

    @Override
    public void periodic() {
        super.periodic();
        if (isSwitchPressed()) {
            encoder.reset();
        }

        SmartDashboard.putNumber("Encoder", encoder.getRawTicks());
        SmartDashboard.putNumber("normEncoder", encoder.getNormalizedTicks());
        SmartDashboard.putBoolean("Switch", isSwitchPressed());

        moveTurret(lastPower);
    }

    public void moveTurret(double power) {
        if (encoder.getRawTicks() < MIN_TICKS && power < 0) {
            motor.set(0);
            return;
        }
        if (encoder.getRawTicks() > MAX_TICKS && power > 0) {
            motor.set(0);
            return;
        }
        motor.set(power);
        lastPower = power;
    }

    public double getSpeed() {
        return (encoder.getNormalizedVelocity());
    }

    public boolean isSwitchPressed() {
        return microSwitch.get();
    }

    public double getTurretLocation() {
        return encoder.getNormalizedTicks();
    }

    public double getAngleRads(){
        return 2 * Math.PI * getTurretLocation();
    }

    public double getTurretSpeed() {
        return encoder.getNormalizedVelocity();
    }

    public double getAbsoluteTurretSpeed() {
        return Math.abs(getTurretSpeed());
    }


}
