/**
 * LogoEvent.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * generic event class for all events
 */
package com.jgrindall.logo.event;

import java.util.*;

public abstract class LogoEvent extends EventObject {
    private Object data;
    public LogoEvent(Object source,Object data) {
        super(source);
        this.data = data;
    }
    public Object getData(){
        return data;
    }
}
