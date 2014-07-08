/**
 * TurtlePanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * This is a container for the graphics themselves, which
 * adds scrollbars and a resize listener to make sure the
 * origin is always central.
 */
package com.jgrindall.logo.views.components;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class TurtlePanel extends JPanel {
    private TurtleCanvas tc;
    public JTextArea outputtf;
    private JScrollPane jsp;
    public TurtlePanel(){
        super();
        this.setLayout(new BorderLayout());
        tc = new TurtleCanvas();
        tc.setVisible(true);
        tc.setOpaque(true);
        tc.setBackground(Color.white);
        tc.setForeground(Color.black);

        jsp = new JScrollPane();
        jsp.getViewport().add(tc);
        this.add(jsp,BorderLayout.CENTER);

        jsp.addComponentListener(new ComponentListener(){
            @Override
            public void componentResized(ComponentEvent e){
                onComponentResized();
            }
            public void componentHidden(ComponentEvent e){

            }
            public void componentMoved(ComponentEvent e){

            }
            public void componentShown(ComponentEvent e){

            }
        });
       
        outputtf= new JTextArea();
        
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        this.add(jp,BorderLayout.SOUTH);
        jp.add(outputtf,BorderLayout.CENTER);
      
        tc.setMinimumSize(new Dimension(TurtleCanvas.MAX_WIDTH,TurtleCanvas.MAX_HEIGHT));
        tc.setMaximumSize(new Dimension(TurtleCanvas.MAX_WIDTH,TurtleCanvas.MAX_HEIGHT));
        tc.setPreferredSize(new Dimension(TurtleCanvas.MAX_WIDTH,TurtleCanvas.MAX_HEIGHT));
    }

    private void onComponentResized(){
        // centre the logo panel
        int x = Math.min( 0, -(TurtleCanvas.MAX_WIDTH/2-jsp.getWidth()/2)   );
        int y = Math.min(0, -(TurtleCanvas.MAX_HEIGHT/2-jsp.getHeight()/2)   );
        jsp.getViewport().setViewPosition(new Point(-x,-y));
    }
    public TurtleCanvas getTurtleCanvas(){
        return tc;
    }
}
