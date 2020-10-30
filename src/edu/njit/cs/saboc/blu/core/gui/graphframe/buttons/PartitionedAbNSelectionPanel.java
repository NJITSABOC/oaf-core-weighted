package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import java.awt.GridLayout;
import java.util.Optional;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris O
 */
public abstract class PartitionedAbNSelectionPanel extends JPanel {
    
    private final JToggleButton btnShowBase;
    private final JToggleButton btnShowFull;
    
    private Optional<PartitionedAbNConfiguration> optCurrentConfig = Optional.empty();
    
    public PartitionedAbNSelectionPanel() {
        super.setLayout(new GridLayout(1, 2, 2, 2));
        
        ButtonGroup bg = new ButtonGroup();
        
        this.btnShowFull = new JToggleButton(" ");
        this.btnShowFull.addActionListener( (ae) -> {
            showFullClicked();
        });
        
        this.btnShowBase = new JToggleButton(" ");
        this.btnShowBase.addActionListener( (ae) -> {
            showBaseClicked();
        });
        
        bg.add(btnShowFull);
        bg.add(btnShowBase);
        
        btnShowFull.setSelected(true);
        
        this.add(btnShowFull);
        this.add(btnShowBase);
    }
    
    public void initialize(PartitionedAbNConfiguration config, boolean showingFull) {
        this.optCurrentConfig = Optional.of(config);
        
        this.btnShowFull.setSelected(showingFull);
        this.btnShowBase.setSelected(!showingFull);
        
        this.btnShowFull.setText(config.getTextConfiguration().getAbNTypeName(false));
        this.btnShowBase.setText(config.getTextConfiguration().getBaseAbNTextConfiguration().getAbNTypeName(false));
        
        this.revalidate();
        this.repaint();
    }
    
    public void clear() {
        this.optCurrentConfig = Optional.empty();
    }
    
    public abstract void showFullClicked();
    
    public abstract void showBaseClicked();
}
