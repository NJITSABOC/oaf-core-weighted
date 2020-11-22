package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.taskbarpanels;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.BaseAbNSearchButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.PartitionedAbNSearchButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;

/**
 *
 * @author Chris O
 */
public class PartitionedAbNTaskBarPanel extends TaskBarPanel {

    public PartitionedAbNTaskBarPanel(
            MultiAbNGraphFrame graphFrame,
            PartitionedAbNConfiguration config) {

        super(graphFrame, config);
    }

    @Override
    protected BaseAbNSearchButton getAbNSearchButton(AbNConfiguration config) {
        
        PartitionedAbNConfiguration partitionedConfig = (PartitionedAbNConfiguration)config;
        
        PartitionedAbNSearchButton btn = new PartitionedAbNSearchButton(
                super.getGraphFrame().getParentFrame(), partitionedConfig);

        return btn;
    }

    @Override
    protected String getAbNMetricsLabel(AbNConfiguration config) {
        PartitionedAbNConfiguration partitionedConfig = (PartitionedAbNConfiguration)config;
        
        int partitionNodeCount = partitionedConfig.getAbstractionNetwork().getBaseAbstractionNetwork().getNodeCount();
        int singlyRootedNodeCount = partitionedConfig.getAbstractionNetwork().getNodeCount();
        int conceptCount = partitionedConfig.getAbstractionNetwork().getSourceHierarchy().size();
        
        return String.format("<html><font size='4'>%s: <font color='RED'><b>%d</b></font> "
                + "| %s: <font color='RED'><b>%d</b></font> "
                + "| %s: <font color='RED'><b>%d</b></font>",
                
                partitionedConfig.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(true),
                partitionNodeCount, 
                partitionedConfig.getTextConfiguration().getNodeTypeName(true),
                singlyRootedNodeCount, 
                partitionedConfig.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true),
                conceptCount);
    }
}
