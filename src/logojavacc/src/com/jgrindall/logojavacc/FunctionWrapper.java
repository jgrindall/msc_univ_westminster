/**
 * FunctionWrapper.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *  Dedicated class to encapsulate a procedure.
 * varlistnode is the list of input variables
 * fnstatementlistnode is the body of the function.
 */

package com.jgrindall.logojavacc;
import java.util.Vector;
public class FunctionWrapper{
    public VarListNode vlNode;
    public FnStatementListNode slNode;
    public FunctionWrapper(VarListNode v,  FnStatementListNode sl){
        vlNode = v;
        slNode = sl;
    }
}