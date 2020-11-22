package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffChildNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffParentNodeTableModel;

/**
 *
 * @author Chris O
 */
public class DiffNodeHierarchyPanel<T extends Node> extends NodeHierarchyPanel<T> {
    
    public DiffNodeHierarchyPanel(AbNConfiguration config) {
        super(config, 
                new DiffParentNodeTableModel<>(config),
                new DiffChildNodeTableModel<>(config));
    }
}
