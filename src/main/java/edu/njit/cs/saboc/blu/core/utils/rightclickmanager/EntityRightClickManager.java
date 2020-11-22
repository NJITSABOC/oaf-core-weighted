package edu.njit.cs.saboc.blu.core.utils.rightclickmanager;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;

/**
 *
 * @author Kevyn
 * @param <T>
 */
public class EntityRightClickManager<T> {
    
    private final JPopupMenu popup;
                
    private Optional<EntityRightClickMenuGenerator<T>> menuGenerator;
    
    private Optional<T> rightClickedItem;
    
    private int menuItemCount = 0;
    
    public EntityRightClickManager(){
        this.menuGenerator = Optional.empty();
        this.rightClickedItem = Optional.empty();
        
        this.popup = new JPopupMenu();
    }
    
    public void setMenuGenerator(EntityRightClickMenuGenerator<T> menuGenerator) {
        this.menuGenerator = Optional.of(menuGenerator);
    }
    
    public void clearMenuGenerator() {
        this.menuGenerator = Optional.empty();
    }

    public void setRightClickedItem(T item) {
        resetPopup();
        
        rightClickedItem = Optional.of(item);

        if(menuGenerator.isPresent()) {
            buildPopup(menuGenerator.get().buildRightClickMenuFor(item));
        }
    }
    
    public void clearRightClickedItem() {
        resetPopup();
        
        this.rightClickedItem = Optional.empty();
        
        if(menuGenerator.isPresent()) {
            buildPopup(menuGenerator.get().buildEmptyListRightClickMenu());
        }
    }
    
    private void resetPopup() {
        this.menuItemCount = 0;
        this.popup.removeAll();
    }
    
    private void buildPopup(ArrayList<JComponent> components) {
        
        components.forEach((component) -> {
            popup.add(component);
        });
        
        this.menuItemCount = components.size();
    }

    public void showPopup(MouseEvent e) {
        if(menuGenerator.isPresent() && menuItemCount > 0) {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
