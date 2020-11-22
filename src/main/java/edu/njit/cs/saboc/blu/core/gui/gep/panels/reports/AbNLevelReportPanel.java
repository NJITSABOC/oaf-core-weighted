package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.AbNLevelReport;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.LevelReportTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AbNLevelReportPanel extends AbNReportPanel {

    private final AbstractEntityList<AbNLevelReport> levelReportList; 

    protected AbNLevelReportPanel(PartitionedAbNConfiguration config, 
            OAFAbstractTableModel<AbNLevelReport> model) {
        
        super(config);
                
        this.setLayout(new BorderLayout());
        
        this.levelReportList = new AbstractEntityList<AbNLevelReport> (model) {
                    
            public String getBorderText(Optional<ArrayList<AbNLevelReport>> reports) {
                if(reports.isPresent()) {
                    return String.format("Abstraction Network Levels (%d total)", reports.get().size());
                } else {
                    return "Abstraction Network Levels";
                }
            }
        };
        
        this.add(levelReportList, BorderLayout.CENTER);
    }
    
    public AbNLevelReportPanel(PartitionedAbNConfiguration config) {
        this(config, new LevelReportTableModel(config));
    }
    
    @Override
    public void displayAbNReport(AbstractionNetwork abn) {
        
        PartitionedAbNConfiguration currentConfig = (PartitionedAbNConfiguration)getConfiguration();
        
        PartitionedAbstractionNetwork pan = (PartitionedAbstractionNetwork)abn;
        
        Set<PartitionedNode> containers = pan.getBaseAbstractionNetwork().getNodes();
        
        HashMap<Integer, Set<PartitionedNode>> containerLevels = new HashMap<>();
        
        containers.forEach((container) -> {
            int containerLevel = currentConfig.getPartitionedNodeLevel(container);
            
            if(!containerLevels.containsKey(containerLevel)) {
                containerLevels.put(containerLevel, new HashSet<>());
            }
            
            containerLevels.get(containerLevel).add(container);
        });
        
        ArrayList<AbNLevelReport> levelReports = new ArrayList<>();
        
        containerLevels.forEach((level, levelContainers) -> {
            Set<SinglyRootedNode> groupsAtLevel = new HashSet<>();
            
            levelContainers.forEach((container) -> {
                groupsAtLevel.addAll(container.getInternalNodes());
            });
            
            Set<Concept> conceptsAtLevel = new HashSet<>();
            
            groupsAtLevel.forEach((group) -> {
                conceptsAtLevel.addAll(group.getConcepts());
            });
            
            Set<Concept> overlappingConceptsAtLevel = new HashSet<>();
            
            levelContainers.forEach((container) -> {
                overlappingConceptsAtLevel.addAll(container.getOverlappingConcepts());
            });
            
            levelReports.add(new AbNLevelReport(level, conceptsAtLevel, overlappingConceptsAtLevel, groupsAtLevel, levelContainers));
        });
        
        Collections.sort(levelReports, (a, b) -> a.getLevel() - b.getLevel());
        
        levelReportList.setContents(levelReports);
    }
}
