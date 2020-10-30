package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public abstract class TANListenerConfiguration extends PartitionedAbNListenerConfiguration<Cluster, Band> {
    
    public TANListenerConfiguration(TANConfiguration config) {
        super(config);
    }
    
    public EntitySelectionListener<Concept> getClusterPatriarchSelectedListener() {
        return new EntitySelectionAdapter<>();
    }

    public EntitySelectionListener<Concept> getBandPatriarchSelectedListener() {
        return new EntitySelectionAdapter<>();
    }
}

