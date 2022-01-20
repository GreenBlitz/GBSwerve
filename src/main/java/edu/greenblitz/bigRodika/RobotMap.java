package edu.greenblitz.bigRodika;

import edu.greenblitz.gblib.gears.GearDependentValue;
import org.greenblitz.motion.interpolation.Dataset;

import java.util.ArrayList;

public class RobotMap {

    public static class Limbo2 {

        // TODO: check values in robotmap
        public static class MathConstants {
            public static final double deltaT = Math.pow(10, -5);
        }

        public static class Joystick {
            public static final int MAIN = 0;
            public static final double MAIN_DEADZONE = 0.05;
            public static final int SIDE = 1;
            public static final double SIDE_DEADZONE = 0.05;
        }

        public static class Chassis {

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
                //      BACK_RIGHT:   2
                //      BACK_LEFT:   3
                public static final double MOTOR_LIMITER = 1;
                public static final double VOLTAGE_TO_ROTATIONS = 3.2690426340000003;
                public static final double WHEEL_PERIMETER = 0.31918581360576;
                public static final int TICKS_PER_ROTATION = 42;
                public static final double TICKS_TO_METERS = WHEEL_PERIMETER / TICKS_PER_ROTATION; // TODO: insert gear conversion in swerve module to constant
                public static final double DRIVE_GEAR_RATIO = 0.125;
                public static final double ROTATION_GEAR_RATIO = 1.0 / 6;

                public static final boolean[] DRIVE_MOTORS_REVERSED = {false, false, true, true};
                public static final boolean[] ROTATION_MOTORS_REVERSED = {false, false, false, false};
                public static final int[] DRIVE_MOTOR_PORTS = {6, 9, 8, 3};
                public static final int[] ROTATION_MOTOR_PORTS = {1, 5, 7, 4};
                public static final int[] LAMPREY_ANALOG_PORTS = {2, 3, 1, 0};

                public static final double MAX_OUTPUT_DRIVE = 1;
                public static final double MAX_OUTPUT_ANGLE = 1;
            }

            public static class SwerveModule {
                public static final double DRIVE_P = 0, DRIVE_I = 0, DRIVE_D = 0, ANGLE_P = 0, ANGLE_I = 0.000, ANGLE_D = 0.0, ANGLE_TOLERANCE = 0.01/*RAD*/;
                public static final GearDependentValue<Double> NORMALIZER_SPARK = new GearDependentValue<Double>(42.0, 2048.0); // TODO: GearDependentValues are deprecated, should be a constant value.
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

				public static final Dataset LAMPERY_TO_NEO = new Dataset(2);

				static {
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(3.0, new double[]{3745.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(5.0, new double[]{3769.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(7.0, new double[]{3795.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(9.0, new double[]{3813.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(11.0, new double[]{3838.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(13.0, new double[]{3874.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(15.0, new double[]{3901.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(17.0, new double[]{3931.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(19.0, new double[]{3959.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(21.0, new double[]{3981.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(23.0, new double[]{9.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(25.0, new double[]{10.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(27.0, new double[]{22.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(29.0, new double[]{60.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(31.0, new double[]{95.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(33.0, new double[]{133.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(35.0, new double[]{166.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(37.0, new double[]{203.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(39.0, new double[]{239.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(41.0, new double[]{281.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(43.0, new double[]{330.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(45.0, new double[]{375.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(47.0, new double[]{419.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(49.0, new double[]{469.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(51.0, new double[]{499.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(53.0, new double[]{544.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(55.0, new double[]{587.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(57.0, new double[]{618.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(59.0, new double[]{650.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(61.0, new double[]{688.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(63.0, new double[]{722.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(65.0, new double[]{747.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(67.0, new double[]{776.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(69.0, new double[]{803.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(71.0, new double[]{828.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(73.0, new double[]{850.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(75.0, new double[]{854.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(77.0, new double[]{859.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(79.0, new double[]{884.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(81.0, new double[]{906.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(83.0, new double[]{929.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(85.0, new double[]{951.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(87.0, new double[]{987.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(89.0, new double[]{1027.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(91.0, new double[]{1070.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(93.0, new double[]{1098.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(95.0, new double[]{1134.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(97.0, new double[]{1175.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(99.0, new double[]{1211.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(101.0, new double[]{1244.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(103.0, new double[]{1294.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(105.0, new double[]{1342.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(107.0, new double[]{1387.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(109.0, new double[]{1444.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(111.0, new double[]{1478.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(113.0, new double[]{1519.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(115.0, new double[]{1557.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(117.0, new double[]{1605.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(119.0, new double[]{1644.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(121.0, new double[]{1677.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(123.0, new double[]{1717.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(125.0, new double[]{1741.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(127.0, new double[]{1777.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(129.0, new double[]{1802.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(131.0, new double[]{1833.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(133.0, new double[]{1860.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(135.0, new double[]{1872.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(137.0, new double[]{1892.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(139.0, new double[]{1914.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(141.0, new double[]{1938.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(143.0, new double[]{1960.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(145.0, new double[]{1989.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(147.0, new double[]{2011.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(149.0, new double[]{2031.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(151.0, new double[]{2060.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(153.0, new double[]{2093.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(155.0, new double[]{2120.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(157.0, new double[]{2154.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(159.0, new double[]{2185.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(161.0, new double[]{2216.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(163.0, new double[]{2241.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(165.0, new double[]{2271.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(166.0, new double[]{2294.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(168.0, new double[]{2327.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(170.0, new double[]{2356.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(172.0, new double[]{2390.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(174.0, new double[]{2420.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(176.0, new double[]{2453.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(178.0, new double[]{2488.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(180.0, new double[]{2510.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(182.0, new double[]{2547.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(184.0, new double[]{2579.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(186.0, new double[]{2608.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(188.0, new double[]{2633.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(190.0, new double[]{2664.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(192.0, new double[]{2691.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(194.0, new double[]{2717.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(196.0, new double[]{2739.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(198.0, new double[]{2757.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(200.0, new double[]{2785.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(202.0, new double[]{2830.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(204.0, new double[]{2867.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(206.0, new double[]{2905.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(208.0, new double[]{2947.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(210.0, new double[]{2983.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(212.0, new double[]{3017.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(214.0, new double[]{3057.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(216.0, new double[]{3090.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(218.0, new double[]{3127.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(220.0, new double[]{3161.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(222.0, new double[]{3196.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(224.0, new double[]{3226.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(226.0, new double[]{3258.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(228.0, new double[]{3286.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(230.0, new double[]{3329.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(232.0, new double[]{3378.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(234.0, new double[]{3400.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(236.0, new double[]{3437.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(238.0, new double[]{3478.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(240.0, new double[]{3509.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(242.0, new double[]{3541.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(244.0, new double[]{3576.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(246.0, new double[]{3611.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(248.0, new double[]{3642.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(250.0, new double[]{3674.0});
					SwerveModule.LAMPERY_TO_NEO.addDatapoint(251.0, new double[]{3687.0});
				}
            }
        }

        // TODO: 06/10/2020 check real values of all constants
        public static class Measurements {
            public static final double LENGTH = 0.6; // Half the vertical distance between the modules
            public static final double WIDTH = 0.5; // Half the horizontal distance between the modules
            public static final double WHEEL_DIST_FROM_CENTER = Math.hypot(LENGTH, WIDTH);
            public static final double ALPHA = Math.atan(WIDTH / LENGTH); // General angle disposition from parallel to horizontal size of robot
            public static final double[] ALPHAS = {ALPHA, Math.PI - ALPHA, Math.PI + ALPHA, -ALPHA};

        }
    }
}

