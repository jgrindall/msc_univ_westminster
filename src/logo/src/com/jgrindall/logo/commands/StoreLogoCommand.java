/**
 * StoreLogoCommand.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Executed whenever logo has changed and we store it in the LogoProxy
 * to make it accessible to other parts of the app
 */
package com.jgrindall.logo.commands;
import com.jgrindall.logo.proxy.*;
import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class StoreLogoCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
        LogoProxy lp = (LogoProxy)facade.retrieveProxy(LogoProxy.NAME);
        lp.setLogo( (String)notification.getBody());
    }
}
