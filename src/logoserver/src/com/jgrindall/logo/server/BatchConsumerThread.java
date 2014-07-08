/**
 * BatchConsumerThread.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * 
 * The consumer in the consumer/producer model.
 * Takes commands and sends them to the NXT.
 * 
 */
package com.jgrindall.logo.server;
import java.io.*;
import com.jgrindall.logo.event.*;
import com.jgrindall.logo.comms.LogoCommandUtils;

public class BatchConsumerThread extends Thread implements ILogoEventDispatcher {
    private IProducer producer;
    private ARobotConnector connector;
    private volatile boolean process = true;
    private LogoEventDispatcher dispatcher;


    public BatchConsumerThread(IProducer p, ARobotConnector conn) {
        super();
        producer = p;
        dispatcher = new LogoEventDispatcher();
        this.connector=conn;
    }
    public void stopProcessing(){
        process=false;
        try{
            connector.forceStop();
        }
        catch(IOException ioe){
            System.out.println("IO on force stop");
            ioe.printStackTrace();
        }
    }


    /*  methods from ILogoEventDispatcher interface */

    public void addLogoEventListener(ILogoEventListener list){
        dispatcher.addLogoEventListener(list);
    }
    public void removeLogoEventListener(ILogoEventListener list){
        dispatcher.removeLogoEventListener(list);
    }
    public void fireLogoFinishedEvent(LogoEvent e){
        dispatcher.fireLogoFinishedEvent(e);
    }
    public void fireLogoParseErrorEvent(LogoEvent e){
        dispatcher.fireLogoParseErrorEvent(e);
    }
    public void fireLogoErrorEvent(LogoEvent e){
        dispatcher.fireLogoErrorEvent(e);
    }
    public void fireLogoTokenErrorEvent(LogoEvent e){
        dispatcher.fireLogoParseErrorEvent(e);
    }

    
    @Override
    /**
     * The main run() method of this Thread
     */
    public void run() {
        // The process flag is a safe way to stop the Thread.
        // It is set to false when errors occur
        
        while ( process ) {
            System.out.println("reader waiting");
            MessageSingleton.getInstance().sendMessage("reader waiting", false);
            String message = producer.getMessage();
            System.out.println("Got message: " + message);
            MessageSingleton.getInstance().sendMessage("Got message: " + message,false);
            try{
                connector.writeMessage(message);
            }
            catch(IOException e){
                System.out.println( "io "+e.getMessage()  );
                MessageSingleton.getInstance().defaultError(9);
                MessageSingleton.getInstance().sendMessage("io "+e.getMessage(), false);
                dispatcher.fireLogoErrorEvent(   new LogoErrorEvent(this, "A communication error occured")   );
                fireLogoFinishedEvent(    new LogoFinishedEvent(this,null)   );
                process=false;
            }
            try{
                String reply = connector.readMessage();
                System.out.println(   "got back: "+reply   );
                MessageSingleton.getInstance().sendMessage("got back: "+reply , false);
                if(reply.equals(LogoCommandUtils.ALL_COMMANDS_DONE)){
                    fireLogoFinishedEvent(    new LogoFinishedEvent(this,null)   );
                    this.process=false;
                }
                else if(reply.equals(LogoCommandUtils.ONE_COMMAND_DONE)){
                    // done one command 
                }
                // wake up the producer thread
                producer.removeMessage();
            }
            catch(EOFException e){
                System.out.println( "eof "+e.getMessage()  );
                MessageSingleton.getInstance().defaultError(10);
                MessageSingleton.getInstance().sendMessage(  "An error occured EOFException "+e.getMessage(), false );
                process=false;
            }
            catch(IOException e){
                dispatcher.fireLogoErrorEvent(   new LogoErrorEvent(this, "A communication error occured")   );
                System.out.println( "io error "+e.getMessage()  );
                MessageSingleton.getInstance().defaultError(11);
                MessageSingleton.getInstance().sendMessage(  "An error occured IOException "+e.getMessage() , false);
                process=false;
            }
            finally{
               //
            }
        }
    }
}

