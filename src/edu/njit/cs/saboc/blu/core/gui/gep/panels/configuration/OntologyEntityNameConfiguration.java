package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

/**
 *
 * @author Chris O
 */
public interface OntologyEntityNameConfiguration {
    
    public String getConceptTypeName(boolean plural);
    public String getPropertyTypeName(boolean plural);
    
    public String getParentConceptTypeName(boolean plural);
    public String getChildConceptTypeName(boolean plural);
    
}
