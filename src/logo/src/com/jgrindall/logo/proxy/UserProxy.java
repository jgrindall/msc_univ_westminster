/**
 * UserProxy.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Holds a reference to the current user.
 */
package com.jgrindall.logo.proxy;
import org.puremvc.java.patterns.proxy.Proxy;
import com.jgrindall.logo.AppFacade;
import com.jgrindall.logo.socket.*;
public class UserProxy extends Proxy  {
    private User user;
    private Boolean loggedIn = false;
    public static final String NAME = "UserProxy";
    public UserProxy(){
        super(NAME, null);
    }
    public User getUser(){
        return user;
    }
    public void setUser(User u){
        user=u;
        loggedIn=false;
        checkUser();
    }
    private void loginSuccess(){
        loggedIn=true;
        // TODO:  in reality this should go to the 'my files' screen.
        sendNotification(AppFacade.LOGIN_SUCCESS,null,null);
    }
    public void logout(){
        user = null;
        loggedIn = false;
    }
    private void loginFail(){
        System.out.println("fail");
        sendNotification(AppFacade.SHOW_POPUP,"Login failed. Please check your username and password",null);
    }
    private void checkUser(){
        // TODO:  This is hardcoded.  Delegate to database...
        boolean ok = Authentication.authenticate(user);
        if(ok){
            loginSuccess();
        }
        else{
            loginFail();
        }
    }
    public String getUserName(){
        if(user!=null){
            return user.getUserName();
        }
        else{
            return "";
        }
    }
    public String getPassword(){
        if(user!=null){
            return user.getPassword();
        }
        else{
            return "";
        }
    }
}
