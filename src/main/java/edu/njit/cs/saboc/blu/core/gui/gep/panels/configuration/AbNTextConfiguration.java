
package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class AbNTextConfiguration<T extends Node> {
    
    private final OntologyEntityNameConfiguration ontologyEntityNameConfig;
    
    public AbNTextConfiguration(OntologyEntityNameConfiguration ontologyEntityNameConfig) {
        this.ontologyEntityNameConfig = ontologyEntityNameConfig;
    }
    
    public OntologyEntityNameConfiguration getOntologyEntityNameConfiguration() {
        return ontologyEntityNameConfig;
    }
    
    public abstract String getAbNName();
    public abstract String getAbNSummary();
    public abstract String getAbNHelpDescription();
    
    public abstract String getAbNTypeName(boolean plural);
    public abstract String getNodeTypeName(boolean plural);
    
    public abstract String getNodeHelpDescription(T node);
}
