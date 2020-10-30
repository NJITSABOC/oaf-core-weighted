package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.options.EntityOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.options.IconOptionButton;
import java.awt.BorderLayout;
import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class NodeOptionsPanel<T extends Node> extends BaseNodeInformationPanel<T> {
    
    private final EntityOptionsPanel<T> basePanel;

    public NodeOptionsPanel() {
        this.setLayout(new BorderLayout());
        
        this.basePanel = new EntityOptionsPanel<>();
        
        this.add(basePanel, BorderLayout.CENTER);
    }
    
    public void addOptionButton(IconOptionButton<T> optionBtn) {
        basePanel.addOptionButton(optionBtn);
    }
    
    public Optional<T> getCurrentNode() {
        return basePanel.getCurrentEntity();
    }

    @Override
    public void setContents(T node) {
        basePanel.setContents(node);
    }

    @Override
    public void clearContents() {
        basePanel.clearContents();
    }
}
