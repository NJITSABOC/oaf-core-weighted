package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class SearchPartitionedNodesAction extends BluGraphSearchAction<PartitionedNode> {
    
    private final PartitionedAbNConfiguration config;
    
    public SearchPartitionedNodesAction(PartitionedAbNConfiguration config) {
        super(config.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(true));
        
        this.config = config;
    }

    @Override
    public ArrayList<SearchButtonResult<PartitionedNode>> doSearch(String query) {

        PartitionedAbstractionNetwork abn = config.getAbstractionNetwork();

        Set<PartitionedNode> partitionedNodes = abn.getBaseAbstractionNetwork().searchNodes(query);

        ArrayList<SearchButtonResult<PartitionedNode>> results = new ArrayList<>();

        partitionedNodes.stream().forEach((node) -> {
            results.add(new SearchButtonResult<>(node.getName(), node));
        });

        results.sort((a, b) -> {
            return a.getResult().getName().compareTo(b.getResult().getName());
        });

        config.getUIConfiguration().getDisplayPanel().highlightPartitionedNodes(partitionedNodes);

        return results;
    }

    @Override
    public void resultSelected(SearchButtonResult<PartitionedNode> o) {
        PartitionedNode node = o.getResult();

        AbstractionNetworkGraph<?> graph = config.getUIConfiguration().getDisplayPanel().getGraph();

        PartitionedNodeEntry entry = graph.getContainerEntries().get(node);
        config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(entry);
    }
}
