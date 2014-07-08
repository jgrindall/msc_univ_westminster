/**
 * ALogoPanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Base class for all Logo Panel mediators.
 * Use this to add functionality by subclassing
 */
package com.jgrindall.logo.views;
import com.jgrindall.logo.views.components.*;
import com.jgrindall.logo.AppFacade;
public abstract class ALogoPanelMediator extends AMediator {
    public ALogoPanelMediator(String mediatorName, Object viewComponent){
        super(mediatorName, viewComponent);
        setViewComponent(viewComponent);
    }
    @Override
    public void destroy(){
        super.destroy();
    }
    protected LogoPanel getLogoPanel(){
        return (LogoPanel)viewComponent;
    }
    protected void removeListeners(){

    }
    protected void addListeners(){
        
    }
    protected void store(){
        // store the current logo string in the LogoProxy
        // so that it can be accessed from elsewhere in the app
        this.sendNotification(AppFacade.STORE_LOGO, getLogoPanel().getLogo(), null);
    }
}