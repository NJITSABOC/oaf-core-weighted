package edu.njit.cs.saboc.blu.core.gui.graphframe;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;

/**
 *
 * @author Chris O
 */
public abstract class AbNDisplayManager {
    
    private final FrameCreationAction fca;
    
    public AbNDisplayManager(FrameCreationAction fca) {
        this.fca = fca;
    }
    
    protected FrameCreationAction getFrameCreationAction() {
        return fca;
    }
    
    public abstract void displayPAreaTaxonomy(PAreaTaxonomy taxonomy);
    
    public abstract void displayAreaTaxonomy(PAreaTaxonomy taxonomy);
    
    public abstract void displayDiffPAreaTaxonomy(DiffPAreaTaxonomy diffPAreaTaxonomy);
    
    public abstract void displayTribalAbstractionNetwork(ClusterTribalAbstractionNetwork tan);
    
    public abstract void displayBandTribalAbstractionNetwork(ClusterTribalAbstractionNetwork tan);
    
    public abstract void displayDisjointPAreaTaxonomy(
            DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy);
    
    public abstract void displayDisjointTribalAbstractionNetwork(
            DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTaxonomy);
    
    public abstract void displayTargetAbstractionNetwork(TargetAbstractionNetwork targetAbN);
}
