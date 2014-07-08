/**
 * LoginPanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * The username/password login screen
 * 
 */
package com.jgrindall.logo.views;
import com.jgrindall.logo.views.components.*;
import com.jgrindall.logo.*;
import org.puremvc.java.patterns.mediator.Mediator;
import org.puremvc.java.interfaces.INotification;
import com.jgrindall.logo.socket.User;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
public class LoginPanelMediator extends AMediator  {
    public static final String NAME = "LoginPanelMediator";
    private ActionListener loginList;
    private KeyAdapter keyList;
    public LoginPanelMediator(LoginPanel viewComponent){
	super(NAME, null);
        setViewComponent(viewComponent);
        loginList = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // attempted login by clicking button
                loginPressed();
            }
        };
        getLoginPanel().getLoginButton().addActionListener(loginList);

        keyList = new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                 // attempted login by pressing enter key on pwd field.
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   loginPressed();
                }
            }
        };
        getLoginPanel().getPasswordTextField().addKeyListener(keyList);
    }
    @Override
    public void destroy(){
        super.destroy();
        getLoginPanel().getLoginButton().removeActionListener(loginList);
        getLoginPanel().getPasswordTextField().removeKeyListener(keyList);
        loginList=null;
        keyList=null;
    }
    private void loginPressed(){
        String username = getLoginPanel().getUserName();
        String password = getLoginPanel().getPassword();
        User u = new User(username, password);
        this.sendNotification(AppFacade.ATTEMPT_LOGIN, u,null);
    }
    private LoginPanel getLoginPanel(){
        return (LoginPanel)viewComponent;
    }
    public boolean connectBoolean(){
        return getLoginPanel().connectBoolean();
    }
    public String getHostName(){
        return getLoginPanel().getHostName();
    }
    @Override
    public String[] listNotificationInterests(){
        return new String[] {};
    }
    @Override
    public void handleNotification(INotification notification){
    	
    }
}
