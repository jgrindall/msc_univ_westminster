/**
 * User.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *  A class shared by all parts of the app (front and back end)
 * represents a user
 */
package com.jgrindall.logo.socket;
import java.io.Serializable;
public class User implements Serializable{
    private String userName = "";
    private String password = "";
    public User(String uName, String pwd){
        userName=uName;
        password=pwd;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
}
