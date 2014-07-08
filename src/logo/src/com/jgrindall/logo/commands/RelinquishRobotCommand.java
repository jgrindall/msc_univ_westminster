/**
 * RelinquishRobotCommand.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Executes when the user clicks to relinquish control of the robot
 */
package com.jgrindall.logo.commands;
import com.jgrindall.logo.proxy.*;
import com.jgrindall.logo.socket.*;
import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;
public class RelinquishRobotCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
        UserProxy up = (UserProxy)facade.retrieveProxy(UserProxy.NAME);
        ASocketObject rel = new RelinquishControlSocketObject(up.getUser());
        ISocket sp = (ISocket)facade.retrieveProxy(ISocket.NAME);
        sp.send(rel);
    }
}