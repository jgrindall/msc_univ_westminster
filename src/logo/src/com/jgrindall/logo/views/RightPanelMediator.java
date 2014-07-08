/**
 * RightPanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *  The panel that contains two components:
 *  1. the turtle graphics panel
 *  2. the robot panel (that handles the backend communication
 * to the robot and the user list)
 */
package com.jgrindall.logo.views;

import com.jgrindall.logo.views.components.*;

public class RightPanelMediator extends AMediator  {
    public static final String NAME = "RightPanelMediator";
    private TurtlePanelMediator tMediator;
    private RobotPanelMediator rMediator;

    public RightPanelMediator(RightPanel viewComponent){
	super(NAME, null);
        setViewComponent(viewComponent);
        TurtlePanel tp = (TurtlePanel)getRightPanel().getTurtlePanel();
        tMediator=new TurtlePanelMediator(tp);
        facade.registerMediator(tMediator);

        RobotPanel rp = (RobotPanel)getRightPanel().getRobotPanel();
        rMediator = new RobotPanelMediator(rp);
        facade.registerMediator(rMediator);
    }
    @Override
    public void destroy(){
        super.destroy();
        facade.removeMediator(tMediator.getMediatorName());
        facade.removeMediator(rMediator.getMediatorName());
        tMediator.destroy();
        rMediator.destroy();
        tMediator=null;
        rMediator=null;
    }
    @Override
    public String[] listNotificationInterests(){
        return new String[]{};
    }
    private RightPanel getRightPanel(){
        return (RightPanel)viewComponent;
    }
}
