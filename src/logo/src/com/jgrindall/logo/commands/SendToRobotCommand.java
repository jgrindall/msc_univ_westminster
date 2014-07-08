/**
 * SendToRobotCommand.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Executed when the user clicks 'execute on robot'
 */
package com.jgrindall.logo.commands;
import com.jgrindall.logo.proxy.*;
import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import com.jgrindall.logojavacc.*;
import com.jgrindall.logo.utils.TextLocationObject;
import java.io.*;
import com.jgrindall.logo.*;
import org.puremvc.java.patterns.command.SimpleCommand;
public class SendToRobotCommand extends SimpleCommand implements ICommand {
    @Override
    public void execute(INotification notification){
        // the LogoProxy class is responsible for holding
        // a reference to the logo string
        LogoProxy lp = (LogoProxy)facade.retrieveProxy(LogoProxy.NAME);
        String s = lp.getLogo();
        if(s==null || s.length()==0  || s.equals("")){
            System.out.println("empty!...");
            // do not bother to parse or send to the server.
            this.sendNotification(AppFacade.BAD_LOGO_SENT_TO_ROBOT, null, null);
        }
        else{
            try{
                InputStream istream = new ByteArrayInputStream(s.getBytes("UTF-8"));
                LogoParser parser = new LogoParser( istream );
                ProgramNode pn = parser.start();
                // the above lines will throw errors if logo is incorrect
                // in that case we don't continue sending to the robot.
                ISocket sp = (ISocket)facade.retrieveProxy(ISocket.NAME);
                sp.sendLogoToRobot(s);
            }
            catch(ParseException e){
                Token t = e.currentToken;
                if(t!=null){
                    TextLocationObject loc = new TextLocationObject(t.next.beginLine,t.next.beginColumn);
                    this.sendNotification(AppFacade.HIGHLIGHT_ERROR,loc,null);
                }
                this.sendNotification(AppFacade.PARSE_ERROR,e.getMessage(),null);
                this.sendNotification(AppFacade.BAD_LOGO_SENT_TO_ROBOT,null,null);
            }
            catch(UnsupportedEncodingException e){
                //
            }
            catch(TokenMgrError tme){
                TextLocationObject textLoc = new TextLocationObject(tme.lineNum,tme.colNum);
                this.sendNotification(AppFacade.HIGHLIGHT_ERROR, textLoc, null);
                this.sendNotification(AppFacade.PARSE_ERROR,tme.getMessage(),null);
                this.sendNotification(AppFacade.BAD_LOGO_SENT_TO_ROBOT,null,null);
            }
        }
    }
}