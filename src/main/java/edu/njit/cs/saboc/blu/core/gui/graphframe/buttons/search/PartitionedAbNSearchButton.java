package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class PartitionedAbNSearchButton extends BaseAbNSearchButton {

    public PartitionedAbNSearchButton(JFrame parent, PartitionedAbNConfiguration config) {

        super(parent, config);

        if (config.getUIConfiguration().showingBaseAbN()) {
            this.addSearchAction(new SearchConceptsInPartitionedNodes(config));
        } else {
            this.addSearchAction(new SearchConceptsInSinglyRootedNodesAction(config));
            this.addSearchAction(new SearchSinglyRootedNodesAction(config));
        }
        
        this.addSearchAction(new SearchPartitionedNodesAction(config));
    }
}
