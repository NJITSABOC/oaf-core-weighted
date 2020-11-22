package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Chris O
 */
public class AggregatePartitionedNodeTextGenerator {
    
    public String formatText(
            PartitionedNode<?> partitionedNode, 
            AbNTextConfiguration textConfig) {
        
        Set<AggregateNode<?>> aggregateNodes = partitionedNode.getInternalNodes().stream().map( (node) -> {
            return (AggregateNode<?>)node;
        }).filter( (node) -> {
            return !node.getAggregatedNodes().isEmpty();
        }).collect(Collectors.toSet());
        
        String result;
        
        if(aggregateNodes.isEmpty()) {
            result = "It contains <font color = 'RED'>no</font> aggregate <nodeTypeName count=2>.";
        } else {
            
            Set<Concept> aggregatedConcepts = new HashSet<>();
            
            aggregateNodes.forEach( (node) -> {
                node.getAggregatedNodes().forEach( (aggregatedNode) -> {
                    aggregatedConcepts.addAll(aggregatedNode.getConcepts());
                });
            });
            
            Set<Node> aggregatedNodes = new HashSet<>();
            
            aggregateNodes.forEach( (node) -> {
                aggregatedNodes.addAll(node.getAggregatedNodes());
            });
            
            result = "It contains <font color = 'RED'><aggregateNodeCount></font> "
                    + "aggregate <nodeTypeName count=<aggregateNodeCount>> that contain "
                    + "<font color = 'RED'><aggregatedNodeCount></font> aggregated "
                    + "<nodeTypeName count=<aggregatedNodeCount>> that summarize a total of "
                    + "<font color = 'RED'><aggregatedConceptCount></font> "
                    + "<conceptTypeName count=<aggregatedConceptCount>>.";
            
            result = result.replaceAll("<aggregateNodeCount>", Integer.toString(aggregateNodes.size()));
            result = result.replaceAll("<aggregatedNodeCount>", Integer.toString(aggregatedNodes.size()));
            result = result.replaceAll("<aggregatedConceptCount>", Integer.toString(aggregatedConcepts.size()));
        }

        return result;
    }
}
