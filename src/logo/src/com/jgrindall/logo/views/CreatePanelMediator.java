/**
 * CreatePanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Mediator for the large main swing component in the card layout that contains:
 *
 * 1. the editorPanel (containing the text area for input,
 * the draw buttons, and the bug reporting panel)
 * 
 * 2. the panel on the right hand side containing the user queue
 * and the turtle gfx.
 */
package com.jgrindall.logo.views;
import com.jgrindall.logo.views.components.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jgrindall.logo.AppFacade;

public class CreatePanelMediator extends AMediator  {
    public static final String NAME = "CreatePanelMediator";
    private EditorPanelMediator eMediator;
    private RightPanelMediator  rMediator;
    private ActionListener logList;
    private ActionListener backList;
    public CreatePanelMediator(CreatePanel viewComponent){
	super(NAME, null);
        setViewComponent(viewComponent);

        // register all children
        
        EditorPanel ep = (EditorPanel)getCreatePanel().getEditorPanel();
        RightPanel rp = (RightPanel)getCreatePanel().getRightPanel();
        eMediator = new EditorPanelMediator(ep);
        facade.registerMediator(eMediator);
        rMediator = new RightPanelMediator(rp);
        facade.registerMediator(rMediator);

        // add listeners.
        backList = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                backClicked();
            }
        };
        logList = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                logoutClicked();
            }
        };
        getCreatePanel().getBackButton().addActionListener(backList);
        getCreatePanel().getLogoutButton().addActionListener(logList);
    }
    private void backClicked(){
        sendNotification(AppFacade.GO_BACK_CLICKED,null,null);
    }
    private void logoutClicked(){
        this.sendNotification(AppFacade.LOGOUT,null,null);
    }
    private CreatePanel getCreatePanel(){
        return (CreatePanel)viewComponent;
    }
    @Override
    public void destroy(){
        super.destroy();
        eMediator.destroy();
        rMediator.destroy();
        facade.removeMediator(eMediator.getMediatorName());
        facade.removeMediator(rMediator.getMediatorName());
        eMediator=null;
        rMediator=null;
        getCreatePanel().getBackButton().removeActionListener(backList);
        backList=null;
        getCreatePanel().getLogoutButton().removeActionListener(logList);
        logList=null;

    }
    @Override
    public String[] listNotificationInterests(){
        return new String[]{};
    }
}
