/**
 * Authentication.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Hard coded username and password checking.
 *
 * currently allows user1,user1  or userJohn,userJohn  etc
 * as long as the chars after user match it is ok.
 */
package com.jgrindall.logo.proxy;
import com.jgrindall.logo.socket.*;
public class Authentication {
   public static boolean authenticate(User user){
        String username = user.getUserName();
        String pwd = user.getPassword();
        if(!username.equals(pwd)){
            return false;
        }
        if(username.length()<=4){
            return false;
        }
        if(username.substring(0, 4).equals("user")  ){
            return true;
        }
        return false;
    }
}

