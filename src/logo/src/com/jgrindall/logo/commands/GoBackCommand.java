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
import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;
public class GoBackCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
       
        sendNotification(AppFacade.STOP_DRAWING,null,null);

        // clear logo string
        LogoProxy lp = (LogoProxy)facade.retrieveProxy(LogoProxy.NAME);
        lp.setLogo("");

         // clear everything else.
        sendNotification(AppFacade.CLEAR_DRAWING,null,null);
        sendNotification(AppFacade.CLEAR_ERROR,null,null);
        sendNotification(AppFacade.CLEAR_ERROR_TEXT,null,null);
        sendNotification(AppFacade.CLEAR_LOGO,null,null);

        // and go back
        this.sendNotification(AppFacade.GOTO_MYFILES,null,null);
    }
}
