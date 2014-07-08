/**
 * ASocketObject.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Abstract class for any object I send over the socket - hence serializable
 */
package com.jgrindall.logo.socket;
import java.io.Serializable;
public abstract class ASocketObject  implements Serializable{
    private Object data;
    public ASocketObject(){
        
    }
    public ASocketObject(Object s){
        setData(s);
    }
    public void setData(Object s){
        data=s;
    }
    public Object getData(){
        return data;
    }
}