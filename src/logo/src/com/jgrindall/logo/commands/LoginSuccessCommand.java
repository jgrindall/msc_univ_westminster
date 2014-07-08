/**
 * LoginSuccessCommand.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * A Command that is executed when the user has actually logged in
 */
package com.jgrindall.logo.commands;
import com.jgrindall.logo.proxy.*;
import com.jgrindall.logo.views.*;
import org.puremvc.java.interfaces.ICommand;
import com.jgrindall.logo.AppFacade;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class LoginSuccessCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
        // "START_BUILD_APP" registers the rest of the puremvc framework
        sendNotification(AppFacade.START_BUILD_APP,null,null);
        sendNotification(AppFacade.GOTO_MYFILES, null,null);
        LoginPanelMediator lm = (LoginPanelMediator)facade.retrieveMediator(LoginPanelMediator.NAME);
        
        // and then connect to back end (the views are now listening
        // to messages from the backend)
        if(lm.connectBoolean()){
            ISocket sp = (ISocket)facade.retrieveProxy(ISocket.NAME);
            sp.connect(lm.getHostName());
        }

    }
}
