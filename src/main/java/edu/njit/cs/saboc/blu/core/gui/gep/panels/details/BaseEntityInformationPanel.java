
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class BaseEntityInformationPanel<T> extends JPanel {

    public abstract void setContents(T entity);

    public abstract void clearContents();
    
}
