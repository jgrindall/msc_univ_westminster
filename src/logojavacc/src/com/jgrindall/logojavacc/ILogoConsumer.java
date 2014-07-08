/**
 * ILogoConsumer.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Base interface for any class that takes a Logo command such as fd/rt
 * and does something with it.  
 */
package com.jgrindall.logojavacc;
import com.jgrindall.logojavacc.ParseException;
import com.jgrindall.logo.comms.ALogoCommandObject;
public interface ILogoConsumer{
    /*
     * the parser pushes objects into an ILogoConsumer
     */
    public void accept(ALogoCommandObject obj) throws ParseException ;

    /*
     * the check method is usde by the parser when ANY node is visited
     * otherwise we could only check for errors when Fd/Rt statements
     * were visited
     */
    public void check() throws ParseException;
}