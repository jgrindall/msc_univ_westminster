/* Generated By:JJTree: Do not edit this line. MinusExpressionNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=,NODE_EXTENDS=BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.jgrindall.logojavacc;

public
class MinusExpressionNode extends SimpleNode {
  public MinusExpressionNode(int id) {
    super(id);
  }

  public MinusExpressionNode(LogoParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(LogoParserVisitor visitor, Object data) throws ParseException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a0d911d0705dcfa7ebaf32bcafd5142e (do not edit this line) */
