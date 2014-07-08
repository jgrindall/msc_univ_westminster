/**
 * ISocket.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Interface for connection to robot
 */
package com.jgrindall.logo.proxy;
import org.puremvc.java.interfaces.IProxy;
import com.jgrindall.logo.socket.*;

public interface ISocket extends IProxy{
    public final String NAME="ISocket";
    void disconnect();
    void connect(String host);
    void stopRobot();
    void sendLogoToRobot(String logoString);
    void send(ASocketObject obj);
}

