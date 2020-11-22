package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.NodeSummaryTextFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Den
 * @param <T>
 */
public class NodeSummaryPanel<T extends Node> extends BaseNodeInformationPanel<T> {

    private final JEditorPane nodeDetailsPane;
    
    private final NodeSummaryTextFactory<T> textFactory;
    
    public NodeSummaryPanel(NodeSummaryTextFactory<T> textFactory) {
        
        this.textFactory = textFactory;
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        nodeDetailsPane = new JEditorPane();
        nodeDetailsPane.setContentType("text/html");
        nodeDetailsPane.setEnabled(true);
        nodeDetailsPane.setEditable(false);
        nodeDetailsPane.setFont(nodeDetailsPane.getFont().deriveFont(Font.BOLD, 14));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(new JScrollPane(nodeDetailsPane), BorderLayout.CENTER);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Details"));
        detailsPanel.setMinimumSize(new Dimension(-1, 100));
        detailsPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.add(detailsPanel);
    }
    
    @Override
    public void setContents(T node) {
        nodeDetailsPane.setText(textFactory.createNodeSummaryText(node));
        
        nodeDetailsPane.setSelectionStart(0);
        nodeDetailsPane.setSelectionEnd(0);
    }
    
    @Override
    public void clearContents() {
        nodeDetailsPane.setText("");
    }
}
