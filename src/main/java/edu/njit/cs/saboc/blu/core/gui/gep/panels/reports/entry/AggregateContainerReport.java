package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateContainerReport extends ContainerReport {
    
    public AggregateContainerReport(
            PartitionedNode container, 
            Set<SinglyRootedNode> groups, 
            Set<Concept> concepts) {

        super(container, groups, concepts, new HashSet<>());
    }
    
}
