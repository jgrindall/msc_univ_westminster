/**
 * ARobot.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * base class for any robot that can perform LOGO
 */
package com.jgrindall.logo.robot;

import com.jgrindall.logo.robot.actions.ARobotAction;
import java.io.Serializable;

public abstract class ARobot implements Serializable{
    protected ARobotAction forwardAction;
    protected ARobotAction turnRightAction;
    public ARobot(){
         config();
    }

    // each robot has a general purpose configuration method
    protected void config(){
        
    }

    public void goForward(double distance) throws RobotException{
        if(forwardAction!=null){
            forwardAction.execute(distance);
        }
    }

    public void turnRight(double angle) throws RobotException{
        if(turnRightAction!=null){
            turnRightAction.execute(angle);
        }
    }
}
