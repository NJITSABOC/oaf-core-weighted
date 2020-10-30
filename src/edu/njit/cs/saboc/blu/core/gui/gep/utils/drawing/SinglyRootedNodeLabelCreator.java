package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 */
public class SinglyRootedNodeLabelCreator<T extends SinglyRootedNode> {
    
    public String getRootNameStr(T node) {
        return node.getRoot().getName();
    }
    
    public String getCountStr(T node) {
        return String.format("(%d)", node.getConceptCount());
    }
}
