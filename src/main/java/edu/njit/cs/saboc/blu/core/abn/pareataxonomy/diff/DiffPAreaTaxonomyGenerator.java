package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.AbstractionNetworkDiffResult;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.TopologicalVisitor;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A generator class for creating diff partial-area taxonomies
 * 
 * @author Chris O
 */
public class DiffPAreaTaxonomyGenerator {

    public DiffPAreaTaxonomy createDiffPAreaTaxonomy(
            DiffPAreaTaxonomyFactory factory,
            Ontology fromOnt, 
            PAreaTaxonomy fromTaxonomy, 
            Ontology toOnt, 
            PAreaTaxonomy toTaxonomy) {
        
        DiffAbstractionNetworkGenerator diffGenerator = new DiffAbstractionNetworkGenerator();
        
        DiffPAreaTaxonomyConceptChangesFactory conceptChangesFactory = 
                new DiffPAreaTaxonomyConceptChangesFactory(
                        fromTaxonomy,
                        toTaxonomy,
                        factory.getPropertyChangeDetailsFactory());
        
        
        AbstractionNetworkDiffResult areaDiffResult = diffGenerator.diff(
                fromOnt, 
                fromTaxonomy.getAreaTaxonomy(), 
                toOnt, 
                toTaxonomy.getAreaTaxonomy(),
                conceptChangesFactory);
        
        AbstractionNetworkDiffResult pareaDiffResult = diffGenerator.diff(
                fromOnt, 
                fromTaxonomy, 
                toOnt, 
                toTaxonomy,
                conceptChangesFactory);
        
        Map<DiffNode, DiffPArea> diffPAreas = new HashMap<>();
        
        Map<Set<InheritableProperty>, Set<DiffPArea>> diffPAreasByArea = new HashMap<>();
        
        pareaDiffResult.getDiffNodes().forEach( (diffNode) -> {
            PArea changedPArea = (PArea)diffNode.getChangeDetails().getChangedNode();
            
            DiffPArea diffPArea = new DiffPArea(diffNode, changedPArea);
            
            diffPAreas.put(diffNode, diffPArea);
            
            Set<InheritableProperty> properties = changedPArea.getRelationships();
            
            if(!diffPAreasByArea.containsKey(properties)) {
                diffPAreasByArea.put(properties, new HashSet<>());
            }

            diffPAreasByArea.get(properties).add(diffPArea);
        });
        
        Map<DiffNode, DiffArea> diffAreas = new HashMap<>();
        
        areaDiffResult.getDiffNodes().forEach( (diffNode) -> {
            Area changedArea = (Area)diffNode.getChangeDetails().getChangedNode();

            Set<DiffPArea> diffAreaPAreas = diffPAreasByArea.get(changedArea.getRelationships());
            
            DiffArea diffArea = new DiffArea(
                    diffNode, 
                    changedArea.getRelationships(), 
                    diffAreaPAreas);
            
            diffAreas.put(diffNode, diffArea);
        });
        
        Set<DiffArea> rootAreas = new HashSet<>();
        
        areaDiffResult.getDiffNodeHierarchy().getRoots().forEach( (diffRoot) -> {
            rootAreas.add(diffAreas.get(diffRoot));
        });
        
        Hierarchy<DiffArea> diffAreaHierarchy = new Hierarchy<>(rootAreas);
        
        areaDiffResult.getDiffNodeHierarchy().getEdges().forEach( (diffEdge) -> {
            DiffArea from = diffAreas.get(diffEdge.getSource());
            DiffArea to = diffAreas.get(diffEdge.getTarget());
            
            diffAreaHierarchy.addEdge(from, to);
        });
        
        Set<DiffPArea> rootPAreas = new HashSet<>();
        
        pareaDiffResult.getDiffNodeHierarchy().getRoots().forEach( (diffRoot) -> {
            rootPAreas.add(diffPAreas.get(diffRoot));
        });
        
        Hierarchy<DiffPArea> diffPAreaHierarchy = new Hierarchy<>(rootPAreas);
        
        pareaDiffResult.getDiffNodeHierarchy().getEdges().forEach( (diffEdge) -> {
            DiffPArea from = diffPAreas.get(diffEdge.getSource());
            DiffPArea to = diffPAreas.get(diffEdge.getTarget());
            
            diffPAreaHierarchy.addEdge(from, to);
        });
        
        DiffAreaTaxonomy diffAreaTaxonomy = factory.createDiffAreaTaxonomy(
                (DiffPAreaTaxonomyConceptChanges)areaDiffResult.getOntologyDifferences(),
                fromTaxonomy.getAreaTaxonomy(), 
                toTaxonomy.getAreaTaxonomy(), 
                diffAreaHierarchy);
        
        DiffPAreaTaxonomy diffPAreaTaxonomy = factory.createDiffPAreaTaxonomy(
                diffAreaTaxonomy, 
                fromTaxonomy, 
                toTaxonomy, 
                diffPAreaHierarchy);
        
        return diffPAreaTaxonomy;
    }
    
    public DiffPAreaTaxonomy getChangeStateDiffPAreaTaxonomy(DiffPAreaTaxonomy diffPAreaTaxonomy, Set<ChangeState> changeTypes) {
        
        if(changeTypes.size() == ChangeState.values().length) {
            return diffPAreaTaxonomy;
        }
        
        Hierarchy<DiffPArea> diffPAreaHierarchy = diffPAreaTaxonomy.getPAreaHierarchy();
        ChangeStateHierarchyBuilderVisitor visitor = new ChangeStateHierarchyBuilderVisitor(diffPAreaHierarchy, changeTypes);
        diffPAreaHierarchy.topologicalDown(visitor);
        
        Hierarchy<DiffPArea> diffPAreaSubset = visitor.getResultHierarchy();
        
        return createDiffPAreaTaxonomyFromDiffPAreaHierarchy(
                diffPAreaTaxonomy.getAreaTaxonomy().getDiffFactory(),
                diffPAreaSubset,
                diffPAreaTaxonomy);
    }
    
    public DiffPAreaTaxonomy createDiffPAreaTaxonomyFromDiffPAreaHierarchy(
            DiffPAreaTaxonomyFactory factory, 
            Hierarchy<DiffPArea> diffPAreaHierarchy,
            DiffPAreaTaxonomy sourceDiffTaxonomy) {
        
        Map<Set<InheritableProperty>, Set<DiffPArea>> pareasByRelationships = new HashMap<>();
        
        diffPAreaHierarchy.getNodes().forEach( (parea) -> {
            Set<InheritableProperty> properties = parea.getRelationships();
            
            if(!pareasByRelationships.containsKey(properties)) {
                pareasByRelationships.put(properties, new HashSet<>());
            }
            
            pareasByRelationships.get(properties).add(parea);
        });

        HashMap<Set<InheritableProperty>, DiffArea> areasByRelationships = new HashMap<>();
        
        DiffArea rootArea = null;
        
        for (Map.Entry<Set<InheritableProperty>, Set<DiffPArea>> entry : pareasByRelationships.entrySet()) {
            Set<DiffPArea> diffPAreas = entry.getValue();
            
            DiffArea area = new DiffArea(
                    // TODO: Is this right? Will it match both added/removed?
                    sourceDiffTaxonomy.getAreaFor(diffPAreas.iterator().next()).getDiffNode(), 
                    entry.getKey(), 
                    diffPAreas);

            if (area.getDiffPAreas().contains(diffPAreaHierarchy.getRoot())) {
                rootArea = area;
            }

            areasByRelationships.put(area.getRelationships(), area);
        }
        
        Hierarchy<DiffArea> areaHierarchy = new Hierarchy<>(rootArea);
 
        areasByRelationships.values().forEach((area) -> {
            if (!area.equals(areaHierarchy.getRoot())) {
                Set<DiffPArea> areaPAreas = area.getDiffPAreas();

                areaPAreas.forEach((parea) -> {
                    DiffArea parentArea = areasByRelationships.get(parea.getRelationships());
                    
                    areaHierarchy.addEdge(area, parentArea);
                });
            }
        });

        DiffAreaTaxonomy diffAreaTaxonomy = factory.createDiffAreaTaxonomy(
                sourceDiffTaxonomy.getOntologyStructuralChanges(), 
                sourceDiffTaxonomy.getAreaTaxonomy().getFrom(), 
                sourceDiffTaxonomy.getAreaTaxonomy().getTo(), 
                areaHierarchy);
        
        DiffPAreaTaxonomy diffPAreaTaxonomy = factory.createDiffPAreaTaxonomy(
                diffAreaTaxonomy,
                sourceDiffTaxonomy.getFrom(), 
                sourceDiffTaxonomy.getTo(), 
                diffPAreaHierarchy);

        return diffPAreaTaxonomy;
    }
}

/**
 * A visitor for creating a subhierarchy of diff partial-areas that have 
 * a specific change state.
 * 
 * Diff partial-areas that do not have a change state that matches the 
 * set of allowed change states are by-passed.
 * 
 * @author Chris O
 */
class ChangeStateHierarchyBuilderVisitor extends TopologicalVisitor<DiffPArea> {
    
    private final Set<ChangeState> allowedChangeStates;
    private final Hierarchy<DiffPArea> resultHierarchy;
    
    private final Map<DiffPArea, Set<DiffPArea>> closestAllowedAncestors;
    
    public ChangeStateHierarchyBuilderVisitor(Hierarchy<DiffPArea> theHierarchy, Set<ChangeState> allowedChangeStates) {
        super(theHierarchy);
        
        this.allowedChangeStates = allowedChangeStates;
        this.resultHierarchy = new Hierarchy<>(theHierarchy.getRoot());
        
        this.closestAllowedAncestors = new HashMap<>();
    }

    @Override
    public void visit(DiffPArea node) {
        Hierarchy<DiffPArea> theHierarchy = super.getHierarchy();
        
        if(node.equals(theHierarchy.getRoot())) { // The root is always displayed
            this.closestAllowedAncestors.put(node, Collections.singleton(node));
            
            return;
        }
        
        Set<DiffPArea> parents = theHierarchy.getParents(node);
        
        if(allowedChangeStates.contains(node.getPAreaState())) {
            parents.forEach( (parent) -> {
                closestAllowedAncestors.get(parent).forEach( (ancestor) -> {
                    resultHierarchy.addEdge(node, ancestor);
                });
            });
            
            closestAllowedAncestors.put(node, Collections.singleton(node));
        } else {
            Set<DiffPArea> nodeAllowedAncestors = new HashSet<>();
            
            parents.forEach( (parent) -> {
                nodeAllowedAncestors.addAll(closestAllowedAncestors.get(parent));
            });
            
            closestAllowedAncestors.put(node, nodeAllowedAncestors);
        }
    }
    
    public Hierarchy<DiffPArea> getResultHierarchy() {
        return resultHierarchy;
    }
}
