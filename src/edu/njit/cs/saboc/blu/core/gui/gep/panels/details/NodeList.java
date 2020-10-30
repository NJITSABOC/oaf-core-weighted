package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.NodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class NodeList<T extends Node> extends AbstractEntityList<T> {
    
    private final AbNConfiguration config;
    
    public NodeList(AbNConfiguration<T> config) {
        this(new NodeTableModel<>(config), config);
    }
    
    public NodeList(OAFAbstractTableModel<T> model, AbNConfiguration config) {
        super(model);
        
        this.config = config;
    }

    @Override
    protected String getBorderText(Optional<ArrayList<T>> entities) {
        String base = config.getTextConfiguration().getNodeTypeName(true);
        
        if(entities.isPresent()) {
            return String.format("%s (%d)", base, entities.get().size());
        } else {
            return String.format("%s (0)", base);
        }
    }
}
