package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNTextConfiguration;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Atsuki Imamura on 2/12/2017.
 * 
 * @param <T>
 */
public class NodeHelpButton<T extends Node> extends NodeOptionButton<T> {

    public NodeHelpButton(AbNConfiguration config){
        super("BluHelp.png", "Help");
        
        this.addActionListener((ae) -> {
            SwingUtilities.invokeLater(() -> {
                displayDetailsWindow(config);
            });
        });
    }

    private void displayDetailsWindow(AbNConfiguration config) {
        String nodeTypeName, nodeHelpDescription;
        Node curNode = getCurrentNode().get();
        
        if (curNode instanceof SinglyRootedNode) {
            nodeTypeName = config.getTextConfiguration().getNodeTypeName(false);
            nodeHelpDescription = config.getTextConfiguration().getNodeHelpDescription(curNode);
        } else {
            nodeTypeName = ((PartitionedAbNTextConfiguration) config.getTextConfiguration()).
                    getBaseAbNTextConfiguration().getNodeTypeName(false);
            
            nodeHelpDescription = ((PartitionedAbNTextConfiguration) config.getTextConfiguration()).
                    getBaseAbNTextConfiguration().getNodeHelpDescription(curNode);
        }

        JDialog detailsDialog = new JDialog();
        
        detailsDialog.setTitle(String.format("(%s) Help / Description",nodeTypeName));
        
        detailsDialog.setSize(400, 400);
        
        detailsDialog.setLocationRelativeTo(null);  //sets the location to the center

        detailsDialog.setResizable(true);
        detailsDialog.setAlwaysOnTop(true);

        detailsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JEditorPane nodeDetailsPane = new JEditorPane();
        nodeDetailsPane.setContentType("text/html");
        nodeDetailsPane.setEnabled(true);
        nodeDetailsPane.setEditable(false);
        nodeDetailsPane.setFont(nodeDetailsPane.getFont().deriveFont(Font.BOLD, 14));
        nodeDetailsPane.setText(nodeHelpDescription);

        detailsDialog.add(new JScrollPane(nodeDetailsPane));
        
        
        detailsDialog.setVisible(true);
    }

    @Override
    public void setEnabledFor(T entity) {
        this.setEnabled(true);
    }
}
