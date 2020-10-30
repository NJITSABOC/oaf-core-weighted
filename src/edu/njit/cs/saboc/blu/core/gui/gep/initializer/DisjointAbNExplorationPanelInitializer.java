package edu.njit.cs.saboc.blu.core.gui.gep.initializer;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.graph.disjointabn.DisjointAbNGraph;
import edu.njit.cs.saboc.blu.core.graph.disjointabn.DisjointAbNLayout;
import edu.njit.cs.saboc.blu.core.graph.disjointabn.DisjointNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel.AggregationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningMessage;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.DisjointAbNWarningManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.framestate.FrameState;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class DisjointAbNExplorationPanelInitializer extends AggregateableAbNExplorationPanelInitializer {
    
    private final DisjointAbNConfiguration config;
    private final PartitionedAbNConfiguration parentConfig;
    
    public DisjointAbNExplorationPanelInitializer(
            DisjointAbNConfiguration config, 
            PartitionedAbNConfiguration parentConfig, 
            AggregationAction aggregationAction,
            AbNWarningManager warningManager,
            FrameState frameState) {
        
        super(warningManager, frameState, aggregationAction);
        
        this.config = config;
        this.parentConfig = parentConfig;
    }
    
    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp) {
        super.initializeAbNDisplayPanel(displayPanel, startUp);
    }
    
    @Override
    public void showAbNAlerts(AbNDisplayPanel displayPanel) {
        
        super.showAbNAlerts(displayPanel);

        AbNWarningManager warningManager = this.getWarningManager();
        
        ArrayList<AbNWarningMessage> warningMessages = new ArrayList<>();
        
        if(warningManager instanceof DisjointAbNWarningManager) {
            DisjointAbNWarningManager disjointWarningManager = (DisjointAbNWarningManager)warningManager;
            
            DisjointAbNGraph<?> disjointAbNGraph = (DisjointAbNGraph)displayPanel.getGraph();

            if (disjointWarningManager.showOverlapsRecoloredMessage()) {
                DisjointAbNLayout layout = disjointAbNGraph.getGraphLayout();

                if (layout.recoloredOverlaps()) {

                    warningMessages.add(() -> {
                        SwingUtilities.invokeLater(() -> {

                            String message = "<html>The nodes in this disjoint "
                                    + "abstraction network have had their  "
                                    + "colors automatically changed."
                                    + "<p><p>This does not affect the disjoint "
                                    + "abstraction network but it removes (or reduces) "
                                    + "gray nodes that"
                                    + "<p>could not be colored properly in the previous "
                                    + "disjoint abstraction network.";

                            String[] options = {"Warn me again", "Do not warn me again"};

                            int result = JOptionPane.showOptionDialog(
                                    null,
                                    message,
                                    "Disjoint Nodes Automatically Recolored",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null,
                                    options,
                                    options[0]);

                            if (result == 1) {
                                disjointWarningManager.setShowOverlapsRecoloredMessage(false);
                            }
                        });
                    });

                }
            }
            
            if(disjointWarningManager.showGrayWarningMessage()) {
                
                Set<SinglyRootedNodeEntry> grayRoots = disjointAbNGraph.getNodeEntries().values().stream().filter( (nodeEntry) -> {
                    
                    DisjointNodeEntry disjointEntry = (DisjointNodeEntry)nodeEntry;
                    
                    DisjointNode node = (DisjointNode)disjointEntry.getNode();
                    
                    if(node.getOverlaps().size() == 1) {
                        Color color = disjointEntry.getColorSet()[0];
                        
                        return color.equals(Color.GRAY);
                    }

                    return false;
                }).collect(Collectors.toSet());
                
                if (grayRoots.size() > 1) {

                    warningMessages.add(() -> {
                        SwingUtilities.invokeLater(() -> {

                            String message = "<html>This disjoint abstraction network has many roots and <br>"
                                    + "we have run out of colors to represent them."
                                    + "<p><p>Consider viewing a subset of this disjoint abstraction network <br>"
                                    + "by clicking on the disjoint abstraction network subset button on the <br>"
                                    + "Dashboard panel.";

                            String[] options = {"Warn me again", "Do not warn me again"};

                            int result = JOptionPane.showOptionDialog(
                                    null,
                                    message,
                                    "Looking a little gray?",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null,
                                    options,
                                    options[0]);

                            if (result == 1) {
                                disjointWarningManager.setShowGrayWarningMessage(false);
                            }
                        });
                    });
                }
            }
            
            if(!warningMessages.isEmpty()) {
                showWarnings(warningMessages);
            }
        }
    }
    
    private void showWarnings(ArrayList<AbNWarningMessage> warningMessages) {
        
        Thread waitThread = new Thread(() -> {

            try {
                Thread.sleep(1000);
                
                warningMessages.forEach( (warningMessage) -> {
                   warningMessage.showWarning();
                });

            } catch (InterruptedException ie) {

            }

            
        });

        waitThread.start();
    }
    
    

}
