/**
 * AMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Extends Mediator as provided by PureMVC and adds destroy method for tidying
 * up and event listening.
 * See http://download.oracle.com/javase/1.5.0/docs/api/
 * javax/swing/event/EventListenerList.html
 */
package com.jgrindall.logo.views;

import org.puremvc.java.patterns.mediator.Mediator;
import com.jgrindall.logo.event.*;
import javax.swing.event.EventListenerList;

public abstract class AMediator extends Mediator{
    
    public static final String NAME = "AMediator";
    protected EventListenerList listenerList = new EventListenerList();

    public AMediator( String mediatorName, Object viewComponent ) {
        super(mediatorName,viewComponent);
    }
    public void destroy(){
        
    }
}
