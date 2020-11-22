package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.tan;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection.BaseRootSelectionOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection.BaseRootSelectionOptionsPanel.RootSelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris O
 */
public class TANPatriarchListPanel extends AbNDerivationWizardPanel {
    
    private final Set<Concept> selectedPatriarchs = new HashSet<>();
    
    private final ConceptList selectedPatriarchList;
    
    private final JToggleButton useChildrenBtn;
    private final JToggleButton userSelectionBtn;
    
    private final BaseRootSelectionOptionsPanel rootSelectionPanel;
    
    public TANPatriarchListPanel(AbNConfiguration config, 
            BaseRootSelectionOptionsPanel rootSelectionPanel) {
        
        this.rootSelectionPanel = rootSelectionPanel;
                
        this.setLayout(new BorderLayout());
        
        String childTypeStr = config.getTextConfiguration().getOntologyEntityNameConfiguration().getChildConceptTypeName(true);
        
        this.useChildrenBtn = new JToggleButton(String.format("Use %s as Roots", childTypeStr));
        
        this.useChildrenBtn.addActionListener( (ae) -> {
            useChildrenSelected();
        });
        
        AbNTextFormatter formatter = new AbNTextFormatter(config.getTextConfiguration());
        
        String useChildrenTooltip = 
                "<html>Use the selected root <conceptTypeName>'s "
                + "<p><childConceptTypeName count=2> as the patriarch "
                + "<p><conceptTypeName count=2>.";
        
        this.useChildrenBtn.setToolTipText(formatter.format(useChildrenTooltip));
        
        String selectRootsTooltip = 
                "<html>Select specific <conceptTypeName count=2> as patriarchs. "
                + "<p>Double click on a <conceptTypeName> in the root list "
                + "<p>located on the left to add it to the list of patriarchs. "
                + "<p>Double click on a <conceptTypeName> in the list of patriarchs "
                + "<p>to remove it from the list of selected patriarchs.";
        
        this.userSelectionBtn = new JToggleButton("Select Individual Roots");
        this.userSelectionBtn.addActionListener( (ae) -> {
            userSelectionSelected();
        });
        
        this.userSelectionBtn.setToolTipText(formatter.format(selectRootsTooltip));
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(useChildrenBtn);
        bg.add(userSelectionBtn);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        northPanel.add(useChildrenBtn);
        northPanel.add(userSelectionBtn);
        
        this.add(northPanel, BorderLayout.NORTH);
        
        this.selectedPatriarchList = new ConceptList(config);
        
        this.add(selectedPatriarchList, BorderLayout.CENTER);
        
        this.rootSelectionPanel.addRootSelectionListener(new RootSelectionListener() {

            @Override
            public void rootSelected(Concept root) {
                if (inUseChildrenMode()) {
                    conceptSelected(root);
                }
            }

            @Override
            public void rootDoubleClicked(Concept root) {
                conceptSelected(root);
            }

            @Override
            public void noRootSelected() {
                resetView();
            }
        });

        this.rootSelectionPanel.addRootSelectionModeChangedListener((mode) -> {
            this.useChildrenBtn.setSelected(true);
        });
        
        resetView();
    }
    
    public Set<Concept> getSelectedPatriarchs() {
        return selectedPatriarchs;
    }
    
    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
    }
    
    public void conceptSelected(Concept concept) {
        
        if (useChildrenBtn.isSelected()) {
            selectedPatriarchs.clear();
            
            if (super.getCurrentOntology().isPresent()) {
                selectedPatriarchs.addAll(super.getCurrentOntology().get().getConceptHierarchy().getChildren(concept));
            }
        } else {
            selectedPatriarchs.add(concept);
        }

        ArrayList<Concept> sortedConcepts = new ArrayList<>(selectedPatriarchs);
        sortedConcepts.sort(new ConceptNameComparator());

        selectedPatriarchList.setContents(sortedConcepts);
    }
    
    @Override
    public void initialize(Ontology ont) {
        super.initialize(ont);
        
        resetView();
    }
    
    @Override
    public void clearContents() {
        super.clearContents();
        
        this.selectedPatriarchList.clearContents();
    }
    
    @Override
    public final void resetView() {
        useChildrenBtn.setSelected(true);
        
        useChildrenSelected();
    }
    
    public boolean inUseChildrenMode() {
        return useChildrenBtn.isSelected();
    }
    
    private void useChildrenSelected() {
        this.selectedPatriarchList.clearContents();
        this.selectedPatriarchs.clear();
    }
    
    private void userSelectionSelected() {
        this.selectedPatriarchList.clearContents();
        this.selectedPatriarchs.clear();
    }
}
