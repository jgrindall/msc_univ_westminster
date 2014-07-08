/**
 * SymbolTable.java
 * @author jgrindall  & Lennart Anderson
 * Created
 * Last modified
 * **********************************
 *
 * Implementation ideas taken from http://www.cs.lth.se/EDA180/2009/examples/CalcAnalysis/
 *
 * Function table, MAX_BLOCKS and error handling added by jgrindall
 *
 * Errors are thrown for errors that cannot happen after the parser and visitor
 * are correctly implemented (ie. can never reach the user).
 *
 * SymTableExceptions are thrown for errors that could occur and should be reported to the user.
 *
 */

package com.jgrindall.logojavacc;
import java.util.Hashtable;

/**
 * Maintains a stack of dictionaries for block-structured symbol handling.
 */

public class SymbolTable implements ISymbolTable{

    private StackedDictionary top = null; // The top block on the stack.

    /*  Added by jgrindall - to keep procedure defintions in a separate table.
     * In logo there are few nested blocks - just inside a procedure or in the 'main' procedure
     */
    private Hashtable functionTable = new Hashtable();
    private int numBlocks = 0;  // The number of blocks on the stack

    /*  added by jgrindall - to prevent deep recursion  */
    public static final int MAX_BLOCKS = 256;

    /* added by jgrindall - stores a function in functionTable  */
    
    public void addFunction(String fnname, Object meaning) throws SymTableException{
        if(functionTable.get(fnname)==null){
            // not found
            functionTable.put(fnname, meaning);
        }
        else{
            throw new SymTableException("Procedure "+fnname+" was already defined");
        }
    }

    /* added by jgrindall - retrieves a function from functionTable  */

    public Object lookUpFunction(String fnname){
        return functionTable.get(fnname);
        
    }


    /**
    * Adds the symbol (and its associated meaning) to the top dictionary.
    * The symbol will shadow existing symbols of the same name.
    */
    public void add(String symbol, Object meaning){
        //System.out.println("-------->  ADD to sym table "+symbol+", "+meaning.toString());
        if (top == null){
            throw new Error("SymbolTable.add was called without any prior call to enterBlock");
        }
        top.t.put(symbol, meaning);
    }

    /**
    * Looks up the meaning of symbol.
    */
    public Object lookup(String symbol) {
        if (top == null){
            throw new Error("SymbolTable.lookup was called without any prior call to enterBlock");
        }  
        Object o = top.lookup(symbol);
        return o;
       
    }

  /**
   * Adds a new dictionary to the stack. This method must be called prior to calls that add or look up symbols.
   */
    public void enterBlock() throws SymTableException{
        if(numBlocks<MAX_BLOCKS){
            top = new StackedDictionary(top); // push a new element on top of the stack
            numBlocks++;
        }
        else{
            throw new SymTableException("Overflow");
        }
    }

  /**
   * Removes the top dictionary from the stack.
   */
    public void exitBlock() {
        if (top == null){
            throw new Error("SymbolTable.exitBlock was called without any prior call to enterBlock");
        }
        StackedDictionary wasTop = top;
        top = top.next;  //pop the top element of the stack
        wasTop.destroy();
        numBlocks--;
    }

    /**
    * Returns true if the symbol is already in the top dictionary
    */
    public boolean alreadyDeclared(String symbol) {
        if (top == null){
            throw new Error("Top stack is null, has enter block been called?");
        }
        return top.t.get(symbol) != null;
    }

    /**
    * Returns the current block level (= the number of dictionaries on the stack)
    */
    public int blockLevel() {
        return numBlocks;
    }

    private class StackedDictionary {
        Hashtable t = new Hashtable();
        StackedDictionary next;

        StackedDictionary(StackedDictionary next) {
            this.next = next;
        }
        public void destroy(){
            t.clear();
            t = null;
            next = null;
        }
        public Object lookup(String symbol) {
            Object result = t.get(symbol);
            if (result == null && next != null){
                return next.lookup(symbol);
                // possible stack error here!
            }
            else{
                return result;
            }
        }
    }
}
