package edu.greenblitz.bigRodika.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.bigRodika.exceptions.MotorPowerOutOfRangeException;
import edu.greenblitz.gblib.gyroscope.IGyroscope;
import edu.greenblitz.gblib.gyroscope.PigeonGyro;


public class Chassis extends GBSubsystem {
    private static Chassis instance;

    private final SwerveModule[] swerveModules = new SwerveModule[4];

    private final IGyroscope gyro;
//    private PowerDistributionPanel robotPDP;

    private Chassis() {
        SwerveModule frontRight = new SwerveModule(RobotMap.Limbo2.Chassis.Motor.FRONT_RIGHT.ROTATE_PORT,
                RobotMap.Limbo2.Chassis.Motor.FRONT_RIGHT.DRIVE_PORT, RobotMap.Limbo2.Chassis.Motor.FRONT_RIGHT.ID);
        swerveModules[frontRight.getID()] = frontRight;
        SwerveModule frontLeft = new SwerveModule(RobotMap.Limbo2.Chassis.Motor.FRONT_LEFT.ROTATE_PORT,
                RobotMap.Limbo2.Chassis.Motor.FRONT_LEFT.DRIVE_PORT, RobotMap.Limbo2.Chassis.Motor.FRONT_LEFT.ID);
        swerveModules[frontLeft.getID()] = frontLeft;
        SwerveModule backLeft = new SwerveModule(RobotMap.Limbo2.Chassis.Motor.BACK_LEFT.ROTATE_PORT,
                RobotMap.Limbo2.Chassis.Motor.BACK_LEFT.DRIVE_PORT, RobotMap.Limbo2.Chassis.Motor.BACK_LEFT.ID);
        swerveModules[backLeft.getID()] = backLeft;
        SwerveModule backRight = new SwerveModule(RobotMap.Limbo2.Chassis.Motor.BACK_RIGHT.ROTATE_PORT,
                RobotMap.Limbo2.Chassis.Motor.BACK_RIGHT.DRIVE_PORT, RobotMap.Limbo2.Chassis.Motor.BACK_RIGHT.ID);
        swerveModules[backRight.getID()] = backRight;

        gyro = new PigeonGyro(new PigeonIMU(RobotMap.Limbo2.Chassis.Pigeon.PIGEON_DEVICE_NUMBER));
        gyro.reset();
//        gyroscope.inverse();
    }

    public static void init() {
        if (instance == null) {
            instance = new Chassis();
//            instance.setDefaultCommand();
        }
    }

    public static Chassis getInstance() {
        return instance;
    }

    public void moveMotors(double[] powers, double[] angles) throws MotorPowerOutOfRangeException {
        for (double power : powers){
            if (power > RobotMap.Limbo2.Chassis.Motor.MOTOR_LIMITER || power < -RobotMap.Limbo2.Chassis.Motor.MOTOR_LIMITER){
                stopMotors();
                throw new MotorPowerOutOfRangeException();
            }
        }
        moveMotorsLimited(powers, angles);
    }

    public void moveMotorsLimited(double[] powers, double[] angles) {
        for (SwerveModule swerveModule : swerveModules){
            swerveModule.setPower(powers[swerveModule.getID()]);
            swerveModule.setAngle(angles[swerveModule.getID()]);
        }
    }

    public void stopMotors(){
        for (SwerveModule swerveModule : swerveModules){
            swerveModule.setPower(0);
        }
    }

    public void toBrake() {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.getM_Drive().setIdleMode(CANSparkMax.IdleMode.kBrake);
        }
    }

    public void toCoast() {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.getM_Drive().setIdleMode(CANSparkMax.IdleMode.kCoast);
        }
    }

    public void arcadeDrive(double power, double rotate) throws MotorPowerOutOfRangeException {
        double[] powers = {power + rotate, power - rotate, power - rotate, power + rotate};
        double[] angles = {0, 0, 0, 0};
        moveMotors(powers, angles);
    }

    public double[] getMeters() {
        return new double[]{swerveModules[0].getDriveEncoder().getNormalizedTicks(), swerveModules[1].getDriveEncoder().getNormalizedTicks(),
                swerveModules[2].getDriveEncoder().getNormalizedTicks(), swerveModules[3].getDriveEncoder().getNormalizedTicks()};
    }

    public double[] getRates() {
        return new double[]{swerveModules[0].getDriveEncoder().getNormalizedVelocity(), swerveModules[1].getDriveEncoder().getNormalizedVelocity(),
                swerveModules[2].getDriveEncoder().getNormalizedVelocity(), swerveModules[3].getDriveEncoder().getNormalizedVelocity()};
    }

// TODO: 04/10/2020
//    public double getLinearVelocity() {}
//    public double getAngularVelocityByWheels() {}

    public double getAngle() {
        return gyro.getNormalizedYaw();
    }

    public double getRawAngle() {
        return gyro.getRawYaw();
    }

    public double getAngularVelocityByGyro() {
        return gyro.getYawRate();
    }

    public void resetGyro() {
        gyro.reset();
    }

    public double[] getWheelDistance() {
        return new double[] {RobotMap.Limbo2.Chassis.Sizes.WHEEL_DIST_WIDTH, RobotMap.Limbo2.Chassis.Sizes.WHEEL_DIST_LENGTH};
        // returning double array with distance between
    }

    @Override
    public void periodic() {
        super.periodic();
    }

    public void resetEncoders() {
        for (SwerveModule swerveModule : swerveModules){
            swerveModule.getAngleEncoder().reset();
        }
    }

}
