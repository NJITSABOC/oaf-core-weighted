
package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.node.SimilarityNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
/**
 *
 * Represents a Band in a tribal abstraction network. Summaries a set of nodes
 * that belong to the same subhierarchies (as rooted at patriarch concepts)
 * 
 * @author Chris O
 */
public class Band extends SimilarityNode {

    private final Set<Concept> patriarchs;

    public Band(Set<Cluster> clusters, Set<Concept> patriarchs) {
        super(clusters);
        
        this.patriarchs = patriarchs;
    }

    public Set<Cluster> getClusters() {
        return (Set<Cluster>)(Set<?>)super.getInternalNodes();
    }
    
    public Set<Concept> getPatriarchs() {
        return patriarchs;
    }

    public boolean equals(Object o) {
        if(o instanceof Band) {
            return ((Band)o).patriarchs.equals(this.patriarchs);
        }

        return false;
    }
    
    public String getName() {
        return getName(", ");
    }
    
    public String getName(String separator) {
        
        ArrayList<String> patriarchNames = new ArrayList<>();
        
        this.patriarchs.forEach( (concept) -> {
            patriarchNames.add(concept.getName());
        });
        
        Collections.sort(patriarchNames);

        String name = patriarchNames.get(0);

        for (int c = 1; c < patriarchNames.size(); c++) {
            name += separator;
            name += patriarchNames.get(c);
        }

        return name;
    }
    
    public int hashCode() {
        return patriarchs.hashCode();
    }
}