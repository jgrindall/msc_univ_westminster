/**
 * Memento.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * A Memento that holds a simple string.
 * Used for the text field undo/redo behaviour
 */
package com.jgrindall.logo.utils;
public class Memento extends AMemento{
    public String state;
    public Memento(String s){
        super(s);
    }
}