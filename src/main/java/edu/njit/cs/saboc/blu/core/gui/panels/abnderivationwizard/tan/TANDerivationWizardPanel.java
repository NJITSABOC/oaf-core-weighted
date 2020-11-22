package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons.TANHelpButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection.BaseRootSelectionOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection.GenericRootSelectionOptionsPanel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class TANDerivationWizardPanel extends AbNDerivationWizardPanel {
    
    public interface DeriveTANAction {
        public void deriveTribalAbstractionNetwork(Set<Concept> patriarchs);
    }
    
    private final BaseRootSelectionOptionsPanel<ClusterTribalAbstractionNetwork> rootSelectionPanel;
    
    private final TANPatriarchListPanel selectedPatriarchPanel;
    
    private final JButton deriveButton;
    
    private final DeriveTANAction derivationAction;
    
    private final TANHelpButton helpBtn;
    
    private final JPanel optionsPanel;

    public TANDerivationWizardPanel(
            TANConfiguration config, 
            DeriveTANAction derivationAction, 
            BaseRootSelectionOptionsPanel<ClusterTribalAbstractionNetwork> rootSelectionMenu) {
        
        this.setLayout(new BorderLayout());
        
        this.derivationAction = derivationAction;
        
        JPanel derivationOptionsPanel = new JPanel();
        derivationOptionsPanel.setLayout(new BoxLayout(derivationOptionsPanel, BoxLayout.X_AXIS));
                
        this.rootSelectionPanel = rootSelectionMenu;
        
        this.rootSelectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                String.format("Select Tribal Abstraction Network Root %s", 
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))));
                
        this.selectedPatriarchPanel = new TANPatriarchListPanel(config, rootSelectionPanel);
        this.selectedPatriarchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                "Selected Patriarchs"));
        
        derivationOptionsPanel.add(rootSelectionPanel);
        derivationOptionsPanel.add(selectedPatriarchPanel);
        
        this.add(derivationOptionsPanel, BorderLayout.CENTER);
        
        this.deriveButton = new JButton("<html><div align='center'>Derive Tribal<p>Abstraction Network");
        this.deriveButton.addActionListener( (ae) -> {
            deriveTribalAbstractionNetwork();
        });
        
        
        this.helpBtn = new TANHelpButton(config);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(deriveButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(helpBtn);
        
        southPanel.add(buttonsPanel, BorderLayout.EAST);
        
        this.optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        southPanel.add(optionsPanel, BorderLayout.CENTER);
        
        this.add(southPanel, BorderLayout.SOUTH);
        
        resetView();
    }
    
    public TANDerivationWizardPanel(
            TANConfiguration config, 
            DeriveTANAction derivationAction) {

        this(config, 
                derivationAction, 
                new GenericRootSelectionOptionsPanel(config));
    }
    
    public void addOptionsPanelItem(JComponent component) {
        this.optionsPanel.add(component);
    }
    
    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);

        rootSelectionPanel.setEnabled(value);
        selectedPatriarchPanel.setEnabled(value);
        
        deriveButton.setEnabled(value);
        helpBtn.setEnabled(value);
    }
    
    public void initialize(Ontology ontology, OntologySearcher searcher) {
        super.initialize(ontology);
        
        selectedPatriarchPanel.resetView();
        rootSelectionPanel.resetView();
        
        rootSelectionPanel.initialize(ontology, searcher);
        selectedPatriarchPanel.initialize(ontology);
    }
    
    @Override
    public void resetView() {
        rootSelectionPanel.resetView();
        selectedPatriarchPanel.resetView();
    }
    
    protected void deriveTribalAbstractionNetwork() {
        if (!super.getCurrentOntology().isPresent()) {

            JOptionPane.showMessageDialog(null,
                    "Cannot derive Tribal Abstraction Network. No ontology loaded.",
                    "Error: No ontology loaded",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        if(selectedPatriarchPanel.getSelectedPatriarchs().size() < 2) {

            JOptionPane.showMessageDialog(null,
                    "Cannot derive Tribal Abstraction Network. At least two patriarch concepts are needed.",
                    "Error: No patriarchs selected",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }
        
        derivationAction.deriveTribalAbstractionNetwork(selectedPatriarchPanel.getSelectedPatriarchs());
    }
}
