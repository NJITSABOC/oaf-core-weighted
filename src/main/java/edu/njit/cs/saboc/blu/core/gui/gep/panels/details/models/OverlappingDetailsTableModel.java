package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingDetailsEntry;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OverlappingDetailsTableModel extends OAFAbstractTableModel<OverlappingDetailsEntry> {
    
    private final AbNConfiguration configuration;
    
    public OverlappingDetailsTableModel(PartitionedAbNConfiguration configuration) {
        
        super(new String[] {
            String.format("Other %s", configuration.getTextConfiguration().getNodeTypeName(false)),
            String.format("# Common %s", configuration.getTextConfiguration().getDisjointNodeTypeName(true),
            String.format("# Overlapping %s", configuration.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)))
        });

        this.configuration = configuration;
    }

    @Override
    protected Object[] createRow(OverlappingDetailsEntry item) {
        Set<Concept> overlappingConcepts = new HashSet<>();
        
        Set<DisjointNode> disjointGroups = item.getDisjointGroups();
        
        disjointGroups.forEach( (disjointGroup) -> {
            overlappingConcepts.addAll(disjointGroup.getConcepts());
        });
        
        return new Object[] {
            item.getOverlappingNode().getName(),
            disjointGroups.size(),
            overlappingConcepts.size()
        };
    }
    
}
