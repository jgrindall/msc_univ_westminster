/**
 * StopParseException.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * A special kind of ParseException that is thrown when the
 * parser is terminated by the user midway through processing
 */
package com.jgrindall.logojavacc;

public class StopParseException extends ParseException {
    public StopParseException(){
        super();
    }
    public StopParseException(String message){
        super(message);
    }
    public StopParseException(Token currentTokenVal,
                        int[][] expectedTokenSequencesVal,
                        String[] tokenImageVal
                       ){
        super(currentTokenVal,expectedTokenSequencesVal,tokenImageVal );
    }
}
