/**
 * BGCheckLogoPanelDecorator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * A specific decorator to add background syntax checking.
 */
package com.jgrindall.logo.views;

import com.jgrindall.logo.utils.TextLocationObject;
import com.jgrindall.logo.*;
import com.jgrindall.logojavacc.*;
import com.jgrindall.logo.threads.*;
import com.jgrindall.logo.event.*;

public class BGCheckLogoPanelDecorator extends ALogoPanelDecorator  {
    public static final String NAME = "BGCheckLogoPanelDecorator";
    private BackgroundCheckThread bg;
    private ILogoEventListener logoList;
    public BGCheckLogoPanelDecorator(ALogoPanelMediator medToWrap){
	super(NAME, medToWrap);
        bg = new BackgroundCheckThread();
        // thread to do processing outside of main thread.
        // This class handles the responsibility of getting the
        // Logo and catching the errors.
        logoList=new LogoEventAdapter(){
            @Override
            public void parseErrorEventPerformed(LogoEvent e){
                parseException(e.getData());
            }
            @Override
            public void tokenErrorEventPerformed(LogoEvent e){
                tokenError(e.getData());
            }
        };
        
        bg.addLogoEventListener(logoList);
        bg.start();
    }
    @Override
    public void destroy(){
        super.destroy();
        bg.stopBg();
        bg.removeLogoEventListener(logoList);
        bg=null;
        logoList=null;
    }
    public void parseException(Object data){
        ParseException pe = (ParseException)data;
        System.out.println("PARSE EXCEPTION "+pe.getMessage());
        Token t = pe.currentToken;
        TextLocationObject textLoc = new TextLocationObject(t.next.beginLine,t.next.beginColumn);
        this.sendNotification(AppFacade.HIGHLIGHT_ERROR, textLoc, null);
        this.sendNotification(AppFacade.PARSE_ERROR, pe.getMessage(), null);
    }
    public void tokenError(Object data){
        TokenMgrError tme = (TokenMgrError)data;
        TextLocationObject textLoc = new TextLocationObject(tme.lineNum,tme.colNum);
        this.sendNotification(AppFacade.HIGHLIGHT_ERROR, textLoc, null);
        this.sendNotification(AppFacade.PARSE_ERROR, tme.getMessage(), null);
    }
}
