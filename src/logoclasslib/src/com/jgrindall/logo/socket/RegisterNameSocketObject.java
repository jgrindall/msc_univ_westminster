/**
 * RegisterNameSocketObject.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * register name of new uer logged in
 */
package com.jgrindall.logo.socket;
public class RegisterNameSocketObject  extends ASocketObject{
     public RegisterNameSocketObject(){
         super();
     }
     public RegisterNameSocketObject(User usr){
       super(usr);
   }
}