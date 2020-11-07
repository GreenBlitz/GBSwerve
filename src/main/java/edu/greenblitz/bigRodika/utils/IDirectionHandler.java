package edu.greenblitz.bigRodika.utils;

/**
 * @author Orel
 */

public interface IDirectionHandler {
    /**\
     *
     * @param angle - the angle of the turn in degrees as long as not mentioned otherwise
     * @param linVel - the linear velocity of the robot *0 treated like positive vel
     * @param args - more arguments
     * @return - the absolute direction of the recommended linear vel. true for absolute forwards ()
     */
    boolean handle(double angle, double linVel, double ... args);
}
