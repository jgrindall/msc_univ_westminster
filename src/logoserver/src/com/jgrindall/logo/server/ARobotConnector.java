/**
 * ARobotConnector.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *
 *
 */
package com.jgrindall.logo.server;
import java.io.*;
public abstract class ARobotConnector{
    protected Boolean isConnected = false;
    public abstract void connect() throws Exception;
    
    public Boolean getIsConnected(){
        return isConnected;
    }
    public abstract void writeMessage(String s) throws IOException;
    
    public abstract String readMessage() throws EOFException, IOException;

    public abstract void forceStop() throws IOException;
}