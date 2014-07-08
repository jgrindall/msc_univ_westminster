/**
 * TurtlePanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Medaitor for turtle graphics panel, where logo is drawn!
 * 
 */
package com.jgrindall.logo.views;
import com.jgrindall.logo.views.components.TurtleCanvas;
import com.jgrindall.logo.views.components.TurtlePanel;
import com.jgrindall.logo.*;
import org.puremvc.java.patterns.mediator.Mediator;
import org.puremvc.java.interfaces.INotification;
import com.jgrindall.logo.comms.*;
public class TurtlePanelMediator extends AMediator  {
    public static final String NAME = "TurtlePanelMediator";
    private TurtleCanvas tc;
    public TurtlePanelMediator(TurtlePanel viewComponent){
	super(NAME, null);
        setViewComponent(viewComponent);
        tc = getTurtlePanel().getTurtleCanvas();
    }
    private TurtlePanel getTurtlePanel(){
        return (TurtlePanel)viewComponent;
    }
    @Override
    public void destroy(){
        super.destroy();
        tc=null;
    }
    @Override
    public String[] listNotificationInterests(){
        return new String[] {AppFacade.DEBUG,AppFacade.DRAW_LOGO, AppFacade.CLEAR_DRAWING};
    }
    @Override
    public void handleNotification(INotification notification){
    	if(notification.getName().equals(AppFacade.DRAW_LOGO)){
            ALogoCommandObject lo = (ALogoCommandObject)notification.getBody();
            // actually draw the lines!
            if(lo instanceof LogoRtObject){
                tc.draw((LogoRtObject)lo);
            }
            else if(lo instanceof LogoFdObject){
                tc.draw((LogoFdObject)lo);
            }
        }
        else if(notification.getName().equals(AppFacade.CLEAR_DRAWING)){
            // clear all graphics and reset to origin.
            tc.reset();
        }
        else if(notification.getName().equals(AppFacade.DEBUG)){
            // clear all graphics and reset to origin.
            getTurtlePanel().outputtf.append(  notification.getBody().toString() );
        }
    }
}
