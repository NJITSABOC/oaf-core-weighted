package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.AbNGraphFrameInitializers;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistoryPanel extends JPanel {
    
    private final AbNDerivationHistoryList derivationList;
    
    private Optional<AbNGraphFrameInitializers> optInitializers = Optional.empty();
    
    private AbNDerivationHistoryEntry selectedEntry;
        
    public AbNDerivationHistoryPanel() {
        
        this.setLayout(new BorderLayout());

        this.derivationList = new AbNDerivationHistoryList();
        this.derivationList.addEntitySelectionListener(new EntitySelectionListener<AbNDerivationHistoryEntry>() {

            @Override
            public void entityClicked(AbNDerivationHistoryEntry entity) {
                selectedEntry = entity;
            }

            @Override
            public void entityDoubleClicked(AbNDerivationHistoryEntry entity) {
                
                if(!optInitializers.isPresent()) {
                    return;
                }
                
                entity.displayEntry(optInitializers.get().getSourceOntology());
            }

            @Override
            public void noEntitySelected() {
                
            }
        });
        
        this.add(derivationList, BorderLayout.CENTER);
    }
    
    public void setInitializers(AbNGraphFrameInitializers initializers) {
        this.optInitializers = Optional.of(initializers);
    }
    
    public void clearInitializers() {
        this.optInitializers = Optional.empty();
    }
    
    public void showHistory(AbNDerivationHistory history) {
        ArrayList<AbNDerivationHistoryEntry> historyList = new ArrayList<>(history.getHistory());
        
        historyList.sort( (a, b) -> a.getDate().compareTo(b.getDate()));
        
        this.derivationList.setContents(historyList);
    }
      
    public AbNDerivationHistoryEntry getSelectedEntry() {
        return selectedEntry;
    } 
}
