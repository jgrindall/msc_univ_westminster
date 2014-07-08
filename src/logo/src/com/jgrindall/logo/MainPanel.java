/**
 * MainPanel.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * 
 */
package com.jgrindall.logo;
import com.jgrindall.logo.style.StyleUtils;
import com.jgrindall.logo.views.components.*;
import javax.swing.*;
import java.awt.*;
import com.jgrindall.logo.images.ImageSingleton;
public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    // the three main panels for login >> viewing my files >> creating logo
    private LoginPanel loginPanel;
    private FilesPanel filesPanel;
    private CreatePanel createPanel;
    // card layout depths
    private static final String LOGIN_DEPTH = "1";
    private static final String FILES_DEPTH = "2";
    private static final String CREATE_DEPTH = "3";
    public MainPanel() {
        super();
        init();
    }
    private void init(){
        setStyles();
        initComponents();
        this.setVisible(true);
    }
    private void setStyles(){
        StyleUtils.setSubstanceStyle();
    }
    public void gotoCreateScreen(){
        // open the main screen (where you write LOGO)
        cardLayout.show(this, MainPanel.CREATE_DEPTH);
    }
    public void gotoFilesScreen(){
        // open the 'my files' panel
        cardLayout.show(this, MainPanel.FILES_DEPTH);
    }
    public void gotoLoginScreen(){
        // open the 'login' panel
        cardLayout.show(this, MainPanel.LOGIN_DEPTH);
    }


    /*  getters for the three main panels */
    public CreatePanel getCreatePanel(){
        return createPanel;
    }
    public FilesPanel getFilesPanel(){
        return filesPanel;
    }
    public LoginPanel getLoginPanel(){
        return loginPanel;
    }

    // create GUI
    private void initComponents() {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        loginPanel = new LoginPanel(ImageSingleton.getInstance().getImage("legoMindstorms.png"));
        filesPanel = new FilesPanel();
        createPanel = new CreatePanel();
        this.add(loginPanel,MainPanel.LOGIN_DEPTH);
        this.add(filesPanel,MainPanel.FILES_DEPTH);
        this.add(createPanel,MainPanel.CREATE_DEPTH);
    }
}
