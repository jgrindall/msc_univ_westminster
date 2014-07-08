/**
 * ServerThread.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *
 * Runs in a separate thread, one for each user logged onto the system.
 * 
 */
package com.jgrindall.logo.server;
import java.io.*;
import com.jgrindall.logo.socket.*;
import java.net.*;
import com.jgrindall.logojavacc.*;
import com.jgrindall.logo.event.*;
/**
 *
 * @param server - the Server object that holds the outputstreams
 * @param socket - the socket that identifies this user/thread
 * @param producer -  needed to send data to the robot in batches.
 * @param consumer -  needed to send data to the robot in batches.
 */
public class ServerThread extends Thread {
    private Server server;
    private Socket socket;
    private boolean process = true;
    private BatchProducerThread producer;
    private BatchConsumerThread consumer;
    protected ARobotConnector connector;
    private LogoEventAdapter prodList;
    private LogoEventAdapter consList;

    public ServerThread( Server server, Socket socket ) {
        this.server = server;
        this.socket = socket;
        connector = new NXTRobotConnector();
        prodList = new LogoEventAdapter(){
                        @Override
                        public void finishedEventPerformed(LogoEvent e){
                            finished();
                        }
                        @Override
                        public void errorEventPerformed(LogoEvent e){
                            sendError((String)e.getData());
                        }
                };
        consList = new LogoEventAdapter(){
                        @Override
                        public void finishedEventPerformed(LogoEvent e){
                            finished();
                        }
                        @Override
                        public void errorEventPerformed(LogoEvent e){
                            sendError((String)e.getData());
                        }
                };
        start();
    }
    /**
     *
     * @param s - error message
     */
    private void sendError(String s){
         server.sendError(this.socket, s );
    }
    /**
     *
     * @param parseEx
     */
    private void sendError(ParseException parseEx){
        sendError(parseEx.getMessage());
    }
    /**
     * The robot has finished executing the LOGO!
     */
    private void finished(){
        server.robotFinished(this.socket);
    }



    /**
     * receive different kinds of object from the client
     * *****************************************************************
     */
    private void receive(RegisterNameSocketObject rnObj){
        // when the client says hello to the back end; register name
        User usr = (User)rnObj.getData();
        server.registerNewUser(this.socket, usr);
    }
    private void receive(RequestControlSocketObject rcObj){
        server.requestControl(this.socket);
    }
    private void receive(RelinquishControlSocketObject rcObj){
        server.relinquishControl(this.socket);
    }
    private void receive(LogoutSocketObject loObj){
        cleanUp();
        server.logout(this.socket);
    }
    public void cleanUp(){
        process=false;
        producer.removeLogoEventListener(prodList);
        consumer.removeLogoEventListener(consList);
        producer.stopProcessing();
        consumer.stopProcessing();
        consumer=null;
        producer=null;

    }
    private void receive(StopRobotSocketObject srObj){
        System.out.println("stop robot ");
        MessageSingleton.getInstance().sendMessage( "stop robot " , true);
        MessageSingleton.getInstance().sendMessage( "stop robot " , false);
        //  stop the robot.
        stopRobot();
    }
    private void stopRobot(){
        producer.stopProcessing();
    }
    private void receive(LogoSocketObject lObj){
        
        // User has clicked 'send to robot' and wants the robot to execute Logo.
        System.out.println("RECEIVE"+lObj);
        MessageSingleton.getInstance().sendMessage( "Draw logo " , false);
        MessageSingleton.getInstance().sendMessage( "Draw logo " , false);
        String logoString = (String)lObj.getData();
        try{
            InputStream istream = new ByteArrayInputStream(logoString.getBytes("UTF-8"));
            final LogoParser parser = new LogoParser( istream );
            SimpleNode rootNode = parser.start();

            /* the above lines throw errors if the LOGO is invalid
            however this is tested on the client too, so the syntax will be ok
             */



            LogoParserVisitor vis = new LogoParserVisitorImpl(parser);
            boolean connected = false;
            try {
                connector.connect();
            }
            catch(Exception e){
                MessageSingleton.getInstance().sendMessage( "Error connecting to NXT" , false);
                MessageSingleton.getInstance().sendMessage( "Error connecting to NXT" , true);
                sendError("Error connecting to NXT. Error code 1");
            }
            if (!connector.getIsConnected()) {
                MessageSingleton.getInstance().sendMessage( "Error connecting to NXT" , false);
                MessageSingleton.getInstance().sendMessage( "Error connecting to NXT" , true);
                sendError("Error connecting to NXT. Error code 2");
            }
            else{
                // report the fact that the robot is executing to all users.
                server.startExecuting();

                // these send data to the robot
                producer = new BatchProducerThread(vis, rootNode);
                consumer = new BatchConsumerThread(producer, connector);
                producer.addLogoEventListener(prodList);
                consumer.addLogoEventListener(consList);

                // start the consumer first so it is ready to
                // handle the first output
                
                consumer.start();
                producer.start();
            }
        }
        catch(UnsupportedEncodingException e){
            sendError("Error connecting to NXT");
            MessageSingleton.getInstance().sendMessage( "Error connecting to NXT" , false);
            MessageSingleton.getInstance().sendMessage( "Error connecting to NXT" , true);
        }
        catch(ParseException e){
            sendError(e.getMessage());
        }
        catch(TokenMgrError tme){
            sendError(tme.getMessage());
        }
    }
     /**
     * *****************************************************************
     */


     private void handleMessage(Object message){
        if(message instanceof LogoSocketObject){
             receive((LogoSocketObject)message);
         }
         else if(message instanceof RegisterNameSocketObject){
             receive((RegisterNameSocketObject)message);
         }
         else if(message instanceof RequestControlSocketObject){
             receive((RequestControlSocketObject)message);
         }
         else if(message instanceof RelinquishControlSocketObject){
             receive((RelinquishControlSocketObject)message);
         }
         else if(message instanceof LogoutSocketObject){
             receive((LogoutSocketObject)message);
         }
         else if(message instanceof StopRobotSocketObject){
             receive((StopRobotSocketObject)message);
         }
     }


    @Override
    public void run() {
        // main run() method of the thread
        try {
            ObjectInputStream objIn = new ObjectInputStream( socket.getInputStream() );
            while (process) {
                 Object message = objIn.readObject();
                 MessageSingleton.getInstance().sendMessage( "received:"+message , false);
                 handleMessage(message);
            }
        }
        catch( EOFException ie ) {
            sendError( "EOF error"  );
            MessageSingleton.getInstance().sendMessage( "EOFException in ServerThread" , false);
            MessageSingleton.getInstance().defaultError(5);
        }
        catch(ClassNotFoundException e){
            sendError( "An unknown error occurred"  );
            System.out.print(e.getMessage());
            MessageSingleton.getInstance().sendMessage( "ClassNotFoundException in ServerThread" , false);
            MessageSingleton.getInstance().defaultError(6);
        }
        catch(SocketException se){
            sendError( "The socket was closed!"  );
            System.out.print(se.getMessage());
            MessageSingleton.getInstance().sendMessage( "SocketException in ServerThread" , false);
            MessageSingleton.getInstance().defaultError(7);
        }
        catch( IOException ie ) {
            sendError( "A communication error occurred"  );
            MessageSingleton.getInstance().sendMessage( "A communication error in ServerThread" , false);
            MessageSingleton.getInstance().defaultError(8);
            System.out.print(ie.getMessage());
        }
        finally {
            System.out.println("closed for one reason or another  "+socket.toString()  );
            // if executing, stop the robot.
            if(server.userIsExecuting(socket)){
                this.stopRobot();
            }
            server.removeConnection( socket );
        }
    }
}