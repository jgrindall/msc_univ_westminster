/**
 * UserListProxy.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Holds a reference to the users logged in.
 */
package com.jgrindall.logo.proxy;
import org.puremvc.java.patterns.proxy.Proxy;
import com.jgrindall.logo.socket.UserSocketObject;
public class UserListProxy extends Proxy  {
    private UserSocketObject usrList;
    public static final String NAME = "UserListProxy";
    public UserListProxy(){
        super(NAME, null);
    }
    public void setUserList(UserSocketObject usrList){
        this.usrList=usrList;
    }
    public UserSocketObject getUserList(){
        return this.usrList;
    }
}
