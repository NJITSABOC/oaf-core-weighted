package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

/**
 *
 * @author Chris O
 */
public interface EntitySelectionListener<ENTITY_T> {
    public void entityClicked(ENTITY_T entity);
    
    public void entityDoubleClicked(ENTITY_T entity);
    
    public void noEntitySelected();
}
