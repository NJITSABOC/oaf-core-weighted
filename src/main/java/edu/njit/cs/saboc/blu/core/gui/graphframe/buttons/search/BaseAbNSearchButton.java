package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PopupToggleButton;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Chris
 */
public abstract class BaseAbNSearchButton extends PopupToggleButton {

    private final SearchResultList resultList;
    
    private final JTextField searchText = new JTextField();
    
    private JPanel searchTypePanel = new JPanel();
    private ButtonGroup searchTypeGroup = new ButtonGroup();
    
    private class SearchActionEntry {
        public JRadioButton radioButton;
        public SearchAction searchAction;
        
        public SearchActionEntry(JRadioButton button, SearchAction searchAction) {
            this.radioButton = button;
            this.searchAction = searchAction;
        }
    }
    
    private final ArrayList<SearchActionEntry> searchActionList = new ArrayList<>();
    
    private final AbNConfiguration config;

    protected BaseAbNSearchButton(JFrame parent, AbNConfiguration config) {
        this(parent, config, new SearchResultListModel());
    }
    
    protected BaseAbNSearchButton(
            JFrame parent, 
            AbNConfiguration config, 
            OAFAbstractTableModel<SearchButtonResult> resultTableModel) {
        
        super(parent, "Search");
        
        this.config = config;
        
        this.resultList = new SearchResultList(resultTableModel);
        this.resultList.addEntitySelectionListener(new EntitySelectionListener<SearchButtonResult>() {

            @Override
            public void entityClicked(SearchButtonResult entity) {
                SearchAction searchAction = getSelectedAction();
                searchAction.resultSelected(entity);
            }

            @Override
            public void entityDoubleClicked(SearchButtonResult entity) {
                entityClicked(entity);
            }

            @Override
            public void noEntitySelected() {
                
            }
        });
        
        JPanel popupPanel  = new JPanel(new BorderLayout());

        popupPanel.setBorder(BorderFactory.createEtchedBorder());
        
        final JButton searchButton = new JButton(ImageManager.getImageManager().getIcon("search.png"));
        
        final JButton filterButton = new JButton(ImageManager.getImageManager().getIcon("filter.png"));

        final JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(new TitledBorder("Search Results"));
        
        resultsPanel.setPreferredSize(new Dimension(500, 300));

        searchText.addKeyListener(new KeyAdapter() {
            
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchButton.doClick();
                }
            }
        });
        
        resultsPanel.add(resultList, BorderLayout.CENTER);
       
        searchButton.addActionListener((ae) -> {
            if (searchText.getText().trim().isEmpty()) {
                return;
            }
            
            final SearchAction searchAction = getSelectedAction();
            
            SwingUtilities.invokeLater(() -> {
                ArrayList<SearchButtonResult> results = searchAction.doSearch(searchText.getText().trim());

                if(results.isEmpty()) {
                    resultList.clearContents();
                } else {
                    resultList.setContents(results);
                }
            });
        });
        
        filterButton.addActionListener( (ae) -> {
            resultList.toggleFilterPanel();
        });
        
        JPanel searchEntryPanel = new JPanel(new BorderLayout());

        searchEntryPanel.setBorder(BorderFactory.createTitledBorder("Search Anywhere"));
        searchEntryPanel.add(searchText, BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel();
        
        btnPanel.add(searchButton);
        btnPanel.add(filterButton);
        
        searchEntryPanel.add(btnPanel, BorderLayout.EAST);
        
        searchText.setPreferredSize(new Dimension(450, 55));

        popupPanel.add(searchTypePanel, BorderLayout.NORTH);
        popupPanel.add(searchEntryPanel, BorderLayout.CENTER);
        popupPanel.add(resultsPanel, BorderLayout.SOUTH);

        this.setPopupContent(popupPanel);
    }
    
    
    public AbNConfiguration getConfiguration() {
        return config;
    }
    
    protected SearchResultList getSearchResultList() {
        return resultList;
    }
    
    protected void addSearchAction(SearchAction searchAction) {
        JRadioButton actionButton = new JRadioButton(searchAction.getSearchActionName());
        
        actionButton.addActionListener((ae) -> {
            resultList.clearContents();
        });
        
        searchActionList.add(new SearchActionEntry(actionButton, searchAction));
        
        searchTypeGroup.add(actionButton);
        
        searchTypePanel.add(actionButton);
        
        if(searchActionList.size() == 1) {
            actionButton.setSelected(true);
        }
        
        searchTypePanel.validate();
    }
    
    private SearchAction getSelectedAction() {
        for (SearchActionEntry searchAction : searchActionList) {
            if (searchAction.radioButton.isSelected()) {
                return searchAction.searchAction;
            }
        }
        
        return null;
    }
}
