/**
 * LogoEventDispatcher.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * see http://download.oracle.com/javase/1.5.0/
 * docs/api/javax/swing/event/EventListenerList.html
 */

package com.jgrindall.logo.event;

import javax.swing.event.EventListenerList;

public class LogoEventDispatcher  implements ILogoEventDispatcher{

    protected EventListenerList listenerList = new EventListenerList();

    public void addLogoEventListener(ILogoEventListener listener) {
        listenerList.add(ILogoEventListener.class, listener);
    }
    public void removeLogoEventListener(ILogoEventListener listener) {
        listenerList.remove(ILogoEventListener.class, listener);
    }
    public void fireLogoParseErrorEvent(LogoEvent e) {
        Object[] listeners = listenerList.getListenerList();
        // it is +2 because getListenerList()
        // Passes back the event listener list as an array of
        // ListenerType-listener pairs.
        // see http://download.oracle.com/javase/1.5.0/docs/api/
        // javax/swing/event/EventListenerList.html
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==ILogoEventListener.class) {
                ((ILogoEventListener)listeners[i+1]).parseErrorEventPerformed(e);
            }
        }
    }
    public void fireLogoErrorEvent(LogoEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==ILogoEventListener.class) {
                ((ILogoEventListener)listeners[i+1]).errorEventPerformed(e);
            }
        }
    }
    public void fireLogoFinishedEvent(LogoEvent e){
        Object[] listeners = listenerList.getListenerList();
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==ILogoEventListener.class) {
                ((ILogoEventListener)listeners[i+1]).finishedEventPerformed(e);
            }
        }
    }
    public void fireLogoTokenErrorEvent(LogoEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==ILogoEventListener.class) {
                ((ILogoEventListener)listeners[i+1]).tokenErrorEventPerformed(e);
            }
        }
    }
}