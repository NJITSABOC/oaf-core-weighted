package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.DisjointPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.tan.DisjointCluster;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNExplorationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.framestate.FrameState;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistoryEntry;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNHistoryNavigationPanel;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Optional;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistory;
import edu.njit.cs.saboc.blu.core.gui.workspace.AbNWorkspace;
import edu.njit.cs.saboc.blu.core.gui.workspace.AbNWorkspaceManager;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFStateFileManager;

/**
 *
 * @author Chris O
 */
public class MultiAbNGraphFrame extends JInternalFrame {

    private final JFrame parentFrame;

    private final JPanel taskPanel;

    private final AbNExplorationPanel abnExplorationPanel;

    private Optional<TaskBarPanel> optCurrentTaskBarPanel = Optional.empty();

    private Optional<AbNGraphFrameInitializers> optInitializers;
    

    private final MultiAbNDisplayManager displayManager;
    
    

    private final AbNDerivationHistory abnDerivationHistory;

    private final AbNHistoryNavigationPanel historyNavigationPanel;
    
    
    
    private Optional<AbNWorkspace> optWorkspace;
    
    private final AbNWorkspaceManager workspaceManager;
    
    private final OAFStateFileManager stateFileManager;
    
    private final FrameState frameState;

    public MultiAbNGraphFrame(
            JFrame parentFrame, 
            OAFStateFileManager stateFileManager) {

        super("Ontology Abstraction Framework (OAF) Abstraction Network Viewer",
                true, // resizable
                true, // closable
                true, // maximizable
                false);// iconifiable

        this.parentFrame = parentFrame;
        this.stateFileManager = stateFileManager;

        this.displayManager = new MultiAbNDisplayManager(this, null);

        this.taskPanel = new JPanel();
        this.taskPanel.setLayout(new BorderLayout());

        this.abnExplorationPanel = new AbNExplorationPanel();
        this.abnExplorationPanel.showLoading();
        
        this.optWorkspace = Optional.empty();
        
        this.optInitializers = Optional.empty();
        
        this.workspaceManager = new AbNWorkspaceManager(this);
        
        this.frameState = new FrameState();

        this.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(taskPanel, BorderLayout.CENTER);
        
        this.abnDerivationHistory = new AbNDerivationHistory(this);

        this.historyNavigationPanel = new AbNHistoryNavigationPanel(
                this,
                abnDerivationHistory);

        northPanel.add(historyNavigationPanel, BorderLayout.WEST);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(abnExplorationPanel, BorderLayout.CENTER);

        this.addInternalFrameListener(new InternalFrameAdapter() {

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if (optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().disposeAllPopupButtons();
                }

                abnExplorationPanel.getDisplayPanel().kill();
                
                historyNavigationPanel.reset();
                historyNavigationPanel.closePopups();
            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                if (optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().closeAllPopupButtons();
                }
                
                historyNavigationPanel.closePopups();
            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {
                if (optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().closeAllPopupButtons();
                }
                
                historyNavigationPanel.closePopups();
            }
        });

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                JInternalFrame frame = (JInternalFrame) e.getSource();

                if (optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().updatePopupLocations(frame.getSize());
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                JInternalFrame frame = (JInternalFrame) e.getSource();

                if (optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().updatePopupLocations(frame.getSize());
                }
            }
            
            
        });

        this.setSize(1200, 512);
        this.setVisible(true);
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }
    
    public FrameState getFrameState(){
        return frameState;
    }
    
    public OAFStateFileManager getStateFileManager() {
        return stateFileManager;
    }
    
    public void setInitializers(AbNGraphFrameInitializers initializers) {
        this.optInitializers = Optional.of(initializers);
        
        this.workspaceManager.setInitializers(initializers);
        this.historyNavigationPanel.setInitializers(initializers);
    }
    
    public Optional<AbNGraphFrameInitializers> getCurrentInitializers() {
        return this.optInitializers;
    }
    
    public void clearInitializers() {
        this.optInitializers = Optional.empty();
        
        this.workspaceManager.clearInitializers();
        this.historyNavigationPanel.clearInitializers();
    }

    public AbNExplorationPanel getAbNExplorationPanel() {
        return abnExplorationPanel;
    }
    
    public AbNDerivationHistory getDerivationHistory() {
        return abnDerivationHistory;
    }
    
    public Optional<AbNWorkspace> getWorkspace() {
        return this.optWorkspace;
    }
    
    public AbNWorkspaceManager getWorkspaceManager() {
        return workspaceManager;
    }
    
    public void setWorkspace(AbNWorkspace workspace) {
        
        if(!this.optInitializers.isPresent()) {
            return;
        }
        
        AbNGraphFrameInitializers initializers = this.optInitializers.get();
        
        this.optWorkspace = Optional.of(workspace);
        
        this.abnDerivationHistory.setHistory(workspace.getWorkspaceHistory().getHistory());
        this.historyNavigationPanel.reset();
        
        this.abnDerivationHistory.getHistory().get(
                abnDerivationHistory.getHistory().size() - 1).displayEntry(initializers.getSourceOntology());
    }
    
    public void clearWorkspace() {
        this.optWorkspace = Optional.empty();
    }
    
    public void displayAbstractionNetwork(AbstractionNetwork<?> abn) {
        displayAbstractionNetwork(abn, true);
    }
    
    public void displayAbstractionNetwork(AbstractionNetwork<?> abn, boolean createHistoryEntry) {
        
        if(abn instanceof PAreaTaxonomy) {
            displayPAreaTaxonomy((PAreaTaxonomy)abn, createHistoryEntry);
        } else if(abn instanceof ClusterTribalAbstractionNetwork) {
            displayTAN( (ClusterTribalAbstractionNetwork)abn, createHistoryEntry);
        } else if(abn instanceof TargetAbstractionNetwork) {
            displayTargetAbstractionNetwork( (TargetAbstractionNetwork)abn, createHistoryEntry);
        } else if(abn instanceof DisjointAbstractionNetwork) {
            DisjointAbstractionNetwork<?, ?, ?> disjointAbN = (DisjointAbstractionNetwork)abn;
            
            if(disjointAbN.getParentAbstractionNetwork() instanceof PAreaTaxonomy) {
                
                displayDisjointPAreaTaxonomy(
                        (DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea>)disjointAbN, 
                        createHistoryEntry);
                
            } else if(disjointAbN.getParentAbstractionNetwork() instanceof ClusterTribalAbstractionNetwork) {
                
                displayDisjointTAN(
                        (DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster>)disjointAbN, 
                        createHistoryEntry);
                
            }
        }
    }
    
    
    public void displayAbstractionNetwork(AbstractionNetwork<?> abn, boolean createHistoryEntry, FrameState frameState) {
        this.frameState.setAggregateProperty(frameState.getAggregateProperty());
        displayAbstractionNetwork(abn, createHistoryEntry);
    }
    
    public AbstractionNetwork<?> applyFrameState(AbstractionNetwork<?> abn, FrameState frameState) {
        
        if (abn instanceof PAreaTaxonomy) {
            return ((PAreaTaxonomy) abn).getAggregated(frameState.getAggregateProperty());
        } else if (abn instanceof ClusterTribalAbstractionNetwork) {
            return ((ClusterTribalAbstractionNetwork) abn).getAggregated(frameState.getAggregateProperty());
        } else if (abn instanceof TargetAbstractionNetwork) {
            return ((TargetAbstractionNetwork) abn).getAggregated(frameState.getAggregateProperty());
        } else if (abn instanceof DisjointAbstractionNetwork) {
            DisjointAbstractionNetwork<?, ?, ?> disjointAbN = (DisjointAbstractionNetwork) abn;

            if (disjointAbN.getParentAbstractionNetwork() instanceof PAreaTaxonomy) {

                return ((DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea>) disjointAbN).getAggregated(frameState.getAggregateProperty());

            } else if (disjointAbN.getParentAbstractionNetwork() instanceof ClusterTribalAbstractionNetwork) {

                return ((DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster>) disjointAbN).getAggregated(frameState.getAggregateProperty());
            } else {
                return abn;
            }

        } else {
            return abn;
        }
    }
    
    
    public void addDerivationHistoryEntry(AbstractionNetwork<?> abn) {
        this.abnDerivationHistory.addEntry(new AbNDerivationHistoryEntry<>(abn.getDerivation(), this), true);
    }

    public void displayPAreaTaxonomy(PAreaTaxonomy taxonomy) {
        displayPAreaTaxonomy(taxonomy, true);
    }

    public void displayPAreaTaxonomy(PAreaTaxonomy taxonomy, boolean createHistoryEntry) {
        
        if(!this.optInitializers.isPresent()) {
            return;
        }
        
        AbNGraphFrameInitializers initializers = this.optInitializers.get();
        
        taxonomy = (PAreaTaxonomy)applyFrameState(taxonomy, frameState);
        initialize(taxonomy, initializers.getPAreaTaxonomyInitializer(frameState));

        if (createHistoryEntry) {
            addDerivationHistoryEntry(taxonomy);
        }
    }
    

    public void displayAreaTaxonomy(PAreaTaxonomy taxonomy) {
        displayAreaTaxonomy(taxonomy, true);
    }

    public void displayAreaTaxonomy(PAreaTaxonomy taxonomy, boolean createHistoryEntry) {
        
        if(!this.optInitializers.isPresent()) {
            return;
        }
        
        AbNGraphFrameInitializers initializers = this.optInitializers.get();
        taxonomy = (PAreaTaxonomy)applyFrameState(taxonomy, frameState);
        initialize(taxonomy, initializers.getAreaTaxonomyInitializer(frameState));

        if (createHistoryEntry) {
            addDerivationHistoryEntry(taxonomy);
        }
    }
    
    public void displayDisjointPAreaTaxonomy(
            DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy) {

        displayDisjointPAreaTaxonomy(disjointTaxonomy, true);
    }

    public void displayDisjointPAreaTaxonomy(
            DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy, boolean createHistoryEntry) {
        
        if (!this.optInitializers.isPresent()) {
            return;
        }

        AbNGraphFrameInitializers initializers = this.optInitializers.get();
        
        disjointTaxonomy = disjointTaxonomy.getAggregated(frameState.getAggregateProperty());       
        initialize(disjointTaxonomy, initializers.getDisjointPAreaTaxonomyInitializer(frameState));

        if (createHistoryEntry) {
            addDerivationHistoryEntry(disjointTaxonomy);
        }
    }
    
    public void displayTAN(ClusterTribalAbstractionNetwork tan) {
        displayTAN(tan, true);
    }

    public void displayTAN(ClusterTribalAbstractionNetwork tan, boolean createHistoryEntry) {
        
        if (!this.optInitializers.isPresent()) {
            return;
        }

        AbNGraphFrameInitializers initializers = this.optInitializers.get();
        
        tan = (ClusterTribalAbstractionNetwork)applyFrameState(tan, frameState);
        initialize(tan, initializers.getTANInitializer(frameState));

        if (createHistoryEntry) {
            addDerivationHistoryEntry(tan);
        }
    }
    
    public void displayBandTAN(ClusterTribalAbstractionNetwork tan) {
        displayBandTAN(tan, true);
    }

    public void displayBandTAN(ClusterTribalAbstractionNetwork tan, boolean createHistoryEntry) {

        if (!this.optInitializers.isPresent()) {
            return;
        }

        AbNGraphFrameInitializers initializers = this.optInitializers.get();
        
        tan = (ClusterTribalAbstractionNetwork)applyFrameState(tan, frameState);
        initialize(tan, initializers.getBandTANInitializer(frameState));

        if (createHistoryEntry) {
            addDerivationHistoryEntry(tan);
        }
    }

    public void displayDisjointTAN(DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN) {
        displayDisjointTAN(disjointTAN, true);
    }

    public void displayDisjointTAN(
            DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN,
            boolean createHistoryEntry) {
        
        if (!this.optInitializers.isPresent()) {
            return;
        }

        AbNGraphFrameInitializers initializers = this.optInitializers.get();
        
        disjointTAN = (DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster>)applyFrameState(disjointTAN, frameState);
        initialize(disjointTAN, initializers.getDisjointTANInitializer(frameState));

        if (createHistoryEntry) {
            addDerivationHistoryEntry(disjointTAN);
        }
    }

    public void displayTargetAbstractionNetwork(TargetAbstractionNetwork targetAbN) {
        displayTargetAbstractionNetwork(targetAbN, true);
    }

    public void displayTargetAbstractionNetwork(TargetAbstractionNetwork targetAbN, boolean createHistoryEntry) {

        if (!this.optInitializers.isPresent()) {
            return;
        }

        AbNGraphFrameInitializers initializers = this.optInitializers.get();
        
        targetAbN = (TargetAbstractionNetwork)applyFrameState(targetAbN, frameState);
        initialize(targetAbN, initializers.getTargetAbNInitializer(frameState));

        if (createHistoryEntry) {
            addDerivationHistoryEntry(targetAbN);
        }
    }

    private AbstractionNetwork currentlyLoadingAbN = null;
    
    private void initialize(
            AbstractionNetwork abn,
            GraphFrameInitializer initializer) {
        
        this.currentlyLoadingAbN = abn;

        this.abnExplorationPanel.showLoading();

        Thread loadThread = new Thread(() -> {
            AbNPainter painter = initializer.getAbNPainter(abn);
            AbNConfiguration config = initializer.getConfiguration(abn, displayManager);

            AbstractionNetworkGraph graph = initializer.getGraph(parentFrame, config, initializer.getLabelCreator(abn));

            AbNExplorationPanelGUIInitializer explorationInitializer = initializer.getExplorationGUIInitializer(config);
            TaskBarPanel tbp = initializer.getTaskBar(this, config);

            // Only display if the AbN is the most recent AbN
            if (abn == this.currentlyLoadingAbN) {

                if (optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().clear();
                }

                this.optCurrentTaskBarPanel = Optional.of(tbp);

                this.historyNavigationPanel.refreshHistoryDisplay();

                displayAbstractionNetwork(
                        graph,
                        tbp,
                        painter,
                        config,
                        explorationInitializer);

                this.currentlyLoadingAbN = null;
                
            } else {
                
            }
        });

        loadThread.start();
    }

    private void displayAbstractionNetwork(
            AbstractionNetworkGraph graph,
            TaskBarPanel tbp,
            AbNPainter painter,
            AbNConfiguration gepConfiguration,
            AbNExplorationPanelGUIInitializer initializer) {

        SwingUtilities.invokeLater(() -> {
                        
            this.taskPanel.removeAll();
            this.taskPanel.add(tbp, BorderLayout.CENTER);

            this.taskPanel.revalidate();
            this.taskPanel.repaint();

            this.abnExplorationPanel.initialize(graph, gepConfiguration, painter, initializer);
        });
    }
}
