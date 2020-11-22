package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.diff;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.PartitionedAbNSearchButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.SearchButtonResult;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class DiffPartitionedAbNSearchButton extends PartitionedAbNSearchButton {
    
    public DiffPartitionedAbNSearchButton(
            JFrame parentFrame, 
            PartitionedAbNConfiguration config) {
        
        super(parentFrame, config);
        
        super.getSearchResultList().setDefaultTableRenderer(
                SearchButtonResult.class, 
                new DiffSearchResultCellRenderer(super.getSearchResultList().getEntityTable()));
    }
}
