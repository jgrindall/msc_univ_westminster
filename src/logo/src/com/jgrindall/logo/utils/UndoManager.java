/**
 * UndoManager.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Generic class to handle undoing.
 * To maintain usefulness it uses abstract class AMemento.
 *
 * TODO:  find reference for details of this
 */
package com.jgrindall.logo.utils;

import java.util.*;
public class UndoManager{
    IUndoable originator;
    ArrayList <AMemento> myList = new ArrayList<AMemento>();
    private int ptr = -1;
    private int MAX =  8;
    public UndoManager(IUndoable orig){
        originator=orig;
    }
    private void clearForwards(){
       for(int i=myList.size()-1;i>ptr;i--){
           myList.remove(i);
       }
    }
    public void add(AMemento m){
        clearForwards();
        if(myList.size()==MAX){
            myList.remove(0);
        }
        else{
            ptr++;
        }
        myList.add(ptr, m);
    }
    public void undo(){
        ptr--;
        originator.performUndo(myList.get(ptr));
    }
    public void redo(){
        ptr++;
        originator.performRedo(myList.get(ptr));
    }
    private void output(){
       System.out.println("----------");
       System.out.println(myList.size()+"  / ptr "+ptr);
       for(int i=0;i<=myList.size()-1;i++){
           System.out.println(i+" "+myList.get(i).state);
       }
       System.out.println("----------");
    }
    public Boolean canRedo(){
        return ptr < myList.size() - 1;
    }
    public Boolean canUndo(){
        return ptr > 0;
    }
}