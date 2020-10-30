package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.layout.AbstractionNetworkGraphLayout;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class AreaTaxonomyGraph<T extends PAreaTaxonomy> extends AbstractionNetworkGraph<T> {
    
    public AreaTaxonomyGraph(
            final JFrame parentFrame, 
            final T taxonomy, 
            final SinglyRootedNodeLabelCreator labelCreator,
            final PAreaTaxonomyConfiguration config) {
        
        super(taxonomy, labelCreator);
        
        AbstractionNetworkGraphLayout<T> layout = (AbstractionNetworkGraphLayout<T>)new AreaTaxonomyLayout(
                        this, 
                        taxonomy, 
                        config);
        
        super.setAbstractionNetworkLayout(layout);
    }

    public T getPAreaTaxonomy() {
        return (T)getAbstractionNetwork();
    }
}
