/**
 * ILogoEventDispatcher.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * can dispatch Logo events
 */

package com.jgrindall.logo.event;

public interface ILogoEventDispatcher {

    void addLogoEventListener(ILogoEventListener listener);
    void removeLogoEventListener(ILogoEventListener listener);
    void fireLogoParseErrorEvent(LogoEvent e);
    void fireLogoTokenErrorEvent(LogoEvent e);
    void fireLogoFinishedEvent(LogoEvent e);
    void fireLogoErrorEvent(LogoEvent e);
}
