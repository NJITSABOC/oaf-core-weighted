package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Chris O
 */
public class PatriarchIntersectionTableModel extends OAFAbstractTableModel<PatriarchIntersectionResult> {
    
    public PatriarchIntersectionTableModel(TANConfiguration config) {
        
        super(new String [] { 
            String.format("Patriarch %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)),
            "# Bands",
            "# Clusters",
            "# Intersecting Concepts"
        });
    }

    @Override
    protected Object[] createRow(PatriarchIntersectionResult item) {
        Set<Band> bands = item.getBands();
  
        Set<Band> intersectingBands = bands.stream().filter( (band) -> {
            return band.getPatriarchs().size() > 1;
        }).collect(Collectors.toSet());
        
        Set<Cluster> intersectingClusters = new HashSet<>();
        Set<Concept> intersectingConcepts = new HashSet<>();
        
        intersectingBands.stream().forEach( (band) -> {
            intersectingClusters.addAll(band.getClusters());
            intersectingConcepts.addAll(band.getConcepts());
        });
        
        return new Object[] {
            item.getPatriarch().getName(),
            intersectingBands.size(),
            intersectingClusters.size(),
            intersectingConcepts.size()
        };
    }
}
