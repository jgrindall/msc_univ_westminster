/**
 * TyredRobot.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * My robot that can perform LOGO
 */
package com.jgrindall.logo.robot;
import lejos.nxt.Motor;
import com.jgrindall.logo.robot.actions.*;
public class TyredRobot extends ARobot{
    /*
     * configuration settings
     */
    public static final int SPEED = 185;
    // convert fd 100 into "forward the right number of seconds"
    public static final double UNITS_TO_MILLISECONDS_TO_WAIT = 45;
    // convert rt 90 (right 90 deg) into the right number of seconds to wait.
    public static final double DEGREES_TO_MILLISECONDS_TO_WAIT = 10.65;

    //TODO:  load these externally rather than at compile time
    public TyredRobot(){
        super();
    }
    @Override
    protected void config(){
        super.config();
        Motor.A.setSpeed(TyredRobot.SPEED);
        Motor.B.setSpeed(TyredRobot.SPEED);
        this.turnRightAction = new TurnRightAction(TyredRobot.DEGREES_TO_MILLISECONDS_TO_WAIT);
        this.forwardAction = new ForwardAction(TyredRobot.UNITS_TO_MILLISECONDS_TO_WAIT);

    }
}
