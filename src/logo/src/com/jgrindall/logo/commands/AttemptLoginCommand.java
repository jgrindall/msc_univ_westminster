/**
 * AttemptLoginCommand.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * A Command that is executed when the user clicks the login button
 */
package com.jgrindall.logo.commands;
import com.jgrindall.logo.proxy.*;
import org.puremvc.java.interfaces.ICommand;
import com.jgrindall.logo.socket.*;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class AttemptLoginCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
        UserProxy up = (UserProxy)facade.retrieveProxy(UserProxy.NAME);
        User usr = (User)notification.getBody();
        up.setUser(usr);
    }
}
