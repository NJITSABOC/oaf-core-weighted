package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.InheritablePropertySelectionPanel;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RelationshipSubtaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.InheritablePropertySelectionPanel.SelectionType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class RelationshipSubtaxonomyDerivationPanel extends JPanel {
    public interface RelationshipSubtaxonomyDerivationAction {
        public void createAndDisplayRelationshipSubtaxonomy(Set<InheritableProperty> properties);
    }
    
    private final JButton derivationButton;
    
    private final InheritablePropertySelectionPanel propertySelectionPanel;
    
    public RelationshipSubtaxonomyDerivationPanel(
            RelationshipSubtaxonomyDerivationAction derivationAction) {
        
        super(new BorderLayout());
        
        this.propertySelectionPanel = new InheritablePropertySelectionPanel(SelectionType.Multiple, false);
        
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Select [Inheritable Property] to Use in Derivation"));

        derivationButton = new JButton("Derive Subtaxonomy");
        derivationButton.addActionListener( (ae) -> {
            derivationAction.createAndDisplayRelationshipSubtaxonomy(propertySelectionPanel.getUserSelectedProperties());
        });
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        contentPanel.add(propertySelectionPanel, BorderLayout.CENTER);
        contentPanel.add(derivationButton, BorderLayout.SOUTH);
        
        this.add(contentPanel, BorderLayout.CENTER);
    }
    
    public void initialize(PAreaTaxonomyConfiguration config, PAreaTaxonomy taxonomy) {

        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                String.format("Select %s to Use in Derivation",
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getPropertyTypeName(true))));
        
        ArrayList<InheritableProperty> taxonomyAvailableProperties = new ArrayList<>(
                taxonomy.getPropertiesInTaxonomy());
        
        taxonomyAvailableProperties.sort( (a, b) -> {
            return a.getName().compareToIgnoreCase(b.getName());
        });
        
        propertySelectionPanel.initialize(taxonomyAvailableProperties);
    }
    
    public void initializeSubtaxonomy(PAreaTaxonomyConfiguration config, RelationshipSubtaxonomy taxonomy) {
        initialize(config, taxonomy.getSuperAbN());
        
        Set<InheritableProperty> usedProperties = taxonomy.getAllowedProperties();
        
        ArrayList<InheritableProperty> taxonomyAvailableProperties = new ArrayList<>(
                taxonomy.getPropertiesInTaxonomy());
        
        taxonomyAvailableProperties.sort( (a, b) -> {
            return a.getName().compareToIgnoreCase(b.getName());
        });
                
        propertySelectionPanel.initialize(taxonomyAvailableProperties, usedProperties);
    }
}
