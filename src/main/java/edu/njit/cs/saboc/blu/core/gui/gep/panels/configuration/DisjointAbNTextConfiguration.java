package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.AggregateAbNTextGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris
 * @param <T>
 */
public abstract class DisjointAbNTextConfiguration<T extends DisjointNode> extends AbNTextConfiguration<T> {

    private final DisjointAbstractionNetwork disjointAbN;
    
    public DisjointAbNTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig,
            DisjointAbstractionNetwork disjointAbN) {
        
        super(ontologyEntityNameConfig);
        
        this.disjointAbN = disjointAbN;
    }
    
     @Override
    public String getAbNSummary() {

        Set<DisjointNode> disjointNodes = disjointAbN.getAllDisjointNodes();

        Map<Integer, Set<DisjointNode>> levels = new HashMap<>();

        for (DisjointNode disjointNode : disjointNodes) {
            int level = disjointNode.getOverlaps().size();

            if (!levels.containsKey(level)) {
                levels.put(level, new HashSet<>());
            }

            levels.get(level).add(disjointNode);
        }

        ArrayList<Integer> sortedLevels = new ArrayList<>(levels.keySet());
        Collections.sort(sortedLevels);

        String summary = "The <b><abnName></b> summarizes "
                + "<font color = 'RED'><conceptCount></font> "
                + "<conceptTypeName count=<conceptCount>> in "
                + "<font color = 'RED'><disjointNodeCount></font> "
                + "<nodeTypeName count=<disjointNodeCount>>.";
        
        summary = summary.replaceAll("<abnName>", 
                disjointAbN.getDerivation().getName());
        
        summary = summary.replaceAll("<conceptCount>", 
                Integer.toString(disjointAbN.getSourceHierarchy().size()));
        
        summary = summary.replaceAll("<disjointNodeCount>", 
                Integer.toString(disjointAbN.getNodeCount()));

        summary += "<p><b>Overlapping <conceptTypeName> Distribution:</b><br>";
        
        for (int level : sortedLevels) {

            if (level == 1) {
                continue;
            }

            Set<DisjointNode> levelDisjointPAreas = levels.get(level);

            int levelClassCount = 0;

            for (DisjointNode levelDisjointPArea : levelDisjointPAreas) {
                levelClassCount += levelDisjointPArea.getConceptCount();
            }

            summary += String.format("Level %d: %d Disjoint <nodeTypeName count=2>:, %d %s<br>", 
                    level, 
                    levelDisjointPAreas.size(), 
                    levelClassCount, 
                    this.getOntologyEntityNameConfiguration().getConceptTypeName(true));
        }
        
        if(disjointAbN.isAggregated()) {
            AggregateAbNTextGenerator generator = new AggregateAbNTextGenerator();
            
            summary += "<p>";
            
            summary += generator.generateAggregateDetailsText(this, disjointAbN);
        }
        
        AbNTextFormatter formatter = new AbNTextFormatter(this);

        return formatter.format(summary);
    }
    
     public abstract String getOverlappingNodeTypeName(boolean plural);
}
