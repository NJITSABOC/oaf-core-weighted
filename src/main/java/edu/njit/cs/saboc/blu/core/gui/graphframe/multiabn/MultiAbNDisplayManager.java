package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.DisjointPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.tan.DisjointCluster;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.FrameCreationAction;

/**
 *
 * @author Chris O
 */
public class MultiAbNDisplayManager extends AbNDisplayManager {

    private final MultiAbNGraphFrame graphFrame;

    public MultiAbNDisplayManager(
            MultiAbNGraphFrame graphFrame,
            FrameCreationAction fca) {

        super(fca);

        this.graphFrame = graphFrame;
    }

    @Override
    public void displayPAreaTaxonomy(PAreaTaxonomy taxonomy) {
        graphFrame.displayPAreaTaxonomy(taxonomy);
    }
    
    @Override
    public void displayAreaTaxonomy(PAreaTaxonomy taxonomy) {
        graphFrame.displayAreaTaxonomy(taxonomy);
    }

    @Override
    public void displayDiffPAreaTaxonomy(DiffPAreaTaxonomy diffPAreaTaxonomy) {
        // Do nothing for now...
    }

    @Override
    public void displayTribalAbstractionNetwork(ClusterTribalAbstractionNetwork tan) {
        graphFrame.displayTAN(tan);
    }
    
    @Override
    public void displayBandTribalAbstractionNetwork(ClusterTribalAbstractionNetwork tan) {
        graphFrame.displayBandTAN(tan);
    }

    @Override
    public void displayDisjointPAreaTaxonomy(DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy) {
        graphFrame.displayDisjointPAreaTaxonomy(
                (DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea>) (DisjointAbstractionNetwork<?, ?, ?>) disjointTaxonomy);
    }

    @Override
    public void displayDisjointTribalAbstractionNetwork(DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTaxonomy) {
        graphFrame.displayDisjointTAN(
                (DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster>) (DisjointAbstractionNetwork<?, ?, ?>) disjointTaxonomy);
    }

    @Override
    public void displayTargetAbstractionNetwork(TargetAbstractionNetwork targetAbN) {
        graphFrame.displayTargetAbstractionNetwork(targetAbN);
    }
}
