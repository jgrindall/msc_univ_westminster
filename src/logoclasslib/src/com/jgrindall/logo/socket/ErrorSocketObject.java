/**
 * ErrorSocketObject.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * an error has occured on the server - send back to the user
 */
package com.jgrindall.logo.socket;
public class ErrorSocketObject extends ASocketObject{
   public ErrorSocketObject(String s){
       super(s);
   }
}