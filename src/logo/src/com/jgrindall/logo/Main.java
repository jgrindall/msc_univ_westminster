/**
 * Main.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *  Main runnable file, contains main() method
 */
package com.jgrindall.logo;
import javax.swing.*;

public class Main  {
    public static void main(String[] args) {
        /* invokeLater causes Swing to run a given runnable when
        * it can on the AWT Event Dispatching Thread
        the "substance" plaf must be invoked on the EDT
         */
      
        Thread creationThread = new FrameCreationThread();
        SwingUtilities.invokeLater(creationThread);
    }
}

/**
 * The FrameCreationThread makes a JFrame for the application
 * in the EDT, for the executable
 * @author John
 */
class FrameCreationThread extends Thread{
     @Override
     public void run(){
        makeGui();
    }
    private void makeGui(){
        MainFrame myFrame = new MainFrame();
        // all objects in GUI are built now

        /* next, startup the puremvc framework
        * use the JPanel not the JFrame because this code
        * must be reusable for applets/exe
         */
        AppFacade f = AppFacade.getInst();
        f.startup(myFrame.mainPanel);
    }
}


