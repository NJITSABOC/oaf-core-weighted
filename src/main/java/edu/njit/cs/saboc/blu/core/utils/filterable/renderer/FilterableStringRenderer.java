package edu.njit.cs.saboc.blu.core.utils.filterable.renderer;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author Chris O
 */
public class FilterableStringRenderer extends BaseFilterableRenderer<String> {
    
    private final JLabel stringLabel;
    
    public FilterableStringRenderer() {
        this.stringLabel = new JLabel("");
        this.stringLabel.setFont(this.stringLabel.getFont().deriveFont(14.0f));
        this.stringLabel.setOpaque(false);
        
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.add(stringLabel);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Filterable<String>> list, 
            Filterable<String> value,
            int index,
            boolean isSelected, 
            boolean cellHasFocus) {
        
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        showDetailsFor(value);
        
        return this;
    }

    @Override
    public void showDetailsFor(Filterable<String> element) {
        this.stringLabel.setText(element.getObject());
    }
}
