package edu.greenblitz.bigRodika.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import edu.greenblitz.bigRodika.Robot;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.bigRodika.commands.tests.singleModule.GraphEncoderVoltage;
import edu.greenblitz.bigRodika.exceptions.MotorPowerOutOfRangeException;
import edu.greenblitz.gblib.gyroscope.IGyroscope;
import edu.greenblitz.gblib.gyroscope.PigeonGyro;

public class Chassis extends GBSubsystem {
    private static Chassis instance;

    private final SwerveModule[] swerveModules = new SwerveModule[4];

    private final IGyroscope gyro;

    private Chassis() {

        for (int i = 0; i < 4; i++) {
            swerveModules[i] = new SwerveModule(i);
        }

        gyro = new PigeonGyro(new PigeonIMU(RobotMap.Limbo2.Chassis.Pigeon.PIGEON_DEVICE_NUMBER));
        gyro.reset();
//        gyroscope.inverse();
    }

    public static void init() {
        if (instance == null) {
            instance = new Chassis();
        }
    }

    public static Chassis getInstance() {
        return instance;
    }

    public void moveMotors(double[] powers, double[] angles, boolean fieldOriented) throws MotorPowerOutOfRangeException {
        if (fieldOriented) {
            for (int i = 0; i < angles.length; i++) {
                // TODO: 14/10/2020 check clockwise = positive in gyro
//                angles[i] = angles[i] - getAngle();
            }
        }
        for (double power : powers) {
            if (power > RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER || power < -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                stopMotors();
                throw new MotorPowerOutOfRangeException();
            }
        }
        moveMotorsLimited(powers, angles);
    }

    public void moveMotorsLimited(double[] powers, double[] angles) {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.setPower(powers[swerveModule.getID()]);
//            swerveModule.setAngle(angles[swerveModule.getID()]); TODO: fix setAngle and uncomment this
        }
    }

    public void moveDriveMotors(double[] powers) throws MotorPowerOutOfRangeException {
        for (double power : powers) {
            if (power > RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER || power < -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                stopMotors();
                throw new MotorPowerOutOfRangeException();
            }
        }
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.setPower(powers[swerveModule.getID()]);
        }
    }

    public void moveRotationMotors(double[] powers) throws MotorPowerOutOfRangeException {
        for (double power : powers) {
            if (power > RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER || power < -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                stopMotors();
                throw new MotorPowerOutOfRangeException();
            }
        }
        for (SwerveModule swerveModule : swerveModules) {
//            swerveModule.setAngle(powers[swerveModule.getID()]); TODO: fix setAngle and uncomment this
        }
    }

    public void stopMotors() {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.setPower(0);
        }
    }

    public void rotateWheelsBySpeedAcceleration(double[] speeds, double[] accelerations) throws MotorPowerOutOfRangeException {
        double[] powers = new double[speeds.length];
        for (int i = 0; i < speeds.length; i++) {
            powers[i] = speeds[i] * RobotMap.Limbo2.Chassis.MiniCIM.ROTATION_KV + accelerations[i] * RobotMap.Limbo2.Chassis.MiniCIM.ROTATION_KA;
        }
        for (double power : powers) {
            if (power > RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER || power < -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                stopMotors();
                throw new MotorPowerOutOfRangeException();
            }
        }
        for (SwerveModule swerveModule : swerveModules) {
//            swerveModule.setAngle(powers[swerveModule.getID()]); TODO: fix setAngle and uncomment this
        }
    }

    public void toBrake() {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.getDriveMotor().setIdleMode(CANSparkMax.IdleMode.kBrake);
        }
    }

    public void toCoast() {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.getDriveMotor().setIdleMode(CANSparkMax.IdleMode.kCoast);
        }
    }

    public void arcadeDrive(double power, double rotate) throws MotorPowerOutOfRangeException {
        double[] powers = {power + rotate, power - rotate, power - rotate, power + rotate};
        double[] angles = {0, 0, 0, 0};
        moveMotors(powers, angles, true);
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
        return new double[]{RobotMap.Limbo2.Chassis.Sizes.WHEEL_DIST_WIDTH, RobotMap.Limbo2.Chassis.Sizes.WHEEL_DIST_LENGTH};
        // returning double array with distance between
    }

    @Override
    public void periodic() {
        super.periodic();
//        putString("Module 0", swerveModules[0].toString());
//        putString("Module 1", swerveModules[1].toString());
//        putString("Module 2", swerveModules[2].toString());
//        putString("Module 3", swerveModules[3].toString());
//        putNumber("Gyro Rate", gyro.getYawRate()    );
//        putNumber("Raw Gyro", gyro.getRawYaw());
//        putNumber("Normalized Gyro", gyro.getNormalizedYaw());

    }

/*
    public void resetEncoders() {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.getAngleEncoder().reset();
        }
    }
*/

    public double[] calculateSwerveMovement(double Vx, double Vy, double omega, double alpha) {
        /**
            transforms Swerve motion to certain module movement.

            @param Vx: the velocity the robot will move in the x axis
            @param Vy: the velocity the robot will move in the y axis
            @param omega: the robot's rotational velocity
            @param alpha: the module's angle in relation to the horizontal axis of the robot

            @returns: double[] {angle to set the module at, velocity to set the module at}
         */
        //insert calculations here
        return new double[]{};
    }

    public void fullSwerve(double Vx, double Vy, double omega) {
        for(int i = 0; i < swerveModules.length; i++) {
            SwerveModule s = swerveModules[i];
            double[] params = calculateSwerveMovement(Vx, Vy, omega, RobotMap.Limbo2.Measures.ALPHAS[i]);
            s.setSpeed(params[1]);
            s.set(params[1]);
        }
    }

}
