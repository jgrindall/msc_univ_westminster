/**
 * LoginPanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * swing component for log in. two text fields and a button
 */
package com.jgrindall.logo.views.components;
import java.awt.*;
import javax.swing.*;
import com.jgrindall.logo.utils.BackgroundPanel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class LoginPanel extends BackgroundPanel {
    private JTextField loginName;
    private JPasswordField loginPassword;
    private JTextField hostName;
    private JButton loginButton;
    private JCheckBox check;
    public LoginPanel(Image img){
        super(img);
        this.setLayout(new GridBagLayout());

        loginName = new JTextField(16);
        loginButton = new JButton("login");
        check = new JCheckBox("Connect to robot");
        check.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                hostName.setEnabled(  connectBoolean()   );
            }
        });
        check.setToolTipText("If your teacher has a Lego TM robot working in your classroom, check this box and ask for the connection information");
        Box vbox = Box.createVerticalBox();

        JPanel p1 = new JPanel(new FlowLayout());
        p1.add( new JLabel("username"));
        p1.add(loginName);

        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(new JLabel("password"));
        loginPassword = new JPasswordField(16);
        p2.add(loginPassword);


        JPanel p3 = new JPanel(new FlowLayout());
        hostName = new JTextField(24);
        p3.add(hostName);


        JPanel p4 = new JPanel(new FlowLayout());
        p4.add(check);

        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(vbox);

        vbox.add(p1);
        vbox.add (Box.createRigidArea(new Dimension(10,10)));
        vbox.add(p2);
        vbox.add (Box.createRigidArea(new Dimension(10,10)));
        vbox.add(loginButton);
        vbox.add (Box.createRigidArea(new Dimension(10,65)));
        vbox.add(p4);
        vbox.add (Box.createRigidArea(new Dimension(10,5)));
        vbox.add(p3);
        
        this.add(p, new GridBagConstraints());

        loginName.setText("user1");
        loginPassword.setText("user1");
        hostName.setText("localhost");
        hostName.setEnabled(false);
    }
    public JButton getLoginButton(){
        return loginButton;
    }
    public String getHostName(){
        return hostName.getText();
    }
    public String getUserName(){
        return loginName.getText();
    }
    public JTextField getPasswordTextField(){
        return loginPassword;
    }
    public String getPassword(){
        return loginPassword.getText();
    }
    public boolean connectBoolean(){
        return check.isSelected();
    }
}