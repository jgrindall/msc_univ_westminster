/**
 * TextLocationObject.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Object to store the location of an error (line num, col num)
 */
package com.jgrindall.logo.utils;
public class TextLocationObject{
    private int lineNum;
    private int colNum;

    public TextLocationObject(int lineNum, int colNum){
        this.lineNum=lineNum;
        this.colNum=colNum;

    }
    public int getLineNum(){
        return lineNum;
    }
    public int getColNum(){
        return colNum;
    }
}