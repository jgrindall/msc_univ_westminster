/* Generated By:JJTree: Do not edit this line. FnNameNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=,NODE_EXTENDS=BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.jgrindall.logojavacc;

public
class FnNameNode extends SimpleNode {
  public FnNameNode(int id) {
    super(id);
  }

  public FnNameNode(LogoParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(LogoParserVisitor visitor, Object data) throws ParseException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=fda4cc021e168bf791b7363f4fe4a852 (do not edit this line) */
