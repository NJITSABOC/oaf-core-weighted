package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ConceptNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Chris o
 */
public class SearchConceptsInPartitionedNodes extends BluGraphSearchAction<ConceptNodeDetails<PartitionedNode>> {
    
    private final PartitionedAbNConfiguration config;
    
    public SearchConceptsInPartitionedNodes(PartitionedAbNConfiguration config) {
        super(config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true));
        
        this.config = config;
    }

    @Override
    public ArrayList<SearchButtonResult<ConceptNodeDetails<PartitionedNode>>> doSearch(String query) {

        ArrayList<SearchButtonResult<ConceptNodeDetails<PartitionedNode>>> results = new ArrayList<>();

        if (query.length() >= 3) {
            AbstractionNetwork abn = config.getAbstractionNetwork().getBaseAbstractionNetwork();

            Set<ConceptNodeDetails<PartitionedNode>> searchResults = abn.searchConcepts(query);

            searchResults.stream().forEach((sr) -> {
                results.add(new SearchButtonResult(sr.getConcept().getName(), sr));
            });

            results.sort((a, b) -> {
                return a.getResult().getConcept().getName().compareToIgnoreCase(
                        b.getResult().getConcept().getName());
            });
        }

        return results;
    }

    @Override
    public void resultSelected(SearchButtonResult<ConceptNodeDetails<PartitionedNode>> o) {
        ConceptNodeDetails<PartitionedNode> result = o.getResult();

        AbstractionNetworkGraph<?> graph = config.getUIConfiguration().getDisplayPanel().getGraph();

        PartitionedNodeEntry entry = graph.getContainerEntries().get(result.getNodes().iterator().next());
        
        config.getUIConfiguration().getDisplayPanel().highlightPartitionedNodes(result.getNodes());
        config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(entry);
    }
}

