/**
 * StartupCommand.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *  Initial command that kicks off the pureMVC framework.
 *  This command is executed after the line
 *  'this.sendNotification(STARTUP, app, null)'
 *  in the AppFacade class.
 *  See www.puremvc.org for more information
 */
package com.jgrindall.logo.commands;
import com.jgrindall.logo.proxy.*;
import org.puremvc.java.interfaces.INotification;
import com.jgrindall.logo.MainPanel;
import com.jgrindall.logo.views.MainPanelMediator;
import org.puremvc.java.patterns.command.SimpleCommand;
public class StartupCommand extends SimpleCommand {
    @Override
    public void execute(INotification notification){
        MainPanel app = (MainPanel) notification.getBody();
        
        // build model first
        facade.registerProxy(new LogoProcessingProxy());
        facade.registerProxy(new LogoProxy());
        facade.registerProxy(new SocketProxy());
        facade.registerProxy(new UserProxy() );
        facade.registerProxy(new UserListProxy() );
        
        // then view can be defined and can load initial
        // values from the proxies. only one mediator is registered,
        // and it registers its children and so on.
        facade.registerMediator(new MainPanelMediator(app));

          
    }
}
