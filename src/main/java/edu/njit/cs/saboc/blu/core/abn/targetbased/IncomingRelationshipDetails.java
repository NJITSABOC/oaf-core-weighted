package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Stores the details about the relationships that are pointing to a given 
 * target group's concepts
 * 
 * @author Chris O
 */
public class IncomingRelationshipDetails {
    
    private final Map<Concept, Set<RelationshipTriple>> relSources = new HashMap<>();
    private final Map<Concept, Set<RelationshipTriple>> relTargets = new HashMap<>();
    
    private final Set<RelationshipTriple> relationships;
    
    public IncomingRelationshipDetails(Set<RelationshipTriple> incomingRelationships) {
        
        this.relationships = incomingRelationships;
        
        incomingRelationships.forEach( (rel) -> {
                        
            if(!relSources.containsKey(rel.getSource())) {
                relSources.put(rel.getSource(), new HashSet<>());
            }
            
            if(!relTargets.containsKey(rel.getTarget())) {
                relTargets.put(rel.getTarget(), new HashSet<>());
            }
            
            relSources.get(rel.getSource()).add(rel);
            relTargets.get(rel.getTarget()).add(rel);
        });

    }
    
    public Set<RelationshipTriple> getAllRelationships() {
        return new HashSet<>(relationships);
    }
    
    public Set<Concept> getSourceConcepts() {
        return new HashSet<>(relSources.keySet());
    }
        
    public Set<Concept> getTargetConcepts() {
        return new HashSet<>(relTargets.keySet());
    }
    
    public Set<RelationshipTriple> getOutgoingRelationshipsFrom(Concept concept) {
        return new HashSet<>(relSources.getOrDefault(concept, Collections.emptySet()));
    }
    
    public Set<RelationshipTriple> getIncomingRelationshipsTo(Concept concept) {        
        return new HashSet<>(relTargets.getOrDefault(concept, Collections.emptySet()));
    }
}
