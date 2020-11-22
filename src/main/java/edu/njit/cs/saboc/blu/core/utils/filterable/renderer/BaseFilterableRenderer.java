package edu.njit.cs.saboc.blu.core.utils.filterable.renderer;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * The base class for all renderers that are used in filterable lists
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class BaseFilterableRenderer<T> extends JPanel 
        implements ListCellRenderer<Filterable<T>> {
    
    public BaseFilterableRenderer() { 
        
    }
    
    @Override
    public Component getListCellRendererComponent(
            JList<? extends Filterable<T>> list, 
            Filterable<T> value, 
            int index, 
            boolean isSelected, 
            boolean cellHasFocus) {
        
        if(cellHasFocus) {
            this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        } else {
            this.setBorder(BorderFactory.createEmptyBorder());
        }

        if(isSelected) {
            this.setBackground(new Color(0, 0, 255, 50));
        } else {

            Color basebackgroundColor;

            if (index % 2 == 1) {
                basebackgroundColor = new Color(240, 240, 255);
            } else {
                basebackgroundColor = new Color(255, 255, 255);
            }

            this.setBackground(basebackgroundColor);
        }

        return this;
    }
    
    public abstract void showDetailsFor(Filterable<T> element);
}
