package edu.greenblitz.bigRodika.commands.chassis;

import edu.greenblitz.bigRodika.RobotMap;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.gblib.command.GBCommand;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.State;
import org.greenblitz.motion.base.Vector2D;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.profiling.ChassisProfiler2D;
import org.greenblitz.motion.profiling.MotionProfile2D;
import org.greenblitz.motion.profiling.followers.PidFollower2D;

import java.util.ArrayList;
import java.util.List;

public class GoFetch extends GBCommand {
    private final double MAX_LIN_V = 0.5, MAX_ANG_V = 0.25, MAX_LIN_A = 0.5, MAX_ANG_A = 0.125, JMP = 0.0001;
    private Point target; // should be gotten by the network tables
    private MotionProfile2D profile2D;
    private PidFollower2D follower2D;
    private double power;
    private double angle;
    private Vector2D v;
    PIDObject collapse, ang;

    public GoFetch(Point target, double angle) {
        super(Chassis.getInstance());
        power = 0.4;
        this.target = target;
        this.angle = angle;
        collapse = new PIDObject(0,0,0);
        ang = new PIDObject(0,0,0);
        List<State> locations = new ArrayList<>();
        locations.add(new State(0, 0, 0));
        locations.add(new State(target.getX(), target.getY(), angle, 0, 0));
        profile2D = ChassisProfiler2D.generateProfile(locations, JMP,
                RobotMap.BigRodika.Chassis.MotionData.POWER.get("0.4"), 0, 1, 800);
        follower2D = new PidFollower2D(1,1,1,1, collapse,0.0,0.0,ang,0.0,  RobotMap.BigRodika.Chassis.WHEEL_DIST, profile2D);
    }

    @Override
    public void initialize() {

        follower2D.init();
    }


    @Override
        public void execute() {
        Vector2D v = follower2D.run(Chassis.getInstance().getLeftRate(), Chassis.getInstance().getRightRate(),Chassis.getInstance().getAngularVelocityByWheels());
        Chassis.getInstance().moveMotors(v.getX(), v.getY());

    }

    @Override
    public boolean isFinished() {
        System.out.println(target.toString() +","+angle);
        System.out.println(Chassis.getInstance().getLeftMeters() + "," + Chassis.getInstance().getRightMeters() + "," + Chassis.getInstance().getAngle());
        return follower2D.isFinished();
    }

    private double clamp(double inp){
        return Math.copySign(Math.min(Math.abs(inp), 1.0), inp) * power;
    }
}
