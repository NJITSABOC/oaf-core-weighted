package edu.njit.cs.saboc.blu.core.gui.hierarchypainter;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class ShowHierarchyDialog {
    
    public static <T extends Concept> void displayHierarchyDialog(Hierarchy<T> hierarchy) {
        CompleteHierarchyPanel panel = new CompleteHierarchyPanel(hierarchy, 800);
        
        JDialog dialog = new JDialog();
        
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(panel, BorderLayout.CENTER);
        
        dialog.add(innerPanel);
        
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(null);
        //dialog.setResizable(false);
        
        dialog.setModal(true);
        
        dialog.setVisible(true);
    }
}
