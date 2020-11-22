package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class ChildClusterTableModel extends OAFAbstractTableModel<Cluster> {

    private final TANConfiguration config;
    
    public ChildClusterTableModel(TANConfiguration config) {
        super(new String [] {
            "Child Cluster",
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            "Band"
        });
        
        this.config = config;
    }
    
    public Cluster getChildCluster(int row) {
        return getItemAtRow(row);
    }

    @Override
    protected Object[] createRow(Cluster cluster) {
        ClusterTribalAbstractionNetwork tan = config.getTribalAbstractionNetwork();
        
        Band band = tan.getBandFor(cluster);

        return new Object [] {
            cluster.getName(),
            cluster.getConceptCount(),
            band.getName("\n")
        };
    }
}