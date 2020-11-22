package edu.njit.cs.saboc.blu.core.utils.filterable.list;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Chris O
 */
public class FilterPanel extends JPanel {
    
    public interface FilterPanelListener {
        public void filterChanged(String filter);
        public void filterClosed();
    }
    
    private final JTextField filterField;
    private final JButton closeButton;
    
    private final ArrayList<FilterPanelListener> filterPanelListeners = new ArrayList<>();
    
    private String currentFilter = "";

    private final Timer updateTimer = new Timer(200, (ae) -> {

        if (canUpdate()) {
            filterPanelListeners.forEach((listener) -> {
                listener.filterChanged(currentFilter);
            });
        }

        stopTimer();
    });
    
    private boolean canUpdate() {
        return currentFilter.equals(filterField.getText());
    }

    public FilterPanel() {
        this.filterField = new JTextField();
        this.closeButton = new JButton();
        
        this.closeButton.setIcon(ImageManager.getImageManager().getIcon("cross.png"));
        this.closeButton.setToolTipText("Close");

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(closeButton);
        this.add(Box.createHorizontalStrut(10));
        
        this.add(new JLabel("Filter:  "));
        this.add(filterField);
        
        filterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    firePanelClosed();
                }
            }
        });

        closeButton.addActionListener((e) -> {
            firePanelClosed();
        });

        filterField.getDocument().addDocumentListener(new DocumentListener() {
            
            private void updateFilter(String filter) {
                currentFilter = filter;
                
                updateTimer.restart();
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter(filterField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter(filterField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFilter(filterField.getText());
            }
        });
        
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                filterField.requestFocus();
            }
        });
    }
    
    private void stopTimer() {
        updateTimer.stop();
    }
    
    private void firePanelClosed() {
        filterPanelListeners.forEach((listener) -> {
            listener.filterClosed();
        });
    }
    
    public void addFilterPanelListener(FilterPanelListener listener) {
        this.filterPanelListeners.add(listener);
    }
    
    public void removeFilterPanelListener(FilterPanelListener listener) {
        this.filterPanelListeners.remove(listener);
    }
    
    public void filteringStarted(String firstChar) {
        
        if(isPrintableCharacter(firstChar.charAt(0))) {  
            filterField.setText(firstChar);
        }

        filterField.requestFocus();
    }
    
    public void reset() {
        filterField.setText("");

        filterField.requestFocus();
    }
    
    private boolean isPrintableCharacter(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        
        return (!Character.isISOControl(c))
                && c != KeyEvent.CHAR_UNDEFINED
                && block != null
                && block != Character.UnicodeBlock.SPECIALS;
    }
    
    @Override
    public void setEnabled(boolean value) {
        
        super.setEnabled(value);
        
        this.filterField.setEditable(value);
        this.closeButton.setEnabled(value);
    }
}
