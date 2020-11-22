package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.targetbased;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.buttons.TargetAbNHelpButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection.BaseRootSelectionOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection.BaseRootSelectionOptionsPanel.RootSelectionListener;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.SubhierarchySearcher;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.InheritablePropertySelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.InheritablePropertySelectionPanel.SelectionType;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection.GenericRootSelectionOptionsPanel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class TargetAbNDerivationWizardPanel extends AbNDerivationWizardPanel{
    
    public interface InheritablePropertyRetriever {
        public Set<InheritableProperty> getInheritablePropertiesInSubhierarchy(Concept root);
    }
    
    public interface TargetHierarchyRetriever {
        public Hierarchy<Concept> getTargetSubhierarchy(Concept root, InheritableProperty propertyType);
    }
    
    public interface DeriveTargetAbNAction {
        public void deriveTargetAbN(
                Hierarchy<Concept> sourceHierarchy, 
                InheritableProperty propertyType, 
                Hierarchy<Concept> targetHierarchy);
    }
    
    private final BaseRootSelectionOptionsPanel<TargetAbstractionNetwork> sourceRootSelectionPanel;
    
    private final InheritablePropertySelectionPanel propertyListPanel;
    
    private final TargetSubhierarchyRootSelectionPanel targetRootSelectionPanel;
    
    private final DeriveTargetAbNAction derivationAction;
    
    private Optional<InheritablePropertyRetriever> optPropertyRetriever = Optional.empty();
    private Optional<TargetHierarchyRetriever> targetSubhierarchyRetriever = Optional.empty();
    
    private final JButton deriveButton;
    
    private final TargetAbNHelpButton helpBtn;
    
    public TargetAbNDerivationWizardPanel(
            TargetAbNConfiguration config,
            DeriveTargetAbNAction derivationAction,
            BaseRootSelectionOptionsPanel<TargetAbstractionNetwork> sourceRootSelectionPanel) {
        
        this.derivationAction = derivationAction;
        
        this.setLayout(new BorderLayout());
        
        JPanel derivationOptionsPanel = new JPanel();
        derivationOptionsPanel.setLayout(new BoxLayout(derivationOptionsPanel, BoxLayout.X_AXIS));
        
        this.sourceRootSelectionPanel = sourceRootSelectionPanel;
        this.sourceRootSelectionPanel.addRootSelectionListener(new RootSelectionListener() {

            @Override
            public void rootSelected(Concept root) {
                if (optPropertyRetriever.isPresent()) {
                    Set<InheritableProperty> propertiesInSubhierarchy = optPropertyRetriever.get().getInheritablePropertiesInSubhierarchy(root);

                    ArrayList<InheritableProperty> sortedProperties = new ArrayList<>(propertiesInSubhierarchy);
                    sortedProperties.sort((a, b) -> {
                        return a.getName().compareToIgnoreCase(b.getName());
                    });

                    propertyListPanel.initialize(sortedProperties, propertiesInSubhierarchy);
                } else {
                    propertyListPanel.clearContents();
                }
            }

            @Override
            public void rootDoubleClicked(Concept root) {
                rootSelected(root);
            }

            @Override
            public void noRootSelected() {
                
            }
            
        });
        
        this.sourceRootSelectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                String.format("1. Select Source Hierarchy Root %s", 
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false))));
        
        derivationOptionsPanel.add(sourceRootSelectionPanel);
        
        this.propertyListPanel = new InheritablePropertySelectionPanel(SelectionType.Single, false);
        
        this.targetRootSelectionPanel = new TargetSubhierarchyRootSelectionPanel(config);
        
        this.propertyListPanel.addSelectedPropertiesChangedListener((properties) -> {
            
            if(!getCurrentOntology().isPresent()) {
                return;
            }
            
            if(!sourceRootSelectionPanel.getSelectedRoot().isPresent()) {
                return;
            }
            
            if(properties.isEmpty()) {
                return;
            }
            
            Ontology ont = getCurrentOntology().get();
            
            Concept sourceRoot = sourceRootSelectionPanel.getSelectedRoot().get();
            
            if (targetSubhierarchyRetriever.isPresent()) {
                
                // Only one property used (for now...)
                Hierarchy<Concept> targetSubhierarchy = targetSubhierarchyRetriever.get().getTargetSubhierarchy(
                        sourceRoot, properties.iterator().next());

                targetRootSelectionPanel.initialize(
                        ont, 
                        new SubhierarchySearcher(sourceRootSelectionPanel.getSearcher().get(), 
                        targetSubhierarchy));
            }
        });
        
        this.propertyListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                String.format("2. Select %s Type",
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getPropertyTypeName(false))));
        
        this.propertyListPanel.setPreferredSize(new Dimension(250, -1));
        
        this.targetRootSelectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                String.format("3. Select Target Hierarchy Root %s",
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false))));

        derivationOptionsPanel.add(propertyListPanel);
        
        derivationOptionsPanel.add(targetRootSelectionPanel);
        
        this.add(derivationOptionsPanel, BorderLayout.CENTER);
        
        this.deriveButton = new JButton("<html><div align='center'>Derive Target<br>Abstraction Network");
        this.deriveButton.addActionListener( (ae) -> {
            deriveTargetAbstractionNetwork();
        });
        
        this.helpBtn = new TargetAbNHelpButton(config);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deriveButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(helpBtn);
        
        southPanel.add(buttonPanel, BorderLayout.EAST);
        
        this.add(southPanel, BorderLayout.SOUTH);
    }
    
     public TargetAbNDerivationWizardPanel(
            TargetAbNConfiguration config,
            DeriveTargetAbNAction derivationAction) {
         
         this(config, derivationAction, new GenericRootSelectionOptionsPanel(config));
    }

    public void initialize(
            Ontology ontology, 
            OntologySearcher searcher, 
            InheritablePropertyRetriever propertyRetriever,
            TargetHierarchyRetriever tripleRetriever) {
        
        super.initialize(ontology);
        
        this.optPropertyRetriever = Optional.of(propertyRetriever);
        this.targetSubhierarchyRetriever = Optional.of(tripleRetriever);
        
        this.sourceRootSelectionPanel.initialize(ontology, searcher);
        
        this.targetRootSelectionPanel.clearContents();
        this.targetRootSelectionPanel.resetView();
        
        this.targetRootSelectionPanel.setEnabled(false);
    }

    public void setEnabled(boolean value) {
        super.setEnabled(value);

        sourceRootSelectionPanel.setEnabled(value);
        propertyListPanel.setEnabled(value);
        targetRootSelectionPanel.setEnabled(value);
        helpBtn.setEnabled(value);
    }

    @Override
    public void clearContents() {
        super.clearContents();
        
        this.optPropertyRetriever = Optional.empty();
        this.targetSubhierarchyRetriever = Optional.empty();
        
        sourceRootSelectionPanel.clearContents();
        propertyListPanel.clearContents();
        targetRootSelectionPanel.clearContents();
    }

    @Override
    public void resetView() {
       sourceRootSelectionPanel.resetView();
       propertyListPanel.resetView();
       targetRootSelectionPanel.resetView();
    }
    
    private void deriveTargetAbstractionNetwork() {
        
        if(!super.getCurrentOntology().isPresent()) {
            
             JOptionPane.showMessageDialog(null,
                    "Cannot derive Target Abstraction Network. No ontology loaded.",
                    "Error: No ontology loaded",
                    JOptionPane.ERROR_MESSAGE);
            
            return;
        }
        
        if(!sourceRootSelectionPanel.getSelectedRoot().isPresent()) {
            
             JOptionPane.showMessageDialog(null,
                    "Cannot derive Target Abstraction Network. No source root concept selected.",
                    "Error: No Source Root Concept Selected",
                    JOptionPane.ERROR_MESSAGE);
            
            return;
        }
        
        if(propertyListPanel.getUserSelectedProperties().isEmpty()) {
            
            
            JOptionPane.showMessageDialog(null,
                    "Cannot derive Target Abstraction Network. No property selected.",
                    "Error: No Property Selected",
                    JOptionPane.ERROR_MESSAGE);
            
            return;
        }
        
        if (!targetRootSelectionPanel.getSelectedRoot().isPresent()) {

            JOptionPane.showMessageDialog(null,
                    "Cannot derive Target Abstraction Network. No target root concept selected.",
                    "Error: No Target Root Concept Selected",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }
        
        Ontology ont = super.getCurrentOntology().get();
        
        Concept sourceRoot = sourceRootSelectionPanel.getSelectedRoot().get();
        InheritableProperty propertyType = propertyListPanel.getUserSelectedProperties().stream().findAny().get();
        Concept targetRoot = targetRootSelectionPanel.getSelectedRoot().get();
        
        Hierarchy<Concept> sourceHierarchy = ont.getConceptHierarchy().getSubhierarchyRootedAt(sourceRoot);
        Hierarchy<Concept> targetHierarchy = ont.getConceptHierarchy().getSubhierarchyRootedAt(targetRoot);
        
        derivationAction.deriveTargetAbN(sourceHierarchy, propertyType, targetHierarchy);
    }
}
