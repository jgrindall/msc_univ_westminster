/* Generated By:JJTree: Do not edit this line. ProgramNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=,NODE_EXTENDS=BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.jgrindall.logojavacc;

public
class ProgramNode extends SimpleNode {
  public ProgramNode(int id) {
    super(id);
  }

  public ProgramNode(LogoParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(LogoParserVisitor visitor, Object data) throws ParseException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=3a3fefe31c4af86b3a59dc18b96ed5a3 (do not edit this line) */
