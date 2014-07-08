/* Generated By:JavaCC: Do not edit this line. LogoParserVisitor.java Version 5.0 */
package com.jgrindall.logojavacc;

public interface LogoParserVisitor
{
  public Object visit(SimpleNode node, Object data) throws ParseException;
  public Object visit(ProgramNode node, Object data) throws ParseException;
  public Object visit(ExpressionNode node, Object data) throws ParseException;
  public Object visit(PlusExpressionNode node, Object data) throws ParseException;
  public Object visit(MinusExpressionNode node, Object data) throws ParseException;
  public Object visit(TimesExpressionNode node, Object data) throws ParseException;
  public Object visit(DivideExpressionNode node, Object data) throws ParseException;
  public Object visit(NegateExpressionNode node, Object data) throws ParseException;
  public Object visit(VariableValueNode node, Object data) throws ParseException;
  public Object visit(NumberNode node, Object data) throws ParseException;
  public Object visit(FnDefineNode node, Object data) throws ParseException;
  public Object visit(VarListNode node, Object data) throws ParseException;
  public Object visit(StatementListNode node, Object data) throws ParseException;
  public Object visit(FnStatementListNode node, Object data) throws ParseException;
  public Object visit(RptStatementNode node, Object data) throws ParseException;
  public Object visit(FnCallNode node, Object data) throws ParseException;
  public Object visit(ExpressionListNode node, Object data) throws ParseException;
  public Object visit(FnNameNode node, Object data) throws ParseException;
  public Object visit(FdStatementNode node, Object data) throws ParseException;
  public Object visit(RtStatementNode node, Object data) throws ParseException;
  public Object visit(MakeStatementNode node, Object data) throws ParseException;
  public Object visit(VariableDefnNode node, Object data) throws ParseException;
}
/* JavaCC - OriginalChecksum=45b47de5ceef7de1049ba2272e9baa4b (do not edit this line) */