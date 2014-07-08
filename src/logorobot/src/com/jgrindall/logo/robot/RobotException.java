/**
 * RobotException.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Like an EOF Exception, but dispatched when Logo has finished being performed.
 */

package com.jgrindall.logo.robot;

public class RobotException extends Exception{
    public RobotException(){
        super();
    }
    public RobotException(String msg){
        super(msg);
    }
}