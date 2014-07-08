/**
 * CreatePanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * main swing component for the create logo panel.
 */
package com.jgrindall.logo.views.components;
import java.awt.*;
import javax.swing.*;
import com.jgrindall.logo.images.ImageSingleton;
public class CreatePanel extends JPanel {
    private EditorPanel editorPanel;
    private RightPanel rightPanel;
    private JButton saveButton;
    private JButton backButton;
    private JButton logoutButton;
    public CreatePanel(){
        this.setLayout(new BorderLayout());
        editorPanel = new EditorPanel();
        rightPanel =  new RightPanel();
        editorPanel.setPreferredSize(new Dimension(0,0));
        JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorPanel, rightPanel );
        main.setResizeWeight(0.3);
        this.add(main,BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(buttonPanel,BorderLayout.SOUTH);
        
        backButton = new JButton("back");
        backButton.setIcon(ImageSingleton.getInstance().getImageIcon("back.png"));
        buttonPanel.add(backButton);

        saveButton = new JButton("save");
        saveButton.setIcon(ImageSingleton.getInstance().getImageIcon("save.png"));
        buttonPanel.add(saveButton);

        logoutButton = new JButton("logout");
        logoutButton.setIcon(ImageSingleton.getInstance().getImageIcon("logout.png"));
        buttonPanel.add(logoutButton);

    }
    public JComponent getEditorPanel(){
        return editorPanel;
    }
    public JComponent getRightPanel(){
        return rightPanel;
    }
    public JButton getBackButton(){
        return backButton;
    }
    public JButton getLogoutButton(){
        return logoutButton;
    }
}

