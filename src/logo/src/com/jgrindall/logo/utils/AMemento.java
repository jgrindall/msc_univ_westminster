/**
 * AMemento.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Memento design pattern.  This is an abstract class for all Mementos
 */
package com.jgrindall.logo.utils;
public abstract class AMemento{
    public Object state;
    public AMemento(Object s){
        state=s;
    }
}