package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.AbNLevelReport;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateAbNLevelReportTableModel extends OAFAbstractTableModel<AbNLevelReport> {
    
     public AggregateAbNLevelReportTableModel(PartitionedAbNConfiguration config) {
        super(new String [] { 
           "Level",
            String.format("# %s", config.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(true)),
            String.format("# Regular %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# Aggregate %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# Removed %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
        });
        
    }
    
    @Override
    protected Object[] createRow(AbNLevelReport item) {
        
        Set<Node> regularNodes = new HashSet<>();
        Set<AggregateNode> aggregateNodes = new HashSet<>();
        
        Set<Node> removedNodes = new HashSet<>();
        
        item.getGroupsAtLevel().forEach((node) -> {
            AggregateNode aggregateNode = (AggregateNode)node;
            
            if(aggregateNode.getAggregatedNodes().isEmpty()) {
                regularNodes.add(node);
            } else {
                aggregateNodes.add(aggregateNode);
                
                removedNodes.addAll(aggregateNode.getAggregatedNodes());
            }
        });
        
        return new Object [] {
            item.getLevel(),
            item.getContainersAtLevel().size(),
            regularNodes.size(),
            aggregateNodes.size(),
            removedNodes.size(),
            item.getOverlappingConceptsAtLevel().size()
        };
    }
}
