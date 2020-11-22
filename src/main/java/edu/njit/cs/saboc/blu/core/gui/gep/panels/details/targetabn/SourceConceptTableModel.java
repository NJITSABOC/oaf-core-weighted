package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.RelationshipTriple;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.AbstractNodeEntityTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Chris O
 */
public class SourceConceptTableModel extends AbstractNodeEntityTableModel<Concept, TargetGroup> {
    
    public SourceConceptTableModel(TargetAbNConfiguration config) {
        
        super(new String [] {
            String.format("Source %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)),
            String.format("Target %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
    }

    @Override
    protected Object[] createRow(Concept item) {
        
        TargetGroup group = super.getCurrentNode().get();
        
        Set<RelationshipTriple> triples = group.getIncomingRelationshipDetails().getOutgoingRelationshipsFrom(item);
        
        Set<Concept> targets = triples.stream().map( (triple -> triple.getTarget())).collect(Collectors.toSet());
        
        ArrayList<Concept> sortedTargets = new ArrayList<>(targets);
        sortedTargets.sort(new ConceptNameComparator());
        
        String targetNameStr = sortedTargets.get(0).getName();
        
        for(int c = 1; c < sortedTargets.size(); c++) {
            targetNameStr += "\n" + sortedTargets.get(c).getName();
        }
        
        return new Object [] {
            item,
            targetNameStr
        };
    }
}
