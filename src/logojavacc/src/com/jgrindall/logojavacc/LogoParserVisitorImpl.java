/**
 * LogoParserVisitorImpl.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Depth first traversal of tree.
 *
 */
package com.jgrindall.logojavacc;
import com.jgrindall.logo.comms.*;
import java.util.*;
public class LogoParserVisitorImpl implements LogoParserVisitor{
    private LogoParser parser;
    private Stack stack = new Stack();
    private ISymbolTable symTable = new SymbolTable();

    public LogoParserVisitorImpl(LogoParser p){
	parser = p;
        try{
            // main block for the entire program
            symTable.enterBlock();
        }
        catch(SymTableException se){
            // 
        }
    }
    public Object visit(SimpleNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        ((ILogoConsumer)data).check();
	return null;
    }
    public Object visit(ExpressionNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        ((ILogoConsumer)data).check();
        return null;
    }
    /*
     * Push the name of the variable onto the stack.
     * It might, for example, be popped off the stack and a number assigned to it
     * after the rhs has been evaluated.
     */
    public Object visit(VariableDefnNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        String name = (String)node.getData("name");
        stackAdd(name);
        ((ILogoConsumer)data).check();
        return null;
    }
    public Object visit(VarListNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        ((ILogoConsumer)data).check();
        return null;
    }
    /* a VariableValueNode means that the variable is not being defined, it is
     being used in another calculation, so it should already be in the
     symbol table.
     */
    public Object visit(VariableValueNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        String varname = (String)node.getData("name");
        Object o = symTable.lookup(varname);
        if(o==null){
            throw getParseException(node, varname+ " not found");
        }
        Double d = (Double)o;
        // accept the value that we looked up onto the stack for use later.
        stackAdd(d);
        ((ILogoConsumer)data).check();
        return null;
    }
    public Object visit(StatementListNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        ((ILogoConsumer)data).check();
        return null;
    }/**
      *
     * @param node
     * @param data
     * @return
     * @throws ParseException
     */
     public Object visit(FnStatementListNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        ((ILogoConsumer)data).check();
        return null;
    }
     /**
      *
      * @param node
      * @param data
      * @return
      * @throws ParseException
      *
      * This is the one place where recursion could cause an
      * overflow error at runtime.
      * Catch it and report a parse exception instead.
      */
    public Object visit(FnNameNode node, Object data) throws ParseException{
        try{
            node.childrenAccept(this, data);
        }
        catch(StackOverflowError s){
            ParseException pe = this.getParseException(node, "Stack overflow error, too many depths of recursion");
            throw pe;
        }
        ((ILogoConsumer)data).check();
        return null;
    }
    /**
     *
     * @param node
     * @param data
     * @return
     * @throws ParseException
     *
     * this node is for example  rpt (1+:a+:b) [ statements ]
     */
    public Object visit(RptStatementNode node, Object data) throws ParseException{
        ExpressionNode loopNumNode = (ExpressionNode)node.children[0];
        FnStatementListNode statementListNode = (FnStatementListNode)node.children[1];
        visit(loopNumNode,data);        
        //now the value which is the number of times we want
        // to loop is on the stack.
        Double loopNumDouble = 0.0;
        long loopNumLong=0;
        Object pop = stackPop();
        try{
           loopNumDouble =(Double)pop;
           loopNumLong = Math.round(loopNumDouble.doubleValue());
        }
        catch(Exception e){
            // not even a number!
            throw this.getParseException(node, "error converting "+pop+" to number");   
        }
        if(loopNumLong<0){
            // it should not be negative
            throw this.getParseException(node, "Number of repeats should be > 0");
        }
        if(Math.floor(loopNumDouble) != loopNumDouble){
            throw this.getParseException(node, "Number of repeats should be a whole number");
        }
        
        for(int i=1;i<=loopNumLong;i++){
            // visit them too, the right number of times.
            visit(statementListNode,data);
        }
        // do not visit children!
        ((ILogoConsumer)data).check();
        return null;
    }
    /**
     *
     * @param node
     * @param data
     * @throws ParseException
     *
     * When we visit a fncall node we check that we are calling it
     * with the right number of parameters (all are doubles so we
     * dont type check)  The parameters are in the VarListNode
     * 
     */
    private void executeFunction(FnCallNode node, Object data) throws ParseException{
        FnNameNode fn = (FnNameNode)node.children[0];
        String fnname = (String)fn.getData("name");
        Vector<Double> d = new Vector<Double>();
        while(this.stackSize()>=1){
            //remove all args
            Double argval = (Double)this.stackPop();
            d.add(argval);
        }
        FunctionWrapper fw = (FunctionWrapper)symTable.lookUpFunction(fnname);
        //check args match
        if(fw==null){
            // function not found in symbol table;
            throw getParseException(node,"Procedure "+fnname+" not found");
        }
        VarListNode vlNode = fw.vlNode;
        FnStatementListNode flNode = fw.slNode;
        /**
         * enter a new block so that variables can shadow
         */
        try{
            symTable.enterBlock();
        }
        catch(StackOverflowError soe){
            ParseException pe = this.getParseException(node, "Stack overflow error, too many depths of recursion");
            throw pe;
        }
        catch(SymTableException ste){
            ParseException pe = this.getParseException(node, "Stack overflow error, too many depths of recursion");
            throw pe;
        }

        if(vlNode==null || vlNode.children==null){
            // no children
            if(d.size()!=0){
                throw getParseException(node,"Mismatch in arguments in procedure "+fnname);
            }
        }
        else{
            // has some children
            if(   vlNode.children.length!=d.size()    ){
                throw getParseException(node,"Mismatch of arguments in procedure "+fnname);
            }
            else{
                /* same size, accept them into the symbol table
                 so we can use them in the rest of the statements
                 */
                for(int i=0;i<=d.size()-1;i++){
                    VariableValueNode vn = (VariableValueNode)vlNode.children[i];
                    String varname = (String)vn.getData("name");
                    Double val = d.elementAt(d.size()-1-i);
                    symTable.add(varname, val);
                }
            }
        }
       //  visit the statements !
        visit(flNode,data);
        //

        // and close the block
        symTable.exitBlock();
    }
    public Object visit(ExpressionListNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        ((ILogoConsumer)data).check();
        return null;
    }
    public Object visit(FnCallNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        executeFunction(node,data);
        ((ILogoConsumer)data).check();
        return null;
    }
    public Object visit(RtStatementNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        Double angle = (Double)this.stackPop();
        // output turn right
        ((ILogoConsumer)data).accept(new LogoRtObject(angle));
        ((ILogoConsumer)data).check();
        return node;
    }
  
    public Object visit(FdStatementNode node, Object data) throws ParseException{
        node.childrenAccept(this, data);
        Double steps = (Double)this.stackPop();
        // output go forward
        ((ILogoConsumer)data).accept(new LogoFdObject(steps));
        ((ILogoConsumer)data).check();
        return null;
    }
    /**
     *
     * @param node
     * @param data
     * @return
     * @throws ParseException
     *
     * variable assignment eg.   make :x (:x+:y+2)
     */
    public Object visit(MakeStatementNode node, Object data) throws ParseException{
        // by definition, on the stack is an expressionNode and a variable node
        node.childrenAccept(this, data);
        /*
         * We visited the :x node first, and then we visited the :x+:y+2 node.
         * So, the value of :x+:y+2 will be a numerical value on the stack.
         * And under that on the stack is the string variable name
         */
        Double value = (Double)this.stackPop();
        String varname   = (String)this.stackPop();
        symTable.add(varname,value);
        return null;
    }
    /**
     *
     * @param node
     * @param data
     * @return
     * @throws ParseException
     *
     *
     * This is the main node containing the entire program
     */
    public Object visit(ProgramNode node, Object data) throws ParseException{
        
        node.childrenAccept(this, data);
        /* the end object is used to indicate to anyone listening
         that the program has ended.
        For example we might have to re-enable the draw button in the GUI.
         */
        ((ILogoConsumer)data).accept(new LogoEndObject());
        ((ILogoConsumer)data).check();
        return null;
    }
    /**
     *
     * @param node
     * @param data
     * @return
     * @throws ParseException
     *
     * Defining a function using proc 
     */
    public Object visit (FnDefineNode node, Object data) throws ParseException{
         //System.out.println("VISIT "+node+"  "+node.uniqueid);
         FnNameNode fnn = (FnNameNode)node.children[0];
         String fnname   = (String)fnn.getData("name");
         VarListNode  vln;
         FnStatementListNode  sln;
         if(node.children[1] instanceof VarListNode){
            // some children variables - these are parameters
            vln = (VarListNode)node.children[1];
            sln = (FnStatementListNode)node.children[2];
            
         }
         else{
             // no parameters
            vln = null;
            sln = (FnStatementListNode)node.children[1];
         }
         try{
            symTable.addFunction(fnname,  new FunctionWrapper(vln,sln)  );
         }
         catch(SymTableException e){
             ParseException pe = this.getParseException(node, "Procedure "+fnname+" already defined");
             throw pe;
         }
         ((ILogoConsumer)data).check();
         return null;
    }
    /**
    * The +,-,*,/ nodes are straightforward.  Visit the child nodes first.
     * Their values will be on the stack.  Compute and put onto the stack.
     */
    public Object visit(PlusExpressionNode node, Object data) throws ParseException{
        node.childrenAccept(this,data);
        Double arg1=(Double)stackPop();
        Double arg2=(Double)stackPop();
        Double toAdd = new Double(arg2.doubleValue()+arg1.doubleValue());
	stackAdd(toAdd);
	return null;
    }
    public Object visit(MinusExpressionNode node, Object data) throws ParseException{
	node.childrenAccept(this,data);
        Double arg1=(Double)stackPop();
        Double arg2=(Double)stackPop();
        Double toAdd = new Double(arg2.doubleValue()-arg1.doubleValue());
	stackAdd(toAdd);
        ((ILogoConsumer)data).check();
	return null;
    }
    public Object visit(TimesExpressionNode node, Object data) throws ParseException{
	node.childrenAccept(this,data);
        Double arg1=(Double)stackPop();
        Double arg2=(Double)stackPop();
        Double toAdd = new Double(arg2.doubleValue()*arg1.doubleValue());
	stackAdd(toAdd);
        ((ILogoConsumer)data).check();
	return null;
    }
    public Object visit(DivideExpressionNode node, Object data) throws ParseException{
	node.childrenAccept(this,data);
        Double arg1=(Double)stackPop();
        Double arg2=(Double)stackPop();
        if(arg1.equals(0.0)){
            throw this.getParseException(node, "Division by zero error");
        }
        Double toAdd = new Double(arg2.doubleValue()/arg1.doubleValue());
        if(Double.isInfinite(toAdd) || Double.isNaN(toAdd)){
            throw this.getParseException(node, "Division by zero error");
        }
	stackAdd(toAdd);
        ((ILogoConsumer)data).check();
	return null;
    }
    public Object visit(NegateExpressionNode node, Object data) throws ParseException{
	node.childrenAccept(this,data);
	Double arg1=(Double)stackPop();
        Double toAdd = new Double(-1*arg1.doubleValue());
	stackAdd(toAdd);
        ((ILogoConsumer)data).check();
	return null;
    }
    public Object visit(NumberNode node, Object data) throws ParseException{
	node.childrenAccept(this,data);
	stackAdd(node.getData("value"));
        ((ILogoConsumer)data).check();
	return null;
    }
    private int stackSize(){
        return stack.size();
    }
    private void stackAdd(Object o) {
        stack.push(o);
    }
    private Object stackPop(){
        Object d = stack.pop();
        return d;
    }
    private void printStack(){
        String s="";
        ListIterator li = stack.listIterator();
        while(li.hasNext()){
            s+=(li.next()).toString()+",";
        }
        System.out.println("stack "+s);
    }
    public ParseException getParseException(SimpleNode node, String msg){
        // create a useful exception
        ParseException e = new ParseException(msg);
        e.currentToken=node.firstToken;
        return e;
    }
}
