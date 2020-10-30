package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.options.EntityOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.options.IconOptionButton;
import java.awt.BorderLayout;
import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AbNOptionsPanel<T extends AbstractionNetwork> extends BaseEntityInformationPanel<T> {
    
    private final EntityOptionsPanel<T> basePanel;

    public AbNOptionsPanel() {
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