/* Generated By:JJTree: Do not edit this line. VariableValueNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=,NODE_EXTENDS=BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.jgrindall.logojavacc;

public
class VariableValueNode extends SimpleNode {
  public VariableValueNode(int id) {
    super(id);
  }

  public VariableValueNode(LogoParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(LogoParserVisitor visitor, Object data) throws ParseException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4b0989cb2c2f51f830b52edb2addd2c2 (do not edit this line) */