/**
 * ALogoPanelDecorator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Base class for any Decorators of the LogoPanel mediators.
 * Use this to add functionality to the Logo text area without subclassing it
 */
package com.jgrindall.logo.views;
import org.puremvc.java.interfaces.INotification;
import com.jgrindall.logo.views.components.*;
public abstract class ALogoPanelDecorator extends ALogoPanelMediator {
    protected ALogoPanelMediator wrappedMediator;
    public ALogoPanelDecorator(String mediatorName, ALogoPanelMediator wrappedMediator){
        super(mediatorName, wrappedMediator.getViewComponent());
        this.wrappedMediator = wrappedMediator;
    }
    @Override
    public void destroy(){
        super.destroy();
        wrappedMediator.destroy();
        wrappedMediator=null;
    }
    @Override
    protected void removeListeners(){
        wrappedMediator.removeListeners();
    }
    @Override
    protected LogoPanel getLogoPanel(){
        return wrappedMediator.getLogoPanel();
    }
    @Override
    protected void addListeners(){
        wrappedMediator.addListeners();
    }
    @Override
    protected void store(){
        wrappedMediator.store();
    }
    @Override
    public String[] listNotificationInterests(){
        return wrappedMediator.listNotificationInterests();
    }
    @Override
    public void handleNotification( INotification notification ){
        wrappedMediator.handleNotification(notification);
    }
}