package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class AbNSearchButton extends BaseAbNSearchButton {

    public AbNSearchButton(JFrame parent, AbNConfiguration config) {
        super(parent, config);
        
        this.addSearchAction(new SearchConceptsInSinglyRootedNodesAction(config));
        this.addSearchAction(new SearchSinglyRootedNodesAction(config));      
    }
}