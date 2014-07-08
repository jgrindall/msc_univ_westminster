/**
 * SocketProxy.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * This class handles all socket input/output.
 * A connection is established in the connect() method.
 * it runs in its own thread to keep main app responsive.
 */
package com.jgrindall.logo.proxy;

import org.puremvc.java.patterns.proxy.Proxy;
import com.jgrindall.logo.socket.*;
import com.jgrindall.logo.AppFacade;
import java.io.*;
import java.net.*;

public class SocketProxy extends Proxy implements Runnable,ISocket{
    private Socket socket;
    private ObjectOutputStream dout;
    private ObjectInputStream din;
    private Integer port =  5000;
    private String host;
    
    // volatile because can be changed from other threads
    private volatile boolean process = false;

    public SocketProxy(){
        super(NAME, null);
    }
    public void disconnect(){
        process = false;
        // listen thread terminates
        UserProxy up = (UserProxy)facade.retrieveProxy(UserProxy.NAME);
        ASocketObject req = new LogoutSocketObject(up.getUser());
        send(req);
    }
    public void connect(String host){
        this.host = host;
        if(host.equals("") || host==null){
            sendNotification(AppFacade.DEBUG,"Bad hostname",null);
            this.sendNotification(AppFacade.SHOW_POPUP,"Bad hostname", null);
        }
        else{
            process=true;
            new Thread(this).start();
        }
    }
    public void stopRobot(){
        send(   new StopRobotSocketObject()   );
    }
    public void sendLogoToRobot(String logoString){
        // send LOGO to robot to be executed by NXT
        send(   new LogoSocketObject(logoString)   );
    }
    private void sendHello(){
        UserProxy uProxy = (UserProxy) facade.retrieveProxy(UserProxy.NAME);
        User usr = uProxy.getUser();
        RegisterNameSocketObject rnObject = new RegisterNameSocketObject();
        rnObject.setData(usr);
        send( rnObject);
    }
    public void send(ASocketObject obj){
        if(dout!=null){
            try{
                System.out.println("send!"+obj);
                dout.writeObject(obj);
            }
            catch(IOException e){
                String msg = "Error connecting to robot. Please check it is connected and you entered its details correctly. Error code 7";
                this.sendNotification(AppFacade.SHOW_POPUP, msg, null);
               
            }
        }
        else{
            String msg = "Error connecting to robot. Please check it is connected and you entered its details correctly. Error code 8";
            this.sendNotification(AppFacade.SHOW_POPUP, msg, null);
        }
    }
    private void receive(UserSocketObject obj){
        //update users.
        obj.dump();
        this.sendNotification(AppFacade.UPDATE_USERS_MODEL, obj, null);
    }
    private void receive(ErrorSocketObject obj){
        // generic error
        String msgString = (String)obj.getData();
        this.sendNotification(AppFacade.SHOW_POPUP, msgString, null);
    }
    private void receive(MultipleLogOnErrorObject obj){
        // multiple log ons attempted.  Go back to log in.
        String msgString = (String)obj.getData();
        this.sendNotification(AppFacade.SHOW_POPUP, msgString, null);
        // redirect to log in screen
        this.sendNotification(AppFacade.LOGOUT,null,null);
    }

    private boolean init(){
        try{
            sendNotification(AppFacade.DEBUG,"connect to "+host+","+port,null);
            socket = new Socket( host,port );
        }
        catch(UnknownHostException ue){
            String msg = "Error connecting to robot. Please check it is connected and you entered its details correctly. Error code 1";
            this.sendNotification(AppFacade.SHOW_POPUP, msg, null);
            return false;
        }
        catch(IOException io){
            String msg = "Error connecting to robot. Please check it is connected and you entered its details correctly. Error code 2";
            this.sendNotification(AppFacade.SHOW_POPUP, msg, null);
            return false;
        }
        sendNotification(AppFacade.DEBUG,"connected",null);
        /*
         * NOTE:  create output stream first and then input stream
         * Otherwise when deployed on a Linux server it threw an
           IOException error (EOFException) immediately
         */

        try{
            dout = new ObjectOutputStream( socket.getOutputStream() );
        }
        catch(EOFException eof){
            String msg = "Error connecting to robot. Please check it is connected and you entered its details correctly. Error code 3";
            this.sendNotification(AppFacade.SHOW_POPUP, msg, null);
            return false;
        }
        catch(IOException io){
            String msg = "Error connecting to robot. Please check it is connected and you entered its details correctly. Error code 4";
            this.sendNotification(AppFacade.SHOW_POPUP, msg, null);
            return false;
        }
        try{
            InputStream input = socket.getInputStream();
            din = new ObjectInputStream( input );
        }
        catch(EOFException eof){
            String msg = "Error connecting to robot. Please check it is connected and you entered its details correctly. Error code 5";
            this.sendNotification(AppFacade.SHOW_POPUP, msg, null);
            return false;
        }
        catch(IOException io){
            String msg = "Error connecting to robot. Please check it is connected and you entered its details correctly. Error code 6";
            this.sendNotification(AppFacade.SHOW_POPUP, msg, null);
            return false;
        }
        sendNotification(AppFacade.DEBUG,"success",null);
        return true;
    }
    // infinite loop that listens for responses.
    // ASocketObject is the base class for any object sent over the socket.
    // readObject() blocks the thread until an object is available.
    public void run() {
        if(!init()){
            return;
        }
        sendHello();
        try {
            // process flag stops the thread safely
            while (process) {
                System.out.println("SocketProxy waiting for object...");
                ASocketObject recObj = (ASocketObject)din.readObject();
                // make sure these are in the right order
                if(recObj instanceof UserSocketObject){
                    receive(  (UserSocketObject)recObj   );
                }
                else if(recObj instanceof MultipleLogOnErrorObject){
                    receive(  (MultipleLogOnErrorObject)recObj  );
                }
                else if(recObj instanceof ErrorSocketObject){
                    receive(  (ErrorSocketObject)recObj  );
                }
               
            }
        }
        catch( IOException ie ) {
            String msg = "Error connecting to robot.  Error code 9";
            this.sendNotification(AppFacade.SHOW_POPUP, msg, null);
        }
        catch(ClassNotFoundException e){
            // 
        }
    }
}
