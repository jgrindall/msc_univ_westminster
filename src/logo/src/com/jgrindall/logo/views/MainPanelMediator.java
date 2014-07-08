/**
 * MainPanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Mediator for MainFrame.java swing component
 */
package com.jgrindall.logo.views;
import com.jgrindall.logo.*;
import org.puremvc.java.interfaces.INotification;
import javax.swing.*;
import java.awt.Component;
import com.jgrindall.logo.views.components.*;

public class MainPanelMediator extends AMediator  {
    public static final String NAME = "MainPanelMediator";
    public MainPanelMediator(MainPanel viewComponent){
	super(NAME, null);
        setViewComponent(viewComponent);
        registerLogin();
    }
    @Override
    public void destroy(){
        super.destroy();
        cleanup();
    }
    private void cleanup(){
        cleanupCreate();
        cleanupLogin();
        cleanupMyFiles();
    }
    private void stop(){
        cleanupCreate();
        cleanupMyFiles();
    }
    private void start(){
        registerCreate();
        registerMyFiles();
    }
    public void showMessage(String msg){
         // since this is the main mediator. I display popups here
         OptionThread t = new OptionThread(msg,this.getMainPanel());
         SwingUtilities.invokeLater(t);
    }
    private void registerCreate(){
        // register the main panel with the puremvc framework
        CreatePanel cp = (CreatePanel)getMainPanel().getCreatePanel();
        facade.registerMediator(new CreatePanelMediator(cp));
    }
    private void registerLogin(){
        // register the login panel with the puremvc framework
        LoginPanel lp = (LoginPanel)getMainPanel().getLoginPanel();
        facade.registerMediator(new LoginPanelMediator(lp));
    }
    private void registerMyFiles(){
         // register the my files panel with the puremvc framework
         // TO DO:  This is incomplete because the 'my files' panel
        //  is not completed
         FilesPanel fp = (FilesPanel)getMainPanel().getFilesPanel();
         facade.registerMediator(new FilesPanelMediator(fp));
    }
    private MainPanel getMainPanel(){
        return (MainPanel)viewComponent;
    }
    @Override
    public String[] listNotificationInterests(){
        return new String[] {AppFacade.START_BUILD_APP, AppFacade.STOP_APP, AppFacade.SHOW_POPUP,AppFacade.GOTO_LOGIN, AppFacade.GOTO_MYFILES, AppFacade.GOTO_CREATE};
    }
    private void cleanupLogin(){
        LoginPanelMediator lpm = (LoginPanelMediator)facade.retrieveMediator(LoginPanelMediator.NAME);
        if(lpm!=null){
            lpm.destroy();
            facade.removeMediator(LoginPanelMediator.NAME);
        }
    }
    private void cleanupMyFiles(){
        FilesPanelMediator fpm = (FilesPanelMediator)facade.retrieveMediator(FilesPanelMediator.NAME);
        if(fpm!=null){
            fpm.destroy();
            facade.removeMediator(FilesPanelMediator.NAME);
        }
    }
    private void cleanupCreate(){
        CreatePanelMediator cpm = (CreatePanelMediator)facade.retrieveMediator(CreatePanelMediator.NAME);
        if(cpm!=null){
            cpm.destroy();
            facade.removeMediator(CreatePanelMediator.NAME);
        }
    }
    @Override
    public void handleNotification(INotification notification){
    	 if(notification.getName().equals(AppFacade.SHOW_POPUP)){
            showMessage((String)notification.getBody());
         }
         else if(notification.getName().equals(AppFacade.GOTO_CREATE)){
            // the user has logged in and is going to the main screen.
            // display the swing component and kick start the puremvc
            // files to handle the functionality
            getMainPanel().gotoCreateScreen();
         }
         else if(notification.getName().equals(AppFacade.START_BUILD_APP)){
            start();
         }
         else if(notification.getName().equals(AppFacade.STOP_APP)){
            stop();
         }
         else if(notification.getName().equals(AppFacade.GOTO_MYFILES)){
            // go to the my files screen
            getMainPanel().gotoFilesScreen();
         }
         else if(notification.getName().equals(AppFacade.GOTO_LOGIN)){
            // the user has logged in and is going to the my files screen
            getMainPanel().gotoLoginScreen();
         }
    }
}


class OptionThread extends Thread{
    // open pop up in separate thread.
    // it seems necessary to do this when using the substance look'n'feel
    // otherwise you get strange run time bugs

    // see http://scala-programming-language.1934581.n4.nabble.com/
    // substance-look-and-feel-td1949070.html
    
    private String msg;
    private Component parent;
    public OptionThread(String msg, Component parent){
        super();
        this.parent = parent;
        this.msg=msg;
    }
    @Override
    public void run(){
        JOptionPane.showMessageDialog(parent, msg, msg, 1);
    }
}
