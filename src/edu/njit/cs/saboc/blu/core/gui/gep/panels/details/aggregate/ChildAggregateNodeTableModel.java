package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class ChildAggregateNodeTableModel<T extends Node> extends OAFAbstractTableModel<T> {
    
    private final AbNConfiguration configuration;
    
    public ChildAggregateNodeTableModel(AbNConfiguration configuration) {
        super(new String [] {
            configuration.getTextConfiguration().getNodeTypeName(false), 
            String.format("# Aggregate %s", configuration.getTextConfiguration().getNodeTypeName(true)),
            String.format("# %s", configuration.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
        
        this.configuration = configuration;
    }
    
    @Override
    protected Object[] createRow(T item) {
        AggregateNode aggregateNode = (AggregateNode)item;
        
        return new Object [] {
            item.getName(),
            aggregateNode.getAggregatedNodes().size(),
            item.getConceptCount()
        };
    }
}