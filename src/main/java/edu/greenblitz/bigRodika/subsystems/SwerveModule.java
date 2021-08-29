package edu.greenblitz.bigRodika.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.gblib.encoder.IEncoder;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.greenblitz.gblib.encoder.TalonEncoder;

public class SwerveModule extends GBSubsystem {

    private final WPI_TalonSRX m_Rotation;
    private final CANSparkMax m_Drive;
    private final IEncoder angleEncoder;
    private final SparkEncoder driveEncoder;
    private int ID;
    private boolean isDriverInverted, isRotateInverted;

    SwerveModule(int ID) { // I'm not sure how to give port numbers in init' should i just add theme to init?
        this.ID = ID;
        isDriverInverted = false;
        isRotateInverted = false;
        m_Rotation = new WPI_TalonSRX(RobotMap.Limbo2.Chassis.Modules.ROTATE_PORTS[ID]);
        m_Drive = new CANSparkMax(RobotMap.Limbo2.Chassis.Modules.DRIVE_PORTS[ID], CANSparkMaxLowLevel.MotorType.kBrushless); // TODO: check device type (2nd arg)
        angleEncoder = new TalonEncoder(RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SRX, m_Rotation);// again, values from past code
        driveEncoder = new SparkEncoder(RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SPARK, m_Drive);
    }

    public int getTicks(){ return angleEncoder.getRawTicks();}
    public int getNormalizedTicks(){ return getTicks()%1024; }

    public int getDegrees(){ return getTicks() * 360/1024;}

    public int getNormalizedDegrees(){return getNormalizedTicks() * 360/1024;}

    public int getID() {
        return ID;
    }

    public WPI_TalonSRX getM_Rotation() {
        return m_Rotation;
    }

    public CANSparkMax getM_Drive() {
        return m_Drive;
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
    
    public boolean isDriverInverted() {
        return isDriverInverted;
    }

    public boolean isRotateInverted() {
        return isRotateInverted;
    }

    public void totalInvert(){
        isDriverInverted = true;
        isRotateInverted = true;
    }

    public void driverInvert(){
        isDriverInverted = true;
    }

    public void rotatorInvert(){
        isRotateInverted = true;
    }

    public void setAsFollowerOf(double portID){
        m_Rotation.set(ControlMode.Follower, portID);
    }

    public void setPower(double power){
        m_Drive.set(power);
    }

    public void setAngle(double destDegrees){
        double destTicks = destDegrees * 1024/360;
        m_Rotation.set(ControlMode.Position, destTicks);
    }
}