package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.label.DetailsPanelLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Chris O
 * 
 * @param <ABN_T>
 */
public class GenericAbNSummaryPanel<ABN_T extends AbstractionNetwork> extends JPanel {
    
    private final DetailsPanelLabel abnNameLabel;
    
    private final JEditorPane abnDetailsPane;
    
    private final AbNOptionsPanel optionsPanel;
    
    public GenericAbNSummaryPanel(AbNConfiguration config) {
        
        abnNameLabel = new DetailsPanelLabel(config.getTextConfiguration().getAbNName());
        
        abnDetailsPane = new JEditorPane();
        abnDetailsPane.setContentType("text/html");
        abnDetailsPane.setEnabled(true);
        abnDetailsPane.setEditable(false);
        abnDetailsPane.setFont(abnDetailsPane.getFont().deriveFont(Font.BOLD, 14));
        abnDetailsPane.setText(config.getTextConfiguration().getAbNSummary());
        abnDetailsPane.setSelectionStart(0);
        abnDetailsPane.setSelectionEnd(0);
        
        optionsPanel = config.getUIConfiguration().getAbNOptionsPanel();
               
        this.setLayout(new BorderLayout());
        
        this.add(abnNameLabel, BorderLayout.NORTH);
        this.add(new JScrollPane(abnDetailsPane), BorderLayout.CENTER);
        this.add(optionsPanel, BorderLayout.SOUTH);
    }
}
