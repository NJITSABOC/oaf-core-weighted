package edu.njit.cs.saboc.blu.core.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author Chris
 */
public class LoadStatusDialog extends JDialog {
    
    public interface LoadingDialogClosedListener {
        public void dialogClosed();
    }
    
    public static LoadStatusDialog display(
            JFrame parentFrame, 
            String message, 
            LoadingDialogClosedListener closeAction) {
        
        return new LoadStatusDialog(parentFrame, message, closeAction);
    }
    
    private final JProgressBar loadProgressBar;
    
    private LoadStatusDialog(
            JFrame parentFrame, 
            String message, 
            LoadingDialogClosedListener closeAction) {
        
        super(parentFrame, false);
        
        this.setAlwaysOnTop(true);
        
        this.setTitle("Loading. Please wait...");
        
        loadProgressBar = new JProgressBar();
        loadProgressBar.setStringPainted(true);
        loadProgressBar.setString("Loading. Please wait...");
        loadProgressBar.setIndeterminate(true);
        
        this.setSize(256, 128);
        
        JLabel messageLabel = new JLabel(String.format("<html>%s", message));
        messageLabel.setFont(messageLabel.getFont().deriveFont(Font.BOLD, 14));
        
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.add(messageLabel, BorderLayout.NORTH);
        
        dialogPanel.add(loadProgressBar, BorderLayout.CENTER);
        
        this.add(dialogPanel);

        this.setLocationRelativeTo(parentFrame);
        this.setResizable(false);
         
        this.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosed(WindowEvent e) {
                closeAction.dialogClosed();
            }
        });

        this.setVisible(true);
    }
}
