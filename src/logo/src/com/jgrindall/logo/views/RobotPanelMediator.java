/**
 * RobotPanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Mediator for the so-called robotpanel swing component.
 * This handles the user queue and sending logo to the robot.
 */
package com.jgrindall.logo.views;
import com.jgrindall.logo.views.components.*;
import org.puremvc.java.interfaces.INotification;
import com.jgrindall.logo.*;
import com.jgrindall.logo.proxy.*;
import com.jgrindall.logo.socket.*;
import java.awt.event.*;
import java.util.*;

public class RobotPanelMediator extends AMediator  {
    public static final String NAME = "RobotPanelMediator";
    private ActionListener robotList;
    private ActionListener reqList;
    private ActionListener relList;
    private ActionListener stopList;
    public RobotPanelMediator(RobotPanel viewComponent){
	super(NAME, null);
        setViewComponent(viewComponent);
        RobotPanel rp = getRobotPanel();

        // add listeners to swing components
        robotList = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            // send logo to robot to draw
                            robotButtonPressed();
                        }
                    };

        reqList = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            requestButtonPressed();
                        }
                    };
        relList = new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        relinquishButtonPressed();
                    }
                };
        stopList = new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        stopButtonPressed();
                    }
                };
        rp.getRobotButton().addActionListener(robotList);
        rp.getRequestButton().addActionListener(reqList);
        rp.getRelinquishButton().addActionListener(relList);
        rp.getStopRobotButton().addActionListener(stopList);
        disableAll();
    }
    private void disableAll(){
        this.enableRequest(false);
        this.enableRelinquish(false);
        this.enableSend(false);
        this.enableStop(false);
    }
    @Override
    public void destroy(){
        super.destroy();
        RobotPanel rp = getRobotPanel();
        rp.getRobotButton().removeActionListener(robotList);
        rp.getRequestButton().removeActionListener(reqList);
        rp.getRelinquishButton().removeActionListener(relList);
        rp.getStopRobotButton().removeActionListener(stopList);
        robotList=null;
        reqList=null;
        relList=null;
    }
    private void requestButtonPressed(){
        // triggers RequestRobotCommand
        this.sendNotification(AppFacade.REQUEST_ROBOT, null, null);
        this.enableRequest(false);
    }
    private void stopButtonPressed(){
        // triggers StopRobotCommand
        this.sendNotification(AppFacade.STOP_ROBOT, null, null);
        this.enableStop(false);
    }
    private void relinquishButtonPressed(){
        // triggers RelinquishRobotCommand
        this.sendNotification(AppFacade.RELINQUISH_ROBOT, null, null);
        this.enableRelinquish(false);
        this.enableSend(false);
    }
    private void robotButtonPressed(){
        this.enableSend(false);
        this.enableRelinquish(false);
        // triggers SendToRobotCommand
        this.sendNotification(AppFacade.SEND_TO_ROBOT, null, null);
    }
    @Override
    public String[] listNotificationInterests(){
        return new String[]{AppFacade.DISABLE_SOCKET_BUTTONS,AppFacade.UPDATE_USERS_VIEW, AppFacade.BAD_LOGO_SENT_TO_ROBOT};
    }
    private RobotPanel getRobotPanel(){
        return (RobotPanel)viewComponent;
    }
    private void configButtons(UserSocketObject uObj){
        // Depending on whether you are using the robot and whether it
        // is currently executing or not, or whether you are allowed
        // to relinquish/request, update the enabled state of the buttons.

        System.out.println("configbuttons");
        Vector<User> waiting = uObj.waiting;
        UserProxy uProxy = (UserProxy) facade.retrieveProxy(UserProxy.NAME);
        String myName = uProxy.getUserName();
        boolean inChargeOfRobot=false;
        if(waiting.size()>=1){
            User topUser = waiting.elementAt(0);
            inChargeOfRobot = (topUser.getUserName().equals(myName));
        }
        boolean executing = uObj.getExecuting();
        boolean enableRequestButton=false;
        boolean enableRelinquishButton=false;
        boolean enableSendButton=false;
        boolean enableStopButton=false;
        if(inChargeOfRobot){
            if(executing){
                enableRequestButton = false;
                enableRelinquishButton = false;
                enableSendButton = false;
                enableStopButton = true;
            }
            else{
                enableRequestButton = false;
                enableRelinquishButton = true;
                enableSendButton = true;
                enableStopButton = false;
            }
        }
        else{
            enableRequestButton = !uObj.isWaiting(myName);
            enableRelinquishButton = false;
            enableSendButton = false;
            enableStopButton = false;
        }
        enableSend(enableSendButton);
        enableStop(enableStopButton);
        enableRequest(enableRequestButton);
        enableRelinquish(enableRelinquishButton);
    }
    private void enableStop(boolean b){
        getRobotPanel().enableStop(b);
    }
    private void enableSend(boolean b){
        // enable/disable the buttons according to what the user can do
        getRobotPanel().enableSend(b);
    }
    private void enableRequest(boolean b){
        // enable/disable the buttons according to what the user can do
        getRobotPanel().enableRequest(b);
    }
    private void enableRelinquish(boolean b){
        // enable/disable the buttons according to what the user can do
        getRobotPanel().enableRelinquish(b);
    }
    public void setUsers(UserSocketObject obj){
         UserProxy uProxy = (UserProxy) facade.retrieveProxy(UserProxy.NAME);
         String myName = uProxy.getUserName();
         System.out.println("***********  set users "+obj);
         getRobotPanel().createUserList(obj, myName);
         configButtons(obj);
    }
    @Override
    public void handleNotification(INotification notification){
    	if(notification.getName().equals(AppFacade.UPDATE_USERS_VIEW)){
            // take the user list and update the display
            UserSocketObject userList = (UserSocketObject)notification.getBody();
            setUsers(userList);
        }
        else if(notification.getName().equals(AppFacade.BAD_LOGO_SENT_TO_ROBOT)){
            // your logo never made it to the robot, so re-enable the buttons
            this.enableSend(true);
            this.enableRelinquish(true);
        }
        else if(notification.getName().equals(AppFacade.DISABLE_SOCKET_BUTTONS)){
            this.disableAll();
        }
    }
}
