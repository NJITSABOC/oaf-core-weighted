package edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation;

import javax.swing.ImageIcon;

/**
 *
 * @author Chris O
 */
public class AbNDerivationExplanationEntry {
    private final ImageIcon image;
    
    private final String text;
    
    public AbNDerivationExplanationEntry(ImageIcon image, String text) {
        this.image = image;
        this.text = text;
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
