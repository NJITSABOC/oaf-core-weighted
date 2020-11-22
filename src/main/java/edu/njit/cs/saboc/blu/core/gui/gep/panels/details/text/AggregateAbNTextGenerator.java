package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateAbNTextGenerator {
    
    public String generateAggregateDetailsText(AbNTextConfiguration config, AbstractionNetwork<?> abn) {

        AggregateAbstractionNetwork aggregateAbN = (AggregateAbstractionNetwork)abn;
                
        String result = "This <AbNTypeName> is <b>aggregated</b> with a bound of "
                + "<font color = 'RED'><aggregateBound></font>. A total of "
                + "<font color = 'RED'><aggregateNodeCount></font> aggregate "
                + "<nodeTypeName count=<aggregateNodeCount>> summarize "
                + "<font color = 'RED'><aggregatedNodeCount></font> small "
                + "<nodeTypeName count=<aggregatedNodeCount>>.";
        
        
        Set<AggregateNode> aggregatedNodes = new HashSet<>();
        
        abn.getNodes().forEach( (node) -> {
            AggregateNode aggregateNode = (AggregateNode)node;
            
            aggregatedNodes.addAll(aggregateNode.getAggregatedNodes());
        });
        
        result = result.replaceAll("<aggregateBound>", 
                Integer.toString(aggregateAbN.getAggregateBound()));
        
        result = result.replaceAll("<aggregateNodeCount>", 
                Integer.toString(abn.getNodeCount()));
        
        result = result.replaceAll("<aggregatedNodeCount>", 
                Integer.toString(aggregatedNodes.size()));
        
        AbNTextFormatter formatter = new AbNTextFormatter(config);
        
        return formatter.format(result);
    }
}
