/* Generated By:JJTree: Do not edit this line. TimesExpressionNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=,NODE_EXTENDS=BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.jgrindall.logojavacc;

public
class TimesExpressionNode extends SimpleNode {
  public TimesExpressionNode(int id) {
    super(id);
  }

  public TimesExpressionNode(LogoParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(LogoParserVisitor visitor, Object data) throws ParseException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=40e838e24f0508ac07fdd7d00ec9c08c (do not edit this line) */
