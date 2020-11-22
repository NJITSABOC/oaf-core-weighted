package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons;

import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris
 */
public class PopupToggleButton extends JToggleButton {

    private final JDialog popup;
    
    private TaskBarPanel taskBarPanel = null;
    
    public PopupToggleButton(JFrame parent, String text) {
        this.setText(text);
        
        popup = new JDialog(parent);
        popup.setUndecorated(true);
        popup.setFocusableWindowState(true);
        popup.setFocusable(true);
        
        popup.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent we) {
                doUnselected();
            }
        });
             
        this.addActionListener( (ae) -> {
            
            if(isSelected()) {
                doSelected();
            } else {
                doUnselected();
            }

        });
        
        parent.addComponentListener(new ComponentListener() {

            public void componentShown(ComponentEvent e) {
                updatePopupLocation();
            }

            public void componentResized(ComponentEvent e) {
                updatePopupLocation();
            }

            public void componentMoved(ComponentEvent e) {
                updatePopupLocation();
            }

            public void componentHidden(ComponentEvent e) {
                updatePopupLocation();
            }
        });
    }
    
    public void setTaskBarPanel(TaskBarPanel taskBarPanel) {
        this.taskBarPanel = taskBarPanel;
    }
        
    public final void doSelected() {
        setSelected(true);
        
        popup.setVisible(true);
        popup.requestFocusInWindow();
        
        
        if(taskBarPanel != null) {
            taskBarPanel.closeAllPopupButtonsException(this);
        }
        
        updatePopupLocation();
    }
    
    public final void doUnselected() {
        setSelected(false);

        popup.setVisible(false);
    }
    
    public void closePopup() {
        doUnselected();
    }
    
    protected void setPopupContent(JComponent component) {
        popup.add(component);
        popup.pack();
    }
    
    public void disposePopup() {
        popup.dispose();
    }

    private void updatePopupLocation() {
        try {
            Point location = getLocationOnScreen();
            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            
            if(location.x + popup.getWidth() > screenSize.width) {
                int widthDiff = location.x + popup.getWidth() - screenSize.width;
                
                location.x -= (widthDiff + 4); 
            }
            
            location.y += getHeight();
            popup.setLocation(location);
        } catch (Exception e) {
            
        }
    }
    
    public void updatePopupLocation(Dimension parentFrame) {
        Point location = getLocationOnScreen();
        
        location.x = parentFrame.width - popup.getWidth() - 4;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        if(location.x + popup.getWidth() > screenSize.width) {
            int widthDiff = location.x + popup.getWidth() - screenSize.width;

            location.x -= (widthDiff + 4); 
        }
        
        location.y += getHeight();
        
        popup.setLocation(location);
    }
}
