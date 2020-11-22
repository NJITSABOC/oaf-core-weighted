package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PatriarchIntersectionResult {
    
    private final Concept patriarch;
    private final Set<Band> bands;
    
    public PatriarchIntersectionResult(Concept patriarch, Set<Band> bands) {
        this.patriarch = patriarch;
        
        this.bands = bands;
    }
    
    public Concept getPatriarch() {
        return patriarch;
    }
    
    public Set<Band> getBands() {
        return bands;
    }
    
    public Set<Cluster> getClusters() {
        
        Set<Cluster> allClusters = new HashSet<>();
        
        bands.forEach( (band) -> {
            allClusters.addAll(band.getClusters());
        });
        
        return allClusters;
    }
}
