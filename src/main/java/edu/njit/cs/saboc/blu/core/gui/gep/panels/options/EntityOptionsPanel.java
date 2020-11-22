package edu.njit.cs.saboc.blu.core.gui.gep.panels.options;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class EntityOptionsPanel<T> extends JPanel {
    
    private final ArrayList<IconOptionButton<T>> optionButtons = new ArrayList<>();
    
    private Optional<T> currentEntity = Optional.empty();
    
    public EntityOptionsPanel() {
        
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.add(Box.createHorizontalStrut(4));
        
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Options"));
    }
    
    public void addOptionButton(IconOptionButton<T> optionBtn) {
        optionButtons.add(optionBtn);
        
        this.add(optionBtn);
        this.add(Box.createHorizontalStrut(4));
    }
    
    public Optional<T> getCurrentEntity() {
        return currentEntity;
    }

    public void setContents(T node) {
        currentEntity = Optional.of(node);
        
        optionButtons.forEach( (btn) -> {
            btn.setCurrentEntity(node);
        });
    }

    public void clearContents() {
        currentEntity = Optional.empty();
        
        optionButtons.forEach((btn) -> {
            btn.clearCurrentEntity();
        });
    }
}
