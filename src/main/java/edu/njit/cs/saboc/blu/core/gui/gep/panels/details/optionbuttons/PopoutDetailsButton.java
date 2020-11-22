package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.options.IconOptionButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class PopoutDetailsButton<T> extends IconOptionButton<T> {
    
    public interface DetailsPanelGenerator {
        public JPanel generatePanel();
    }
    
    private final DetailsPanelGenerator generatorAction;
    
    public PopoutDetailsButton(
            String type, 
            DetailsPanelGenerator generatorAction) {
        
        super("BluExpandWindow.png", String.format("Display %s details in new window.", type));
        
        this.addActionListener( (ae) -> {
            SwingUtilities.invokeLater(() -> {
                displayDetailsWindow();
            });
        });
        
        this.generatorAction = generatorAction;
    }
    
    private void displayDetailsWindow() {
        JDialog detailsDialog = new JDialog();
        
        detailsDialog.setSize(700, 800);
        detailsDialog.setLocationRelativeTo(null);
        detailsDialog.add(generatorAction.generatePanel());
        
        detailsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        detailsDialog.setVisible(true);
        detailsDialog.setModal(true);
        detailsDialog.setAlwaysOnTop(true);
    }

    @Override
    public void setEnabledFor(T node) {
        this.setEnabled(true);
    }
}
