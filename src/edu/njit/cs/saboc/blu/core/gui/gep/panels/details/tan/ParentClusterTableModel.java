package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ParentClusterTableModel extends OAFAbstractTableModel<ParentNodeDetails<Cluster>> {
    
    private final TANConfiguration config;
    
    public ParentClusterTableModel(TANConfiguration config) {
        super(new String [] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getParentConceptTypeName(false),
            "Parent Cluster",
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            "Band"
        });
        
        this.config = config;
    }
    
    public Concept getParentConcept(int row) {
        return getItemAtRow(row).getParentConcept();
    }
    
    public Cluster getParentCluster(int row) {
        return getItemAtRow(row).getParentNode();
    }

    @Override
    protected Object[] createRow(ParentNodeDetails<Cluster> parentInfo) {
        ClusterTribalAbstractionNetwork tan = config.getTribalAbstractionNetwork();
        
        Band band = tan.getBandFor(parentInfo.getParentNode());
        
        return new Object [] {
            parentInfo.getParentConcept().getName(), 
            parentInfo.getParentNode().getName(),
            parentInfo.getParentNode().getConceptCount(),
            band.getName("\n")
        };
    }
}