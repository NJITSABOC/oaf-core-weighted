package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.ConceptChange.ConceptAddedRemovedChangeType;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange.ChangeInheritanceType;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.ConceptParentChange.ParentState;
import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents the changes to a given subhierarchy of concepts between the 
 * "FROM" and "TO" releases of an ontology. 
 * 
 * @author Chris O
 */
public class HierarchicalChanges extends OntologyChanges {
    
    private final Map<Concept, Set<ConceptHierarchicalChange>> changeEffects = new HashMap<>();
    
    private final Hierarchy<Concept> fromSubhierarchy;
    private final Hierarchy<Concept> toSubhierarchy;
    
    private final Set<Concept> removedOntConcepts;
    private final Set<Concept> addedOntConcepts;
    private final Set<Concept> removedHierarchyConcepts;
    private final Set<Concept> addedHierarchyConcepts;
    private final Set<Concept> transferredHierarchyConcepts;

    public HierarchicalChanges(
            Ontology fromOntology,
            Hierarchy<Concept> fromSubhierarchy, 
            Ontology toOntology,
            Hierarchy<Concept> toSubhierarchy) {
        
        super(fromOntology, toOntology);

        this.fromSubhierarchy = fromSubhierarchy;
        this.toSubhierarchy = toSubhierarchy;

        // Concepts that were removed from the ontology
        this.removedOntConcepts = SetUtilities.getSetDifference(
                fromOntology.getConceptHierarchy().getNodes(), 
                toOntology.getConceptHierarchy().getNodes());
        
        this.removedOntConcepts.forEach( (concept) -> {
            determineConceptAddedRemovedEffects(concept, fromSubhierarchy, ConceptAddedRemovedChangeType.RemovedFromOnt);
        });
        
        // Concepts that were added to the ontology
        this.addedOntConcepts = SetUtilities.getSetDifference(
                toOntology.getConceptHierarchy().getNodes(), 
                fromOntology.getConceptHierarchy().getNodes());
        
        this.addedOntConcepts.forEach( (concept) -> {
            determineConceptAddedRemovedEffects(concept, toSubhierarchy, ConceptAddedRemovedChangeType.AddedToOnt);
        });
        
        // Concepts that were removed from the hierarchy (but not the ontology)
        this.removedHierarchyConcepts = SetUtilities.getSetDifference(
                SetUtilities.getSetDifference(
                    fromSubhierarchy.getNodes(), 
                    toSubhierarchy.getNodes()), 
                    removedOntConcepts);
        
        this.removedHierarchyConcepts.forEach( (concept) -> {
            determineConceptAddedRemovedEffects(concept, fromSubhierarchy, ConceptAddedRemovedChangeType.RemovedFromSubhierarchy);
        });
        
        // Concepts that were added to the hierarchy (but were in the ontology in from)
        this.addedHierarchyConcepts = SetUtilities.getSetDifference(
                    SetUtilities.getSetDifference(
                        toSubhierarchy.getNodes(), 
                        fromSubhierarchy.getNodes()), 
                
                    addedOntConcepts);
        
        this.addedHierarchyConcepts.forEach( (concept) -> {
            determineConceptAddedRemovedEffects(concept, toSubhierarchy, ConceptAddedRemovedChangeType.AddedToSubhierarchy);
        });
        
        this.transferredHierarchyConcepts = SetUtilities.getSetIntersection(
                fromSubhierarchy.getNodes(), 
                toSubhierarchy.getNodes());
                
        this.transferredHierarchyConcepts.forEach( (concept) -> {
            Set<Concept> fromParents = fromSubhierarchy.getParents(concept);
            Set<Concept> toParents = toSubhierarchy.getParents(concept);
            
            // Removed parent relationship to a concept still in the subhierarchy in to
            Set<Concept> removedParents = SetUtilities.getSetDifference(fromParents, toParents);
            removedParents = SetUtilities.getSetDifference(removedParents, removedOntConcepts);
            removedParents = SetUtilities.getSetDifference(removedParents, removedHierarchyConcepts);
            
            removedParents.forEach( (parent) -> {
                determineParentAddedRemovedEffects(concept, toSubhierarchy, parent, ParentState.Removed);
            });
            
            Set<Concept> addedParents = SetUtilities.getSetDifference(toParents, fromParents);
            addedParents = SetUtilities.getSetDifference(addedParents, addedOntConcepts);
            addedParents = SetUtilities.getSetDifference(addedParents, addedHierarchyConcepts);
            
            // Added parent relationship to a concept that was in the subhierarchy in from
            addedParents.forEach( (parent) -> {
                determineParentAddedRemovedEffects(concept, toSubhierarchy, parent, ParentState.Added);
            });
        });
    }

    public Hierarchy<Concept> getFromSubhierarchy() {
        return fromSubhierarchy;
    }

    public Hierarchy<Concept> getToSubhierarchy() {
        return toSubhierarchy;
    }

    public Set<Concept> getRemovedOntConcepts() {
        return removedOntConcepts;
    }
    
    public Set<Concept> getAddedOntConcepts() {
        return addedOntConcepts;
    }

    public Set<Concept> getRemovedHierarchyConcepts() {
        return removedHierarchyConcepts;
    }

    public Set<Concept> getAddedHierarchyConcepts() {
        return addedHierarchyConcepts;
    }

    public Set<Concept> getTransferredHierarchyConcepts() {
        return transferredHierarchyConcepts;
    }
    
    public Set<ConceptHierarchicalChange> getChangesFor(Concept concept) {
        return changeEffects.getOrDefault(concept, Collections.emptySet());
    }
    
    private void determineConceptAddedRemovedEffects(Concept changedConcept, Hierarchy<Concept> subhierarchy, ConceptAddedRemovedChangeType changeType) {
        if (!changeEffects.containsKey(changedConcept)) {
            changeEffects.put(changedConcept, new HashSet<>());
        }

        changeEffects.get(changedConcept).add(
                new ConceptChange(
                        changedConcept,
                        ChangeInheritanceType.Direct,
                        changedConcept,
                        changeType)
        );

        Set<Concept> descendants = subhierarchy.getDescendants(changedConcept);

        descendants.forEach((descendant) -> {
            if (!changeEffects.containsKey(descendant)) {
                changeEffects.put(descendant, new HashSet<>());
            }

            changeEffects.get(descendant).add(
                    new ConceptChange(
                            descendant,
                            ChangeInheritanceType.Indirect,
                            changedConcept,
                            changeType));
        });
    }

    private void determineParentAddedRemovedEffects(
            Concept changedConcept, 
            Hierarchy<Concept> subhierarchy, 
            Concept parent, 
            ParentState parentState) {
        
        if (!changeEffects.containsKey(changedConcept)) {
            changeEffects.put(changedConcept, new HashSet<>());
        }

        changeEffects.get(changedConcept).add(
                new ConceptParentChange(
                        changedConcept,
                        changedConcept,
                        ChangeInheritanceType.Direct,
                        parent,
                        parentState)
        );

        Set<Concept> descendants = subhierarchy.getDescendants(changedConcept);

        descendants.forEach((descendant) -> {
            if (!changeEffects.containsKey(descendant)) {
                changeEffects.put(descendant, new HashSet<>());
            }

            changeEffects.get(descendant).add(
                    new ConceptParentChange(
                            descendant,
                            changedConcept,
                            ChangeInheritanceType.Indirect,
                            parent,
                            parentState));
        });
    }
}
