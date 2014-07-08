/**
 * ALogoCommandObject.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Base class for all commands that can be
 * executed in Logo (on screen or by robot)
 */
package com.jgrindall.logo.comms;
import java.io.Serializable;
public abstract class ALogoCommandObject implements Serializable{
    protected Double data;
    public ALogoCommandObject(){
        
    }
    public ALogoCommandObject(Double d){
        data=d;
    }
    public Double getData(){
        return data;
    }
}