/**
 * ProcessLogoCommand.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Executes when the user clicks 'draw'
 */
package com.jgrindall.logo.commands;
import com.jgrindall.logo.proxy.*;
import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class ProcessLogoCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
        ILogoProcess lpp = (ILogoProcess)facade.retrieveProxy(ILogoProcess.NAME);
        LogoProxy lp = (LogoProxy)facade.retrieveProxy(LogoProxy.NAME);
        lpp.process(lp.getLogo());
    }
}
