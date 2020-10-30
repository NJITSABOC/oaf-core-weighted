package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class SearchSinglyRootedNodesAction extends BluGraphSearchAction<SinglyRootedNode> {
    
    private final AbNConfiguration config;
    
    public SearchSinglyRootedNodesAction(AbNConfiguration config) {
        super(config.getTextConfiguration().getNodeTypeName(true));
        
        this.config = config;
    }

    @Override
    public ArrayList<SearchButtonResult<SinglyRootedNode>> doSearch(String query) {
        
        AbstractionNetwork abn = config.getAbstractionNetwork();

        ArrayList<SearchButtonResult<SinglyRootedNode>> results = new ArrayList<>();

        Set<Node> pareas = abn.searchNodes(query);

        pareas.forEach((node) -> {
            SinglyRootedNode singlyRootedNode = (SinglyRootedNode) node;

            results.add(new SearchButtonResult<>(singlyRootedNode.getName(), singlyRootedNode));
        });

        results.sort((a, b) -> {
            return a.getResult().getName().compareToIgnoreCase(b.getResult().getName());
        });

        return results;
    }

    @Override
    public void resultSelected(SearchButtonResult<SinglyRootedNode> o) {
        SinglyRootedNode result = o.getResult();

        SinglyRootedNodeEntry resultEntry = (SinglyRootedNodeEntry) config.getUIConfiguration().getDisplayPanel().getGraph().getNodeEntries().get(result);

        config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(resultEntry);
        config.getUIConfiguration().getDisplayPanel().highlightSinglyRootedNodes(Collections.singleton(result));
    }
}
