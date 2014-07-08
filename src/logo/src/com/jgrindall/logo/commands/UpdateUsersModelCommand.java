/**
 * UpdateUsersModelCommand.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * A Command that is executed when the users logged in changes
 */
package com.jgrindall.logo.commands;
import com.jgrindall.logo.proxy.*;
import org.puremvc.java.interfaces.ICommand;
import com.jgrindall.logo.socket.*;
import com.jgrindall.logo.AppFacade;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class UpdateUsersModelCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
        UserListProxy up = (UserListProxy)facade.retrieveProxy(UserListProxy.NAME);
        UserSocketObject usrList = (UserSocketObject)notification.getBody();
        up.setUserList(usrList);
        this.sendNotification(AppFacade.UPDATE_USERS_VIEW,usrList,null);
    }
}
