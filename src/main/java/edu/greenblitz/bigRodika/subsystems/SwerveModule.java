package edu.greenblitz.bigRodika.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANAnalog;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.gblib.encoder.IEncoder;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.greenblitz.gblib.encoder.TalonEncoder;
import edu.greenblitz.bigRodika.commands.tests.singleModule.OneModuleTestByJoystick;
import edu.greenblitz.gblib.hid.SmartJoystick;

import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.Modules.*;

public class SwerveModule extends GBSubsystem {

    private final CANSparkMax rotationMotor;
    private final CANSparkMax driveMotor;
    private final CANAnalog angleEncoder;
    private final SparkEncoder driveEncoder;
    private int ID;
    private boolean isDriveInverted, isRotateInverted;

    public SwerveModule(int ID) { // I'm not sure how to give port numbers in init' should i just add theme to init?
        this.ID = ID;
        isDriveInverted = false;
        isRotateInverted = false;
        rotationMotor = new CANSparkMax(ROTATION_MOTOR_PORTS[ID], CANSparkMaxLowLevel.MotorType.kBrushless);
        driveMotor = new CANSparkMax(DRIVE_MOTOR_PORTS[ID], CANSparkMaxLowLevel.MotorType.kBrushless); // TODO: check device type (2nd arg)
        angleEncoder = new CANAnalog(rotationMotor, CANAnalog.AnalogMode.kAbsolute);// again, values from past code
        driveEncoder = new SparkEncoder(RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SPARK, driveMotor);
    }

    public double getTicks() {
        return angleEncoder.getVoltage();
    }

    public double getNormalizedTicks() {
        return getTicks() % TICKS_TO_ROTATIONS;
    }

    public double getDegrees() {
        return getTicks() * 360 / TICKS_TO_ROTATIONS;
    }

    public double getNormalizedDegrees() {
        return getNormalizedTicks() * 360 / TICKS_TO_ROTATIONS;
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

    public CANAnalog getAngleEncoder() {
        return angleEncoder;
    }

    public SparkEncoder getDriveEncoder() {
        return driveEncoder;
    }

    public double getLinVel(){
        return driveEncoder.getNormalizedVelocity();
    }

    public double getAngVel(){
        return angleEncoder.getVelocity();
    }

    public boolean isDriveInverted() {
        return isDriveInverted;
    }

    public boolean isRotateInverted() {
        return isRotateInverted;
    }

    public void driveInvert(){
        isDriveInverted = !isDriveInverted;
    }

    public void rotateInvert(){
        isRotateInverted = !isRotateInverted;
    }

    public void setAsFollowerOf(CANSparkMax motor){
        rotationMotor.follow(motor);
    }

    public void setPower(double power){
        driveMotor.set(power);
    }

    public void setAngle(double destDegrees){
        double destTicks = destDegrees * TICKS_TO_ROTATIONS/360;
        rotationMotor.set(destTicks);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new OneModuleTestByJoystick(new SmartJoystick(RobotMap.Limbo2.Joystick.MAIN,
                RobotMap.Limbo2.Joystick.MAIN_DEADZONE), this));
    }
}