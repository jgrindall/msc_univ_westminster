/**
 * FilesPanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * INCOMPLETE
 *
 */
package com.jgrindall.logo.views.components;
import java.awt.*;
import javax.swing.*;
import com.jgrindall.logo.images.ImageSingleton;
public class FilesPanel extends JPanel {
    private JButton forwardButton;
    private JButton logoutButton;
    public FilesPanel(){
        super();
        this.setLayout(new BorderLayout());
        String msg1 = "This version of robologo does not include loading and saving files.";
        String msg2 = "Please click on \"new file\" to start a new Logo file.";

        Box vbox = Box.createVerticalBox();
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(vbox);
        vbox.add(new JLabel(msg1));
        vbox.add(new JLabel(msg2));

        add(p,BorderLayout.CENTER);
        forwardButton = new JButton("new file");
        logoutButton = new JButton("logout");

        forwardButton.setIcon(ImageSingleton.getInstance().getImageIcon("add.png"));
        logoutButton.setIcon(ImageSingleton.getInstance().getImageIcon("logout.png"));
        JPanel hbox = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(hbox,BorderLayout.SOUTH);
        hbox.add(forwardButton);
        hbox.add(logoutButton);
    }
    public JButton getForwardButton(){
        return forwardButton;
    }
    public JButton getLogoutButton(){
        return logoutButton;
    }
}

