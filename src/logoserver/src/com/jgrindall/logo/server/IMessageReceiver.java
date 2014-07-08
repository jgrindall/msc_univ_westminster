
package com.jgrindall.logo.server;

public interface IMessageReceiver {
    void receiveMessage(Object obj, boolean display);
    void receiveUsers(String s);
}
