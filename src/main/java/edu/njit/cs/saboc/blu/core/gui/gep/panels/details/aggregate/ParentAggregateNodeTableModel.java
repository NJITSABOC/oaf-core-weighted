package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class ParentAggregateNodeTableModel<T extends Node> extends OAFAbstractTableModel<ParentNodeDetails<T>> {
    
    private final AbNConfiguration config;
    
    public ParentAggregateNodeTableModel(AbNConfiguration config) {
        super(new String [] {
            String.format("Parent %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)),
            String.format("Parent %s", config.getTextConfiguration().getNodeTypeName(false)),
            String.format("# Aggregated %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ParentNodeDetails<T> item) {
        T node = item.getParentNode();
        AggregateNode aggregateNode = (AggregateNode)item.getParentNode();

        return new Object [] {
            item.getParentConcept().getName(),
            node.getName(),
            aggregateNode.getAggregatedNodes().size(),
            node.getConceptCount()
        };
    }
}