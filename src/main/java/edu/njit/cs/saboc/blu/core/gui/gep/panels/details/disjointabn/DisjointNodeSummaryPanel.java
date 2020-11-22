package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class DisjointNodeSummaryPanel extends NodeSummaryPanel<DisjointNode> {
    
    private final OverlappingNodeList overlapsPanel;
    
    private final DisjointAbNConfiguration configuration;
    
    public DisjointNodeSummaryPanel(DisjointAbNConfiguration configuration) {
        this(configuration, new DisjointNodeSummaryTextFactory(configuration));
    }
    
    public DisjointNodeSummaryPanel(DisjointAbNConfiguration configuration, DisjointNodeSummaryTextFactory summaryTextFactory) {
        
        super(summaryTextFactory);
        
        this.configuration = configuration;
        
        this.overlapsPanel = new OverlappingNodeList(configuration);
        this.overlapsPanel.setMinimumSize(new Dimension(-1, 100));
        this.overlapsPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.add(this.overlapsPanel);
    }
    
    
    public void setContents(DisjointNode disjointNode) {
        super.setContents(disjointNode);
        
        ArrayList<Node> overlaps = new ArrayList<>(disjointNode.getOverlaps());
        
        Collections.sort(overlaps, (a, b) -> a.getName().compareTo(b.getName()));
        
        overlapsPanel.setContents(overlaps);
    }
    
    public void clearContents() {
        super.clearContents();
        
        overlapsPanel.clearContents();
    }
}
