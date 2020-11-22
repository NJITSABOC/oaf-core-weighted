
package edu.njit.cs.saboc.blu.core.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class ConceptSearchPanel extends JPanel {
    
    public static enum SearchType {
        Starting,
        Anywhere,
        Exact,
        ID
    }
        
     // Textbox with a spinner in it
    private class SpinnerTextField extends JPanel {
        private final JTextField textField;
        private final JLabel spinner;

        public SpinnerTextField() {
            super(new BorderLayout());
            
            textField = new JTextField();
            spinner = new JLabel(ImageManager.getImageManager().getIcon("spinner.gif"));
            spinner.setOpaque(true);
            spinner.setBackground(textField.getBackground());
            
            setBorder(textField.getBorder());
            
            textField.setBorder(null);
            
            add(spinner, BorderLayout.EAST);
            add(textField, BorderLayout.CENTER);

            spinner.setVisible(false);
        }

        public JTextField getTextField() {
            return textField;
        }
        
        public String getText() {
            return textField.getText();
        }
        
        public void clearText() {
            textField.setText("");
        }

        public void setSpinnerVisible(boolean value) {
            spinner.setVisible(value);
        }
    }
    
    
    private JRadioButton makeRadioButton(String text, ButtonGroup group, Container panel) {
        return makeRadioButton(text, group, panel, null);
    }

    private JRadioButton makeRadioButton(String text, ButtonGroup group, Container panel, GridBagConstraints c) {
        JRadioButton rb = new JRadioButton(text);
        group.add(rb);
        
        if(c == null) {
            panel.add(rb);
        }
        else {
            panel.add(rb, c);
        }

        return rb;
    }
    
    
    private final JPanel pnlSearch;

    private final JRadioButton optStarting;
    private final JRadioButton optAnywhere;
    private final JRadioButton optExact;
    private final JRadioButton optID;
    
    private final SpinnerTextField txtSearchBox;
    private final JButton btnDoSearch;
    private final JButton btnCancelSearch;
    
    private final ConceptList resultList;
    
    private final ConceptSearchConfiguration searchConfiguration;
    
    public ConceptSearchPanel(
            AbNConfiguration dummyConfiguration, 
            ConceptSearchConfiguration searchConfiguration) {
        
        this.searchConfiguration = searchConfiguration;

        setLayout(new BorderLayout());

        // Search Panel
        JPanel buttonPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        
        optStarting = makeRadioButton("Starts with", group, buttonPanel);
        optAnywhere = makeRadioButton("Anywhere", group, buttonPanel);
        optExact = makeRadioButton("Exact", group, buttonPanel);
        optID = makeRadioButton("ID", group, buttonPanel);
        
        pnlSearch = new JPanel(new GridBagLayout());
        
        optStarting.setSelected(true);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        pnlSearch.add(buttonPanel, c);

        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 1;
        c.gridwidth = 1;
        
        txtSearchBox = new SpinnerTextField();
        pnlSearch.add(txtSearchBox, c);
        
        txtSearchBox.getTextField().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doSearch();
                }
            }
        });

        c.gridx = 1;
        c.weightx = 0;
        btnDoSearch = new JButton(ImageManager.getImageManager().getIcon("search.png"));
        btnDoSearch.addActionListener( (ae) -> {
            doSearch();
        });

        pnlSearch.add(btnDoSearch, c);

        btnCancelSearch = new JButton(ImageManager.getImageManager().getIcon("cancel.png"));
        btnCancelSearch.setToolTipText("Cancel serach");

        pnlSearch.add(btnCancelSearch, c);
        
        btnCancelSearch.setVisible(false);
        btnCancelSearch.setEnabled(false);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        resultList = new ConceptList(dummyConfiguration);
        resultList.setFilterPanelOpen(false, null);
        
        resultList.addEntitySelectionListener(new EntitySelectionListener<Concept>() {

            @Override
            public void entityClicked(Concept entity) {
                searchConfiguration.searchResultSelected(entity);
            }

            @Override
            public void entityDoubleClicked(Concept entity) {
                searchConfiguration.searchResultDoubleClicked(entity);
            }

            @Override
            public void noEntitySelected() {
                searchConfiguration.noSearchResultSelected();
            }
            
        });
        
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.add(pnlSearch, BorderLayout.NORTH);
        this.add(resultList, BorderLayout.CENTER);
    }
    
    public void setEnabled(boolean value) {
        super.setEnabled(value);

        optStarting.setEnabled(value);
        optAnywhere.setEnabled(value);
        optExact.setEnabled(value);
        optID.setEnabled(value);

        txtSearchBox.setEnabled(value);
        btnDoSearch.setEnabled(value);
        btnCancelSearch.setEnabled(value);

        resultList.setEnabled(value);
    }
    
    private void doSearch() {
        final SearchType type;
        
        if(optStarting.isSelected()) {
            type = SearchType.Starting;
        } else if(optAnywhere.isSelected()) {
            type = SearchType.Anywhere;
        } else if(optExact.isSelected()) {
            type = SearchType.Exact;
        } else {
            type = SearchType.ID;
        }
        
        final String query = txtSearchBox.getText();
        
        Thread searchThread = new Thread( () -> {
            ArrayList<Concept> results = searchConfiguration.doSearch(type, query);
            
            SwingUtilities.invokeLater( () -> {
                resultList.setContents(results);
            });
        });
        
        searchThread.start();
    }
    
    public void clearResults() {
        resultList.clearContents();
    }
    
    public void resetView() {
        txtSearchBox.clearText();
        
        clearResults();
    }
}
