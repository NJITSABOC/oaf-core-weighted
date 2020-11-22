package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * A node that represents a subhierarchy of concepts that serve as the targets of
 * relationships that have a specific type. The root of the node is the 
 * lowest common ancestor of those concepts that is not a target.
 * 
 * @author Chris O
 */
public class TargetGroup extends SinglyRootedNode {
    
    private final IncomingRelationshipDetails relationshipDetails;
    
    public TargetGroup(
            Hierarchy<Concept> conceptHierarchy, 
            IncomingRelationshipDetails relationships) {
        
        super(conceptHierarchy);
        
        this.relationshipDetails = relationships;
    }

    @Override
    public int getConceptCount() {
        return getIncomingRelationshipTargets().size();
    }

    @Override
    public Set<Concept> getConcepts() {
        return getIncomingRelationshipTargets();
    }
 
    public IncomingRelationshipDetails getIncomingRelationshipDetails() {
        return relationshipDetails;
    }
    
    public Set<Concept> getIncomingRelationshipSources() {
        return relationshipDetails.getSourceConcepts();
    }
    
    public Set<Concept> getIncomingRelationshipTargets() {
        return relationshipDetails.getTargetConcepts();
    }
}
