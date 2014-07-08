/**
 * LineNumberedBorder.java
 * @author
 * Paul Durbin http://www.esus.com/docs/GetQuestionPage.jsp?uid=1326&type=pf
 * & jgrindall
 *
 * Created
 * Last modified
***********************************
 * Extends swing's border class and adds line numbers, and exclamation
 * marks down the left hand side.
 * Implementation borrows heavily from above website - jgrindall
**/

package com.jgrindall.logo.views.components;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgrindall.logo.images.ImageSingleton;
public class LineNumberedBorder extends AbstractBorder {
    // TODO:  Check line break characters on non windows computers
   public static final int NO_ERRORS = -1;
   // a line number of -1 means no errors.
   private int errorLine = NO_ERRORS;
   // to start with no errors.  Later, the exclamation mark
   // is drawn on this line number.
   private String sep = " ";
   private JTextArea textArea;
   private Image errorImg = ImageSingleton.getInstance().getImage("errorSmall.png");
   public LineNumberedBorder(JTextArea textArea){
       super();
       this.textArea=textArea;
   }
   /*
    * Taken from http://www.esus.com
    */
   @Override
   public Insets getBorderInsets(Component c) {
      return getBorderInsets(c, new Insets(0, 0, 0, 0));
   }
   @Override
   public Insets getBorderInsets(Component c, Insets insets) {
      if (c instanceof JTextArea) {
          insets.left = getLeftInset();
      }
      return insets;
   }


   /*
    * Extra method to define where the exclamation mark appears.
    */
   public void setError(int i){
       errorLine=i;
   }


    /*
    * Taken from http://www.esus.com
    */
   private int getMaxLineNum(){
       FontMetrics fm = textArea.getFontMetrics(textArea.getFont());
       int fontHeight = fm.getHeight();
       int maxLineNum =  (int)(textArea.getHeight() / fontHeight );
       return maxLineNum;
   }
   public int getLeftInset( ){
       FontMetrics fm = textArea.getFontMetrics(textArea.getFont());
       return fm.stringWidth(""+getMaxLineNum()+sep);
   }


    /*
    * Taken from http://www.esus.com
    */
   @Override
   public void paintBorder(Component c, Graphics g, int x, int y,int width, int height) {
      g.setColor(textArea.getForeground());
      Rectangle clip = g.getClipBounds();
      FontMetrics fm = g.getFontMetrics();
      int fontHeight = fm.getHeight();
      int ybaseline = y + fm.getAscent();
      int startingLineNumber = (clip.y / fontHeight) + 1;
      int maxLineNum =  getMaxLineNum();
      String maxLineNumAsString = ""+maxLineNum;
      int maxNumChars = maxLineNumAsString.length();
      // eg 22 ->  "22"  has 2 chars
      
      if (ybaseline < clip.y) {
         ybaseline = y + startingLineNumber * fontHeight -(fontHeight - fm.getAscent());
      }
      int yend = ybaseline + height;
      if (yend > (y + height)) {
         yend = y + height;
      }
      int lnxstart = x+getLeftInset();
      while (ybaseline < yend) {
          if(startingLineNumber==this.errorLine){
              /*
               * Extra code to add image.
               18 and 12 are adjusted manually until it 'looks right'
               */
            g.drawImage(errorImg, lnxstart-18, ybaseline-12, null);
          }
          else{
            String label = padLabel(startingLineNumber, maxNumChars);
            g.drawString(label, lnxstart - fm.stringWidth(label), ybaseline);
          }
          ybaseline += fontHeight;
          startingLineNumber++;
      }
   }
   
    /*
    * Taken from http://www.esus.com
    */
   private String padLabel(int lineNumber, int length) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(lineNumber);
	  int c = length - buffer.length();
	  while(c>0){
		  buffer.insert(0, ' ');
		  c--;
	  }
      buffer.append(sep);
      return buffer.toString();
   }
}
