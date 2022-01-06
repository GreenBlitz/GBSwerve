package edu.greenblitz.bigRodika.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import edu.greenblitz.bigRodika.OI;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.bigRodika.commands.HolonomicDrive;
import edu.greenblitz.bigRodika.commands.SimpleHolonomicDrive;
import edu.greenblitz.bigRodika.exceptions.MotorPowerOutOfRangeException;
import edu.greenblitz.gblib.gyroscope.IGyroscope;
import edu.greenblitz.gblib.gyroscope.PigeonGyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import org.greenblitz.motion.base.Vector2D;

import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Measurements.ALPHAS;
import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Measurements.WHEEL_DIST_FROM_CENTER;

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

    public void moveMotors(double[] powers, double[] angles) throws MotorPowerOutOfRangeException {

        for (double power : powers) {
            if (power > RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER || power < -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                stopMotors();
                throw new MotorPowerOutOfRangeException();
            }
        }
        moveMotorsLimited(powers, angles);
    }

	public void moveMotor(int id, double power,double angle) throws MotorPowerOutOfRangeException {
			if (power > RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER || power < -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
				stopMotors();
				throw new MotorPowerOutOfRangeException();
			}
		moveMotorLimited(id, power, angle);

	}

    public void moveMotorsLimited(double[] powers, double[] angles) {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.setPower(powers[swerveModule.getID()]);
            swerveModule.setAngle(angles[swerveModule.getID()]);
        }
    }

	public void moveMotorLimited(int id, double power, double angle) {
		swerveModules[id].setPower(power);
		swerveModules[id].setAngle(angle);
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
            swerveModule.setAngle(powers[swerveModule.getID()]);
        }
    }

    public void stopMotors() {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.setPower(0);
        }
    }

	public void stopMotor(int id) {
		swerveModules[id].setPower(0);
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
            swerveModule.setAngle(powers[swerveModule.getID()]);
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
        moveMotors(powers, angles);
    }

    public double[] getMeters() {
        return new double[]{swerveModules[0].getDriveEncoder().getNormalizedTicks(), swerveModules[1].getDriveEncoder().getNormalizedTicks(),
                swerveModules[2].getDriveEncoder().getNormalizedTicks(), swerveModules[3].getDriveEncoder().getNormalizedTicks()};
    }

    public double[] getRates() {
        return new double[]{swerveModules[0].getLinVel(), swerveModules[1].getLinVel(), swerveModules[2].getLinVel(),
                swerveModules[3].getLinVel()};
    }

    public double[] getAngles() {
        return new double[]{swerveModules[0].getNormalizedAngle(), swerveModules[1].getNormalizedAngle(),
                swerveModules[2].getNormalizedAngle(), swerveModules[3].getNormalizedAngle()};
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

    public void printAllEncoderValues (){
        for (SwerveModule modulE :swerveModules) {
            System.out.println("ID:"+modulE.getID()+"value:"+modulE.getEncoderValue());
        }
    }

    /**
     * getLinVel calculates the lin vel of the chassis
     *
     * @return vector2D
     */
    public Vector2D getLinVel() {

        double vySum = 0;
        for (int i = 0; i < 4; i++) {
            vySum += swerveModules[i].getLinVel() * Math.cos(swerveModules[i].getAngle());
        }
        double vy = vySum / 4;
        // calculating avg velocity on y-axis
        double vxSum = 0;
        for (int i = 0; i < 4; i++) {
            vxSum += swerveModules[i].getLinVel() * -Math.sin(swerveModules[i].getAngle());
        }
        double vx = vxSum / 4;
        //calculating avg velocity on the x-axis
        return new Vector2D(vx, vy);
        //speeeeeeeeeed @TODO tal \
    }

    // TODO: check if getLinVel works
    public double getAngVelByWheels() {
        double[] wheelAngleFromCenter = ALPHAS;
        double wheelRotationAngle = 0; // beta       alpha /\ (one line above)
        double[] angleWheelToTangent = new double[4]; // gamma

        for (int i = 0; i < 4; i++) {
            wheelRotationAngle = swerveModules[i].getAngle();
            angleWheelToTangent[i] =(wheelAngleFromCenter[i])+ (Math.PI * 0.5)-wheelRotationAngle;
        } //gets all wheel angles compared to tangent in an array

        double avgTanVel = 0;
        for (int i = 0; i < 4; i++) {
            avgTanVel += 0.25 * Math.cos(angleWheelToTangent[i]) * swerveModules[i].getLinVel();
        } // calculates all tangent velocities and adds them to get the total tan vel

        return avgTanVel/WHEEL_DIST_FROM_CENTER; // tanVel/radius= angVel (in Rads)
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
        double deltaT = RobotMap.Limbo2.MathConstants.deltaT;
        double r = WHEEL_DIST_FROM_CENTER;

        //holonomic case
        double holonomicDeltaX = Vx * deltaT;
        double holonomicDeltaY = Vy * deltaT;

        //rotation case
        double deltaTheta = omega * deltaT;
        double rotationDeltaX = r * (Math.cos(deltaTheta + alpha) - Math.cos(alpha));
        double rotationDeltaY = r * (Math.sin(deltaTheta + alpha) - Math.sin(alpha));

        //combined case
        double combinedDeltaX = holonomicDeltaX + rotationDeltaX;
        double combinedDeltaY = holonomicDeltaY + rotationDeltaY;
        double theta = Math.atan(combinedDeltaY / combinedDeltaX);
        double wheelVelocity = (Math.hypot(combinedDeltaX, combinedDeltaY)) / (deltaT);

        return new double[]{theta, wheelVelocity};
    }

    public void fullSwerve(double Vx, double Vy, double omega) {
        for (int i = 0; i < swerveModules.length; i++) {
            SwerveModule s = swerveModules[i];
            double[] params = calculateSwerveMovement(Vx, Vy, omega, ALPHAS[i]);
            s.setSpeed(params[1]);
            s.setAngle(params[0]);
        }
    }

	public void initDefaultCommand() {
		setDefaultCommand(new HolonomicDrive(OI.getInstance().getMainJoystick(), true, 0.4));
	}

}
