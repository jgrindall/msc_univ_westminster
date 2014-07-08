/**
 * ErrorPanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * swing component for error panel.
 */
package com.jgrindall.logo.views.components;

import java.awt.*;
import javax.swing.*;
import com.jgrindall.logo.images.ImageSingleton;
public class ErrorPanel extends JPanel {
    public JTextArea tf;
    public ErrorPanel(){
        super();
        this.setLayout(new BorderLayout());

        JLabel jl = new JLabel("your bugs appear here");
        jl.setIcon(ImageSingleton.getInstance().getImageIcon("bug.png"));
        JScrollPane jsp = new JScrollPane();
        
        tf = new JTextArea();
        tf.setWrapStyleWord(true);
        tf.setLineWrap(true);
        tf.setEditable(false);
        tf.setFont(new Font("Courier New",  Font.PLAIN, 12));

        this.add(jl,BorderLayout.NORTH);
        this.add(jsp,BorderLayout.CENTER);

        jsp.getViewport().add(tf);
      
        tf.setBorder(BorderFactory.createLineBorder(Color.gray));
        
    }
    public void setText(String s){
        tf.setText(s);
    }
}
