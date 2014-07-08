/**
 * Utils.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Useful utilities
 */
package com.jgrindall.logo.robot;
import lejos.nxt.LCD;
import java.io.DataInputStream;
import com.jgrindall.logo.comms.LogoCommandUtils;

public class Utils {
    public static final int WAIT = 300;
    /**
     *
     * @param s
     * @param wait
     * helper methods to write useful comments to the LCD of the NXT
     */
    public static void toLCD(String s, boolean wait){
        LCD.clear();
        LCD.drawString(s, 0, 0);
        LCD.refresh();
        if(wait){
            // so that a human can read it!
            try{
                Thread.sleep(Utils.WAIT);
            }
            catch(InterruptedException ie){
                
            }
        }
    }
    public static void toLCD(StringBuffer sb, boolean wait){
        Utils.toLCD(sb.toString(), wait);
    }
    /**
     *
     * @param stop
     * @param dis
     * @param out
     *
     * Read from dis stream, put into buffer out.
     * stop when we reach the stop character
     */
    public static void readUpTo(char stop, DataInputStream dis, StringBuffer out) throws RobotException{
         char c=' ';
         do {
             try{
                c = dis.readChar();
                if(c!=stop){
                    out.append(c);
                }
             }
             catch(Exception e){
                 Utils.toLCD("error 2",true);
                 throw new RobotException(LogoCommandUtils.END);
             }
         }
         while (c!=stop);
    }
}

