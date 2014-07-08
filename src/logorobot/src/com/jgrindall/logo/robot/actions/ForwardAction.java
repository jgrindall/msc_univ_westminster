/**
 * ForwardAction.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 */
package com.jgrindall.logo.robot.actions;
import com.jgrindall.logo.robot.RobotException;
import lejos.nxt.Motor;

public class ForwardAction extends ARobotAction{
    protected double unitsToSeconds;
    protected int MAX_MILLISECONDS = 10000;
    public ForwardAction(double unitsToSeconds){
        super();
        this.unitsToSeconds=unitsToSeconds;
    }
    @Override
    /**
     * move forward (whichever direction the robot is facing!)
     * @param data
     */
    public void execute(Object data) throws RobotException{
        Double d = (Double)data;
        Double absD = Math.abs(d);
        int milliSeconds =(int)(absD*unitsToSeconds);
        if(milliSeconds > MAX_MILLISECONDS){
            throw new RobotException("Input to the forward command was too large.");
        }
        if(d>=0){
            Motor.A.forward();
            Motor.B.forward();
        }
        else{
            Motor.A.backward();
            Motor.B.backward();
        }
        try{
            Thread.sleep( milliSeconds );
            Motor.A.stop();
            Motor.B.stop();
        }
        catch(InterruptedException e){

        }
    }
}