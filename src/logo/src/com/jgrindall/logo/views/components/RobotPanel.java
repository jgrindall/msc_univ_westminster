/**
 * RobotPanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *
 */
package com.jgrindall.logo.views.components;
import com.jgrindall.logo.socket.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import com.jgrindall.logo.images.ImageSingleton;
public class RobotPanel extends JPanel {
    // who is control of the robot (the icon changes accordingly)
    public static final String ME = "ME";
    public static final String SOMEBODY_ELSE = "SOMEBODY_ELSE";
    public static final String NOBODY = "NOBODY";
    /*  there are three lists - one for the people waiting, one for those
     logged in and not waiting
    and another containing the person in charge (if anyone).
    The latter is of course not a list (just a name) but I use a list to
     keep the looknfeel consistent
     */
    final private DefaultListModel waitingListModel;
    final private DefaultListModel notWaitingListModel;
    final private DefaultListModel inChargeModel;
    // buttons
    private JButton requestBut;
    private JButton relinquishBut;
    private JButton runBut;
    private JButton stopBut;
    // who is using the robot now.
    private JLabel inChargeLabel;
    public RobotPanel(){
        super();

        // build swing stuff.
        
        this.setLayout(   new BorderLayout()   );
        
        waitingListModel = new DefaultListModel();
        JList waitingList = new JList(waitingListModel);
       // waitingList.setEnabled(false);

        notWaitingListModel = new DefaultListModel();
        JList notWaitingList = new JList(notWaitingListModel);
        //notWaitingList.setEnabled(false);

        inChargeModel = new DefaultListModel();
        JList inChargeList = new JList(inChargeModel);
        //inChargeList.setEnabled(false);


        requestBut = new JButton("request");
        relinquishBut = new JButton("relinquish");
        runBut = new JButton("run on robot");
        stopBut = new JButton("stop robot");
        relinquishBut.setIcon(ImageSingleton.getInstance().getImageIcon("relinquish.png") );
        requestBut.setIcon(ImageSingleton.getInstance().getImageIcon("request.png"));
        runBut.setIcon(  ImageSingleton.getInstance().getImageIcon("robot.png") );
        stopBut.setIcon(  ImageSingleton.getInstance().getImageIcon("robotStop.png") );
        JPanel buttonBox = new JPanel();
        buttonBox.setLayout(new GridLayout(4,1));
        JScrollPane sp1 = new JScrollPane();
        sp1.getViewport().add(waitingList);

        JScrollPane sp2 = new JScrollPane();
        sp2.getViewport().add(notWaitingList);

        inChargeLabel = new JLabel("in charge of the robot:");



        JLabel lbl1 = new JLabel("waiting to use the robot");
        lbl1.setIcon(ImageSingleton.getInstance().getImageIcon("users.png"));

        JLabel lbl2 = new JLabel("other users");


        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel top = new JPanel(new BorderLayout());

        JPanel topBox = new JPanel(new BorderLayout());
        
        topBox.add(inChargeLabel,BorderLayout.NORTH);
        topBox.add(inChargeList,BorderLayout.CENTER);
        topBox.add(lbl1,BorderLayout.SOUTH);


        top.add(topBox, BorderLayout.NORTH);
        top.add(sp1, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());

        bottom.add(lbl2, BorderLayout.NORTH);
        bottom.add(sp2, BorderLayout.CENTER);

        split.setTopComponent(top);
        split.setBottomComponent(bottom);
        
        this.add(split);
        split.setResizeWeight(0.5);

        this.add(buttonBox,BorderLayout.SOUTH);
        buttonBox.add(requestBut);
        buttonBox.add(relinquishBut);
        buttonBox.add(runBut);
        buttonBox.add(stopBut);
        
        iAmInCharge(RobotPanel.NOBODY);

        enableSend(false);
        enableRequest(false);
        enableStop(false);
        enableRelinquish(false);
        
    }
    public void iAmInCharge(String status){
        // change icon depending on who is using the robot now
        if(status.equals(RobotPanel.NOBODY)){
            inChargeLabel.setIcon(ImageSingleton.getInstance().getImageIcon("userDark.png"));
        }
        else if(status.equals(RobotPanel.ME)){
            inChargeLabel.setIcon(ImageSingleton.getInstance().getImageIcon("userRed.png"));
        }
        else if(status.equals(RobotPanel.SOMEBODY_ELSE)){
            inChargeLabel.setIcon(ImageSingleton.getInstance().getImageIcon("userGrey.png"));
        }
    }
    /*
     * refresh the user lists
     */
    public void createUserList(UserSocketObject uObj, String myName){

        waitingListModel.clear();
        notWaitingListModel.clear();
        inChargeModel.clear();

        //TODO:  would be better to use some kind of iterators here

        Vector<User> waiting = uObj.waiting;
        Vector<User> notWaiting = uObj.notWaiting;
        System.out.println("create user list: " );
        uObj.dump();
        if(waiting.size()==0){
             inChargeModel.addElement("nobody!");
             iAmInCharge(  RobotPanel.NOBODY );
        }
        else{
            for(int i=0;i<=waiting.size()-1;i++){
                User u = waiting.elementAt(i);
                if(i==0){
                    inChargeModel.addElement(u.getUserName());
                    if( u.getUserName().equals(myName) ){
                         iAmInCharge(  RobotPanel.ME );
                    }
                    else{
                        iAmInCharge(  RobotPanel.SOMEBODY_ELSE );
                    }
                }
                else{
                    waitingListModel.addElement(u.getUserName());
                }
            }
        }
        for(int i=0;i<=notWaiting.size()-1;i++){
            User u = notWaiting.elementAt(i);
            notWaitingListModel.addElement(u.getUserName());
        }
    }
    public void enableSend(boolean b){
        runBut.setEnabled(b);
    }
    public void enableRequest(boolean b){
        requestBut.setEnabled(b);
    }
    public void enableRelinquish(boolean b){
        relinquishBut.setEnabled(b);
    }
    public void enableStop(boolean b){
        stopBut.setEnabled(b);
    }
    public JButton getRobotButton(){
        return runBut;
    }
    public JButton getRequestButton(){
        return requestBut;
    }
    public JButton getRelinquishButton(){
        return relinquishBut;
    }
    public JButton getStopRobotButton(){
        return stopBut;
    }
}
