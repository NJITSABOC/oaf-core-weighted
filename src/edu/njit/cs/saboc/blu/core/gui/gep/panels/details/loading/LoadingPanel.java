package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.loading;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.label.DetailsPanelLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Chris O
 */
public class LoadingPanel extends JPanel {
    
    private final DetailsPanelLabel loadingLabel;
    private final JEditorPane loadingDetailsPane;
    
    public LoadingPanel() {
        
        loadingLabel = new DetailsPanelLabel("Loading.... Please wait.");
        
        loadingDetailsPane = new JEditorPane();
        loadingDetailsPane.setContentType("text/html");
        loadingDetailsPane.setEnabled(true);
        loadingDetailsPane.setEditable(false);
        loadingDetailsPane.setFont(loadingDetailsPane.getFont().deriveFont(Font.BOLD, 14));
        loadingDetailsPane.setText("One moment please. Information about the abstraction network is currently "
                + "being processed. This message may appear for a few moments.");
               
        this.setLayout(new BorderLayout());
        
        this.add(loadingLabel, BorderLayout.NORTH);
        this.add(new JScrollPane(loadingDetailsPane), BorderLayout.CENTER);
    }
}
