package edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy;

import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.TextDrawingUtilities;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Chris
 */
public class ConceptEntry {

    public static final int CONCEPT_WIDTH = 200;
    public static final int CONCEPT_HEIGHT = 60;

    private final Concept concept;

    private Rectangle currentBounds = new Rectangle();

    // Simplify with a single vaiable and a state enum
    private boolean filledAsParent = false;

    private boolean filledAsChild = false;

    private boolean highlighted = false;
    private boolean selected = false;

    public ConceptEntry(Concept concept) {
        this.concept = concept;
    }

    public Concept getConcept() {
        return concept;
    }

    public void drawConceptAt(ConceptPainter painter, Graphics2D g2d, int x, int y) {
        
        Rectangle bounds = new Rectangle(x, y, CONCEPT_WIDTH, CONCEPT_HEIGHT);
        currentBounds = bounds;
        
        painter.paintConceptEntry(this, g2d);
        
        String text = concept.getName();

        if (text.length() > 25) {
            text = text.substring(0, 25) + "...";
        }

        Font savedFont = g2d.getFont();

        g2d.setFont(new Font("Tahoma", Font.PLAIN, 11));

        TextDrawingUtilities.drawTextWithinBounds(g2d, text,
                new Rectangle(x + 2, y + 2, bounds.width - 4, bounds.height - 4),
                new Rectangle(x, y, bounds.width, bounds.width));

        g2d.setFont(savedFont);
    }
    
    public Rectangle getBounds() {
        return currentBounds;
    }
    
    public void resetState() {
        this.selected = false;
        this.filledAsChild = false;
        this.filledAsParent = false;
    }
    
    public boolean isFilledAsParent() {
        return filledAsParent;
    }

    public boolean isFilledAsChild() {
        return filledAsChild;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public boolean isSelected() {
        return selected;
    }
    
    public void setFilledAsParent(boolean filledAsParent) {
        this.filledAsParent = filledAsParent;
    }

    public void setFilledAsChild(boolean filledAsChild) {
        this.filledAsChild = filledAsChild;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
