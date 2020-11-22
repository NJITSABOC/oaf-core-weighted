package edu.njit.cs.saboc.blu.core.gui.utils.renderers;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ConceptListConceptRenderer<T extends Concept> extends JPanel implements TableCellRenderer {

    private final BasicFilterableConceptRenderer<T> renderer;
    
    public ConceptListConceptRenderer() {
        this.setLayout(new BorderLayout());
        
        this.renderer = new BasicFilterableConceptRenderer<>();
        
        this.add(renderer, BorderLayout.CENTER);
    }
    
    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int rowIndex,
            int colIndex) {

        renderer.showDetailsFor(new BasicFilterableConcept<>((T)value));
        
        table.setRowHeight(rowIndex, 38);

        return this;
    }
}
