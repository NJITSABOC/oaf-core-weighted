package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomyListenerConfiguration extends PartitionedAbNListenerConfiguration<PArea, Area> {
    
    public PAreaTaxonomyListenerConfiguration(PAreaTaxonomyConfiguration config) {
        super(config);
    }
    
    public EntitySelectionListener<InheritableProperty> getGroupRelationshipSelectedListener() {
        return new EntitySelectionAdapter<>();
    }
    
    public EntitySelectionListener<InheritableProperty> getContainerRelationshipSelectedListener() {
        return new EntitySelectionAdapter<>();
    }
}
