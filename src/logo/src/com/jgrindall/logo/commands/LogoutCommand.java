/**
 * LogoutCommand.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Executes when the user clicks to logout
 */
package com.jgrindall.logo.commands;
import com.jgrindall.logo.proxy.*;
import com.jgrindall.logo.AppFacade;
import com.jgrindall.logo.socket.*;
import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;
public class LogoutCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
        System.out.println("logout executed");
        UserListProxy uList = (UserListProxy)facade.retrieveProxy(UserListProxy.NAME);
        UserSocketObject uObj = uList.getUserList();
        UserProxy up =  (UserProxy)facade.retrieveProxy(UserProxy.NAME);
        String uName = up.getUserName();
       
        sendNotification(AppFacade.STOP_DRAWING,null,null);

        // send disconnect message
        ISocket sp = (ISocket)facade.retrieveProxy(ISocket.NAME);
        sp.disconnect();

        // clear user
        up.logout();

        // clear logo
        LogoProxy lp = (LogoProxy)facade.retrieveProxy(LogoProxy.NAME);
        lp.setLogo("");

         // clear everything else.
        sendNotification(AppFacade.CLEAR_DRAWING,null,null);
        sendNotification(AppFacade.CLEAR_ERROR,null,null);
        sendNotification(AppFacade.CLEAR_ERROR_TEXT,null,null);
        sendNotification(AppFacade.CLEAR_LOGO,null,null);
        sendNotification(AppFacade.DISABLE_SOCKET_BUTTONS,null,null);

         // unregister mediators and socket.
        sendNotification(AppFacade.STOP_APP,null,null);
        sendNotification(AppFacade.GOTO_LOGIN,null,null);
         
    }
}
