package edu.greenblitz.bigRodika.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.Modules.*;
import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.SwerveModule.*;

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

        configureDrive(DRIVE_P, DRIVE_I, DRIVE_D, DRIVE_FF);
    }

    public void configureDrive(double p, double i, double d, double ff) {
        CANPIDController controller = this.driveMotor.getPIDController();

        controller.setP(p);
        controller.setI(i);
        controller.setD(d);
        controller.setFF(ff);
    }

    public CANPIDController getDrivePID() {
        return this.driveMotor.getPIDController();
    }

    public void setSpeed(double speed) {
        this.driveMotor.getPIDController().setReference(speed, ControlType.kVelocity);
    }

    public double getNormalizedAngle() {
        return getEncoderValue() % VOLTAGE_TO_ROTATIONS;
    }

    public double getEncoderValue() {
        return angleEncoder.getVoltage();
    }

    public double getAngle() {
        return getNormalizedAngle() / VOLTAGE_TO_ROTATIONS * 360;
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

    public void initDefaultCommand() {
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}