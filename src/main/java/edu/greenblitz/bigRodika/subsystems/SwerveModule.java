package edu.greenblitz.bigRodika.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.wpi.first.wpilibj.AnalogInput;

import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.Modules.TICKS_TO_ROTATIONS;

public class SwerveModule extends GBSubsystem {

    private final CANSparkMax rotationMotor;
    private final CANSparkMax driveMotor;
    private final AnalogInput angleEncoder;
    private final SparkEncoder driveEncoder;
    private int ID;
    private boolean isDriveInverted, isRotateInverted;

    SwerveModule(int ID) { // I'm not sure how to give port numbers in init' should i just add theme to init?
        this.ID = ID;
        isDriveInverted = false;
        isRotateInverted = false;
        rotationMotor = new CANSparkMax(RobotMap.Limbo2.Chassis.Modules.ROTATION_MOTOR_PORTS[ID], CANSparkMaxLowLevel.MotorType.kBrushless);
        driveMotor = new CANSparkMax(RobotMap.Limbo2.Chassis.Modules.DRIVE_MOTOR_PORTS[ID], CANSparkMaxLowLevel.MotorType.kBrushless); // TODO: check device type (2nd arg)
        angleEncoder = new AnalogInput(3);//RobotMap.Limbo2.Chassis.Modules.LAMPREY_ANALOG_PORTS[ID]);// again, values from past code
        driveEncoder = new SparkEncoder(RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SPARK, driveMotor);
    }

    public double getNormalizedAngle() {
        return getTicks() % TICKS_TO_ROTATIONS;
    }

    public double getTicks() {
        return angleEncoder.getVoltage();
    }

    public double getDegrees() {
        return getNormalizedAngle() / TICKS_TO_ROTATIONS * 360;
    }


    public int getID() {
        return ID;
    }

    public CANSparkMax getRotationMotor() {
        return rotationMotor;
    }

    public CANSparkMax getDriveMotor() {
        return driveMotor;
    }

    public AnalogInput getAngleEncoder() {
        return angleEncoder;
    }

    public SparkEncoder getDriveEncoder() {
        return driveEncoder;
    }

    public double getLinVel() {
        return driveEncoder.getNormalizedVelocity();
    }

    public boolean isDriveInverted() {
        return isDriveInverted;
    }

    public boolean isRotateInverted() {
        return isRotateInverted;
    }

    public void invertDrive() {
        isDriveInverted = !isDriveInverted;
    }

    public void invertRotate() {
        isRotateInverted = !isRotateInverted;
    }

    public void setAsFollowerOf(CANSparkMax motor) {
        rotationMotor.follow(motor);
    }

    public void setPower(double power) {
        driveMotor.set(power);
    }

    /*public void setAngle(double destDegrees) {
        double destTicks = destDegrees * RobotMap.Limbo2.Chassis.Modules.TICKS_TO_ROTATIONS / 360;
        rotationMotor.set(ControlMode.Position, destTicks);
    }*/ // TODO: PID for angle


    public void driveInvert() {
        isDriveInverted = !isDriveInverted;
    }

    public void rotateInvert() {
        isRotateInverted = !isRotateInverted;
    }


    public void setAngle(double destDegrees) {
        double destTicks = destDegrees * TICKS_TO_ROTATIONS / 360;
        rotationMotor.set(destTicks);
    }

    public void initDefaultCommand() {
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}