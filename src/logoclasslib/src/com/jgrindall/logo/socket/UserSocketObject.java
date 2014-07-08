/**
 * UserSocketObject.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * A class shared by all parts of the app (front and back end)
 * represents the user list (waiting, not waiting, and
 * is the robot busy executing logo)
 */
package com.jgrindall.logo.socket;
import java.util.*;
public class UserSocketObject extends ASocketObject{
    // people waiting to use the robot
    public Vector<User> waiting;

    // people logged on but not waiting (they have not clicked request)
    public Vector<User> notWaiting;

    // is the robot executing LOGO?
    private boolean executing  = false;

    public UserSocketObject(){
       waiting = new Vector<User>();
       notWaiting = new Vector<User>();
    }
    public void destroy(){
        waiting.clear();
        notWaiting.clear();
    }
    public boolean getExecuting(){
        return executing;
    }
    /**
     *
     * @param name
     * @return
     *
     * Is this person logged on?
     */
    public boolean contains(String name){
        return isWaiting(name) || isNotWaiting(name);
    }
    /**
     *
     * @param name
     * @return
     *
     * Is this person in the waiting queue?
     */
    public boolean isWaiting(String name){
        Iterator<User> itr = waiting.iterator();
        while(itr.hasNext()){
            User u = itr.next();
            if(name.equals(u.getUserName())){
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param name
     * @return
     *
     * Is this person in the not waiting queue?
     */
    public boolean isNotWaiting(String name){
        Iterator<User> itr = notWaiting.iterator();
        while(itr.hasNext()){
            User u = itr.next();
            if(name.equals(u.getUserName())){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString(){
        String s="waiting:\n";
        Iterator<User> itr = waiting.iterator();
        while(itr.hasNext()){
            User u = itr.next();
            s+=u.getUserName()+"\n";
        }
        s+="not waiting:\n";
        itr = notWaiting.iterator();
        while(itr.hasNext()){
            User u = itr.next();
            s+=u.getUserName()+"\n";
        }
        return s;
    }
    public void dump(){
        System.out.println(this.toString());
    }
    public boolean userIsExecuting(String uName){
        if(waiting.size()>=1){
            User u = waiting.elementAt(0);
            return u.getUserName().equals(uName);
        }
        return false;
    }
    /**
     *
     * @param b
     */
    public void setExecuting(boolean b){
        executing = b;
    }
    /**
     *
     * @param usr
     * 
     */
    public void addToNotWaiting(User usr){
        System.out.println("add "+usr.getUserName());
        notWaiting.add(usr);
        dump();
    }
    /**
     *
     * @param usr
     * remove user completely
     */
    public void remove(User usr){
        System.out.println("remove "+usr.getUserName());
        if(userIsExecuting(usr.getUserName())){
            executing=false;
        }
        waiting.remove(usr);
        notWaiting.remove(usr);
        dump();
    }
    /**
     *
     * @param usr
     * if not in waiting queue, move to waiting.
     * 
     */
    public void moveToWaiting(User usr){
        System.out.println("move to waiting"+usr.getUserName());
        if(!isWaiting(usr.getUserName())){
            System.out.println("success   ");
            notWaiting.remove(usr);
            waiting.add(usr);
            dump();
        }
        else{
            System.out.println("failed");
        }
    }
    /**
     * 
     * @param usr
     */
    public void moveToNotWaiting(User usr){
        System.out.println("move to not waiting"+usr.getUserName());
        waiting.remove(usr);
        notWaiting.add(usr);
        dump();
    }
}