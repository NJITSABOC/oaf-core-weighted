package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.PropertyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class AreaSummaryPanel extends NodeSummaryPanel<Area> {
    
    private final PropertyPanel relationshipPanel;
    
    private final PAreaTaxonomyConfiguration configuration;
    
    public AreaSummaryPanel(PAreaTaxonomyConfiguration configuration, AreaSummaryTextFactory summaryTextFactory) {
        
        super(summaryTextFactory);

        this.configuration = configuration;

        relationshipPanel = new PropertyPanel(configuration, configuration.getUIConfiguration().getPropertyTableModel(true)); // TODO: Need model...

        relationshipPanel.setMinimumSize(new Dimension(-1, 100));
        relationshipPanel.setPreferredSize(new Dimension(-1, 100));

        relationshipPanel.addEntitySelectionListener(configuration.getUIConfiguration().getListenerConfiguration().getContainerRelationshipSelectedListener());

        this.add(relationshipPanel);
    }
    
    public AreaSummaryPanel(PAreaTaxonomyConfiguration configuration) {  
        this(configuration, new AreaSummaryTextFactory(configuration));
    }
    
    public void setContents(Area area) {
        super.setContents(area);

        ArrayList<InheritableProperty> sortedProperties = new ArrayList<>(area.getRelationships());
        
        sortedProperties.sort( (a,b) -> a.getName().compareToIgnoreCase(b.getName()));
        
        relationshipPanel.setContents(sortedProperties);
    }
    
    public void clearContents() {
        super.clearContents();
        
        relationshipPanel.clearContents();
    }
}
