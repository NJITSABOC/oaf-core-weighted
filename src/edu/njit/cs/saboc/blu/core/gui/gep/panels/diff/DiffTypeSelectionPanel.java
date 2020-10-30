package edu.njit.cs.saboc.blu.core.gui.gep.panels.diff;

import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class DiffTypeSelectionPanel extends AbNDisplayWidget {
    
    private final JCheckBox chkShowUnmodified;
    private final JCheckBox chkShowIntroduced;
    private final JCheckBox chkShowRemoved;
    private final JCheckBox chkShowModified;
    
    public DiffTypeSelectionPanel(AbNDisplayPanel displayPanel) {
        super(displayPanel);
        
        this.chkShowUnmodified = new JCheckBox("Unmodified");
        this.chkShowIntroduced = new JCheckBox("Introduced");
        this.chkShowRemoved = new JCheckBox("Removed");
        this.chkShowModified = new JCheckBox("Modified");
        
        JPanel optionPanel = new JPanel(new GridLayout(2, 2, 1, 1));
        
        optionPanel.add(chkShowUnmodified);
        optionPanel.add(chkShowIntroduced);
        optionPanel.add(chkShowRemoved);
        optionPanel.add(chkShowModified);
        
        JButton deriveBtn = new JButton("Show");
        
        this.setLayout(new BorderLayout());
        
        this.add(optionPanel, BorderLayout.CENTER);
        this.add(deriveBtn, BorderLayout.SOUTH);
    }
}
