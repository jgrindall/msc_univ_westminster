/**
 * BackgroundPanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * A panel that loads an optional image
 */
package com.jgrindall.logo.utils;
import javax.swing.*;
import java.awt.*;
public class BackgroundPanel extends JPanel{
    Image image;
    public BackgroundPanel(Image img){
        image=img;
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (image != null){
            g.drawImage(image, 0,0,this.image.getWidth(this),this.image.getHeight(this),this);
        }
    }
}