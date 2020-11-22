package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ConceptNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class SearchConceptsInSinglyRootedNodesAction extends BluGraphSearchAction<ConceptNodeDetails<SinglyRootedNode>> {
    
    private final AbNConfiguration config;
    
    public SearchConceptsInSinglyRootedNodesAction(AbNConfiguration config) {
        super(config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true));
        
        this.config = config;
    }

    @Override
    public ArrayList<SearchButtonResult<ConceptNodeDetails<SinglyRootedNode>>> doSearch(String query) {

        ArrayList<SearchButtonResult<ConceptNodeDetails<SinglyRootedNode>>> results = new ArrayList<>();

        if (query.length() >= 3) {
            AbstractionNetwork abn = config.getAbstractionNetwork();

            Set<ConceptNodeDetails<SinglyRootedNode>> searchResults = abn.searchConcepts(query);

            searchResults.stream().forEach((sr) -> {
                results.add(new SearchButtonResult(sr.getConcept().getName(), sr));
            });

            results.sort((a, b) -> {
                return a.getResult().getConcept().getName().compareToIgnoreCase(b.getResult().getConcept().getName());
            });
        }

        return results;
    }

    @Override
    public void resultSelected(SearchButtonResult<ConceptNodeDetails<SinglyRootedNode>> o) {
        ConceptNodeDetails<SinglyRootedNode> result = o.getResult();

        // Java!
        SinglyRootedNodeEntry firstResultEntry = (SinglyRootedNodeEntry) config.getUIConfiguration().getDisplayPanel().getGraph().getNodeEntries().get(
                result.getNodes().iterator().next());

        config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(firstResultEntry);

        config.getUIConfiguration().getDisplayPanel().highlightSinglyRootedNodes(result.getNodes());
    }
}
