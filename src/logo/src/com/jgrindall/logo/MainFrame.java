/**
 * MainFrame.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Parent swing component for application.
 */
package com.jgrindall.logo;
import javax.swing.*;
import java.awt.event.*;
public class MainFrame extends JFrame {
    public MainPanel mainPanel;
    public MainFrame() {
        super();
        mainPanel = new MainPanel();
        this.add(mainPanel);
        this.addWindowListener(new WindowCloseAdapter());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setSize(800,600);
    }
}
 
class WindowCloseAdapter extends WindowAdapter{
    @Override
    public void windowClosing(WindowEvent e){
        e.getWindow().dispose();
        System.exit(0);
    }
}
