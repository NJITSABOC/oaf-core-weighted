package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ParentDisjointNodeTableModel<T extends DisjointNode> extends OAFAbstractTableModel<ParentNodeDetails<T>> {
    
    private final DisjointAbNConfiguration<T> config;
    
    public ParentDisjointNodeTableModel(DisjointAbNConfiguration<T> config) {
        super(new String [] {
            String.format("Parent %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)),
            String.format("Parent %s", config.getTextConfiguration().getNodeTypeName(false)),
            String.format("Parent Overlapping %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ParentNodeDetails<T> item) {
        ArrayList<String> overlappingNodeNames = new ArrayList<>();
        
        DisjointNode parentDisjointNode = item.getParentNode();
        
        Set<Node> overlaps = parentDisjointNode.getOverlaps();
        
        overlaps.forEach((node) -> {
            overlappingNodeNames.add(String.format("%s (%d)", 
                    node.getName(),
                    node.getConceptCount()));
        });
        
        Collections.sort(overlappingNodeNames);
        
        String overlapsStr = overlappingNodeNames.get(0);
        
        for(int c = 1; c < overlappingNodeNames.size(); c++) {
            overlapsStr += ("\n" + overlappingNodeNames.get(c));
        }
        
        return new Object [] {
            item.getParentConcept().getName(),
            parentDisjointNode.getName(),
            overlapsStr,
            parentDisjointNode.getConceptCount()
        };
        
    }
}
