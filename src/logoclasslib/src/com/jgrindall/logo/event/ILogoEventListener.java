/**
 * ILogoEventListener.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * generic interface for listening to Logo events
 */

package com.jgrindall.logo.event;

import java.util.*;

public interface ILogoEventListener extends EventListener {
    public void parseErrorEventPerformed(LogoEvent e);
    public void tokenErrorEventPerformed(LogoEvent e);
    public void finishedEventPerformed(LogoEvent e);
    public void errorEventPerformed(LogoEvent e);
}
