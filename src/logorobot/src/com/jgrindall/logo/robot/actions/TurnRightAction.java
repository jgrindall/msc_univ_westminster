/**
 * TurnRightAction.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 */
package com.jgrindall.logo.robot.actions;
import lejos.nxt.Motor;
import com.jgrindall.logo.robot.RobotException;

public class TurnRightAction extends ARobotAction{
    protected double degreesToSeconds;
    protected int MAX_MILLISECONDS = 10000;
    public TurnRightAction(double degreesToSeconds){
        super();
        this.degreesToSeconds=degreesToSeconds;
    }


    @Override
    /**
     * rotate clockwise
     * @param data
     */
    public void execute(Object data) throws RobotException{
        Double angle = (Double)data;
        Double absAngle = Math.abs(angle);
        int milliSeconds = (int)(absAngle*degreesToSeconds);
        if(milliSeconds > MAX_MILLISECONDS){
            throw new RobotException("Input to the right command was too large.");
        }
        if(angle>0){
            Motor.A.forward();
            Motor.B.backward();
        }
        else{
            Motor.A.backward();
            Motor.B.forward();
        }
        try{
            Thread.sleep(  milliSeconds  );
            Motor.A.stop();
            Motor.B.stop();
        }
        catch(InterruptedException e){

        }
    }
}