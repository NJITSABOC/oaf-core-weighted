
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.AggregatedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class ContainerAggregatedGroupTableModel<T extends Node> extends OAFAbstractTableModel<AggregatedNodeEntry<T>> {

    protected final PartitionedAbNConfiguration configuration;
    
    public ContainerAggregatedGroupTableModel(PartitionedAbNConfiguration config) {
        super(new String[] {
           String.format("Aggregated %s",config.getTextConfiguration().getNodeTypeName(false)),
           String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
           "Aggregated Into"
        });
        
        this.configuration = config;
    }
    
    @Override
    protected Object[] createRow(AggregatedNodeEntry<T> item) {
        String aggregatedNodeName = item.getAggregatedNode().getName();
        
        ArrayList<String> aggregateNodeNames = new ArrayList<>();
        
        item.getAggregatedIntoNodes().forEach((aggregateNode) -> {
            Node node = (Node)aggregateNode;
            
            aggregateNodeNames.add(String.format("%s (%d) [%d]", 
                    aggregateNode.getAggregatedHierarchy().getRoot().getName(), 
                    node.getConceptCount(),
                    aggregateNode.getAggregatedNodes().size()));
        });
        
        Collections.sort(aggregateNodeNames);
        
        String aggregateNodeNameStr = aggregateNodeNames.get(0);
        
        for(int c = 1; c < aggregateNodeNames.size() ; c++) {
            aggregateNodeNameStr += ("\n" + aggregateNodeNames.get(c));
        }

        return new Object [] {
            aggregatedNodeName,
            item.getAggregatedNode().getConceptCount(),
            aggregateNodeNameStr
        };
    }
}