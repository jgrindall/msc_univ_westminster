/**
 * ImageSingleton.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Central location for image loading
 */


package com.jgrindall.logo.images;
import java.awt.*;
import javax.swing.*;
public class ImageSingleton{
    /**
     * Singleton instance
     */
    protected static ImageSingleton instance;

    private ImageSingleton( ){
        instance = this;
    }
    public Image getImage(String s){
        return Toolkit.getDefaultToolkit().getImage(getClass().getResource(s));
    }
    public ImageIcon getImageIcon(String s){
        return new ImageIcon(  getClass().getResource(s)  );
    }
    public synchronized static ImageSingleton getInstance( ){
        if (instance == null) {
            instance = new ImageSingleton();
        }
        return instance;
    }
}
