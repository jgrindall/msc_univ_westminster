/**
 * LogoCommandUtils.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * utilities to convert from object to string for sending over char streams
 * each object must be converted to string for sending as characters over a
 * stream (the NXT has no object streams)
 *
 * Then on the robot end they must be converted back!
 * ArrayList is the only typed dynamic array type supported by Lejos
 */
package com.jgrindall.logo.comms;
import java.util.ArrayList;

public class LogoCommandUtils  {

    /**
     * variables used for the encoding/decoding
     */
    public final static char END_COMMAND=';';
    public final static char SEPARATOR=':';
    public final static char END_ALL='\n';
    public final static String FD = "fd";
    public final static String RT = "rt";
    public final static String END = "end";
    public static final String ONE_COMMAND_DONE="doneone";
    public static final String ALL_COMMANDS_DONE="doneall";

     /**
      * convert command to string
      * @param obj
      * @return
      */
    public static String toString(ALogoCommandObject obj){
        String name="";
        String data="";
        if(obj instanceof LogoEndObject){
            name = LogoCommandUtils.END;
            data = "0";
        }
        else if(obj instanceof LogoFdObject){
            name = LogoCommandUtils.FD;
            data = (  ((LogoFdObject)obj).data ).toString();
        }
        else if(obj instanceof LogoRtObject){
            name = LogoCommandUtils.RT;
            data = (  ((LogoRtObject)obj).data ).toString();
        }
       return name.toString()+LogoCommandUtils.SEPARATOR+data.toString()+LogoCommandUtils.END_COMMAND;
    }


    /**
     * add object to ArrayList
     * @param arrayList
     * @param commandSB
     * @param dataSB
     */
    private static void add(ArrayList<ALogoCommandObject> arrayList, StringBuffer commandSB, StringBuffer dataSB) throws LogoException{
        String commandString = commandSB.toString();
        try{
            Double data = Double.parseDouble(  dataSB.toString()  );
            if(commandString.equals(LogoCommandUtils.FD) ){
            arrayList.add(new LogoFdObject(data));
            }
            else if(commandString.equals(LogoCommandUtils.RT) ){
                arrayList.add(new LogoRtObject(data));
            }
            else if(commandString.equals(LogoCommandUtils.END) ){
                arrayList.add(new LogoEndObject());
            }
        }
        catch(NumberFormatException nex){
            throw new LogoException("Badly formatted logo commands");
        }
    }

    /**
     *
     * @param s
     * @return
     */
    public static ArrayList<ALogoCommandObject> stringToObject(StringBuffer s) throws LogoException{
        return LogoCommandUtils.stringToObject(s.toString());
    }

    /**
     * turn string back into a list of commands.
     * @param s
     * @return
     */
    public static ArrayList<ALogoCommandObject> stringToObject(String s) throws LogoException{
        // ArrayList is the only typed dynamic array type supported by Lejos
        ArrayList<ALogoCommandObject> arrayList = new ArrayList<ALogoCommandObject>();
        boolean sep = false;
        boolean commandComplete = false;
        StringBuffer commandSB = new StringBuffer();
        StringBuffer dataSB = new StringBuffer();
        for(int i=0; i<= s.length()-1;i++){
            char c = s.charAt(i);
            if(c==LogoCommandUtils.END_ALL){
                // do nothing
            }
            else if(c==LogoCommandUtils.END_COMMAND){
                /* next word.*/
                LogoCommandUtils.add(arrayList, commandSB, dataSB);
                commandSB = new StringBuffer();
                dataSB = new StringBuffer();
                sep=false;
                commandComplete = true;
            }
            else if(c==LogoCommandUtils.SEPARATOR){
                sep=true;
                commandComplete = false;
            }
            else if(sep){
                // append character to data since we have read in the separator.
                dataSB.append(c);
                commandComplete = false;
            }
            else{
                // append character to command since we have
                // not reached the separator.
                commandSB.append(c);
                commandComplete = false;
            }
        }
        if(!commandComplete){
            throw new LogoException("badly formed logo commands");
        }
        return arrayList;
    }
}