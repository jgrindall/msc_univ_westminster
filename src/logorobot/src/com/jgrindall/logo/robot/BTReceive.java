/**
 * BTReceive.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *  Main executable running on robot.
 *  Uses ideas from BTReceive (in NXT install/examples folder)
 */
package com.jgrindall.logo.robot;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.nxt.Button;
import lejos.nxt.Sound;
import com.jgrindall.logo.comms.LogoCommandUtils;

public class BTReceive {
    /**
     * many different kinds of robot are possible which might implement
     * Fd, rt in different ways.
     */
    private ARobot myRobot;
    /*
     * Since the robot can only accept char streams, not object streams,
     * I 'interpret' the char streams and convert them to objects in a
     * separate class.
     */
    private IInterpreter myInterpreter;
    public static void main(String [] args)  throws Exception {
        new BTReceive();
    }
    
    public BTReceive(){
        // my robot has two tyres, so I call it a TyredRobot
        myRobot = new TyredRobot();
        myInterpreter = new ConcreteInterpreter(myRobot);
        // listen to button presses to terminate program
        new ListenThread().start();
        init();
    }
    private void init(){
        while(true){
            try{
                Utils.toLCD("waiting for commands...", false);
                BTConnection btc = Bluetooth.waitForConnection();
                DataInputStream dis     =   btc.openDataInputStream();
                DataOutputStream dos    =   btc.openDataOutputStream();
                while (true){
                    // StringBuffers are more memory efficient than
                    // instantiating new Strings all the time
                    StringBuffer allCharsSB = new StringBuffer();
                    try{
                        Utils.readUpTo(LogoCommandUtils.END_ALL, dis, allCharsSB);
                        Utils.toLCD("drawing", false);
                        myInterpreter.interpret(allCharsSB);
                    }
                    catch(RobotException e){
                        dos.writeChars(LogoCommandUtils.ALL_COMMANDS_DONE+LogoCommandUtils.END_ALL);
                        dos.flush();
                        dos.close();
                        dis.close();
                        btc.close();
                        //break out of this loop into the outer loop.
                        Sound.twoBeeps();
                        break;
                    }
                    dos.writeChars(LogoCommandUtils.ONE_COMMAND_DONE+LogoCommandUtils.END_ALL);
                    dos.flush();
                }
            }
            catch(IOException e){
                Utils.toLCD("ioex",true);
            }
        }
    }
    private class ListenThread extends Thread{
        @Override
        public void run(){
            Button.waitForPress();
            System.exit(0);
        }
    }
}
