/**
 * AppFacade.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *
 * Main PureMVC AppFacade.  See www.puremvc.org for details.
 *
 */

package com.jgrindall.logo;

import org.puremvc.java.patterns.facade.Facade;
import com.jgrindall.logo.commands.*;

public class AppFacade extends Facade {
    /*  notification names */

    /* ******** linked to commands  ********/
    public static final String STARTUP = "STARTUP";           // starts PureMVC

    public static final String PROCESS_LOGO = "PROCESS_LOGO";   // draw button pressed
    public static final String SEND_TO_ROBOT="SEND_TO_ROBOT";   // send logo to robot
    public static final String STOP_ROBOT="STOP_ROBOT";         // stop robot moving
    public static final String STORE_LOGO = "STORE_LOGO";       // store logo after edit, to make it accessible to all parts of application
    public static final String REQUEST_ROBOT="REQUEST_ROBOT";   // request button pressed
    public static final String RELINQUISH_ROBOT="RELINQUISH_ROBOT";     // relinquish button pressed
    public static final String ATTEMPT_LOGIN = "ATTEMPT_LOGIN";         // login button pressed
    public static final String LOGOUT = "LOGOUT";                       // logout button pressed
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";         // login successfully
    public static final String DISABLE_SOCKET_BUTTONS = "DISABLE_SOCKET_BUTTONS";   // disable all the socket buttons (send, stop, request, relinquish)
    public static final String UPDATE_USERS_MODEL = "UPDATE_USER_MODEL";            // store users in proxy
    public static final String GO_BACK_CLICKED = "GO_BACK_CLICKED" ;                // go back button clicked


     /* ******** not linked to commands  ********/

      // for navigation between screens
    public static final String GOTO_CREATE = "GOTO_CREATE";
    public static final String GOTO_MYFILES = "GOTO_MYFILES";
    public static final String GOTO_LOGIN = "GOTO_LOGIN";

     // for clearing up
    public static final String CLEAR_ERROR = "CLEAR_ERROR";             // clear errors on text area
    public static final String CLEAR_ERROR_TEXT = "CLEAR_ERROR_TEXT";   // clear error text in bug panel
    public static final String CLEAR_LOGO = "CLEAR_LOGO";               // clear LOGO text
    public static final String CLEAR_DRAWING = "CLEAR_DRAWING";         // clear turtle graphics

    
    public static final String STOP_DRAWING = "STOP_DRAWING" ;          // stop logo drawing on screen
    public static final String START_BUILD_APP = "START_BUILD_APP";     // build application mediators and start socket after logging in
    public static final String STOP_APP = "STOP_APP";                   // remove application mediators and stop socket
    public static final String DEBUG    =   "DEBUG";                    // for debugging!
    public static final String SHOW_POPUP = "SHOW_POPUP";               // to display a message
    public static final String PARSE_ERROR = "PARSE_ERROR";             // parse errors to display in error box
    public static final String UPDATE_USERS_VIEW = "UPDATE_USER_VIEW";       // update user list in gui
    public static final String BAD_LOGO_SENT_TO_ROBOT = "BAD_LOGO_SENT_TO_ROBOT";  // do not send to robot - contains syntax errors


    public static final String DRAW_LOGO = "DRAW_LOGO";                 // contains an actual object to draw on screen
    public static final String START_DRAWING_UPDATE_BUTTONS = "START_DRAWING_UPDATE_BUTTONS";     // update buttons to show we are drawing
    public static final String STOP_DRAWING_UPDATE_BUTTONS = "STOP_DRAWING_UPDATE_BUTTONS";       // update buttons to show we are not drawing
    public static final String HIGHLIGHT_ERROR = "HIGHLIGHT_ERROR";     // highlight LOGO text area
    
    private static AppFacade instance = null;

    public static AppFacade getInst(){
        if (instance == null) {
            instance = new AppFacade();
	}
	return (AppFacade) instance;
    }
    
    @Override
    protected void initializeController(){
        // register commands with notifications
	super.initializeController();
        // start up kick starts the application.  Required by PureMVC
        registerCommand(AppFacade.STARTUP, StartupCommand.class);

        // commands for iteration 1
        registerCommand(AppFacade.ATTEMPT_LOGIN,AttemptLoginCommand.class);
        registerCommand(AppFacade.LOGIN_SUCCESS,LoginSuccessCommand.class);

        registerCommand(AppFacade.STORE_LOGO,StoreLogoCommand.class);
        registerCommand(AppFacade.PROCESS_LOGO,ProcessLogoCommand.class);
        registerCommand(AppFacade.STOP_DRAWING,StopDrawingCommand.class);
        registerCommand(AppFacade.GO_BACK_CLICKED,GoBackCommand.class);

        registerCommand(AppFacade.SEND_TO_ROBOT,SendToRobotCommand.class);
        registerCommand(AppFacade.STOP_ROBOT,StopRobotCommand.class);
        

        // commands for iteration 2
        registerCommand(AppFacade.REQUEST_ROBOT,RequestRobotCommand.class);
        registerCommand(AppFacade.RELINQUISH_ROBOT,RelinquishRobotCommand.class);
        registerCommand(AppFacade.LOGOUT,LogoutCommand.class);
        registerCommand(AppFacade.UPDATE_USERS_MODEL,UpdateUsersModelCommand.class);
        
    }
    public void startup(MainPanel app){
        this.sendNotification(STARTUP, app, null);
    }
}
