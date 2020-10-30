package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.diff;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 */
public class DiffNodeStatusReport {
    
    public static enum DiffNodeStatus {
        MovedExactly,
        MovedSubset,
        MovedSuperset,
        MovedDifference,
        IntroducedFromNew,
        IntroducedFromOneNode,
        IntroducedFromMultipleNodes,
        RemovedFromOnt,
        RemovedToOneNode,
        RemovedToMultipleNodes
    }
    
    private final SinglyRootedNode node;
    private final DiffNodeStatus status;
    
    public DiffNodeStatusReport(SinglyRootedNode node, DiffNodeStatus status) {
        this.node = node;
        this.status = status;
    }
    
    public SinglyRootedNode getNode() {
        return node;
    }
    
    public DiffNodeStatus getStatus() {
        return status;
    }
}
