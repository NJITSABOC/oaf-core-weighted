package edu.njit.cs.saboc.blu.core.gui.gep.panels.options;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Chris O
 */
public abstract class IconOptionButton<T> extends JButton {
    
    private Optional<T> currentEntity = Optional.empty();
    
    public IconOptionButton(
            String iconFileName, 
            String toolTipText) {
        
        ImageIcon icon = ImageManager.getImageManager().getIcon(iconFileName);

        this.setIcon(icon);
        
        this.setBackground(new Color(240, 240, 255));

        Dimension sizeDimension = new Dimension(50, 50);

        this.setMinimumSize(sizeDimension);
        this.setMaximumSize(sizeDimension);
        this.setPreferredSize(sizeDimension);
        
        this.setToolTipText(toolTipText);
    }
    
    public void setCurrentEntity(T entity) {
        this.currentEntity = Optional.of(entity);
        
        setEnabledFor(entity);
    }
    
    public Optional<T> getCurrentEntity() {
        return currentEntity;
    }

    public void clearCurrentEntity() {
        currentEntity = Optional.empty();
        
        this.setEnabled(false);
    }
    
    public abstract void setEnabledFor(T entity);
}
