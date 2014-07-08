/**
 * StyleUtils.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Styles the whole application.  Requires substance.jar and trident.jar
 */
package com.jgrindall.logo.style;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.*;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;

public class StyleUtils{
    public static void setUIFont (FontUIResource f){
        // Code snippet from http://www.rgagnon.com/javadetails/java-0335.html
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (   keys.hasMoreElements()   ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource){
                UIManager.put(key, f);
            }
        }
    }
    
    public static void setSubstanceStyle(){
        try{
            UIManager.setLookAndFeel(   new SubstanceGraphiteGlassLookAndFeel()   );
        }
        catch(Exception e){
            e.printStackTrace();
        }
        StyleUtils.setUIFont (   new FontUIResource("Helvetica",Font.PLAIN,13)   );
     }
}