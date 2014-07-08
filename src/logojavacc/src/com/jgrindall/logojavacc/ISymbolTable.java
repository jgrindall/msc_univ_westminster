/**
 * ISymbolTable.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *
 *  Interface any symbol table must implement
 *
 */

package com.jgrindall.logojavacc;

public interface ISymbolTable{

    /*  add a function.  For Logo it makes sense to treat functions differently
     For other more complex languages it would not make sense
     */
    
    void addFunction(String fnname, Object meaning) throws SymTableException;

    Object lookUpFunction(String fnname);


    /**
    * Adds the symbol (and its associated meaning) 
    */
    void add(String symbol, Object meaning);


    /**
    * Looks up the meaning of symbol.
    */
    Object lookup(String symbol) ;


   /**
   * Adds a new dictionary to the stack. This method must be called prior to calls that add or look up symbols.
   */
    void enterBlock() throws SymTableException;



   /**
   * Removes the top dictionary from the stack.
   */
    void exitBlock() ;

}