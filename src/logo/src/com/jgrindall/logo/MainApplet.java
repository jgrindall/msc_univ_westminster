/**
 * MainApplet.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *  Main runnable file for launch as applet, contains init() method
 */

package com.jgrindall.logo;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class MainApplet extends JApplet {
    //Called when this applet is loaded into the browser.
    @Override
    public void init() {
        // invokeLater causes Swing to run a given runnable when
        // it can on the AWT Event Dispatching Thread
        // the "substance" plaf must be invoked on the EDT
        
        Thread creationThread = new PanelCreationThread(this);
        SwingUtilities.invokeLater(creationThread);
    }
}


/**
 * The PanelCreationThread makes a JPanel for the application
 * in the EDT, for the applet
 * @author John
 */
class PanelCreationThread extends Thread{
    private JApplet applet;
    public PanelCreationThread(JApplet applet){
        this.applet = applet;
    }
    @Override
    public void run(){
        makeGui();
    }
    private void makeGui(){
        MainPanel myPanel = new MainPanel();
        myPanel.setSize(800, 600);
        applet.add(myPanel);
        // all objects in GUI are built now

        // next, startup the puremvc framework
        // use the JPanel not the JFrame because this code
        // must be reusable for applets/exe
        AppFacade f = AppFacade.getInst();
        f.startup(myPanel);
    }
}
