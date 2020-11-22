package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;


/**
 *
 * @author Chris O
 */
public class ParentNodeTableModel<T extends Node> extends OAFAbstractTableModel<ParentNodeDetails<T>> {
    
    public ParentNodeTableModel(String [] columnNames) {
        super(columnNames);
    }

    public ParentNodeTableModel(AbNConfiguration<T> config) {
        super(new String [] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getParentConceptTypeName(false),
            String.format("Parent %s", config.getTextConfiguration().getNodeTypeName(false)),
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
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
        return new Object [] {
            parentInfo.getParentConcept().getName(), 
            parentInfo.getParentNode().getName(),
            parentInfo.getParentNode().getConceptCount()
        };
    }
}
