package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 * @param <ABN_T>
 */
public abstract class BaseRootSelectionOptionsPanel<ABN_T extends AbstractionNetwork> extends AbNDerivationWizardPanel {

    public interface RootSelectionListener {
        public void rootSelected(Concept root);
        public void rootDoubleClicked(Concept root);
        public void noRootSelected();
    }
    
    public interface RootSelectionTypeChangedListener {
        public void selectionTypeChanged(RootSelectionPanel mode);
    }
    
    private final JPanel rootSelectionOptionPanel;
    
    private final ButtonGroup rootSelectionOptionGroup = new ButtonGroup();
    private final ArrayList<JToggleButton> rootSelectionOptionButtons = new ArrayList<>();
    
    private final Map<String, RootSelectionPanel> rootSelectionPanels = new HashMap<>();
    
    private Optional<Concept> currentRoot = Optional.empty();
        
    private final JPanel currentRootSelectionPanel;
    
    private final ArrayList<RootSelectionListener> rootSelectionListeners = new ArrayList<>();
    
    private final ArrayList<RootSelectionTypeChangedListener> selectionModeChangedListeners = new ArrayList<>();
    
    private Optional<OntologySearcher> optSearcher = Optional.empty();
    
    public BaseRootSelectionOptionsPanel() {
        this.setLayout(new BorderLayout());
                
        this.rootSelectionOptionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        this.add(rootSelectionOptionPanel, BorderLayout.NORTH);
        
        currentRootSelectionPanel = new JPanel(new CardLayout());
        
        this.add(currentRootSelectionPanel, BorderLayout.CENTER);
    }
    
    public void addRootSelectionOption(RootSelectionPanel panel) {
        
        JToggleButton rootSelectionBtn = new JToggleButton(panel.getSelectionType());
        rootSelectionBtn.addActionListener( (ae) -> {
            
            if(rootSelectionBtn.isSelected()) {
                
                this.selectionModeChangedListeners.forEach((listener) -> {
                    listener.selectionTypeChanged(panel);
                });

                ((CardLayout)currentRootSelectionPanel.getLayout()).show(
                        currentRootSelectionPanel, 
                        panel.getSelectionType());
                
                panel.selected();
            }
        });
        
        panel.addRootSelectionListener(new RootSelectionListener() {

            @Override
            public void rootSelected(Concept root) {
                currentRoot = Optional.of(root);
                
                rootSelectionListeners.forEach( (listener) -> {
                    listener.rootSelected(root);
                });
            }

            @Override
            public void rootDoubleClicked(Concept root) {
                currentRoot = Optional.of(root);
                
                rootSelectionListeners.forEach( (listener) -> {
                    listener.rootDoubleClicked(root);
                });
            }

            @Override
            public void noRootSelected() {
                clearCurrentRoot();
            }
        });
        
        this.rootSelectionOptionGroup.add(rootSelectionBtn);
        this.rootSelectionOptionButtons.add(rootSelectionBtn);

        this.rootSelectionOptionPanel.add(rootSelectionBtn);
        
        this.currentRootSelectionPanel.add(panel, panel.getSelectionType());
        
        this.rootSelectionPanels.put(panel.getSelectionType(), panel);
    }
        
    public void addRootSelectionListener(RootSelectionListener rootSelectionListener) {
        rootSelectionListeners.add(rootSelectionListener);
    }
    
    public void removeRootSelectionListener(RootSelectionListener rootSelectionListener) {
        rootSelectionListeners.remove(rootSelectionListener);
    }
    
    public void addRootSelectionModeChangedListener(RootSelectionTypeChangedListener rootSelectionModeChangedListener) {
        selectionModeChangedListeners.add(rootSelectionModeChangedListener);
    }
    
    public void removeRootSelectionModeChangedListener(RootSelectionTypeChangedListener rootSelectionModeChangedListener) {
        selectionModeChangedListeners.remove(rootSelectionModeChangedListener);
    }
    
    public Optional<OntologySearcher> getSearcher() {
        return optSearcher;
    }
    
    public Optional<Concept> getSelectedRoot() {
        return currentRoot;
    }
    
    public void clearCurrentRoot() {
        this.currentRoot = Optional.empty();

        rootSelectionListeners.forEach((listener) -> {
            listener.noRootSelected();
        });
    }

    public void initialize(Ontology ontology, OntologySearcher searcher) {        
        super.initialize(ontology);

        this.optSearcher = Optional.of(searcher);
        
        if(rootSelectionPanels.isEmpty()) {
            return;
        }
        
        this.rootSelectionPanels.values().forEach( (panel) -> {
            panel.initialize(ontology, searcher);
        });
        
        SwingUtilities.invokeLater( () -> {
            this.rootSelectionOptionButtons.get(0).doClick();
        });
    }
    
    @Override
    public void clearContents() {
        super.clearContents();
        
        clearCurrentRoot();
        
        this.rootSelectionPanels.values().forEach( (panel) -> { 
            panel.clear();
        });
    }

    @Override
    public final void resetView() {
        if (!rootSelectionPanels.isEmpty()) {
            this.rootSelectionOptionButtons.get(0).setSelected(true);

            this.rootSelectionPanels.values().forEach((panel) -> {
                panel.resetView();
            });
        }
    }

    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.rootSelectionOptionButtons.forEach( (btn) -> {
            btn.setEnabled(value);
        });
        
        this.rootSelectionPanels.values().forEach( (panel) -> {
            panel.setEnabled(value);
        });
    }
}
