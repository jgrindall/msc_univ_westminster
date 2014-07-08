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
import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;
public class StopDrawingCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
        ILogoProcess p = (ILogoProcess)facade.retrieveProxy(ILogoProcess.NAME);
        p.stop();
    }
}
