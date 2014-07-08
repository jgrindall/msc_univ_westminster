/**
 * StopRobotCommand.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Executed when the user clicks 'stop robot'
 */
package com.jgrindall.logo.commands;
import com.jgrindall.logo.proxy.*;
import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;
public class StopRobotCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
        ISocket sp = (ISocket)facade.retrieveProxy(ISocket.NAME);
        sp.stopRobot();
    }
}