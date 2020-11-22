
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeList;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class OverlappingNodeList<T extends Node> extends NodeList<T> {

    private final DisjointAbNConfiguration config;
    
    public OverlappingNodeList(DisjointAbNConfiguration config) {
        super(new OverlappingNodeTableModel<T>(config), config);
        
        this.config = config;
    }
    
    @Override
    protected String getBorderText(Optional<ArrayList<T>> entities) {
        if(entities.isPresent()) {
            String base = String.format("Overlapping %s", config.getTextConfiguration().getOverlappingNodeTypeName(false));
            
            return String.format("%s (%d)", base, entities.get().size());
        } else {
            return "Overlapping";
        }
    }
}
