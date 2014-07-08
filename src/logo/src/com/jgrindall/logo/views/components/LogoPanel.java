/**
 * LogoPanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * swing component for the panel where logo is entered.
 * Contains as children the text area and the draw button.
 *
 * the
 */
package com.jgrindall.logo.views.components;
import java.awt.*;
import javax.swing.*;
import com.jgrindall.logo.images.ImageSingleton;
public class LogoPanel extends JPanel {
    private JButton drawButton;
    private JButton stopButton;
    private JLabel helpLabel;
    private ILogoEditorTextArea logotf;
    public static final String DRAWING = "DRAWING";
    public static final String STOPPED = "STOPPED";
    public LogoPanel(){
        super();

        this.setLayout(new BorderLayout());
        // add labels etc here.
        JPanel top = new JPanel(new BorderLayout());
        JLabel jlb =  new JLabel("Write your logo here")  ;
        jlb.setIcon(  ImageSingleton.getInstance().getImageIcon("write.png")   );
        helpLabel =  new JLabel("")  ;
        helpLabel.setIcon(  ImageSingleton.getInstance().getImageIcon("help.png")   );
        helpLabel.setToolTipText("Help");
        top.add(jlb,BorderLayout.WEST);
        top.add(helpLabel,BorderLayout.EAST);
        this.add( top,BorderLayout.NORTH);
        logotf = new LogoEditorTextArea();
        JScrollPane jsp = new JScrollPane();
        JPanel jpInner = new JPanel(new BorderLayout());
        jpInner.add((JComponent)logotf,BorderLayout.CENTER);
        jsp.getViewport().add(jpInner);
        this.add(jsp,BorderLayout.CENTER);
        JPanel b = new JPanel();
        b.setLayout(new FlowLayout(FlowLayout.LEFT));
        drawButton = new JButton("draw");
        stopButton = new JButton("stop");
        drawButton.setIcon(ImageSingleton.getInstance().getImageIcon("play.png") );
        b.add(drawButton);
        stopButton.setIcon(ImageSingleton.getInstance().getImageIcon("stop.png") );
        b.add(stopButton);
        this.add(b,BorderLayout.SOUTH);
        this.setStatus(LogoPanel.STOPPED);

    }
    public void setStatus(String s){
        if(s.equals(LogoPanel.DRAWING)){
            drawButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
        else if(s.equals(LogoPanel.STOPPED)){
            drawButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }
    public void setLogo(String s){
        logotf.setText(s);
    }
    public String getLogo(){
        return logotf.getText();
    }
    public ILogoEditorTextArea getLogoEditorTextArea(){
        return logotf;
    }
    public JLabel getHelpLabel(){
        return helpLabel;
    }
    public JButton getDrawButton(){
        return drawButton;
    }
    public JButton getStopButton(){
        return stopButton;
    }
}
