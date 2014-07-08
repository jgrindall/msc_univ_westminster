/**
 * ErrorPanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Mediator for the error panel (the bug panel)
 */
package com.jgrindall.logo.views;
import com.jgrindall.logo.views.components.ErrorPanel;
import com.jgrindall.logo.*;
import org.puremvc.java.interfaces.INotification;
public class ErrorPanelMediator extends AMediator  {
    public static final String NAME = "ErrorPanelMediator";
    public ErrorPanelMediator(ErrorPanel viewComponent){
	super(NAME, null);
        setViewComponent(viewComponent);
    }
    private ErrorPanel getErrorPanel(){
        return (ErrorPanel)viewComponent;
    }
    @Override
    public void destroy(){
        super.destroy();
    }
    @Override
    public String[] listNotificationInterests(){
        return new String[] {AppFacade.PARSE_ERROR, AppFacade.CLEAR_ERROR_TEXT};
        // display error and clear error are the
        // only things that this class does
    }
    @Override
    public void handleNotification( INotification notification ){
        if(notification.getName().equals(AppFacade.PARSE_ERROR)){
            String m = (String)notification.getBody();
            getErrorPanel().setText(m);
        }
        else if (notification.getName().equals(AppFacade.CLEAR_ERROR_TEXT)){
            getErrorPanel().setText("");
        }
    }
}
