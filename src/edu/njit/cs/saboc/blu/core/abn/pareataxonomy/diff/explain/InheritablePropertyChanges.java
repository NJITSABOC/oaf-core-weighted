package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange.ChangeInheritanceType;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.HierarchicalChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.OntologyChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyDomainChange.DomainModificationType;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyDomainChange.PropertyState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyHierarchyChange.HierarchicalConnectionState;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The set of all changes to inheritable properties that affected an ontology
 * between two releases
 * 
 * @author Chris O
 */
public class InheritablePropertyChanges extends OntologyChanges {
    
    // Changes to the "domains" of a property (i.e., which concepts are modeled explicitly 
    // with a a certain type of property)
    private final Map<Concept, Set<InheritablePropertyDomainChange>> propertyDomainChanges;
    
    // Changes to the inheritance of properties due to hierarchical changes
    private final Map<Concept, Set<InheritablePropertyHierarchyChange>> propertyHierarchyChange;
    
    private final HierarchicalChanges hierarchyChanges;
    
    private final Set<InheritableProperty> addedProperties;
    private final Set<InheritableProperty> removedProperties;
    private final Set<InheritableProperty> transferredProperties;
    
    public InheritablePropertyChanges(
            PAreaTaxonomy fromTaxonomy,
            PAreaTaxonomy toTaxonomy,
            HierarchicalChanges hierarchyChanges,
            PropertyChangeDetailsFactory changeDetailsFactory) {
        
        super(hierarchyChanges.getFromOntology(), 
                hierarchyChanges.getToOntology());
        
        this.hierarchyChanges = hierarchyChanges;
        
        Set<InheritableProperty> fromOntProperties = changeDetailsFactory.getFromOntProperties();
        Set<InheritableProperty> toOntProperties = changeDetailsFactory.getToOntProperties();
        
        this.addedProperties = SetUtilities.getSetDifference(toOntProperties, fromOntProperties);
        this.removedProperties = SetUtilities.getSetDifference(fromOntProperties, toOntProperties);
        this.transferredProperties = SetUtilities.getSetIntersection(fromOntProperties, toOntProperties);
        
        Map<InheritableProperty, Set<Concept>> fromDomains = changeDetailsFactory.getFromDomains();
        Map<InheritableProperty, Set<Concept>> toDomains = changeDetailsFactory.getToDomains();
        
        this.propertyDomainChanges = explainPropertyChangeEffects(
                fromTaxonomy, 
                toTaxonomy, 
                addedProperties,
                removedProperties,
                transferredProperties, 
                fromDomains,
                toDomains);
        
        this.propertyHierarchyChange = explainHierarchyChangeEffects(
                fromTaxonomy, 
                toTaxonomy, 
                hierarchyChanges.getTransferredHierarchyConcepts(), 
                propertyDomainChanges);
    }
    
    public Set<InheritablePropertyDomainChange> getPropertyDomainChangesFor(Concept c) {
        return propertyDomainChanges.getOrDefault(c, Collections.emptySet());
    }
    
    public Set<InheritablePropertyHierarchyChange> getPropertyHierarchyChangesFor(Concept c) {
        return propertyHierarchyChange.getOrDefault(c, Collections.emptySet());
    }
    
    /**
     * Explains the explicit and implicit effects of adding, removing, and modifying 
     * inheritable properties
     * 
     * @param fromTaxonomy
     * @param toTaxonomy
     * @param addedProperties
     * @param removedProperties
     * @param transferredProperties
     * @param fromPropertyDomains
     * @param toPropertyDomains
     * @return 
     */
    private Map<Concept, Set<InheritablePropertyDomainChange>> explainPropertyChangeEffects(
            PAreaTaxonomy fromTaxonomy,
            PAreaTaxonomy toTaxonomy,
            
            Set<InheritableProperty> addedProperties,
            Set<InheritableProperty> removedProperties,
            Set<InheritableProperty> transferredProperties,
            
            Map<InheritableProperty, Set<Concept>> fromPropertyDomains,
            Map<InheritableProperty, Set<Concept>> toPropertyDomains) {

        Map<Concept, Set<InheritablePropertyDomainChange>> modifiedClasses = new HashMap<>();

        addedProperties.forEach((addedProperty) -> {
            Set<Concept> domain = toPropertyDomains.getOrDefault(addedProperty, Collections.emptySet());
  
            domain.forEach((domainConcept) -> {
                
                Hierarchy<Concept> inheritedBy = toTaxonomy.getSourceHierarchy().getSubhierarchyRootedAt(domainConcept);
                
                if (!modifiedClasses.containsKey(domainConcept)) {
                    modifiedClasses.put(domainConcept, new HashSet<>());
                }
                
                modifiedClasses.get(domainConcept).add(new InheritablePropertyDomainChange(
                        PropertyState.Added,
                        addedProperty,
                        ChangeInheritanceType.Direct,
                        domainConcept,
                        DomainModificationType.Added));
                
                Set<Concept> domainDescendants = inheritedBy.getNodes();
                domainDescendants.remove(domainConcept);
                
                if (!domainDescendants.isEmpty()) {
                    InheritablePropertyDomainChange implicitModification = new InheritablePropertyDomainChange(
                            PropertyState.Added,
                            addedProperty,
                            ChangeInheritanceType.Indirect,
                            domainConcept,
                            DomainModificationType.Added);

                    for (Concept descendant : domainDescendants) {
                        if (!modifiedClasses.containsKey(descendant)) {
                            modifiedClasses.put(descendant, new HashSet<>());
                        }

                        modifiedClasses.get(descendant).add(implicitModification);
                    }
                }
            });
        });

        removedProperties.forEach((removedProperty) -> {
            Set<Concept> domain = fromPropertyDomains.getOrDefault(removedProperty, Collections.emptySet());

            domain.forEach((domainConcept) -> {
                Hierarchy<Concept> inheritedBy = fromTaxonomy.getSourceHierarchy().getSubhierarchyRootedAt(domainConcept);
                
                if (!modifiedClasses.containsKey(domainConcept)) {
                    modifiedClasses.put(domainConcept, new HashSet<>());
                }
                
                modifiedClasses.get(domainConcept).add(new InheritablePropertyDomainChange(
                        PropertyState.Removed,
                        removedProperty,
                        ChangeInheritanceType.Direct,
                        domainConcept,
                        DomainModificationType.Removed));
                
                Set<Concept> domainDescendants = inheritedBy.getNodes();
                domainDescendants.remove(domainConcept);
                
                if (!domainDescendants.isEmpty()) {
                    InheritablePropertyDomainChange implicitModification = new InheritablePropertyDomainChange(
                            PropertyState.Removed,
                            removedProperty,
                            ChangeInheritanceType.Indirect,
                            domainConcept,
                            DomainModificationType.Removed);

                    for (Concept descendant : domainDescendants) {
                        if (!modifiedClasses.containsKey(descendant)) {
                            modifiedClasses.put(descendant, new HashSet<>());
                        }

                        modifiedClasses.get(descendant).add(implicitModification);
                    }
                }
            });
        });

        transferredProperties.forEach( (transferredProperty) -> {
            Set<Concept> beforeDomain = fromPropertyDomains.getOrDefault(transferredProperty, Collections.emptySet());
            Set<Concept> afterDomain = toPropertyDomains.getOrDefault(transferredProperty, Collections.emptySet());
            
            if (!beforeDomain.equals(afterDomain)) {
                Set<Concept> addedDomains = SetUtilities.getSetDifference(afterDomain, beforeDomain);
                Set<Concept> removedDomains = SetUtilities.getSetDifference(beforeDomain, afterDomain);
                Set<Concept> unchangedDomains = SetUtilities.getSetIntersection(addedDomains, removedDomains);

                Set<Concept> clsesInDomain = new HashSet<>();

                for (Concept unchangedDomain : unchangedDomains) {
                    Hierarchy<Concept> inheritedBy = toTaxonomy.getSourceHierarchy().getSubhierarchyRootedAt(unchangedDomain);
                    clsesInDomain.addAll(inheritedBy.getNodes());
                }

                addedDomains.forEach( (addedDomain) -> {
                    Hierarchy<Concept> inheritedBy = toTaxonomy.getSourceHierarchy().getSubhierarchyRootedAt(addedDomain);
                    
                    Set<Concept> newToDomain = SetUtilities.getSetDifference(inheritedBy.getNodes(), clsesInDomain);
                    newToDomain.remove(addedDomain);
                    
                    if (!modifiedClasses.containsKey(addedDomain)) {
                        modifiedClasses.put(addedDomain, new HashSet<>());
                    }
                    
                    modifiedClasses.get(addedDomain).add(new InheritablePropertyDomainChange(
                            PropertyState.Modified,
                            transferredProperty,
                            ChangeInheritanceType.Direct,
                            addedDomain,
                            DomainModificationType.Added));
                    
                    if (!newToDomain.isEmpty()) {
                        InheritablePropertyDomainChange implicitModification = new InheritablePropertyDomainChange(
                                PropertyState.Modified,
                                transferredProperty,
                                DiffAbNConceptChange.ChangeInheritanceType.Indirect,
                                addedDomain,
                                DomainModificationType.Added);
                        
                        for (Concept descendant : newToDomain) {
                            if (!modifiedClasses.containsKey(descendant)) {
                                modifiedClasses.put(descendant, new HashSet<>());
                            }

                            modifiedClasses.get(descendant).add(implicitModification);
                        }
                    }
                });

                removedDomains.forEach((removedDomain) -> {
                    
                    Hierarchy<Concept> inheritedBy = fromTaxonomy.getSourceHierarchy().getSubhierarchyRootedAt(removedDomain);
                    
                    Set<Concept> removedFromDomain = SetUtilities.getSetDifference(inheritedBy.getNodes(), clsesInDomain);
                    removedFromDomain.remove(removedDomain);
                    
                    if (!modifiedClasses.containsKey(removedDomain)) {
                        modifiedClasses.put(removedDomain, new HashSet<>());
                    }
                    
                    modifiedClasses.get(removedDomain).add(new InheritablePropertyDomainChange(
                            PropertyState.Modified,
                            transferredProperty,
                            ChangeInheritanceType.Direct,
                            removedDomain,
                            DomainModificationType.Removed));
                    
                    
                    if (!removedFromDomain.isEmpty()) {
                        InheritablePropertyDomainChange implicitModification = new InheritablePropertyDomainChange(
                                PropertyState.Modified,
                                transferredProperty,
                                ChangeInheritanceType.Indirect,
                                removedDomain,
                                DomainModificationType.Removed);
                        
                        for (Concept descendant : removedFromDomain) {
                            if (!modifiedClasses.containsKey(descendant)) {
                                modifiedClasses.put(descendant, new HashSet<>());
                            }

                            modifiedClasses.get(descendant).add(implicitModification);
                        }
                    }
                });
            }
        });
                
        return modifiedClasses;
    }

    /**
     * Determines how the addition and removals of parent concepts affected the 
     * inheritance of inheritable properties in a subhierarchy
     * 
     * @param fromTaxonomy
     * @param toTaxonomy
     * @param transferredConcepts
     * @param conceptsAffectedByPropertyChange
     * @return 
     */
    private Map<Concept, Set<InheritablePropertyHierarchyChange>> explainHierarchyChangeEffects(
            PAreaTaxonomy fromTaxonomy,
            PAreaTaxonomy toTaxonomy, 
            Set<Concept> transferredConcepts, 
            Map<Concept, Set<InheritablePropertyDomainChange>> conceptsAffectedByPropertyChange) {
        
        Map<Concept, Set<InheritablePropertyHierarchyChange>> hierarchicalPropertyChanges = new HashMap<>();
        
        Hierarchy<Concept> fromHierarchy = fromTaxonomy.getSourceHierarchy();
        Hierarchy<Concept> toHierarchy = toTaxonomy.getSourceHierarchy();
        
        Map<Concept, Set<InheritableProperty>> fromInferredProperties = new HashMap<>();
        Map<Concept, Set<InheritableProperty>> toInferredProperties = new HashMap<>();
        
        fromTaxonomy.getAreaTaxonomy().getConceptAreas().forEach( (concept, area) -> {
            fromInferredProperties.put((Concept)concept, ((Area)area).getRelationships());
        });
        
        toTaxonomy.getAreaTaxonomy().getConceptAreas().forEach( (concept, area) -> {
            toInferredProperties.put((Concept)concept, ((Area)area).getRelationships());
        });
        
        // Find classes that were affected by changes to property domains
        Map<Concept, Set<InheritablePropertyDomainChange>> transferredConceptsAffectedByPropertyChange = new HashMap<>();
        
        for (Concept transferredConcept : transferredConcepts) {
            if (conceptsAffectedByPropertyChange.containsKey(transferredConcept)) {
                if (!transferredConceptsAffectedByPropertyChange.containsKey(transferredConcept)) {
                    transferredConceptsAffectedByPropertyChange.put(transferredConcept, new HashSet<>());
                }

                transferredConceptsAffectedByPropertyChange.get(transferredConcept).addAll(conceptsAffectedByPropertyChange.get(transferredConcept));
            }
        }

        // Only consider classes that are still in the hierarchy
        Set<Concept> inHierarchyTransferredConcepts = SetUtilities.getSetIntersection(transferredConcepts, fromHierarchy.getNodes());
        inHierarchyTransferredConcepts = SetUtilities.getSetIntersection(inHierarchyTransferredConcepts, toHierarchy.getNodes());
        
        for (Concept transferredConcept : inHierarchyTransferredConcepts) {
                       
            Set<Concept> fromParents = fromHierarchy.getParents(transferredConcept);
            Set<Concept> toParents = toHierarchy.getParents(transferredConcept);

            Set<InheritableProperty> fromProps = fromInferredProperties.get(transferredConcept);
            Set<InheritableProperty> toProps = toInferredProperties.get(transferredConcept);
            
            if (!fromParents.equals(toParents) && !fromProps.equals(toProps)) {
                
                Set<InheritableProperty> addedProps = SetUtilities.getSetDifference(toProps, fromProps);
                Set<InheritableProperty> removedProps = SetUtilities.getSetDifference(fromProps, toProps);

                Set<InheritableProperty> unmodifiedAddedProps = new HashSet<>();
                Set<InheritableProperty> unmodifiedRemovedProps = new HashSet<>();

                if(conceptsAffectedByPropertyChange.containsKey(transferredConcept)) {
                    Set<InheritablePropertyDomainChange> modifications = conceptsAffectedByPropertyChange.get(transferredConcept);

                    for(InheritableProperty addedProp : addedProps) {
                        boolean found = false;

                        for(InheritablePropertyDomainChange modification : modifications) {
                            if(modification.getModificationType() == DomainModificationType.Added) {
                                
                                if(modification.getProperty().equals(addedProp)) {
                                    found = true;
                                    break;
                                }
                                
                            }
                        }

                        if(!found) {
                            unmodifiedAddedProps.add(addedProp);
                        }
                    }

                    for(InheritableProperty removedProp : removedProps) {
                        boolean found = false;

                        for(InheritablePropertyDomainChange modification : modifications) {
                            if(modification.getModificationType() == DomainModificationType.Removed) {
                                if(modification.getProperty().equals(modification.getProperty())){
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if(!found) {
                            unmodifiedRemovedProps.add(removedProp);
                        }
                    }
                } else {
                    unmodifiedAddedProps = new HashSet<>(addedProps);
                    unmodifiedRemovedProps = new HashSet<>(removedProps);
                }

                Set<Concept> addedParents = SetUtilities.getSetDifference(toParents, fromParents);
                Set<Concept> removedParents = SetUtilities.getSetDifference(fromParents, toParents);

                for (Concept removedParent : removedParents) {
                    Set<InheritableProperty> parentFromProps = fromInferredProperties.get(removedParent);
                    Set<InheritableProperty> parentToProps = toInferredProperties.get(removedParent);

                    Set<InheritableProperty> previouslyInheritedProps = SetUtilities.getSetIntersection(parentFromProps, unmodifiedRemovedProps);

                    if (!previouslyInheritedProps.isEmpty()) {
                        Hierarchy<Concept> descendantHierarchy = fromHierarchy.getSubhierarchyRootedAt(transferredConcept);

                        Set<Concept> descendantConcepts = descendantHierarchy.getNodes();
                        descendantConcepts.remove(transferredConcept);

                        if (hierarchyChanges.getRemovedOntConcepts().contains(removedParent) || !toHierarchy.getNodes().contains(removedParent)) {
                            continue;
                        }

                        for (InheritableProperty inheritedProp : previouslyInheritedProps) {
                            if (parentToProps.contains(inheritedProp)) {
                                
                                if(!hierarchicalPropertyChanges.containsKey(transferredConcept)) {
                                    hierarchicalPropertyChanges.put(transferredConcept, new HashSet<>());
                                }
                                
                                InheritablePropertyHierarchyChange directChange
                                        = new InheritablePropertyHierarchyChange(
                                                transferredConcept,
                                                removedParent,
                                                inheritedProp,
                                                HierarchicalConnectionState.Removed,
                                                ChangeInheritanceType.Direct);

                                hierarchicalPropertyChanges.get(transferredConcept).add(directChange);
                                
                                InheritablePropertyHierarchyChange indirectChange
                                        = new InheritablePropertyHierarchyChange(
                                                transferredConcept,
                                                removedParent,
                                                inheritedProp,
                                                HierarchicalConnectionState.Removed,
                                                ChangeInheritanceType.Indirect);

                                descendantConcepts.forEach((descendant) -> {
                                    
                                    Set<InheritableProperty> descendantToProps = toInferredProperties.get(descendant);
                                    
                                    if (!descendantToProps.contains(inheritedProp)) {
                                        if (!hierarchicalPropertyChanges.containsKey(descendant)) {
                                            hierarchicalPropertyChanges.put(descendant, new HashSet<>());
                                        }

                                        hierarchicalPropertyChanges.get(descendant).add(indirectChange);
                                    }
                                });
                            }
                        }
                    }
                }

                for(Concept addedParent : addedParents) {
                    Set<InheritableProperty> parentFromProps = fromInferredProperties.get(addedParent);
                    Set<InheritableProperty> parentToProps = toInferredProperties.get(addedParent);

                    Set<InheritableProperty> newlyInheritedProps = SetUtilities.getSetIntersection(parentFromProps, unmodifiedAddedProps);

                    if(!newlyInheritedProps.isEmpty()) {
                        Hierarchy<Concept> descendantHierarchy = toHierarchy.getSubhierarchyRootedAt(transferredConcept);

                        Set<Concept> descendantConcepts = descendantHierarchy.getNodes();
                        descendantConcepts.remove(transferredConcept);

                        if (hierarchyChanges.getAddedOntConcepts().contains(addedParent) || !fromHierarchy.getNodes().contains(addedParent)) {
                            continue;
                        }

                        for (InheritableProperty inheritedProp : newlyInheritedProps) {
                            if (parentToProps.contains(inheritedProp)) {
                                
                                InheritablePropertyHierarchyChange directChange
                                        = new InheritablePropertyHierarchyChange(
                                                transferredConcept,
                                                addedParent,
                                                inheritedProp,
                                                HierarchicalConnectionState.Added,
                                                ChangeInheritanceType.Direct);

                                if(!hierarchicalPropertyChanges.containsKey(transferredConcept)) {
                                    hierarchicalPropertyChanges.put(transferredConcept, new HashSet<>());
                                }
                                
                                hierarchicalPropertyChanges.get(transferredConcept).add(directChange);
                                
                                InheritablePropertyHierarchyChange indirectChange
                                        = new InheritablePropertyHierarchyChange(
                                                transferredConcept,
                                                addedParent,
                                                inheritedProp,
                                                HierarchicalConnectionState.Added,
                                                ChangeInheritanceType.Indirect);

                                descendantConcepts.forEach((descendant) -> {                                    
                                    Set<InheritableProperty> descendantFromProps = fromInferredProperties.getOrDefault(descendant, Collections.emptySet());
                                    
                                    if (!descendantFromProps.contains(inheritedProp)) {
                                        if (!hierarchicalPropertyChanges.containsKey(descendant)) {
                                            hierarchicalPropertyChanges.put(descendant, new HashSet<>());
                                        }

                                        hierarchicalPropertyChanges.get(descendant).add(indirectChange);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }

        return hierarchicalPropertyChanges;
    }
}
