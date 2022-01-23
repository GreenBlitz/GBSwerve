package edu.greenblitz.bigRodika.commands;

import edu.greenblitz.bigRodika.commands.chassis.ChassisCommand;
import edu.greenblitz.bigRodika.subsystems.SwerveModule;

public class Rotate extends ChassisCommand {
    private double power;

    public Rotate(double power){
        this.power = power;
    }

    @Override
    public void initialize() {
        double currAngle = 0;
        SwerveModule[] modules = chassis.getSwerveModules();
        for(SwerveModule s : modules){
            s.setAngle(currAngle);
            currAngle += Math.PI / 2;
        }
    }

    @Override
    public void execute() {
        SwerveModule[] modules = chassis.getSwerveModules();
        for(SwerveModule s : modules){
            s.setDrivePower(power);
        }
    }

    @Override
    public void end(boolean interrupted) {
        chassis.stopMotors();
    }
}
