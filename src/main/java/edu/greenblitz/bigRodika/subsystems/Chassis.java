package edu.greenblitz.bigRodika.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import edu.greenblitz.bigRodika.OI;
import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.bigRodika.commands.chassis.HolonomicDrive;
import edu.greenblitz.bigRodika.commands.tests.chassis.DumbHolonomic;
import edu.greenblitz.bigRodika.commands.swervemodule.OpMode;
import edu.greenblitz.gblib.gyroscope.IGyroscope;
import edu.greenblitz.gblib.gyroscope.PigeonGyro;
import org.greenblitz.motion.base.Vector2D;

import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Measurements.ALPHAS;
import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Measurements.WHEEL_DIST_FROM_CENTER;

public class Chassis extends GBSubsystem {
    private static Chassis instance;

    private final SwerveModule[] swerveModules = new SwerveModule[4];

    private final IGyroscope gyro;
	private OpMode opMode;

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
        for (int i = 0; i < 4; i++) {
            Chassis.getInstance().swerveModules[i].init();
        }
    }

    public static Chassis getInstance() {
		if (instance == null){
			init();
		}
        return instance;
    }

	public void setTargets(double[] speeds, double[] angles){
		for (int i = 0; i < swerveModules.length; i++) {
			swerveModules[i].setSpeed(speeds[i]);
			swerveModules[i].setAngle(angles[i]);
		}
	}

	public void resetTargets(){
		for (int i = 0; i < swerveModules.length; i++) {
			swerveModules[i].setSpeed(0);
			swerveModules[i].setAngle(swerveModules[i].getAngle());
		}
	}

    public void moveMotors(double[] drive, double[] rotation) {
	    if(opMode != OpMode.BY_POWER){
		    System.out.println("power is being set in non power opMode (Chassis)");
	    }
        // Using regular for so that we can change the values
        for (int i = 0; i < drive.length; i++) {
            if (drive[i] > RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                drive[i] = RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER;
            } else if (drive[i] < -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                drive[i] = -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER;
            }
        }
        moveMotorsLimited(drive, rotation);
    }

    public void moveMotors(double drive, double rotation){
        moveMotors(new double[]{drive,drive,drive,drive}, new double[]{rotation,rotation,rotation,rotation});
    }

    public void moveMotor(int id, double drive, double rotation) {


        if (drive > RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
            drive = RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER;
        } else if (drive < -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
            drive = -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER;
        }
        moveMotorLimited(id, drive, rotation);
    }

    public void moveMotorsLimited(double[] drive, double[] rotation) {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.moveMotors(drive[swerveModule.getID()], rotation[swerveModule.getID()]);
        }
    }

    public void moveMotorLimited(int id, double drive, double rotation) {
        swerveModules[id].moveMotors(drive, rotation);
    }

    public void moveDriveMotors(double[] powers) {
        // Using regular for so that we can change the values
        for (int i = 0; i < powers.length; i++) {
            if (powers[i] > RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                powers[i] = RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER;
            } else if (powers[i] < -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                powers[i] = -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER;
            }
        }
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.setDrivePower(powers[swerveModule.getID()]);
        }
    }

    public void moveRotationMotors(double[] powers) {
        for (int i = 0; i < powers.length; i++) {
            if (powers[i] > RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                powers[i] = RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER;
            } else if (powers[i] < -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER) {
                powers[i] = -RobotMap.Limbo2.Chassis.Modules.MOTOR_LIMITER;
            }
        }
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.moveMotors(0, powers[swerveModule.getID()]);
        }
    }

    public void stopMotors() {
        for (int id = 0; id < 4; id ++) {
            stopMotor(id);
        }
    }
    
    public void stopMotor(int id) {
		swerveModules[id].setOpMode(OpMode.BY_POWER);
        swerveModules[id].moveMotors(0, 0);
    }

    public void toBrake() {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.setDriveIdleMode(CANSparkMax.IdleMode.kBrake);
        }
    }

    public void toCoast() {
        for (SwerveModule swerveModule : swerveModules) {
            swerveModule.setDriveIdleMode(CANSparkMax.IdleMode.kCoast);
        }
    }

	public void setModuleOpMode(OpMode opMode) {
		for (SwerveModule module : swerveModules) {
			module.setOpMode(opMode);
		}
	}

    public void arcadeDrive(double power, double rotate) {
        double[] powers = {power + rotate, power - rotate, power - rotate, power + rotate};
        double[] angles = {0, 0, 0, 0};
        moveMotors(powers, angles);
    }

    public double[] getVelocities() {
        return new double[]{swerveModules[0].getLinVel(), swerveModules[1].getLinVel(), swerveModules[2].getLinVel(), swerveModules[3].getLinVel()};
    }

    public double[] getRates() {
        return new double[]{swerveModules[0].getLinVel(), swerveModules[1].getLinVel(), swerveModules[2].getLinVel(), swerveModules[3].getLinVel()};
    }

    public double[] getAngles() {
        return new double[]{swerveModules[0].getAngle(), swerveModules[1].getAngle(), swerveModules[2].getAngle(), swerveModules[3].getAngle()};
    }

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

    public void printAllEncoderValues() {
        for (SwerveModule modulE : swerveModules) {
            System.out.println("ID:" + modulE.getID() + "value:" + modulE.getAngleEncoderValue());
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
    }

    // TODO: check if getLinVel works
    public double getAngVelByWheels() {
        double[] wheelAngleFromCenter = ALPHAS;
        double wheelRotationAngle = 0; // beta       alpha /\ (one line above)
        double[] angleWheelToTangent = new double[4]; // gamma

        for (int i = 0; i < 4; i++) {
            wheelRotationAngle = swerveModules[i].getAngle();

            angleWheelToTangent[i] = (wheelAngleFromCenter[i]) + (Math.PI * 0.5) - wheelRotationAngle;
        } //gets all wheel angles compared to tangent in an array

        double avgTanVel = 0;
        for (int i = 0; i < 4; i++) {
            avgTanVel += 0.25 * Math.cos(angleWheelToTangent[i]) * swerveModules[i].getLinVel();
        } // calculates all tangent velocities and adds them to get the total tan vel

        return avgTanVel / WHEEL_DIST_FROM_CENTER; // tanVel/radius= angVel (in Rads)
    }

    @Override
    public void periodic() {
        super.periodic();
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
        setModuleOpMode(OpMode.BY_PID);
        for (int i = 0; i < swerveModules.length; i++) {
            SwerveModule s = swerveModules[i];
            double[] params = calculateSwerveMovement(Vx, Vy, omega, ALPHAS[i]);
            s.setSpeed(params[1]);
            s.setAngle(params[0]);
        }
    }

	public SwerveModule[] getSwerveModules(){
		return this.swerveModules;
	}

    public void initDefaultCommand() {
        setDefaultCommand(new HolonomicDrive(OI.getInstance().getMainJoystick(), true, 0.4));
    }

    public void initTestCommand() {
        setDefaultCommand(new DumbHolonomic());

    }
}
