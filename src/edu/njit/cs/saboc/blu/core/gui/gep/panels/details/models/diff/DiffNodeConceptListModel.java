package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.AbstractNodeEntityTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DiffNodeConceptListModel<T extends Node> extends AbstractNodeEntityTableModel<Concept, T> {
    
    private final Map<Concept, NodeConceptChange> changedConcepts = new HashMap<>();
    
    private final AbNConfiguration config;
    private final AbNTextConfiguration textConfig;
    
    public DiffNodeConceptListModel(
            AbNConfiguration config,
            AbNTextConfiguration textConfig) {
        
        
        super(new String[] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false),
            "Change"
        });
        
        this.config = config;
        this.textConfig = textConfig;
    }
    
    protected AbNConfiguration getConfiguration() {
        return config;
    }

    @Override
    public void clearCurrentNode() {
        super.clearCurrentNode();
        
        changedConcepts.clear();
    }

    @Override
    public void setCurrentNode(T node) {
        
        super.setCurrentNode(node);
        
        Set<NodeConceptChange> changes = ((DiffNodeInstance)node).getDiffNode().getChangeDetails().getConceptChanges();
        
        changes.forEach( (change) -> {
            changedConcepts.put(change.getConcept(), change);
        });
    }
    
    public NodeConceptChange getChangeForConcept(Concept c) {
        return this.changedConcepts.get(c);
    }

    @Override
    protected Object[] createRow(Concept item) {
        
        if(changedConcepts.containsKey(item)) {
            NodeConceptChange change = changedConcepts.get(item);

            return new Object [] {
                item,
                change.getChangeName(textConfig)
            };

        } else {
            return new Object [] {
                item,
                ""
            };
        }
        
    }
}
