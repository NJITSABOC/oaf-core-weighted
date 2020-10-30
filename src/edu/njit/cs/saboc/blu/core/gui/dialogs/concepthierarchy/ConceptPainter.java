package edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author Chris
 */
public class ConceptPainter<T> {
    public void paintConceptEntry(ConceptEntry entry, Graphics2D g2d) {
        
        g2d.setPaint(getEntryColor(entry));
      
        g2d.fillRect(entry.getBounds().x, entry.getBounds().y, entry.getBounds().width, entry.getBounds().height);

        Stroke savedStroke = g2d.getStroke();

        g2d.setStroke(new BasicStroke(1));

        g2d.setPaint(Color.BLACK);

        g2d.drawRect(entry.getBounds().x, entry.getBounds().y, entry.getBounds().width, entry.getBounds().height);

        g2d.setColor(Color.BLACK);

        g2d.setStroke(savedStroke);
    }
    
    public Color getEntryColor(ConceptEntry entry) {
        if (entry.isHighlighted()) {
            return Color.CYAN;
        } else if (entry.isFilledAsParent()) {
            return Color.BLUE;
        } else if (entry.isFilledAsChild()) {
            return Color.MAGENTA;
        } else if (entry.isSelected()) {
            return Color.YELLOW;
        } else {
           return Color.LIGHT_GRAY;
        }
    }
}
