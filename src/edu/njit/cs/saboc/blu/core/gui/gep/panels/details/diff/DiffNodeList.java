package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeTableModel;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DiffNodeList<T extends Node> extends AbstractEntityList<T> {
    
    private final PartitionedAbNConfiguration config;
    
    public DiffNodeList(PartitionedAbNConfiguration configuration) {
        this(new DiffNodeTableModel(configuration), configuration);
    }
    
    public DiffNodeList(OAFAbstractTableModel<T> model, PartitionedAbNConfiguration configuration) {
        super(model);
        
        this.config = configuration;
    }

    @Override
    protected String getBorderText(Optional<ArrayList<T>> entities) {
        return "";
    }
}
