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
            public static final double WHEEL_DIST = 0.622;

            public static class Motor {
                public static class FRONT_LEFT {
                    public static final int ROTATE_PORT = 1 ;
                    public static final int DRIVE_PORT = 1;
                    public static final int ID = 0;
                }
                public static class FRONT_RIGHT {
                    public static final int ROTATE_PORT = 1;
                    public static final int DRIVE_PORT = 1;
                    public static final int ID = 1;
                }
                public static class BACK_LEFT {
                    public static final int ROTATE_PORT = 1;
                    public static final int DRIVE_PORT = 1;
                    public static final int ID = 2;
                }
                public static class BACK_RIGHT {
                    public static final int ROTATE_PORT = 1;
                    public static final int DRIVE_PORT = 1;
                    public static final int ID = 3;
                }

            }
            public static class SwerveModule{
                public static final GearDependentValue<Double> NORMALIZER = new GearDependentValue<>(28672.0,
                        28672.0); //TODO: check what it does
            }
        }
    }
}