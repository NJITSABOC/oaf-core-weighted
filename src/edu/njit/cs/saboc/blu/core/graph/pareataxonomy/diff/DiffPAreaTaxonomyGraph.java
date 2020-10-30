package edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.DefaultPAreaTaxonomyLayoutFactory;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.PAreaTaxonomyGraph;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.PAreaTaxonomyLayoutFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomyGraph extends PAreaTaxonomyGraph<DiffPAreaTaxonomy> {

    public DiffPAreaTaxonomyGraph(
            final JFrame parentFrame, 
            final DiffPAreaTaxonomy diffPAreaTaxonomy, 
            final SinglyRootedNodeLabelCreator<DiffPArea> labelCreator, 
            final DiffPAreaTaxonomyConfiguration config) {

        this(parentFrame, diffPAreaTaxonomy, labelCreator, config, new DefaultPAreaTaxonomyLayoutFactory());
    }
    
    public DiffPAreaTaxonomyGraph(
            final JFrame parentFrame, 
            final DiffPAreaTaxonomy diffPAreaTaxonomy, 
            final SinglyRootedNodeLabelCreator<DiffPArea> labelCreator, 
            final DiffPAreaTaxonomyConfiguration config, 
            final PAreaTaxonomyLayoutFactory layoutFactory) {

        super(parentFrame, diffPAreaTaxonomy, labelCreator, config, layoutFactory);
    }
    
    @Override
    public DiffPAreaTaxonomy getPAreaTaxonomy() {
        return (DiffPAreaTaxonomy) getAbstractionNetwork();
    }
}
