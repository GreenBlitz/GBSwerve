package edu.greenblitz.bigRodika.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.gblib.encoder.IEncoder;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.greenblitz.gblib.encoder.TalonEncoder;

import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.Modules.*;

public class SwerveModule extends GBSubsystem {

    private final WPI_TalonSRX rotationMotor;
    private final CANSparkMax driveMotor;
    private final IEncoder angleEncoder;
    private final SparkEncoder driveEncoder;
    private int ID;
    private boolean isDriveInverted, isRotateInverted;

    public SwerveModule(int ID) { // I'm not sure how to give port numbers in init' should i just add theme to init?
        this.ID = ID;
        isDriveInverted = false;
        isRotateInverted = false;
        rotationMotor = new WPI_TalonSRX(ROTATION_MOTOR_PORTS[ID]);
        driveMotor = new CANSparkMax(DRIVE_MOTOR_PORTS[ID], CANSparkMaxLowLevel.MotorType.kBrushless); // TODO: check device type (2nd arg)
        angleEncoder = new TalonEncoder(RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SRX, rotationMotor);// again, values from past code
        driveEncoder = new SparkEncoder(RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SPARK, driveMotor);
    }

    public int getTicks() {
        return angleEncoder.getRawTicks();
    }

    public int getNormalizedTicks() {
        return getTicks() % TICKS_TO_ROTATIONS;
    }

    public int getDegrees() {
        return getTicks() * 360 / TICKS_TO_ROTATIONS;
    }

    public int getNormalizedDegrees() {
        return getNormalizedTicks() * 360 / TICKS_TO_ROTATIONS;
    }

    public int getID() {
        return ID;
    }

    public WPI_TalonSRX getRotationMotor() {
        return rotationMotor;
    }

    public CANSparkMax getDriveMotor() {
        return driveMotor;
    }

    public IEncoder getAngleEncoder() {
        return angleEncoder;
    }

    public SparkEncoder getDriveEncoder() {
        return driveEncoder;
    }

    public double getLinVel(){
        return driveEncoder.getNormalizedVelocity();
    }

    public double getAngVel(){
        return angleEncoder.getNormalizedVelocity();
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

    public void setAsFollowerOf(TalonSRX motor){
        rotationMotor.follow(motor);
    }

    public void setPower(double power){
        driveMotor.set(power);
    }

    public void setAngle(double destDegrees){
        double destTicks = destDegrees * TICKS_TO_ROTATIONS/360;
        rotationMotor.set(ControlMode.Position, destTicks);
    }
}
