/**
 * LogoProcessingProxy.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * This class runs the JavaCC parser in a separate thread.
 * The process() method checks correctness of Logo and
 * starts a thread to walk the tree.
 */
package com.jgrindall.logo.proxy;
import com.jgrindall.logo.utils.TextLocationObject;
import java.io.*;
import com.jgrindall.logo.*;
import com.jgrindall.logojavacc.*;
import com.jgrindall.logo.comms.*;
import org.puremvc.java.patterns.proxy.Proxy;

public class LogoProcessingProxy extends Proxy implements ILogoProcess{
    // The active flag is a safe way to stop the thread
    //
    private Boolean active = false;
    public LogoProcessingProxy(){
        super(NAME, null);  
    }
    public void stop(){
        System.out.println("stop");
        active=false;
    }
    public void process(String logoString) {
       // reference to this object for Thread
       final LogoProcessingProxy thisProxy = this;

       try{
            InputStream istream = new ByteArrayInputStream(logoString.getBytes("UTF-8"));
            final LogoParser parser = new LogoParser( istream );
            final ProgramNode pn = parser.start();
            active = true;
            new  Thread(){
                @Override
                public void run(){
                    try{
                       pn.jjtAccept( new LogoParserVisitorImpl(parser), thisProxy);
                    }
                    catch(ParseException e){
                        parseException(e);
                    }
                }
            }.start();
        }
       catch(StopParseException e){
            // do nothing, the user has stopped the parsing.
       }
       catch(ParseException e){
            parseException(e);
       }

       catch(TokenMgrError e){
           tokenError(e);
       }
       catch(UnsupportedEncodingException e){
            //
       }
       catch(IOException e){
           System.out.println("IOException in process "+e.getMessage());
       }
    }
    public void check() throws ParseException{
        if(!active){
            throw new StopParseException();
        }
    }
    public void accept(ALogoCommandObject obj) throws ParseException{
        if(obj instanceof LogoEndObject){
            stop();
            this.sendNotification(AppFacade.STOP_DRAWING_UPDATE_BUTTONS,null,null);
        }
        else{
            this.sendNotification(AppFacade.DRAW_LOGO,obj,null);
        }
    }
    protected void parseException(ParseException e){
        System.out.println("ParseException "+e.getMessage());
        Token t = e.currentToken;
        if(t!=null){
            TextLocationObject loc = new TextLocationObject(t.next.beginLine,t.next.beginColumn);
            /**
             * this notification is listened to by the text area to
             * display errors with an exclamation mark character
             */
            this.sendNotification(AppFacade.HIGHLIGHT_ERROR,loc,null);
        }
        // display errors in the ErrorPanel
        this.sendNotification(AppFacade.PARSE_ERROR,e.getMessage(),null);
        // and reset the buttons so that the user can click draw again.
        this.sendNotification(AppFacade.STOP_DRAWING_UPDATE_BUTTONS,null,null);
    }
    protected void tokenError(TokenMgrError tme){
        TextLocationObject textLoc = new TextLocationObject(tme.lineNum,tme.colNum);
        this.sendNotification(AppFacade.HIGHLIGHT_ERROR, textLoc, null);
        this.sendNotification(AppFacade.PARSE_ERROR,tme.getMessage(),null);
        this.sendNotification(AppFacade.STOP_DRAWING_UPDATE_BUTTONS,null,null);
    }
}
