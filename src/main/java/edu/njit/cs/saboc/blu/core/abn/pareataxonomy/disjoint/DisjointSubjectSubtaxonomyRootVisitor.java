package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.disjoint;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.HierarchyVisitor;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DisjointSubjectSubtaxonomyRootVisitor extends HierarchyVisitor<Concept> {
    
    private final PAreaTaxonomyFactory factory;
    private final Area subtaxonomyArea;
    
    private final Set<Concept> roots;
    
    public DisjointSubjectSubtaxonomyRootVisitor(
            Hierarchy<Concept> theHierarchy, 
            PAreaTaxonomyFactory factory,
            Area subtaxonomyArea) {
        
        super(theHierarchy);
        
        this.factory = factory;
        this.subtaxonomyArea = subtaxonomyArea;
        
        this.roots = new HashSet<>();
    }

    @Override
    public void visit(Concept node) {
        
        Set<Concept> parents = super.getHierarchy().getParents(node);
        
        boolean allParentsOutsideArea = parents.stream().allMatch((parent) -> {
            Set<InheritableProperty> properties = factory.getRelationships(parent);
            
            return !properties.equals(subtaxonomyArea.getRelationships());
        });
        
        if(factory.getRelationships(node).equals(subtaxonomyArea.getRelationships()) && allParentsOutsideArea) {
            roots.add(node);
        }
    }
    
    public Set<Concept> getRoots() {
        return roots;
    }
}
