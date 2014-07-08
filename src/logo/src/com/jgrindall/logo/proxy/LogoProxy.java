/**
 * LogoProxy.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Stores a reference to the logo string the user has entered
 */
package com.jgrindall.logo.proxy;

import org.puremvc.java.patterns.proxy.Proxy;
public class LogoProxy extends Proxy  {
    private String logo = "";
    public static final String NAME = "LogoProxy";
    public LogoProxy(){
        super(NAME, null);
    }
    public void setLogo(String s){
        logo=s;
    }
    public String getLogo(){
        return logo;
    }
}
