package com.jgrindall.logojavacc;
/**
 * ALogoCommandObject.java
 * @author John
 * Created
 * Last modified
 *
 * For testing the interpreter.  Simply outputs commands
 */
import com.jgrindall.logo.comms.*;
public class TestLogoWriter implements ILogoConsumer {
    private int num = 0;
    public static final int MAX = 40;
    public void accept(ALogoCommandObject obj) throws ParseException {
        System.out.println("OUTPUT:  "+LogoCommandUtils.toString(obj)  );
        num++;
        if(num==TestLogoWriter.MAX){
            throw new ParseException("LIMIT REACHED SUCCESSFULLY");
        }
    }
    public void check() throws ParseException{

    }
}
