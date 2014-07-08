/**
 * IUndoable.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *  An interface that says I have undo/redo functionality available.
 */
package com.jgrindall.logo.utils;

public interface IUndoable{
    public void performUndo(AMemento m);
    public void performRedo(AMemento m);
   
}