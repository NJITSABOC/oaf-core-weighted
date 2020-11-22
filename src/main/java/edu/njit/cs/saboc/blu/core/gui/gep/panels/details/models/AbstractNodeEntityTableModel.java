package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Optional;

/**
 *
 * @author Chris O
 * 
 * @param <ENTITY_T>
 * @param <NODE_T>
 */
public abstract class AbstractNodeEntityTableModel<ENTITY_T, NODE_T extends Node> extends OAFAbstractTableModel<ENTITY_T> {
    
    private Optional<NODE_T> currentNode = Optional.empty();
    
    public AbstractNodeEntityTableModel(String [] columnNames) {
        super(columnNames);
    }
    
    public Optional<NODE_T> getCurrentNode() {
        return currentNode;
    }
    
    public void setCurrentNode(NODE_T node) {
        currentNode = Optional.of(node);
    }
    
    public void clearCurrentNode() {
        currentNode = Optional.empty();
    }
}
