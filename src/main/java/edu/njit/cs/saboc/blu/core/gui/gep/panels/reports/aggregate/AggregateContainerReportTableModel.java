package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateContainerReportTableModel extends OAFAbstractTableModel<ContainerReport> {
    
    private final PartitionedAbNConfiguration config;
    
    public AggregateContainerReportTableModel(PartitionedAbNConfiguration config) {
        
        super(new String[] {
            config.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(false),
            String.format("# Regular %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# Aggregate %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# Removed %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ContainerReport item) {
        
        Set<Node> regularNodes = new HashSet<>();
        Set<AggregateNode> aggregateNodes = new HashSet<>();
        
        Set<Node> removedNodes = new HashSet<>();
        
        item.getGroups().forEach((group) -> {
            AggregateNode aggregateNode = (AggregateNode)group;
            
            if(aggregateNode.getAggregatedNodes().isEmpty()) {
                regularNodes.add(group);
            } else {
                aggregateNodes.add(aggregateNode);
                
                removedNodes.addAll(aggregateNode.getAggregatedNodes());
            }
        });
        
        return new Object[] {
            item.getContainer().getName(),
            regularNodes.size(),
            aggregateNodes.size(),
            removedNodes.size(),
            item.getConcepts().size()
        };
    }
}
