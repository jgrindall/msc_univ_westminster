/**
 * NXTRobotConnector.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *
 *
 */
package com.jgrindall.logo.server;
import java.io.*;
import lejos.pc.comm.NXTConnector;



public class NXTRobotConnector extends ARobotConnector{
    private NXTConnector nxt;
    private DataOutputStream dos;
    private DataInputStream dis;
    public static final char EOL = '\n';
    // TODO:  Use System.getProperty("line.separator") ??
  
    
    @Override
    public void connect() throws Exception{
        nxt = new NXTConnector();
        this.isConnected = nxt.connectTo("btspp://");
        dos = nxt.getDataOut();  // data going to the robot
        dis = nxt.getDataIn();    // data coming back from the robot
    }
    @Override
    public void writeMessage(String s) throws IOException{
         dos.writeChars(s + NXTRobotConnector.EOL);
         dos.flush();
    }
    public void forceStop() throws  IOException{
        System.out.println("FORCE");
        dos.flush();
        dos.close();
        dis.close();
        nxt.close();
        nxt=null;
        dos=null;
        dis=null;
    }
    public String readMessage() throws EOFException, IOException{
         // read reply from the robot
        StringBuffer sb = new StringBuffer();
        char c;
        do {
            c = dis.readChar();
            if(c!=NXTRobotConnector.EOL){
                sb.append(c);
            }
        }
        while (c != NXTRobotConnector.EOL);
        return sb.toString();
    }
}


