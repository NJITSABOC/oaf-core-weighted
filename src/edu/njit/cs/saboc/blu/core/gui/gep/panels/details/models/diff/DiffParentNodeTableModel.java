package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class DiffParentNodeTableModel<T extends Node> extends OAFAbstractTableModel<ParentNodeDetails<T>> {
    
    public DiffParentNodeTableModel(String [] columnNames) {
        super(columnNames);
    }

    public DiffParentNodeTableModel(AbNConfiguration<T> config) {
        this(new String [] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getParentConceptTypeName(false),
            String.format("Parent %s", config.getTextConfiguration().getNodeTypeName(false)),
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            "Diff State"
        });
    }
    
    public Concept getParentConcept(int row) {
        return this.getItemAtRow(row).getParentConcept();
    }
    
    public Node getParentNode(int row) {
        return this.getItemAtRow(row).getParentNode();
    }

    @Override
    protected Object[] createRow(ParentNodeDetails<T> parentInfo) {
        
        ChangeState state = ((DiffNodeInstance)parentInfo.getParentNode()).getDiffNode().getChangeDetails().getNodeState();
        
        return new Object [] {
            parentInfo.getParentConcept().getName(), 
            parentInfo.getParentNode().getName(),
            parentInfo.getParentNode().getConceptCount(),
            state
        };
    }
}