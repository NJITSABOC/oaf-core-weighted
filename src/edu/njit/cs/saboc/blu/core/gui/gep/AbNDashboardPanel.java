package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.CompactNodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.CompactAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.loading.LoadingPanel;
import java.awt.BorderLayout;
import java.util.Optional;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class AbNDashboardPanel extends JPanel {
    
    private final LoadingPanel loadingPanel;
    
    private Optional<AbNDetailsPanel> abnDetailsPanel = Optional.empty();
    private Optional<CompactAbNDetailsPanel> compactAbNDetailsPanel = Optional.empty();
    
    private Optional<NodeDashboardPanel> nodeDetailsPanel = Optional.empty();
    private Optional<CompactNodeDashboardPanel> compactNodeDetailsPanel = Optional.empty();
    
    private Optional<NodeDashboardPanel> partitionedNodeDetailsPanel = Optional.empty();
    private Optional<CompactNodeDashboardPanel> compactPartitionedNodeDetailsPanel = Optional.empty();

    private boolean showCompact = true;

    private Optional<SinglyRootedNode> selectedNode = Optional.empty();
    private Optional<PartitionedNode> selectedPartitionedNode = Optional.empty();
    
    public AbNDashboardPanel() {
        super(new BorderLayout());
        
        loadingPanel = new LoadingPanel();
    }
    
    public void setShowCompact(boolean value) {
        this.showCompact = value;
        
        if(selectedNode.isPresent()) {
            this.displayDetailsForNode(selectedNode.get());
        } else if(selectedPartitionedNode.isPresent()) {
            this.displayDetailsForPartitionedNode(selectedPartitionedNode.get());
        } else {
            reset();
        }
    }
    
    public void initialize(AbNConfiguration configuration) {
        this.abnDetailsPanel = Optional.of(configuration.getUIConfiguration().createAbNDetailsPanel());
        this.compactAbNDetailsPanel = Optional.of(configuration.getUIConfiguration().createCompactAbNDetailsPanel());

        if (abnDetailsPanel.isPresent()) {
            this.setDetailsPanelContents(abnDetailsPanel.get());
        }

        if (configuration.getUIConfiguration().hasNodeDetailsPanel()) {
            this.nodeDetailsPanel = Optional.of(configuration.getUIConfiguration().createNodeDetailsPanel());
            this.compactNodeDetailsPanel = Optional.of(configuration.getUIConfiguration().createCompactNodeDetailsPanel());
        } else {
            this.nodeDetailsPanel = Optional.empty();
            this.compactNodeDetailsPanel = Optional.empty();
        }

        if (configuration.getUIConfiguration() instanceof PartitionedAbNUIConfiguration) {
            PartitionedAbNUIConfiguration config = (PartitionedAbNUIConfiguration) configuration.getUIConfiguration();

            if (config.hasPartitionedNodeDetailsPanel()) {
                this.partitionedNodeDetailsPanel = Optional.of(config.createPartitionedNodeDetailsPanel());
                this.compactPartitionedNodeDetailsPanel = Optional.of(config.createCompactPartitionedNodeDetailsPanel());
                
            } else {
                this.partitionedNodeDetailsPanel = Optional.empty();
                this.compactPartitionedNodeDetailsPanel = Optional.empty();
            }
            
        } else {
            this.partitionedNodeDetailsPanel = Optional.empty();
            this.compactPartitionedNodeDetailsPanel = Optional.empty();
        }
    }
    
    private void clearPartitionedNodePanels() {
        selectedPartitionedNode = Optional.empty();
        
        if (partitionedNodeDetailsPanel.isPresent()) {
            partitionedNodeDetailsPanel.get().clearContents();
        }
        
        if (compactPartitionedNodeDetailsPanel.isPresent()) {
            compactPartitionedNodeDetailsPanel.get().clearContents();
        }
    }
    
    private void clearNodePanels() {
        selectedNode = Optional.empty();
        
        if (nodeDetailsPanel.isPresent()) {
            nodeDetailsPanel.get().clearContents();
        }

        if (compactNodeDetailsPanel.isPresent()) {
            compactNodeDetailsPanel.get().clearContents();
        }
    }
    
    public void displayDetailsForNode(SinglyRootedNode node) {
        
        clearPartitionedNodePanels();
        
        this.selectedNode = Optional.of(node);
        
        if (showCompact) {
            if (compactNodeDetailsPanel.isPresent()) {
                setDetailsPanelContents(loadingPanel);

                compactNodeDetailsPanel.get().clearContents();

                Thread loadThread = new Thread(() -> {
                    compactNodeDetailsPanel.get().setContents(node);

                    SwingUtilities.invokeLater(() -> {
                        setDetailsPanelContents(compactNodeDetailsPanel.get());
                    });
                });

                loadThread.start();
            }
        } else {
            if (nodeDetailsPanel.isPresent()) {
                setDetailsPanelContents(loadingPanel);

                nodeDetailsPanel.get().clearContents();

                Thread loadThread = new Thread(() -> {
                    nodeDetailsPanel.get().setContents(node);

                    SwingUtilities.invokeLater(() -> {
                        setDetailsPanelContents(nodeDetailsPanel.get());
                    });
                });

                loadThread.start();
            }
        }
    }
    
    public void displayDetailsForPartitionedNode(PartitionedNode partitionedNode) {
        clearNodePanels();
        
        this.selectedPartitionedNode = Optional.of(partitionedNode);

        if (showCompact) {
            if (compactPartitionedNodeDetailsPanel.isPresent()) {
                setDetailsPanelContents(loadingPanel);

                compactPartitionedNodeDetailsPanel.get().clearContents();

                Thread loadThread = new Thread(() -> {
                    compactPartitionedNodeDetailsPanel.get().setContents(partitionedNode);

                    SwingUtilities.invokeLater(() -> {
                        setDetailsPanelContents(compactPartitionedNodeDetailsPanel.get());
                    });
                });

                loadThread.start();
            }
        } else {
            if (partitionedNodeDetailsPanel.isPresent()) {
                setDetailsPanelContents(loadingPanel);

                partitionedNodeDetailsPanel.get().clearContents();

                Thread loadThread = new Thread(() -> {
                    partitionedNodeDetailsPanel.get().setContents(partitionedNode);

                    SwingUtilities.invokeLater(() -> {
                        setDetailsPanelContents(partitionedNodeDetailsPanel.get());
                    });
                });

                loadThread.start();
            }
        }
    }

    private void setDetailsPanelContents(JPanel panel) {
        this.removeAll();
        
        this.add(panel, BorderLayout.CENTER);
        
        this.revalidate();
        this.repaint();
    }
    
    public void reset() {
        clearNodePanels();
        clearPartitionedNodePanels();

        if(showCompact) {
            if (compactAbNDetailsPanel.isPresent()) {
                setDetailsPanelContents(compactAbNDetailsPanel.get());
            }
        } else {
            if (abnDetailsPanel.isPresent()) {
                setDetailsPanelContents(abnDetailsPanel.get());
            }
        }
    }
    
    public void clear() {
        reset();
        
        abnDetailsPanel = Optional.empty();
        compactAbNDetailsPanel = Optional.empty();
        
        partitionedNodeDetailsPanel = Optional.empty();
        compactPartitionedNodeDetailsPanel = Optional.empty();
        
        nodeDetailsPanel = Optional.empty();
        compactNodeDetailsPanel = Optional.empty();
        
        this.setDetailsPanelContents(loadingPanel);
    }
}
