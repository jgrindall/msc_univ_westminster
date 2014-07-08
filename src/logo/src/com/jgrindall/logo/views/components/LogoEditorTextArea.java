/**
 * LogoEditorTextArea.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * swing component for the text area.
 *
 * Implementation details of highlighting taken from:
 * http://www.velocityreviews.com/forums/
 * t147950-how-can-i-highlight-the-current-row-in-a-jtextarea.html
 */
package com.jgrindall.logo.views.components;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import com.jgrindall.logo.utils.TextLocationObject;
public class LogoEditorTextArea extends JTextArea implements ILogoEditorTextArea{
    static final Color HL_COLOR = new Color(230, 0, 0, 50);
    // highlight colour (red, 50% transparent)
    private Highlighter.HighlightPainter painter;
    private LineNumberedBorder lnBorder;
    private Object highlight;
    public LogoEditorTextArea(){
        super();
        // monospaced font for the code!
        this.setFont(new Font("Courier New",  Font.PLAIN, 12));
        // now build border
        lnBorder = new LineNumberedBorder(this);
        lnBorder.setError(LineNumberedBorder.NO_ERRORS);
        this.setBorder( lnBorder );
        // and initiate the highlighting
        buildStyles();
    }
    protected void buildStyles(){
        painter = new DefaultHighlighter.DefaultHighlightPainter(HL_COLOR);
        setDefault();
        this.repaint();
    }
    
    public void highlight(TextLocationObject loc){
        setDefault();
        lnBorder.setError(LineNumberedBorder.NO_ERRORS);
        if(loc!=null){
            int lineNum=loc.getLineNum();
            int colNum=loc.getColNum();
            lnBorder.setError(lineNum);
            try{
                int startOffset = this.getLineStartOffset(lineNum-1);
                int endOffset = this.getLineEndOffset(lineNum-1);
                setHighlight(startOffset, endOffset);
            }
            catch(BadLocationException e){
                e.printStackTrace();
            }
        }
        this.repaint();
    }
    protected void setDefault(){
        if(highlight!=null){
            Highlighter h = this.getHighlighter();
            h.removeHighlight(highlight);
        }
    }
    protected void setHighlight (final int startOffset, final int endOffset){
       try{
            // getHighlighter is inherited from JTextComponent
            Highlighter h = this.getHighlighter();
            highlight = h.addHighlight(startOffset, endOffset , painter);
        }
        catch (BadLocationException ex){
            ex.printStackTrace();
        }
    }
}

