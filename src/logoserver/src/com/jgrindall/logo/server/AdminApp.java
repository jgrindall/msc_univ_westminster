/**
 * AdminApp.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Parent swing component for application.
 */
package com.jgrindall.logo.server;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import com.jgrindall.logo.style.StyleUtils;
import java.awt.BorderLayout;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class AdminApp extends JFrame implements IMessageReceiver{
    private JButton startBut;
    private JButton stopBut;
    private JTextArea output;
    private JTextArea usrOutput;
    private Server server;
    private boolean logMessages = false;

    public static void main(String[] args) {
        // invokeLater causes Swing to run a given runnable when it
        // can on the AWT Event Dispatching Thread
        // the "substance" plaf must be invoked on the EDT
        Thread creationThread = new FrameCreationThread();
        SwingUtilities.invokeLater(creationThread);
    }
    public AdminApp() {
        super();
        MessageSingleton.getInstance().setReceiver(this);
        StyleUtils.setSubstanceStyle();
        startBut = new JButton("start");
        stopBut = new JButton("stop");
        output = new JTextArea();
        output.setWrapStyleWord(true);
        output.setLineWrap(true);
        output.setText("Click start to start the robologo server\n");

        usrOutput = new JTextArea();
        usrOutput.setWrapStyleWord(true);
        usrOutput.setLineWrap(true);
        usrOutput.setText("Users logged in:\n");

        startBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                start();
            }
        });
        stopBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                stop();
            }
        });
        JPanel hbox = new JPanel();
        hbox.add(startBut);
        hbox.add(stopBut);
        startBut.setIcon(   new ImageIcon(  getClass().getResource("play.png"))   );
        stopBut.setIcon(   new ImageIcon(  getClass().getResource("stop.png"))   );
        this.add(hbox, BorderLayout.SOUTH);

        
        JScrollPane usr = new JScrollPane();
        JScrollPane jsp = new JScrollPane();
        jsp.getViewport().add(output);
        usr.getViewport().add(usrOutput);
        

        JSplitPane splitPane = new JSplitPane();
        splitPane.add(jsp,JSplitPane.LEFT);
        splitPane.add(usr,JSplitPane.RIGHT);
        this.add(splitPane, BorderLayout.CENTER);
        splitPane.setResizeWeight(0.85);
        stopBut.setEnabled(false);
        WindowAdapter wClose = new WindowAdapter(){
                                    @Override
                                    public void windowClosing(WindowEvent e){
                                        closeClicked();
                                       
                                 }};
        this.addWindowListener(wClose);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setSize(450,250);
    }
    private void closeClicked(){
        output.append("Shutting down...\n");
        stop();
        // wait a little while
        Timer timer = new Timer();
        // 1500 milliseconds
        timer.schedule(new ShutDown(this), 1500);
    }
    public void receiveUsers(String s){
        usrOutput.setText("Users logged in:\n");
        usrOutput.append(s);
    }
    public void receiveMessage(Object msg, boolean display){
        if(!logMessages){
            return;
        }
        if(display){
            output.append((String)msg);
            output.append("\n");
        }
        else{
            // write to log
            try{
                // Create file
                BufferedWriter logWriter = new BufferedWriter(new FileWriter("robologo.log",true));
                String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                logWriter.write(time);
                logWriter.newLine();
                logWriter.write((String)msg);
                logWriter.newLine();
                logWriter.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    private void start(){
        logMessages=true;
        startBut.setEnabled(false);
        stopBut.setEnabled(true);
        server = new Server();
    }
    private void stop(){
        logMessages=false;
        startBut.setEnabled(true);
        stopBut.setEnabled(false);
        if(server!=null){
            server.stopSever();
            server=null;
        }
        output.append("Stopped.\n");
    }
}


class FrameCreationThread extends Thread{
     @Override
     public void run(){
        AdminApp app = new AdminApp();
    }
}


class ShutDown extends TimerTask{
    private AdminApp app;
    public ShutDown(AdminApp a){
        super();
        this.app=a;
    }
    public void run(){
        app.dispose();
        System.exit(0);
    }
}