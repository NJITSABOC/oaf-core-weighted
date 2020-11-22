package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.disjoint;

import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.PartitionedNodeDisjointAbN;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.DisjointPAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DisjointSubjectSubtaxonomyGenerator {
    
    public boolean canDeriveDisjointSubjectSubtaxonomy(
            PAreaTaxonomy taxonomy,
            Area subtaxonomyArea) {
        
        Hierarchy<Concept> conceptHierarchy = subtaxonomyArea.getHierarchy();
        
        PAreaTaxonomyFactory factory = taxonomy.getPAreaTaxonomyFactory();
        
        Set<Concept> parentsInArea = new HashSet<>();
        
        conceptHierarchy.getNodes().forEach( (concept) -> {
            
            Set<Concept> allParents = taxonomy.getPAreaTaxonomyFactory().getSourceOntology().getConceptHierarchy().getParents(concept);
            
            allParents.forEach( (parent) -> {
                if(factory.getRelationships(parent).equals(subtaxonomyArea.getRelationships())) {
                    parentsInArea.add(parent);
                }
            });
        });
        
        Set<Concept> outsideSubtaxonomyArea = SetUtilities.getSetDifference(parentsInArea, conceptHierarchy.getNodes());
        
        return !outsideSubtaxonomyArea.isEmpty();
    }
    
    public PartitionedNodeDisjointAbN createDisjointSubjectSubtaxonomy(
            PAreaTaxonomy taxonomy,
            Area subtaxonomyArea) {
        
        Hierarchy<Concept> subtaxonomyAreaHierarchy = subtaxonomyArea.getHierarchy();
        
        Hierarchy<Concept> ontologyHierarchy = taxonomy.getPAreaTaxonomyFactory().getSourceOntology().getConceptHierarchy();
        
        DisjointSubjectSubtaxonomyRootVisitor visitor = new DisjointSubjectSubtaxonomyRootVisitor(
                ontologyHierarchy, 
                taxonomy.getPAreaTaxonomyFactory(), 
                subtaxonomyArea);
        
        ontologyHierarchy.BFSUp(subtaxonomyAreaHierarchy.getNodes(), visitor);
        
        Set<Concept> identifiedRoots = visitor.getRoots();
        
        identifiedRoots.addAll(subtaxonomyAreaHierarchy.getRoots());
        
        // TODO: This can all be done in one pass through the hierarchy
        Hierarchy<Concept> ancestorHierarchy = ontologyHierarchy.getAncestorHierarchy(subtaxonomyAreaHierarchy.getNodes());
        
        Hierarchy<Concept> subjectSubtaxonomyHierarchy = ancestorHierarchy.getSubhierarchyRootedAt(identifiedRoots);
        
        // Create fake PAreas
        Set<PArea> dummyPAreas = new HashSet<>();
        
        identifiedRoots.forEach( (root) -> {
            Hierarchy<Concept> pareaHierarchy = subjectSubtaxonomyHierarchy.getSubhierarchyRootedAt(root);
            
            PArea parea = new PArea(pareaHierarchy, subtaxonomyArea.getRelationships());
            
            dummyPAreas.add(parea);
        });
        
        // Create a fake area to apply disjoint derivation on
        Area dummyArea = new Area(dummyPAreas, subtaxonomyArea.getRelationships());
        
        Hierarchy<Area> dummyAreaHierarchy = new Hierarchy<>(dummyArea);
        
        AreaTaxonomy dummyAreaTaxonomy = taxonomy.getPAreaTaxonomyFactory().createAreaTaxonomy(
                dummyAreaHierarchy, 
                subjectSubtaxonomyHierarchy);
        
        Hierarchy<PArea> dummyPAreaHierarchy = new Hierarchy<>(dummyPAreas);
        
        PAreaTaxonomy dummyPAreaTaxonomy = taxonomy.getPAreaTaxonomyFactory().createPAreaTaxonomy(
                dummyAreaTaxonomy, 
                dummyPAreaHierarchy, 
                subjectSubtaxonomyHierarchy);
        
        DisjointAbNGenerator<PAreaTaxonomy<PArea>, PArea> generator = new DisjointAbNGenerator<>();

        DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy
                = generator.generateDisjointAbstractionNetwork(
                        new DisjointPAreaTaxonomyFactory(),
                        dummyPAreaTaxonomy,
                        dummyArea.getPAreas());
        
        DisjointSubjectSubtaxonomyDerivation derivation = 
                new DisjointSubjectSubtaxonomyDerivation(
                        taxonomy.getDerivation(), 
                        subtaxonomyArea, 
                        new DisjointPAreaTaxonomyFactory());

        return new PartitionedNodeDisjointAbN<>(
                disjointTaxonomy, 
                dummyArea, 
                derivation);
    }
}
