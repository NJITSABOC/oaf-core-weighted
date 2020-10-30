package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterPanel;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterPanel.FilterPanelListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris O
 */
public class InheritablePropertySelectionPanel extends AbNDerivationWizardPanel {
    
    public static enum SelectionType {
        Multiple,
        Single
    }
    
    public interface InheritablePropertySelectionListener {
        public void propertiesSelected(Set<InheritableProperty> properties);
    }
    
    private ButtonGroup buttonGroup;
    
    private final ArrayList<JToggleButton> propertyBoxes;
    private final ArrayList<InheritableProperty> availableProperties;
    
    private final JPanel propertyListPanel;
    
    private final JButton btnUnselectAll;
    private final JButton btnSelectAll;
    
    private final JScrollPane propertyScroller;
    
    private final FilterPanel filterPanel;
    
    private final SelectionType selectionType;
    
    private final boolean showFilter;
    
    private final ArrayList<InheritablePropertySelectionListener> selectedPropertiesChangedListeners = new ArrayList<>();
    
    public InheritablePropertySelectionPanel(SelectionType selectionType, boolean showFilter) {
        
        this.selectionType = selectionType;
        this.showFilter = showFilter;
        
        this.setLayout(new BorderLayout());
        
        this.propertyBoxes = new ArrayList<>();
        this.availableProperties = new ArrayList<>();
        
        this.propertyListPanel = new JPanel();
        this.propertyListPanel.setLayout(new BoxLayout(propertyListPanel, BoxLayout.Y_AXIS));
        
        this.propertyListPanel.setBackground(Color.WHITE);
        
        this.propertyScroller = new JScrollPane(propertyListPanel);
        
        this.add(propertyScroller, BorderLayout.CENTER);

        btnUnselectAll = new JButton("Deselect All");
        btnUnselectAll.setToolTipText("Deselect all of the Above Check Boxes");
        btnUnselectAll.addActionListener( (ae) -> {
            propertyBoxes.forEach( (cb) -> {
               cb.setSelected(false);
            });
            
            buttonGroup.clearSelection();
        });
        
        btnSelectAll = new JButton("Select All");
        btnSelectAll.setToolTipText("Select all of the Above Check Boxes");
        btnSelectAll.addActionListener( (ae) -> {
            propertyBoxes.forEach( (cb) -> {
               cb.setSelected(true);
            });
        });

        JPanel selectionBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        selectionBtnPanel.add(btnUnselectAll);
        
        if(this.selectionType == SelectionType.Multiple) {
            selectionBtnPanel.add(btnSelectAll);
        }
        
        this.buttonGroup = new ButtonGroup();

        this.filterPanel = new FilterPanel();
        this.filterPanel.addFilterPanelListener(new FilterPanelListener() {

            @Override
            public void filterChanged(String filter) {
                filter(filter);
            }

            @Override
            public void filterClosed() {
                showFilterPanel(false);
            }
            
        });
        
        JPanel optionPanel = new JPanel(new BorderLayout());
        
        if(showFilter) {
            JButton filterButton = new JButton();

            filterButton.setPreferredSize(new Dimension(24, 24));
            filterButton.setIcon(ImageManager.getImageManager().getIcon("filter.png"));
            filterButton.setToolTipText("Filter these entries");
            filterButton.addActionListener((ae) -> {
                showFilterPanel(!filterPanel.isShowing());
            });
            
            optionPanel.add(filterButton, BorderLayout.WEST);
            optionPanel.add(filterPanel, BorderLayout.CENTER);
            
            showFilterPanel(false);
        }
        
        optionPanel.add(selectionBtnPanel, BorderLayout.EAST);
        
        this.add(optionPanel, BorderLayout.SOUTH);
    }
    
    public void addSelectedPropertiesChangedListener(InheritablePropertySelectionListener listener) {
        this.selectedPropertiesChangedListeners.add(listener);
    }
    
    public void removeSelectedPropertiesChangedListener(InheritablePropertySelectionListener listener) {
        this.selectedPropertiesChangedListeners.remove(listener);
    }
    
    public final void showFilterPanel(boolean value) {

        if (value) {
            filterPanel.reset();
        } else {
            filter("");
        }

        filterPanel.setVisible(value);
    }
    
    private void filter(String str) {
        if(availableProperties.isEmpty()) {
            return;
        }
        
        if(str.isEmpty()) {
            propertyBoxes.forEach( (btn) -> {
                btn.setVisible(true);
            });
            
            return;
        }
        
        for(int c = 0; c < availableProperties.size(); c++) {
            InheritableProperty property = availableProperties.get(c);
            
            if(property.getName().toLowerCase().contains(str.toLowerCase())) {
                propertyBoxes.get(c).setVisible(true);
            } else {
                propertyBoxes.get(c).setVisible(false);
            }
        }
    }
    
    public Set<InheritableProperty> getAvailableProperties() {
        return new HashSet<>(availableProperties);
    }
    
    public Set<InheritableProperty> getUserSelectedProperties() {
        Set<InheritableProperty> propertyDetails = new HashSet<>();
        
        for(int c = 0; c < availableProperties.size(); c++) {
            if(propertyBoxes.get(c).isSelected()) {
                propertyDetails.add(availableProperties.get(c));
            }
        }
        
        return propertyDetails;
    }
    
    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        btnUnselectAll.setEnabled(value);
        btnSelectAll.setEnabled(value);
    }
    
    @Override
    public void clearContents() {
        propertyBoxes.clear();
        availableProperties.clear();
        
        fireSelectedPropertiesChangedListeners();

        propertyListPanel.removeAll();
    }
    
    @Override
    public void resetView() {
        clearContents();
        
        doRepaint();
    }
    
    public void initialize(ArrayList<InheritableProperty> availableProperties) {
        initialize(availableProperties, new HashSet<>(availableProperties));
    }
    
    public void initialize(ArrayList<InheritableProperty> availableProperties, Set<InheritableProperty> selectedProperties) {
        this.propertyBoxes.clear();
        this.availableProperties.clear();

        this.propertyListPanel.removeAll();

        this.buttonGroup = new ButtonGroup();

        availableProperties.forEach( (property) -> {
            String propertyName = property.getName();

            JToggleButton chkSelectProperty;

            if (selectionType == SelectionType.Multiple) {
                chkSelectProperty = new JCheckBox(propertyName);
            } else {
                chkSelectProperty = new JRadioButton(propertyName);
                buttonGroup.add(chkSelectProperty);
            }

            chkSelectProperty.setOpaque(false);

            chkSelectProperty.setSelected(selectedProperties.contains(property));
            
            chkSelectProperty.addActionListener( (ae) -> {
                fireSelectedPropertiesChangedListeners();
            });
            
            if (showFilter) {
                chkSelectProperty.addKeyListener(new KeyAdapter() {

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (chkSelectProperty.hasFocus()) {
                            showFilterPanel(true);

                            filterPanel.filteringStarted(Character.toString(e.getKeyChar()));
                        }
                    }
                });
            }

            propertyBoxes.add(chkSelectProperty);
            propertyListPanel.add(chkSelectProperty);

            this.availableProperties.add(property);
        });
        
        fireSelectedPropertiesChangedListeners();

        buttonGroup.clearSelection();

        doRepaint();
    }
    
    private void doRepaint() {
        propertyScroller.validate();
        propertyScroller.repaint();

        propertyListPanel.validate();
        propertyListPanel.repaint();
    }
    
    private void fireSelectedPropertiesChangedListeners() {
        selectedPropertiesChangedListeners.forEach( (listener) -> {
            listener.propertiesSelected(this.getUserSelectedProperties());
        });
    }
}
