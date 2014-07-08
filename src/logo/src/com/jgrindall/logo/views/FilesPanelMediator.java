/**
 * FilesPanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * INCOMPLETE
 *
 * This is the mediator for the 'my files' panel.
 *
 * 
 */
package com.jgrindall.logo.views;
import com.jgrindall.logo.*;
import org.puremvc.java.interfaces.INotification;
import com.jgrindall.logo.views.components.*;
import java.awt.event.*;
public class FilesPanelMediator extends AMediator  {
    public static final String NAME = "FilesPanelMediator";
    private ActionListener aList;
    private ActionListener logList;
    public FilesPanelMediator(FilesPanel viewComponent){
	super(NAME, null);
        setViewComponent(viewComponent);
        aList = new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    // user clicks fd
                    fdButtonPressed();
                }
            };
        logList = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                logoutClicked();
            }
        };
        getFilesPanel().getForwardButton().addActionListener(aList);
        getFilesPanel().getLogoutButton().addActionListener(logList);
    }


    @Override
    public void destroy(){
        super.destroy();
        getFilesPanel().getForwardButton().removeActionListener(aList);
        getFilesPanel().getLogoutButton().removeActionListener(logList);
        aList=null;
        logList=null;
    }
    private void logoutClicked(){
        this.sendNotification(AppFacade.LOGOUT,null,null);
    }
    private void fdButtonPressed(){
        this.sendNotification(AppFacade.GOTO_CREATE, null,null);
    }
    private FilesPanel getFilesPanel(){
        return (FilesPanel)viewComponent;
    }
    @Override
    public String[] listNotificationInterests(){
        return new String[] {};
    }
    @Override
    public void handleNotification(INotification notification){
    	
    }
}
