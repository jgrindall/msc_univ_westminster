/**
 * RightPanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * contains the turtle panel where logo is drawn and the robot panel that
 * deals with users and sending logo to the robot
 */
package com.jgrindall.logo.views.components;

import javax.swing.*;

public class RightPanel extends JSplitPane{
    private JComponent turtlePanel;
    private JComponent robotPanel;
    public RightPanel(){
        super(JSplitPane.HORIZONTAL_SPLIT);
        turtlePanel = new TurtlePanel();
        robotPanel = new RobotPanel();
        this.setLeftComponent(turtlePanel);
        this.setRightComponent(robotPanel);
        this.setResizeWeight(0.85);
    }
    public JComponent getRobotPanel(){
        return robotPanel;
    }
    public JComponent getTurtlePanel(){
        return turtlePanel;
    }
}