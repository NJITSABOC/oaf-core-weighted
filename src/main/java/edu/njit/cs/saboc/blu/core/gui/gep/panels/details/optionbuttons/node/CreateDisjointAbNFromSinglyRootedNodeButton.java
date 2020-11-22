package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.CreateOverlappingNodeDisjointAbNAction;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class CreateDisjointAbNFromSinglyRootedNodeButton<T extends SinglyRootedNode> extends NodeOptionButton<T> {

    private final PartitionedAbNConfiguration config;
    private final DisplayAbNAction<DisjointAbstractionNetwork> displayAbNAction;

    public CreateDisjointAbNFromSinglyRootedNodeButton(
            PartitionedAbNConfiguration config,
            DisplayAbNAction<DisjointAbstractionNetwork> displayAbNAction) {

        super("BluDisjointAbN.png",
                String.format("Create disjoint %s",
                        config.getTextConfiguration().getAbNTypeName(false)));

        this.config = config;
        this.displayAbNAction = displayAbNAction;

        this.addActionListener( (ae) -> {
            createAndDisplayDisjointAbNAction();
        });
    }

    public final void createAndDisplayDisjointAbNAction() {
        T currentNode = super.getCurrentNode().get();
        
        PartitionedNode partitionedNode = config.getAbstractionNetwork().getPartitionNodeFor(currentNode);

        CreateOverlappingNodeDisjointAbNAction display = 
                new CreateOverlappingNodeDisjointAbNAction(
                    config, 
                    displayAbNAction, 
                    partitionedNode, 
                    currentNode,
                    String.format("Creating %s Disjoint %s",
                        currentNode.getName(),
                        config.getTextConfiguration().getAbNTypeName(false)));
        
        display.startThread();
    }

    @Override
    public void setEnabledFor(T node) {
        PartitionedNode partitionedNode = config.getAbstractionNetwork().getPartitionNodeFor(node);
        
        Map<Concept, Set<T>> conceptNodes = partitionedNode.getConceptNodes();
        
        boolean enabled = node.getConcepts().stream().anyMatch( (concept) -> {
            return conceptNodes.get(concept).size() > 1;
        });
        
        this.setEnabled(enabled);
    }
}
