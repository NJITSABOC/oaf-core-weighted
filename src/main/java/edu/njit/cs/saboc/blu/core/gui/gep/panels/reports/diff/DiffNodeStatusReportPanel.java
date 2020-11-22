package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkInstance;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.diff.DiffNodeStatusReport;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.diff.DiffNodeStatusTableModel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import javax.swing.JTable;

/**
 *
 * @author Chris O
 */
public class DiffNodeStatusReportPanel extends AbNReportPanel<AbstractionNetwork<SinglyRootedNode>> {
    
    private final AbstractEntityList<DiffNodeStatusReport> diffNodeStatusReportPanel; 
    
    public DiffNodeStatusReportPanel(AbNConfiguration config) {
        this(config, new DiffNodeStatusTableModel(config));
    }
    
    public DiffNodeStatusReportPanel(
            AbNConfiguration config, 
            OAFAbstractTableModel<DiffNodeStatusReport> model) {
        
        super(config);
        
        this.setLayout(new BorderLayout());
        
        this.diffNodeStatusReportPanel = new AbstractEntityList<DiffNodeStatusReport> (model) {
                    
            @Override
            public String getBorderText(Optional<ArrayList<DiffNodeStatusReport>> reports) {
                
               return String.format("%s Change Type", 
                       config.getTextConfiguration().getNodeTypeName(false));
               
            }
        };
        
        this.diffNodeStatusReportPanel.addEntitySelectionListener(new EntitySelectionAdapter<DiffNodeStatusReport>() {
            
            @Override
            public void entityDoubleClicked(DiffNodeStatusReport entity) {
                
                Optional<AbNNodeEntry> optEntry = getEntryFor(entity.getNode());
                
                if(optEntry.isPresent()) {
                    displayEntry(optEntry.get());
                }
            }
            
        });
        
        this.diffNodeStatusReportPanel.getEntityTable().addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseMoved(MouseEvent e) {
                
                JTable sourceTable = diffNodeStatusReportPanel.getEntityTable();
                
                int rowIndex = sourceTable.rowAtPoint(e.getPoint());
                
                if(rowIndex < 0) {
                    return;
                }
                
                DiffNodeStatusReport report = diffNodeStatusReportPanel.getItemAtRow(rowIndex);
                
                String toolTip = null;
                
                switch (report.getStatus()) {
                    case MovedExactly:
                        
                        toolTip = "This introduced <nodeTypeName> contains exactly "
                                + "the same <conceptTypeName count=2> as "
                                + "a removed <nodeTypeName> (the root <nodeTypeName count=2> are the same).";
                        
                        break;
                        
                    case MovedSubset:
                        
                        toolTip = "This introduced <nodeTypeName> contains a subset of "
                                + "the <conceptTypeName count=2> from "
                                + "a removed <nodeTypeName> (the root <nodeTypeName count=2> are the same).";
                        
                        break;
                        
                    case MovedSuperset:
                        
                    case MovedDifference:
                        
                        toolTip = "This introduced <nodeTypeName> contains some of "
                                + "the same <conceptTypeName count=2> as "
                                + "a removed <nodeTypeName>, along with additional <conceptTypeName count=2>"
                                + " (the root <nodeTypeName count=2> are the same).";
                        
                        break;
                        
                    case IntroducedFromNew:
                        toolTip = "This introduced <nodeTypeName> consists of <conceptTypeName count=2> that "
                                + "were added to the ontology.";

                        break;
                        
                    case IntroducedFromOneNode:
                        
                    case IntroducedFromMultipleNodes:
                        
                    case RemovedToOneNode:
                        
                    case RemovedToMultipleNodes:
                        
                    case RemovedFromOnt:
                        toolTip = "This removed <nodeTypeName> consists of <conceptTypeName count=2> that "
                                + "were removed from the ontology.";
                        
                        break;
                }
                
//                if(toolTip == null) {
//                    sourceTable.setToolTipText(null);
//                }
//                
//                AbNTextFormatter textFormatter = new AbNTextFormatter(config.getTextConfiguration());
//                
//                toolTip = textFormatter.format(toolTip);
//                
//                sourceTable.setToolTipText(toolTip);
            }
        });
        
        // TODO: Add right click menu to diffNodeStatusReportPanel for navigation
        
        this.add(diffNodeStatusReportPanel, BorderLayout.CENTER);
    }
    
    private Optional<AbNNodeEntry> getEntryFor(SinglyRootedNode node) {
        return Optional.of(super.getConfiguration().getUIConfiguration().
                getDisplayPanel().getGraph().getNodeEntries().get(node));
    }
    
    private void displayEntry(AbNNodeEntry entry) {
        AbNConfiguration config = super.getConfiguration();
        
        config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(entry);
        
        config.getUIConfiguration().getDisplayPanel().highlightSinglyRootedNodes(
                Collections.singleton((SinglyRootedNode)entry.getNode()));
    }
        
    @Override
    public void displayAbNReport(AbstractionNetwork<SinglyRootedNode> abn) {
        
        DiffAbstractionNetworkInstance<AbstractionNetwork<SinglyRootedNode>> diffAbN = 
                (DiffAbstractionNetworkInstance<AbstractionNetwork<SinglyRootedNode>>)abn;
        
        DiffNodeStateReport stateReport = new DiffNodeStateReport(diffAbN);

        diffNodeStatusReportPanel.setContents(stateReport.getEntries());
    }
}