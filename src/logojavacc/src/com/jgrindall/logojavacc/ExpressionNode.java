/* Generated By:JJTree: Do not edit this line. ExpressionNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=,NODE_EXTENDS=BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.jgrindall.logojavacc;

public
class ExpressionNode extends SimpleNode {
  public ExpressionNode(int id) {
    super(id);
  }

  public ExpressionNode(LogoParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(LogoParserVisitor visitor, Object data) throws ParseException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=8926797a1e4ab050b295cf9d4053aebc (do not edit this line) */