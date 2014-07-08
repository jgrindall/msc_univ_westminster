/**
 * IInterpreter.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * interface to interpret stream of chars and convert them to useful objects
 */
package com.jgrindall.logo.robot;
import com.jgrindall.logo.comms.*;

public interface IInterpreter{
   
    /**
     *
     * @param s
     * @throws Exception
     *
     * interpret takes a string and splits it into useful commands
     */
    public void interpret (StringBuffer s) throws RobotException;
    /**
     * 
     * @param commandSB
     * @param dataSB
     * @throws Exception
     *
     * perform makes the robot move.
     */
    public void perform(ALogoCommandObject obj) throws RobotException;
}
