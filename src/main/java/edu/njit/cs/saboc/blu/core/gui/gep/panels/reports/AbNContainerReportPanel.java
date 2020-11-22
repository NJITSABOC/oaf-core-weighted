package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.ContainerReportTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AbNContainerReportPanel extends AbNReportPanel {
    
    private final AbstractEntityList<ContainerReport> containerReportPanel; 
    
    public AbNContainerReportPanel(
            PartitionedAbNConfiguration config, 
            OAFAbstractTableModel<ContainerReport> model) {
        
        super(config);
        
        this.setLayout(new BorderLayout());
        
        this.containerReportPanel = new AbstractEntityList<ContainerReport> (model) {
                    
            public String getBorderText(Optional<ArrayList<ContainerReport>> reports) {
                if(reports.isPresent()) {
                    return String.format("%s (%d total)", 
                            config.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(true), reports.get().size());
                } else {
                    return config.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(true);
                }
            }
        };
        
        containerReportPanel.addEntitySelectionListener(config.getUIConfiguration().getListenerConfiguration().getContainerReportSelectedListener());
        
        this.add(containerReportPanel, BorderLayout.CENTER);
    }
    
    public AbNContainerReportPanel(PartitionedAbNConfiguration config) {
        this(config, new ContainerReportTableModel(config));
    }
    
    @Override
    public void displayAbNReport(AbstractionNetwork abn) {
        PartitionedAbNConfiguration partitionedConfig = (PartitionedAbNConfiguration)super.getConfiguration();
        
        PartitionedAbstractionNetwork pan = (PartitionedAbstractionNetwork)abn;
        
        Set<PartitionedNode> containers = pan.getBaseAbstractionNetwork().getNodes();
                
        ArrayList<ContainerReport> entries = new ArrayList<>();
        
        containers.forEach((container) -> {
            Set<SinglyRootedNode> groups = container.getInternalNodes();
            
            Set<Concept> concepts = new HashSet<>();
            
            groups.forEach((group) -> {
                concepts.addAll(group.getConcepts());
            });
            
            entries.add(new ContainerReport(container, 
                    groups, 
                    concepts,
                    container.getOverlappingConcepts()));
        });
        
        Collections.sort(entries, (a, b) -> {
            int aLevel = partitionedConfig.getPartitionedNodeLevel(a.getContainer());
            int bLevel = partitionedConfig.getPartitionedNodeLevel(b.getContainer());
            
            if(aLevel == bLevel) {
                int aCount = a.getConcepts().size();
                int bCount = b.getConcepts().size();
                
                return bCount - aCount;
            } else {
                return aLevel - bLevel;
            }
        });
        
        containerReportPanel.setContents(entries);
    }
}
