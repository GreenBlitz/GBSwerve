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
                public static final double TICKS_TO_METERS = WHEEL_PERIMETER / 2048;


                public static final int[] DRIVE_MOTOR_PORTS = {4, 1, 2, 3};
                public static final int[] ROTATION_MOTOR_PORTS = {2, 5, 6, 7};
                public static final int[] LAMPREY_ANALOG_PORTS = {3, 0, 0, 0};

                public static final double MAX_OUTPUT_DRIVE = 1;
                public static final double MAX_OUTPUT_ANGLE = 1;
            }

            public static class SwerveModule {
                public static final double DRIVE_P = 0, DRIVE_I = 0, DRIVE_D = 0, DRIVE_FF = 0,
                        ANGLE_P = 0.5, ANGLE_I = 0, ANGLE_D = 2;
                public static final GearDependentValue<Double> NORMALIZER_SPARK = new GearDependentValue<Double>(42.0,
                        2048.0); // TODO: GearDependentValues are deprecated, should be a constant value.
                public static Dataset SPEED_TO_FF = new Dataset(2);

                static {
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.025622150692418782,new double[]{0.05});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.05784468842910955,new double[]{0.1});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.12217383172192706,new double[]{0.2});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.1868448022306453,new double[]{0.3});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.252499376683109,new double[]{0.4});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.31614872575868314,new double[]{0.5});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.38144475060222066,new double[]{0.6});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.44575686253390745,new double[]{0.7});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.5131285121903226,new double[]{0.8});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.5770942062081239,new double[]{0.9});
                    SwerveModule.SPEED_TO_FF.addDatapoint(0.6378760665940705,new double[]{1});
            }
        }
    }

    public static class Measures {
        public static final double LENGTH = 1.5;
        public static final double WIDTH = 1;
        public static final double WHEEL_DIST_FROM_CENTER = Math.sqrt(Math.pow(LENGTH, 2) + Math.pow(WIDTH, 2));
    }
}
}

