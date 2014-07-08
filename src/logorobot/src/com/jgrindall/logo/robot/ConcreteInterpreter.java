/**
 * ConcreteInterpreter.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * interpret - for example  "fd:100.0;rt:90;end:0" is
 * "go forward 100, then turn right 90, then end"
 */
package com.jgrindall.logo.robot;

import java.util.ArrayList;
import com.jgrindall.logo.comms.*;

public class ConcreteInterpreter implements IInterpreter{
    private ARobot myRobot;

    public ConcreteInterpreter(ARobot r){
        myRobot=r;
    }
    /**
     * 
     * @param s
     * @throws Exception
     */
    public void interpret (StringBuffer s) throws RobotException{
        // ArrayList is the only typed dynamic array type supported by Lejos
        try{
            ArrayList<ALogoCommandObject> allCommands = LogoCommandUtils.stringToObject(s);
            for(int i=0;i<=allCommands.size()-1;i++){
                ALogoCommandObject obj = allCommands.get(i);
                perform(obj);
            }
        }
        catch(LogoException lex){
            // bad logo commands for some reason.
            // fail gracefully and reset the robot.
            throw new RobotException(LogoCommandUtils.END);
        }
    }
    /**
     * 
     * @param commandSB
     * @param dataSB
     * @throws Exception
     */
    public void perform(ALogoCommandObject obj) throws RobotException{
        if(obj instanceof LogoFdObject){
            myRobot.goForward(obj.getData());
        }
        else if(obj instanceof LogoRtObject){
            myRobot.turnRight(obj.getData());
        }
        else if(obj instanceof LogoEndObject){
            Utils.toLCD("END", true);
            // this exception is caught in BTReceive.java
            throw new RobotException(LogoCommandUtils.END);
        }
    }
}
