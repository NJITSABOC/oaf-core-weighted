package edu.njit.cs.saboc.blu.core.utils.rightclickmanager;

import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class EntityRightClickMenuGenerator<T> {

    public EntityRightClickMenuGenerator() {
        
    }
    
    public abstract ArrayList<JComponent> buildRightClickMenuFor(T item);
    public abstract ArrayList<JComponent> buildEmptyListRightClickMenu();
}
