/**
 * ISocket.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Interface for all logo processing
 */
package com.jgrindall.logo.proxy;

import org.puremvc.java.interfaces.IProxy;
import com.jgrindall.logojavacc.*;

public interface ILogoProcess extends IProxy, ILogoConsumer{
    public final String NAME="ILogoProcess";
    void stop();
    void process(String logoString);
}
