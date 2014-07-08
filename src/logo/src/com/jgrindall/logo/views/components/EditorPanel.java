/**
 * EditorPanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * swing component for the Editor (logo text area, buttons and bug panel)
 */
package com.jgrindall.logo.views.components;
import javax.swing.*;
public class EditorPanel extends JSplitPane{
    public JComponent logoPanel;
    public JComponent errorPanel;
    public EditorPanel(){
        super(JSplitPane.VERTICAL_SPLIT);
        logoPanel = new LogoPanel();
        errorPanel = new ErrorPanel();
        this.setTopComponent(logoPanel);
        this.setBottomComponent(errorPanel);
        this.setResizeWeight(0.75);
    }
    public LogoPanel getLogoPanel(){
        return (LogoPanel)logoPanel;
    }
    public ErrorPanel getErrorPanel(){
        return (ErrorPanel)errorPanel;
    }
}