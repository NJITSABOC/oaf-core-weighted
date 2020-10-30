package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.AbNGraphFrameInitializers;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.gui.workspace.AbNWorkspaceButton;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class AbNHistoryNavigationPanel extends JPanel {

    private final JButton backBtn;
    private final JButton forwardBtn;
   
    private final AbNWorkspaceButton workspaceBtn;
    
    private final AbNHistoryButton viewHistoryBtn;
    
    private final AbNDerivationHistory derivationHistory;
    
    private final AbNHistoryNavigationManager historyNavigationManager;
    
    private final MultiAbNGraphFrame graphFrame;

    public AbNHistoryNavigationPanel(
            MultiAbNGraphFrame graphFrame,
            AbNDerivationHistory derivationHistory) {
        
        this.graphFrame = graphFrame;
        
        this.derivationHistory = derivationHistory;
        
        historyNavigationManager = new AbNHistoryNavigationManager(derivationHistory);
        
        backBtn = new JButton();
        backBtn.setIcon(ImageManager.getImageManager().getIcon("left-arrow.png"));
        backBtn.addActionListener((ae) -> {
            historyNavigationManager.goBack();
            
            updateNavigationButtons();
        });
        
        backBtn.setPreferredSize(new Dimension(60, 24));

        forwardBtn = new JButton();
        forwardBtn.setIcon(ImageManager.getImageManager().getIcon("right-arrow.png"));
        forwardBtn.addActionListener( (ae) -> {
            historyNavigationManager.goForward();
            
            updateNavigationButtons();
        });
        
        forwardBtn.setPreferredSize(new Dimension(60, 24));
        
        derivationHistory.addHistoryChangedListener( () -> {
            updateNavigationButtons();
            refreshHistoryDisplay();
        });

        this.viewHistoryBtn = new AbNHistoryButton(graphFrame, derivationHistory);
     
        this.workspaceBtn = new AbNWorkspaceButton(graphFrame);
        
        if(!graphFrame.getStateFileManager().isInitialized()) {
            workspaceBtn.setEnabled(false);
            workspaceBtn.setToolTipText("OAF Workspaces are disabled (unable to create AppData folder)");
        }
        
        this.add(backBtn);
        this.add(Box.createHorizontalStrut(4));
        this.add(viewHistoryBtn);
        this.add(Box.createHorizontalStrut(2));
        this.add(workspaceBtn);
        this.add(Box.createHorizontalStrut(4));
        this.add(forwardBtn);
    }
    
    public void setInitializers(AbNGraphFrameInitializers initializers) {
        viewHistoryBtn.setInitializers(initializers);
        historyNavigationManager.setInitializers(initializers);
        
        updateWorkspaceButton(initializers);
    }
    
    private void updateWorkspaceButton(AbNGraphFrameInitializers initializers) {
        
        if(graphFrame.getStateFileManager().isInitialized()) {
            
            if(initializers.getRecentAbNWorkspaceFiles() != null) {
                workspaceBtn.setEnabled(true);
                
                return;
            }
        }
        
        workspaceBtn.setEnabled(false);
    }

    public void clearInitializers() {
        viewHistoryBtn.clearInitializers();
        historyNavigationManager.clearInitializers();
        
        workspaceBtn.setEnabled(false);
    }
    
    public final void refreshHistoryDisplay() {
        this.viewHistoryBtn.displayHistory(derivationHistory);
    }
    
    public void reset() {
        historyNavigationManager.reset();
        
        this.updateNavigationButtons();
        this.refreshHistoryDisplay();
    }
    
    public void closePopups() {
        this.viewHistoryBtn.closePopup();
    }

    private void updateNavigationButtons() {
        backBtn.setEnabled(historyNavigationManager.canGoBack());
        forwardBtn.setEnabled(historyNavigationManager.canGoForward());
    }
}
