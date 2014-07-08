/**
 * MessageSingleton.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Central location for messages
 */

package com.jgrindall.logo.server;

public class MessageSingleton{
    /**
     * Singleton instance
     */
    private IMessageReceiver receiver;
    protected static MessageSingleton instance;
    public void setReceiver(IMessageReceiver r){
        receiver=r;
    }
    private MessageSingleton( ){
        instance = this;
       
    }
    public void defaultError(int i){
        this.sendMessage("An error occurred, make sure that the application isn't running already. See the log file for more information. Error code "+i, true);
    }
    public void sendMessage(Object msg, boolean display){
        if(receiver!=null){
            receiver.receiveMessage(msg, display);
        }
    }
    public void sendUsers(String s){
        receiver.receiveUsers(s);
    }
    public synchronized static MessageSingleton getInstance( ){
        if (instance == null) {
            instance = new MessageSingleton();
        }
        return instance;
    }
}
