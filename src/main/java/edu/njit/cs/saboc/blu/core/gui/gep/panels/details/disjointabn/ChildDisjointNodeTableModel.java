package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ChildDisjointNodeTableModel<T extends DisjointNode> extends OAFAbstractTableModel<T> {
    
    private final DisjointAbNConfiguration<T> configuration;
    
    public ChildDisjointNodeTableModel(DisjointAbNConfiguration<T> configuration) {
        super(new String [] {
            configuration.getTextConfiguration().getNodeTypeName(false), 
            String.format("Overlapping %s", configuration.getTextConfiguration().getOverlappingNodeTypeName(true)),
            String.format("# %s", configuration.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
        
        this.configuration = configuration;
    }
    
    @Override
    protected Object[] createRow(T disjointNode) {        
        ArrayList<String> overlappingPAreaNames = new ArrayList<>();
        
        Set<Node> overlaps = disjointNode.getOverlaps();
        
        overlaps.forEach( (node) -> {
            overlappingPAreaNames.add(String.format("%s (%d)", 
                    node.getName(),
                    node.getConceptCount()));
        });
        
        Collections.sort(overlappingPAreaNames);
        
        String overlapsStr = overlappingPAreaNames.get(0);
        
        for(int c = 1; c < overlappingPAreaNames.size(); c++) {
            overlapsStr += ("\n" + overlappingPAreaNames.get(c));
        }
        
        return new Object [] {
            disjointNode.getName(),
            overlapsStr,
            disjointNode.getConceptCount()
        };
    }
}
