package edu.greenblitz.bigRodika;

import edu.greenblitz.gblib.gears.GearDependentValue;
import org.greenblitz.motion.interpolation.Dataset;


public class RobotMap {

    public static class Limbo2 {

        // TODO: check values in robotmap


        public static class Joystick {
            public static final int MAIN = 0;
            public static final double MAIN_DEADZONE = 0.05;
            public static final int SIDE = 1;
            public static final double SIDE_DEADZONE = 0.05;
        }


        public static class Chassis {

            // TODO: 06/10/2020 check real values of all constants
            public static class Sizes {
                public static final double WHEEL_DIST_WIDTH = 0;
                public static final double WHEEL_DIST_LENGTH = 0;
            }

            public static class MiniCIM {
                public static final double DRIVE_KV = 1;
                public static final double DRIVE_KA = 1;
                public static final double ROTATION_KV = 1;
                public static final double ROTATION_KA = 1;
            }

            public static class Spark {
                public static final double DRIVE_KV = 1;
                public static final double DRIVE_KA = 1;
                public static final double ROTATION_KV = 1;
                public static final double ROTATION_KA = 1;
            }

            public static final double MAX_LINEAR_VELOCITY = 3;
            public static final double MAX_ANGULAR_VELOCITY = 3;

            public static class Pigeon {
                public static final int PIGEON_DEVICE_NUMBER = 0; //random number
            }

            public static class Modules {
                // IDs:
                //      FRONT_RIGHT: 0
                //      FRONT_LEFT:  1
                //      BACK_LEFT:   2
                //      BACK_LEFT:   3
                public static final double MOTOR_LIMITER = 1;
                public static final double VOLTAGE_TO_ROTATIONS = 3.2690426340000003;
                public static final double WHEEL_PERIMETER = 0.31918581360576;
                public static final double TICKS_PER_ROTATION = 42;
                public static final double TICKS_TO_METERS = WHEEL_PERIMETER / TICKS_PER_ROTATION; // TODO: insert gear conversion in swerve module to constant
                public static final double DRIVE_GEAR_RATIO = 0.125;
                public static final double ROTATION_GEAR_RATIO = 1.0 / 6;


                public static final int[] DRIVE_MOTOR_PORTS = {4, 1, 2, 3};
                public static final int[] ROTATION_MOTOR_PORTS = {2, 5, 6, 7};
                public static final int[] LAMPREY_ANALOG_PORTS = {3, 0, 0, 0};

                public static final double MAX_OUTPUT_DRIVE = 1;
                public static final double MAX_OUTPUT_ANGLE = 1;
            }

            public static class SwerveModule {
                public static final double DRIVE_P = 0, DRIVE_I = 0, DRIVE_D = 0,
                        ANGLE_P = 0.5, ANGLE_I = 0, ANGLE_D = 2;
                public static final GearDependentValue<Double> NORMALIZER_SPARK = new GearDependentValue<Double>(42.0,
                        2048.0); // TODO: GearDependentValues are deprecated, should be a constant value.
                public static Dataset SPEED_TO_FF = new Dataset(2);

                static {
                    SwerveModule.SPEED_TO_FF.addDatapoint(-0.000000000000001, new double[]{0.0});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.15874853129485736, new double[]{0.05});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.36219983433057434, new double[]{0.1});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.7855288332582557, new double[]{0.2});
                    SwerveModule.SPEED_TO_FF.addDatapoint(1.1745280432855925, new double[]{0.3});
                    SwerveModule.SPEED_TO_FF.addDatapoint(1.5679483142460156, new double[]{0.4});
                    SwerveModule.SPEED_TO_FF.addDatapoint(2.018132357679125, new double[]{0.5});
                    SwerveModule.SPEED_TO_FF.addDatapoint(2.4253292179198764, new double[]{0.6});
                    SwerveModule.SPEED_TO_FF.addDatapoint(2.841689838182813, new double[]{0.7});
                    SwerveModule.SPEED_TO_FF.addDatapoint(3.153622487669066, new double[]{0.8});
                    SwerveModule.SPEED_TO_FF.addDatapoint(3.6464959043498486, new double[]{0.9});
                    SwerveModule.SPEED_TO_FF.addDatapoint(4.049262404948307, new double[]{1});
                }
            }
        }

        public static class Measures {
            public static final double LENGTH = 1.5; // Half the vertical distance between the modules
            public static final double WIDTH = 1; // Half the horizontal distance between the modules
            public static final double WHEEL_DIST_FROM_CENTER = Math.sqrt(Math.pow(LENGTH, 2) + Math.pow(WIDTH, 2));
            public static final double ALPHA = Math.atan(LENGTH / WIDTH); // General angle disposition from parallel to horizontal size of robot
            public static final double[] ALPHAS = {ALPHA, Math.PI - ALPHA, Math.PI + ALPHA, -ALPHA};

            public static double getRealdeg(double deg) {
                double realDegs = deg % 360; //Didnt add what happans if deg is negetive, pls fix
                return realDegs;
            }
            public static double getRealRad(double rad) {
                double realRad = rad% (2*Math.PI); //Didnt add what happans if deg is negetive, pls fix
                return realRad;
            }

            public static double degToRadians(double deg) {
                double degInRadians = deg / 360 * 2 * Math.PI;
                return degInRadians;
            }

            public static double radiansToDeg(double rad) {
                double RadiansInDeg = rad / (2 * Math.PI) * 360;
                double realDegs = RadiansInDeg % 360;
                return RadiansInDeg;
            }

            public static int quarteredInPlainByDegPostiveVaule(double deg) {// Put here only real values Of Deg
                int quarter = 0;
                if (0 < deg && deg < 90) {
                    quarter = 1;
                    return quarter;
                }
                if (90 < deg && deg < 180) {
                    quarter = 2;
                    return quarter;
                }
                if (180 < deg && deg < 270) {
                    quarter = 3;
                    return quarter;
                }
                if (270 < deg && deg < 360) {
                    quarter = 4;
                    return quarter;
                }
                else {
                    return -1;
                }

            }

            public static int quarteredInPlainByRadPostiveVaule(double rad) {// Put here only real values Of Rad

                int quarter = 0;
                if (0 < rad && rad < Math.PI/2) {
                    quarter = 1;
                    return quarter;
                }
                if (Math.PI/2< rad && rad < Math.PI) {
                    quarter = 2;
                    return quarter;
                }
                if (Math.PI < rad && rad < Math.PI*1.5) {
                    quarter = 3;
                    return quarter;
                }
                if ( Math.PI*1.5< rad && rad < Math.PI*2) {
                    quarter = 4;
                    return quarter;
                }
                else{
                    return -1;
                }
            }
                public static double getCurrnetYPostionAddChangeWithDeg(double currnetYpostionInDeg, double addedDeg){ //Returns Change of Y postion after change in deg
                    double currnetYPostionInDeg = WHEEL_DIST_FROM_CENTER*Math.sin(currnetYpostionInDeg+addedDeg);
                    return currnetYPostionInDeg;
                }
            public static double getCurrnetYPostionAddChangeWithRad(double currnetYpostionInRad, double addedrad){ //Returns Change of Y postion after Change in rad
                //double currnetYPostionInRad = WHEEL_DIST_FROM_CENTER*Math.sin(currnetYpostionInDeg+addedDeg);
                //return currnetYPostionInRad;
                return 0;
            }



        }


    }
}

