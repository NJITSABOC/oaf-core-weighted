package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.AbstractNodeEntityTableModel;
import java.util.Optional;

/**
 *  A list for displaying information about entities related to some node.
 *  (e.g., concepts in a partial-area, an area's relationships)
 * @author Chris O
 * 
 * @param <ENTITY_T> The type of the entities being displayed in the list
 */
public abstract class NodeEntityList<T extends Node, ENTITY_T> extends AbstractEntityList<ENTITY_T>  {
    
    private Optional<T> currentNode = Optional.empty();
    
    public NodeEntityList(AbstractNodeEntityTableModel<ENTITY_T, T> tableModel) {
        super(tableModel);
    }
    
    public AbstractNodeEntityTableModel<ENTITY_T, T> getTableModel() {
        return (AbstractNodeEntityTableModel<ENTITY_T, T>)super.getTableModel();
    }
    
    public void setCurrentNode(T node) {
        this.currentNode = Optional.of(node);
        
        getTableModel().setCurrentNode(node);
    }
    
    public Optional<T> getCurrentNode() {
        return currentNode;
    }
    
    public void clearCurrentNode() {
        this.currentNode = Optional.empty();
        
        getTableModel().clearCurrentNode();
    }
}
