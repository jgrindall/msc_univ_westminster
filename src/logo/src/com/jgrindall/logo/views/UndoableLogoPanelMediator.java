/**
 * UndoableLogoPanelMediator.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Extends the basic mediator to add undoable behaviour.
 */
package com.jgrindall.logo.views;
import com.jgrindall.logo.utils.IUndoable;
import com.jgrindall.logo.utils.UndoManager;
import com.jgrindall.logo.utils.*;
import javax.swing.*;
import com.jgrindall.logo.views.components.LogoPanel;
import java.awt.event.*;

public class UndoableLogoPanelMediator extends LogoPanelMediator implements IUndoable{
    protected UndoManager undoManager = new UndoManager(this);
    protected KeyAdapter keyAdapter;
    public static final String NAME = "UndoableLogoPanelMediator";
    public UndoableLogoPanelMediator(String mediatorName, LogoPanel viewComponent){
	super(mediatorName,viewComponent);
        // store empty string so we can undo up to and including it.
        undoManager.add(  createMemento()  );
    }
    @Override
    public void destroy(){
        super.destroy();
        undoManager = null;
        keyAdapter = null;
        removeListeners();
    }
    public Memento createMemento(){
        // build a memento object holding the string.
        return new Memento(getLogoPanel().getLogo());
    }
    @Override
    /*
     * super.store() sends the logo to the LogoProxy
       which holds a reference to it for
     * the rest of the app.
     *
     * add the undo behaviout here.
     *
     * TODO: It would might be better to store words rather characters
     */
    protected void store(){
        super.store();
        undoManager.add(  createMemento()  );
    }
    @Override
    protected void removeListeners(){
        super.removeListeners();
        JTextArea je =  (JTextArea)getLogoPanel().getLogoEditorTextArea();
        je.removeKeyListener(keyAdapter);
    }
    @Override
    protected void addListeners(){
        super.addListeners();
        JTextArea je =  (JTextArea)getLogoPanel().getLogoEditorTextArea();
        if(keyAdapter==null){
            keyAdapter = new MyKeyAdapter(this);
        }
        je.addKeyListener(keyAdapter);
    }
    public void setFromMemento(AMemento m){
        // turn off listening otherwise we get an
        //infinite loop of edits
        removeListeners();
        getLogoPanel().setLogo(  (String)(m.state)  );
        // put listeners back
        addListeners();
    }
    public void performUndo(AMemento m){
        setFromMemento(m);
    }
    public void performRedo(AMemento m){
        setFromMemento(m);
    }
    private void undo(){
        if(undoManager.canUndo()){
            undoManager.undo();
        }
    }
    private void redo(){
        if(undoManager.canRedo()){    
            undoManager.redo();
        }
    }
    public void onKeyPressed(KeyEvent e){
        // TODO: Check keys on non windows computers!
        if((e.getKeyCode() == KeyEvent.VK_Z) && (e.isControlDown())){
            undo();
        }
        if((e.getKeyCode() == KeyEvent.VK_Y) && (e.isControlDown())){
           redo();
        }
    }
}


class MyKeyAdapter extends KeyAdapter{
    private UndoableLogoPanelMediator med;
    public MyKeyAdapter(UndoableLogoPanelMediator med){
        super();
        this.med=med;
    }
    @Override
    public void keyPressed(KeyEvent e){
        med.onKeyPressed(e);
    }
}
