/**
 * BatchProducerThread.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * This is the producer in the Producer/consumer model.
 * 
 * By executing wait() from a synchronized block, a thread gives up its hold on
 * the lock and goes to sleep. A thread might do this if it needs to wait
 * for something to happen in another part of the application...
 * Later, when the necessary event happens, the thread that is running it calls notify()
 * from a block synchronized on the same object. Now the first thread wakes up and begins
 * trying to acquire the lock again. - http://oreilly.com/catalog/expjava/excerpt/index.html
 *
 * In this class wait/notify are called from blocks using the msgs object which acts as the lock
 * 
 * 
 *
 * notify() wakes up the first thread that called wait( ) on the same object.
 *
 * notifyAll() wakes up all the threads that called wait( )
 * on the same object. The highest priority thread will run first.
 *
 * This class implements ILogoConsumer because it accepts FD/RT commands from the parser
 */
package com.jgrindall.logo.server;
import com.jgrindall.logojavacc.*;
import com.jgrindall.logo.comms.*;
import java.util.*;
import com.jgrindall.logo.event.*;


public class BatchProducerThread extends Thread implements ILogoConsumer,IProducer, ILogoEventDispatcher{
    
    // the node we wish to visit (this is actually a ProgramNode)
    private SimpleNode startNode;
    
    private LogoParserVisitor visitor;
    
    private static final int MAXQUEUE = 2;
    
    // a list of messages  - the buffer for the producer/consumer.
    private Vector<String> msgs;
    
    private LogoEventDispatcher dispatcher;

    /* volatile because they will be accessed by the Producer and the Consumer
    // could also be done with synchronized getters and setters.
    */
    private volatile Boolean process = true;

    public BatchProducerThread(LogoParserVisitor vis, SimpleNode s){
        super();
        msgs = new Vector<String>();
        dispatcher = new LogoEventDispatcher();
        startNode=s;
        this.visitor=vis;
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


   
    /**
     * The main run() method of this thread
     */
    @Override
    public void run(){
        System.out.println("run "+visitor);
        try{
            // begin walking the tree and then send end message.
            startNode.jjtAccept(visitor , this);
            accept(new LogoEndObject());
        }
        catch(ParseException e){
            /* a run-time error has occurred.
               report to the user and stop robot
             */
            String msg = "An error occured while drawing the Logo: "+e.getMessage()+" please check your Logo again.";
            System.out.println(msg);
            dispatcher.fireLogoErrorEvent(   new LogoErrorEvent(this, msg)   );
            stopProcessing();
        }
    }
    /**
      *   called when the user presses the stop button
     */
    public void stopProcessing(){
        process=false;
        checkStop(true);
    }

    /**
     *
     * @param ob
     *
     * accept() is called by the JavaCC Parser visitor
     *
     */
    public synchronized void accept(ALogoCommandObject ob) throws ParseException {
        if(process){
            while(msgs.size()==MAXQUEUE){
                try{
                    System.out.println(  "WAIT"  );
                    MessageSingleton.getInstance().sendMessage(  "WAIT" , false);
                    wait();
                }
                catch(InterruptedException e){
                    System.out.println(  "interrupted ex2 "+e.getMessage()   );
                    MessageSingleton.getInstance().defaultError(3);
                    MessageSingleton.getInstance().sendMessage(  "InterruptedException" , false);
                }
            }

            // once the lock has been obtained, the buffer size must be < MAX
            String toSend = LogoCommandUtils.toString(ob);
            msgs.add(toSend);
            System.out.println(  "now: "+msgs.size()   );
            // try to wake up the consumer thread to take a message
            notifyAll();
        }
        else{
            checkStop(true);
        }
    }

    /**
     *
     * @return
     *
     * called by the consumer to grab the next message
     */
    public synchronized String getMessage() {
        while ( msgs.size() == 0 ){
            try{
                /*  pause the consumer thread (which calls this method) until a
                 *  message is available
                */
                wait();
            }
            catch(InterruptedException e){
                MessageSingleton.getInstance().defaultError(4);
                MessageSingleton.getInstance().sendMessage("InterruptedException", false);
                System.out.println(  "interrupted ex1 "+e.getMessage()   );
            }
        }
        String message = msgs.elementAt(0);
        MessageSingleton.getInstance().sendMessage("returned "+message+", now "+msgs.size() , false);
        System.out.println(  "returned "+message+", now "+msgs.size()   );
        return message;
    }
    private synchronized void checkStop(Boolean thisThread){
        /* thisThread is true if the thread that created BatchProducerThread
        *  has called the method, and false if the Consumer has called it*/
         if(!process){
            MessageSingleton.getInstance().sendMessage("STOP ROBOT" , false);
            System.out.println("STOP ROBOT");
            msgs.clear();
            msgs.add(   LogoCommandUtils.toString(new LogoEndObject()  )  );
            if(thisThread){
                // if the Producer called, wake up the Consumer
                notifyAll();
            }
        }
    }
    public synchronized void check() throws ParseException{
        checkStop(true);
    }
    /**
     * the consumer removes the next message from the list and
     * allow the buffer to be filled again
     * this method is always called by the consuemr (BatchReaderThread)
     */
    public synchronized void removeMessage(){
        msgs.remove(0);
        MessageSingleton.getInstance().sendMessage("removed , now "+msgs.size()  , false);
        System.out.println(  "removed , now "+msgs.size()   );
        checkStop(false);
        notifyAll();

        /* The consumer thread removes a message when the robot has finished it
         * notifyAll() enables this producer thread to continue
           to fill the buffer.
         */
    }
}
