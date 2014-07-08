/**
 * LogoPanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *  The basic no-frills mediator for the logo panel.
 *  Has a reference to the LogoPanel swing component which contains
 *  the text area itself and the draw/stop buttons
 */
package com.jgrindall.logo.views;

import com.jgrindall.logo.utils.TextLocationObject;
import javax.swing.*;
import com.jgrindall.logo.*;
import com.jgrindall.logo.utils.Help;
import org.puremvc.java.interfaces.INotification;
import java.awt.event.*;
import java.awt.Dimension;
import com.jgrindall.logo.proxy.*;
import com.jgrindall.logo.views.components.*;
import javax.swing.event.*;

public class LogoPanelMediator extends ALogoPanelMediator  {
    public static final String NAME = "SimpleLogoPanelMediator";
    protected final DocumentListener docListener = new DocListener(this);
    private ActionListener drawList;
    private ActionListener stopList;
    private MouseAdapter helpList;

    public LogoPanelMediator(String mediatorName, LogoPanel viewComponent){
	super(mediatorName, viewComponent);
        drawList = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            // user clicks draw
                            drawButtonPressed(e);
                         }
                    };
        
        stopList = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            // user clicks stop
                            stopButtonPressed(e);
                        }
                    };
        helpList = new MouseAdapter(){
                        @Override
                        public void mouseReleased(MouseEvent e){
                            helpPressed(e);
                        }
                    };
        getLogoPanel().getDrawButton().addActionListener(drawList);
        getLogoPanel().getStopButton().addActionListener(stopList);
        getLogoPanel().getHelpLabel().addMouseListener(helpList);
       
        // initiate the text
        LogoProxy lp = (LogoProxy)facade.retrieveProxy(LogoProxy.NAME);
        getLogoPanel().setLogo(lp.getLogo());

         // add the listeners that listen to text changed events
        addListeners();
    }
    @Override
    public void destroy(){
        super.destroy();
        getLogoPanel().getDrawButton().removeActionListener(drawList);
        getLogoPanel().getStopButton().removeActionListener(stopList);
        getLogoPanel().getHelpLabel().removeMouseListener(helpList);
        removeListeners();
        drawList=null;
        stopList=null;
        helpList = null;
    }
    public void doRemoveUpdate(){
        store();
        textChanged();
    }
    @Override
    protected void store(){
        super.store();
    }
    public void doInsertUpdate(){
        store();
        textChanged();
    }
    @Override
    protected void removeListeners(){
        super.removeListeners();
        JTextArea je =  (JTextArea)getLogoPanel().getLogoEditorTextArea();
        je.getDocument().removeDocumentListener(docListener);
    }
    @Override
    /* add and remove listeners have their own methods because they need to
     be removed when logo is edited by the code (for example the undo behaviour
     and when the border changes due to an exclamation mark appearing)
     otherwise a 'text changed' event is dispatched every time!
    */
    protected void addListeners(){
        super.addListeners();
        JTextArea je =  (JTextArea)getLogoPanel().getLogoEditorTextArea();
        je.getDocument().addDocumentListener(docListener);
    }
    protected void textChanged(){
        // when you type anything make sure we delete all error messages
        sendNotification(AppFacade.CLEAR_ERROR,null,null);
        // hide the exclamation mark and the highlighting
        sendNotification(AppFacade.CLEAR_ERROR_TEXT,null,null);
        // clear the text in the bug panel
    }
    private void helpPressed(MouseEvent e){
         HelpThread h = new HelpThread();
         SwingUtilities.invokeLater(h);
    }
    private void drawButtonPressed(ActionEvent e){
        // clear errors
        sendNotification(AppFacade.CLEAR_ERROR,null,null);
        sendNotification(AppFacade.CLEAR_ERROR_TEXT,null,null);
        // disable the draw button
        sendNotification(AppFacade.START_DRAWING_UPDATE_BUTTONS,null,null);
        // clear the turtle graphics
        this.sendNotification(AppFacade.CLEAR_DRAWING, null, null);
        // begin processing.
        this.sendNotification(AppFacade.PROCESS_LOGO, null, null);
    }
    private void stopButtonPressed(ActionEvent e){
        this.sendNotification(AppFacade.STOP_DRAWING, null, null);
    }
    @Override
    public String[] listNotificationInterests(){
        return new String[] { AppFacade.CLEAR_LOGO,AppFacade.CLEAR_ERROR, AppFacade.START_DRAWING_UPDATE_BUTTONS, AppFacade.STOP_DRAWING_UPDATE_BUTTONS,AppFacade.HIGHLIGHT_ERROR};
    }

    @Override
    public void handleNotification(INotification notification){
    	if(notification.getName().equals(AppFacade.START_DRAWING_UPDATE_BUTTONS)){
            getLogoPanel().setStatus(LogoPanel.DRAWING);
        }
        else if(notification.getName().equals(AppFacade.CLEAR_LOGO)){
            getLogoPanel().setLogo("");
        }
        else if(notification.getName().equals(AppFacade.STOP_DRAWING_UPDATE_BUTTONS)){
            getLogoPanel().setStatus(LogoPanel.STOPPED);
        }
        else if(notification.getName().equals(AppFacade.HIGHLIGHT_ERROR)){
            TextLocationObject loc = (TextLocationObject)notification.getBody();
            getLogoPanel().getLogoEditorTextArea().highlight(loc);
        }
        else if(notification.getName().equals(AppFacade.CLEAR_ERROR)){
            ILogoEditorTextArea ta = getLogoPanel().getLogoEditorTextArea();
            ta.highlight(null);
        }
    }
}


class DocListener implements DocumentListener{
    private LogoPanelMediator med;
    /*  listen to changes to the text */
    public DocListener(LogoPanelMediator m){
        med=m;
    }
    public void changedUpdate(DocumentEvent e) {
        // DO NOT change here - this is fired even if the background changes!
    }
    public void removeUpdate(DocumentEvent e) {
        med.doRemoveUpdate();
    }
    public void insertUpdate(DocumentEvent e) {
        med.doInsertUpdate();
    }
}


class HelpThread extends Thread{
    // open pop up in separate thread.
    // it seems necessary to do this when using the substance look'n'feel
    // otherwise you get strange run time bugs

    // see http://scala-programming-language.1934581.n4.nabble.com/
    // substance-look-and-feel-td1949070.html

    @Override
    public void run(){
        JOptionPane j = new JOptionPane();
        j.setMaximumSize(new Dimension(600,400));
        j.setOptions(new Object[] {"Ok"});
        j.setMessage(Help.getMessage());
        JDialog jd = j.createDialog("Help");
        jd.setModal(false);
        jd.setVisible(true);
    }
}

