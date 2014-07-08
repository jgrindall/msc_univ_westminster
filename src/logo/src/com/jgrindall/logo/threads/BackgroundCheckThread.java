/**
 * BackgroundCheckThread.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * A thread that runs continuously, checking your LOGO syntax for you and
 * displaying errors.
 */
package com.jgrindall.logo.threads;

import java.io.*;
import com.jgrindall.logo.AppFacade;
import com.jgrindall.logo.proxy.LogoProxy;
import com.jgrindall.logojavacc.*;
import com.jgrindall.logo.event.*;

public class BackgroundCheckThread extends Thread implements ILogoEventDispatcher{
    private volatile boolean process = true;
    private LogoEventDispatcher dispatcher;
    public BackgroundCheckThread(){
        super();
        dispatcher = new LogoEventDispatcher();
    }
    public void stopBg(){
        process=false;
    }
    
    public void addLogoEventListener(ILogoEventListener list){
        dispatcher.addLogoEventListener(list);
    }
    public void removeLogoEventListener(ILogoEventListener list){
        dispatcher.removeLogoEventListener(list);
    }
    public void fireLogoParseErrorEvent(LogoEvent e){
        dispatcher.fireLogoParseErrorEvent(e);
    }
    public void fireLogoTokenErrorEvent(LogoEvent e){
        dispatcher.fireLogoTokenErrorEvent(e);
    }
    public void fireLogoFinishedEvent(LogoEvent e){
        dispatcher.fireLogoTokenErrorEvent(e);
    }
    public void fireLogoErrorEvent(LogoEvent e){
        dispatcher.fireLogoErrorEvent(e);
    }
    private String getLogo(){
        LogoProxy lp = (LogoProxy)AppFacade.getInstance().retrieveProxy(LogoProxy.NAME);
        return lp.getLogo();
    }
    public void syntaxCheck() throws ParseException, UnsupportedEncodingException {
        //System.out.println("process background");
        String logo = getLogo();
        InputStream istream = new ByteArrayInputStream(logo.getBytes("UTF-8"));
        LogoParser parser = new LogoParser( istream );
        ProgramNode pn = parser.start();
    }
    @Override
    public void run(){
        while(process){
            try {
                syntaxCheck();
            }
            catch(ParseException pex){
                dispatcher.fireLogoParseErrorEvent(    new LogoParseErrorEvent(this, pex)     );
            }
            catch(TokenMgrError tme){
                dispatcher.fireLogoTokenErrorEvent(    new LogoTokenErrorEvent(this, tme)     );
            }
            catch(UnsupportedEncodingException e){
                //
            }
            finally{
                try{
                    
                    /**
                     * TODO:  This is simplistic - currently sleeping for
                     * 5 seconds. It would be better to wait N seconds after
                     * the last user interaction and then syntax check once!
                     */

                    sleep(5000);
                }
                catch(InterruptedException e){
                    
                }
            }
        }
    }
}
