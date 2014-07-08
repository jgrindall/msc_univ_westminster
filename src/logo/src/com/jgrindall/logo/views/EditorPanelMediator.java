/**
 * EditorPanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Mediator for the Error Panel.
 * Build the mediators for the text area and the error panel here.
 */
package com.jgrindall.logo.views;
import com.jgrindall.logo.views.components.ErrorPanel;
import com.jgrindall.logo.views.components.LogoPanel;
import com.jgrindall.logo.views.components.EditorPanel;

public class EditorPanelMediator extends AMediator  {
    public static final String NAME = "EditorPanelMediator";
    private ALogoPanelMediator bgMediator;
    private ErrorPanelMediator eMediator;
    public EditorPanelMediator(EditorPanel viewComponent){
	super(NAME, null);
        setViewComponent(viewComponent);
        LogoPanel lp = getEditorPanel().getLogoPanel();
        ALogoPanelMediator undo     =   new UndoableLogoPanelMediator(UndoableLogoPanelMediator.NAME,lp);
        bgMediator       =   new BGCheckLogoPanelDecorator(undo);
        //
        facade.registerMediator(bgMediator);
        ErrorPanel ep = getEditorPanel().getErrorPanel();
        eMediator = new ErrorPanelMediator(ep);
        facade.registerMediator(eMediator);
    }
    @Override
    public void destroy(){
        super.destroy();
        bgMediator.destroy();
        facade.removeMediator(bgMediator.getMediatorName());
        eMediator.destroy();
        facade.removeMediator(eMediator.getMediatorName());
        bgMediator=null;
        eMediator=null;
    }
    private EditorPanel getEditorPanel(){
        return (EditorPanel)viewComponent;
    }
    @Override
    public String[] listNotificationInterests(){
        return new String[] {};
    }
}
