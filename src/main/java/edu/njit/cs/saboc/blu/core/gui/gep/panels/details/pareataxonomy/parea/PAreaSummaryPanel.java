package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.PropertyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class PAreaSummaryPanel extends NodeSummaryPanel<PArea> {
    
    private final PropertyPanel relationshipPanel;
    
    private final PAreaTaxonomyConfiguration configuration;
    
    public PAreaSummaryPanel(PAreaTaxonomyConfiguration configuration, PAreaSummaryTextFactory summaryTextFactory) {
        super(summaryTextFactory);

        this.configuration = configuration;

        this.relationshipPanel = new PropertyPanel(configuration,  
                configuration.getUIConfiguration().getPropertyTableModel(false));
        
        this.relationshipPanel.setMinimumSize(new Dimension(-1, 100));
        this.relationshipPanel.setPreferredSize(new Dimension(-1, 100));

        this.relationshipPanel.addEntitySelectionListener(configuration.getUIConfiguration().getListenerConfiguration().getGroupRelationshipSelectedListener());

        this.add(this.relationshipPanel);
    }

    public PAreaSummaryPanel(PAreaTaxonomyConfiguration configuration) {
        this(configuration, new PAreaSummaryTextFactory(configuration));
    }
    
    public void setContents(PArea parea) {        
        super.setContents(parea);
                
        ArrayList<InheritableProperty> sortedProperties = new ArrayList<>(parea.getRelationships());
        
        sortedProperties.sort( (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        
        relationshipPanel.setContents(sortedProperties);
    }
    
    public void clearContents() {
        super.clearContents();
        
        relationshipPanel.clearContents();
    }
}
