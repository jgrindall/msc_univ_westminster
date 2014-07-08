/**
 * Server.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Main program running on backend
 *

     * Implementation ideas taken from
     * http://forums.sun.com/thread.jspa?threadID=5290961


 *
 * Creates threads to handle each user and also manages
 * communication back to the client.
 *
 * Many methods are syncronised because all the threads can access this class.
 */

package com.jgrindall.logo.server;
import java.io.*;
import com.jgrindall.logo.socket.*;
import java.net.*;
import java.util.*;
public class Server {

    private ServerSocket serverSocket;

    // store Socket and outputstreams in a hashtable
    final private Hashtable outputStreams = new Hashtable();

    // list of users, also mapped to Sockets in hashtable
    final private Hashtable users = new Hashtable();

    // stores all users logged on to the system
    final private UserSocketObject userObj = new UserSocketObject();
    private boolean process = true;

    public Server( )  {
        (new Thread(){
            @Override
            public void run(){
                init();
            }
        }).start();
    }

    public synchronized void stopSever(){
        process=false;
        Enumeration e = outputStreams.keys();
        MessageSingleton.getInstance().sendMessage("Stopping..."  , true);
        while(e.hasMoreElements()){
            Socket s =  (Socket) e.nextElement( );
            removeConnection(s);
        }
        MessageSingleton.getInstance().sendMessage("Removed connections..."  , true);
        try{
            if(serverSocket!=null){
                serverSocket.close();
            }
        }
        catch(IOException io){
            //
        }
        finally{
            MessageSingleton.getInstance().sendMessage("Cleaning up..."  , true);
            serverSocket = null;
            outputStreams.clear();
            users.clear();
            userObj.destroy();
        }
    }
    private void init(){
        // Load hostname and port from external file
        try{
            int port =  5000;
            serverSocket = new ServerSocket( port );
            System.out.println( "Listening on "+serverSocket );
            MessageSingleton.getInstance().sendMessage("Application launched successfully. Listening on "+serverSocket  , true);
            MessageSingleton.getInstance().sendMessage("Application launched successfully. Listening on "+serverSocket  , false);
            while (process) {
                // 'accept' blocks until a connection is made
                Socket s = serverSocket.accept();
                System.out.println( "Connection from "+s );
                MessageSingleton.getInstance().sendMessage("Received a new connection from "+s  , true);
                MessageSingleton.getInstance().sendMessage("Received a new connection from "+s  , false);
                ObjectOutputStream dout = new ObjectOutputStream( s.getOutputStream() );
                outputStreams.put(s,dout);
                /* each Socket has a corresponding outputstream, pass them
                 to the hashtable so we can lookup the streams when we need to.
                 */
                new ServerThread( this, s );
            }
        }
        catch(IOException e){
            MessageSingleton.getInstance().defaultError(1);
            MessageSingleton.getInstance().sendMessage(e.getMessage()  , false);
            System.out.println("io exception in Server");
        }
    }
    /**
       update the user list to show everyone who is using the robot
       synchronise because all the server threads can access this object.
     */
    public void startExecuting(){
       
        synchronized(userObj){
            userObj.setExecuting(true);
            updateUserList();
        }
    }

    
    /**
     * robot has finished - update user list
     */
    public void stopExecuting(){
        synchronized(userObj){
            userObj.setExecuting(false);
            updateUserList();
        }
    }

    /**
     *
     * @param socket
     * @param usr
     *
     * Register a name of a user and report new user list to everyone else
     */
    public void registerNewUser(Socket socket, User usr){
        synchronized(userObj){

             System.out.println("register name "+usr.getUserName());
             MessageSingleton.getInstance().sendMessage( "register name "+usr.getUserName() , true);
             MessageSingleton.getInstance().sendMessage( "register name "+usr.getUserName() , false);

            if(userObj.contains(usr.getUserName())){
                //error
                sendToOne(socket,new MultipleLogOnErrorObject("That user is already logged in"));
                removeConnection(socket);

            }
            else{
                users.put(socket, usr);
                userObj.addToNotWaiting( usr );
                updateUserList();
            }
        }
    }

    /**
     *
     * @param socket
     *
     * The user clicked 'request robot'
     */
    public void requestControl(Socket socket){
        synchronized(userObj){
            User u = (User) users.get(socket);
            System.out.println("request control "+u.getUserName());
            MessageSingleton.getInstance().sendMessage( "request control "+u.getUserName() , true);
            MessageSingleton.getInstance().sendMessage( "request control "+u.getUserName() , false);
            userObj.moveToWaiting(u);
            System.out.println("Sending user data to all");
            MessageSingleton.getInstance().sendMessage("Sending user data to all"  , false);
            updateUserList();
        }
    }
    private void updateUserList(){
        synchronized(userObj){
            MessageSingleton.getInstance().sendUsers(userObj.toString());
            sendToAll(  userObj  );
        }
    }
    /**
     *
     * @param socket
     * user clicked logout
     */
     public void logout(Socket socket){
         synchronized(userObj){
            User u = (User) users.get(socket);
            userObj.remove(u);
            System.out.println("User logged out "+u.getUserName());
            MessageSingleton.getInstance().sendMessage("User logged out "+u.getUserName()  , false);
            MessageSingleton.getInstance().sendMessage("User logged out "+u.getUserName()  , true);
            updateUserList();
        }
     }
    /**
     *
     * @param socket
     * The user clicked 'relinquish robot'
     */
    public void relinquishControl(Socket socket){
        synchronized(userObj){
          
            User u = (User) users.get(socket);
            userObj.moveToNotWaiting(u);
            System.out.println("User relinquished control "+u.getUserName());
            MessageSingleton.getInstance().sendMessage("User relinquished control  "+u.getUserName()  , false);
            MessageSingleton.getInstance().sendMessage("User relinquished control  "+u.getUserName()  , true);
            updateUserList();
        }
    }
    private void sendToAll(ASocketObject sObj){
        synchronized( outputStreams ) {
           Enumeration e = outputStreams.keys();
           while(e.hasMoreElements()){
               Socket s =  (Socket) e.nextElement( );
               sendToOne(s,sObj);
            }
        }
    }
    /**
     *
     * @param socket
     * @param sObj
     *
     * Send the object through this socket
     */
    private void sendToOne(Socket socket, ASocketObject sObj){
        synchronized( outputStreams ) {
            ObjectOutputStream dout = (ObjectOutputStream)outputStreams.get(socket);
            try {
                 dout.writeObject(sObj);
                 dout.flush();
                 dout.reset();
            }
            catch( IOException ie ) {
                System.out.println( ie );
            }
        }
    }
    
    /**
     * The robot finished executing the Logo
     * @param socket
     */
    public void robotFinished(Socket socket){
        synchronized(userObj){
            userObj.setExecuting(false);
            updateUserList();
        }
    }
    /**
     * Report error to user
     * @param socket
     * @param msg
     */
    public void sendError(Socket socket, String msg){
        synchronized(userObj){
            userObj.setExecuting(false);
            sendToOne(socket,new ErrorSocketObject(msg));
            updateUserList();
        }
    }
    public synchronized Boolean userIsExecuting(Socket socket){
        User u = (User)users.get(socket);
        if(userObj.userIsExecuting(u.getUserName())){
            return true;
        }
        return false;
    }
   
    // Remove a socket, and it's corresponding output stream, from our
    // list. This is usually called by a connection thread that has
    // discovered that the connection to the client is dead.
    public synchronized void removeConnection( Socket socket ) {
        System.out.println( "Removing connection to "+socket );
        MessageSingleton.getInstance().sendMessage("Removing connection to "+socket  , false);
        MessageSingleton.getInstance().sendMessage("Removing connection to "+socket   , true);
        User u = (User)users.get(socket);
        userObj.remove(u);
        ObjectOutputStream dout = (ObjectOutputStream)outputStreams.get(socket);
        outputStreams.remove(socket);
        users.remove(socket);
        try {
            dout.close();
        }
        catch( IOException ie ) {
            System.out.println( "Error closing stream "+socket );
            MessageSingleton.getInstance().sendMessage("Error closing socket "+socket , false);
            MessageSingleton.getInstance().defaultError(2);
        }


        try{
             socket.close();
        }
        catch( IOException ie ) {
            System.out.println( "Error closing socket "+socket );
            MessageSingleton.getInstance().sendMessage("Error closing socket "+socket , false);
            MessageSingleton.getInstance().defaultError(12);
        }
        updateUserList();
    }
}