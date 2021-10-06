package edu.greenblitz.bigRodika;

import edu.greenblitz.gblib.gears.GearDependentValue;

import java.util.HashMap;

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
                public static final double MOTOR_LIMITER = 1;
                public static final int TICKS_TO_ROTATIONS = 1024;

                public static final int[] DRIVE_MOTOR_PORTS = {4, 1, 2, 3};
                public static final int[] ROTATION_MOTOR_PORTS = {2, 5, 6, 7};


            }
            public static class SwerveModule{
                public static final GearDependentValue<Double> NORMALIZER_SRX = new GearDependentValue<>(28672.0,
                        28672.0); //TODO: check what it does
                public static final GearDependentValue<Double> NORMALIZER_SPARK = new GearDependentValue<>(2300.0 * 0.64,
                        1234.0 / 2.0);
            }
        }
        public static class Measures{
            public static final double LENGTH = 1.5;
            public static final double WIDTH = 1;
            public static final double WHEEL_DIST_FROM_CENTER = Math.sqrt(Math.pow(LENGTH, 2) + Math.pow(WIDTH, 2));
        }
    }
}
