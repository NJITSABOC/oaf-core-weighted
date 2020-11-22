package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNReportPanel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PatriarchIntersectionReport extends AbNReportPanel<ClusterTribalAbstractionNetwork<Cluster>> {
    
    private final AbstractEntityList<PatriarchIntersectionResult> reportList;

    public PatriarchIntersectionReport(TANConfiguration config) {
        
        super(config);

        this.reportList = new AbstractEntityList<PatriarchIntersectionResult>(new PatriarchIntersectionTableModel(config)) {

            @Override
            protected String getBorderText(Optional<ArrayList<PatriarchIntersectionResult>> entities) {
                
                String base = "Patriarch Intersection Metrics";
                
                if(entities.isPresent()) {
                    base = String.format("%s (%d)", base, entities.get().size());
                }
                
                return base;
            }
        };
        
        this.setLayout(new BorderLayout());
        
        this.add(reportList, BorderLayout.CENTER);
    }

    @Override
    public void displayAbNReport(ClusterTribalAbstractionNetwork<Cluster> abn) {
        
        Map<Concept, Set<Band>> bandPatriarchs = new HashMap<>();
        
        abn.getBands().forEach( (band) -> {
            band.getPatriarchs().forEach( (patriarch) -> {
                
                if(!bandPatriarchs.containsKey(patriarch)) {
                    bandPatriarchs.put(patriarch, new HashSet<>());
                }
                
                bandPatriarchs.get(patriarch).add(band);
            });
        });
        
        ArrayList<PatriarchIntersectionResult> reports = new ArrayList<>();
        
        bandPatriarchs.forEach( (patriarch, bands) -> {
            reports.add(new PatriarchIntersectionResult(patriarch, bands));
        });
        
        reports.sort( (a, b) -> {
            return a.getPatriarch().getName().compareToIgnoreCase(b.getPatriarch().getName());
        });
        
        reportList.setContents(reports);
    }
}
