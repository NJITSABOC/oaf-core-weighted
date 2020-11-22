package edu.njit.cs.saboc.blu.core.gui.dialogs;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Chris
 */
public class NodeEditMenu extends JPopupMenu {
    
    private final AbstractionNetworkGraph<?> graph;
    
    private SinglyRootedNode currentNode;

    public NodeEditMenu(AbstractionNetworkGraph<?> graph) {
        this.graph = graph;
        
        this.setFocusable(true);
        this.addMouseListener(new MenuItemListener());
        
        createEdgeMenu();
    }
    
    public void setCurrentGroup(SinglyRootedNode group) {
        this.currentNode = group;
    }
    
    private final void createEdgeMenu() {
        JMenuItem drawParEdgesMenu = new JMenuItem("Draw All Parent Edges");

        drawParEdgesMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent me) {
                
                Set<SinglyRootedNode> currentNodeParents = graph.getAbstractionNetwork().getNodeHierarchy().getParents(currentNode);
                SinglyRootedNodeEntry currentNodeEntry = graph.getNodeEntries().get(currentNode);
                
                
                currentNodeParents.forEach((parentNode) -> {
                    SinglyRootedNodeEntry parentEntry = graph.getNodeEntries().get(parentNode);

                    if (!graph.edgeAlreadyDrawn(currentNodeEntry, parentEntry)
                            && parentEntry.getGroupLevelParent().getParentPartition().isVisible()) {

                        graph.drawRoutedEdge(currentNodeEntry, parentEntry);
                    }
                });

                requestFocusInWindow();
            }
        });

        drawParEdgesMenu.addMouseListener(new MenuItemListener());
        this.add(drawParEdgesMenu);

        JMenuItem drawChdEdgesMenu = new JMenuItem("Draw All Child Edges");
        drawChdEdgesMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent me) {
    
                Set<SinglyRootedNode> currentNodeChildren = graph.getAbstractionNetwork().getNodeHierarchy().getChildren(currentNode);
                
                SinglyRootedNodeEntry currentNodeEntry = graph.getNodeEntries().get(currentNode);

                for (SinglyRootedNode child : currentNodeChildren) {
                    SinglyRootedNodeEntry childNodeEntry = graph.getNodeEntries().get(child);
                    
                    if (!graph.edgeAlreadyDrawn(childNodeEntry, currentNodeEntry)
                            && childNodeEntry.getGroupLevelParent().getParentPartition().isVisible()) {

                        graph.drawRoutedEdge(childNodeEntry, currentNodeEntry);
                    }
                }


                requestFocusInWindow();
            }
        });

        drawChdEdgesMenu.addMouseListener(new MenuItemListener());
        this.add(drawChdEdgesMenu);

        JMenuItem deleteEdgesMenu = new JMenuItem("Delete All Parent Edges");
        deleteEdgesMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                ArrayList<GraphEdge> removeEdges = new ArrayList<>();
                ArrayList<GraphEdge> edges = graph.getEdges();

                for (GraphEdge temp : edges) {
                    if (temp.getSource().equals(graph.getNodeEntries().get((SinglyRootedNode)currentNode))) {
                        removeEdges.add(temp);
                    }
                }

                edges.removeAll(removeEdges);
                graph.removeEdges(removeEdges);
                graph.redrawEdges();
                
                requestFocusInWindow();
            }
        });

        deleteEdgesMenu.addMouseListener(new MenuItemListener());
        this.add(deleteEdgesMenu);

        JMenuItem deleteIncomingEdges = new JMenuItem("Delete All Child Edges");
        deleteIncomingEdges.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                ArrayList<GraphEdge> removeEdges = new ArrayList<>();

                ArrayList<GraphEdge> edges = graph.getEdges();

                for (GraphEdge temp : edges) {
                    if (temp.getTarget().equals(graph.getNodeEntries().get((SinglyRootedNode)currentNode))) {
                        removeEdges.add(temp);
                    }
                }

                edges.removeAll(removeEdges);
                graph.removeEdges(removeEdges);
                graph.redrawEdges();
                requestFocusInWindow();
            }
        });

        deleteIncomingEdges.addMouseListener(new MenuItemListener());
        this.add(deleteIncomingEdges);
    }
    
    
    
    class MenuItemListener implements MouseListener {

        public void mouseEntered(MouseEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                ((JMenuItem) e.getSource()).setBackground(Color.lightGray);
            }
        }

        public void mouseExited(MouseEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                ((JMenuItem) (e.getSource())).setBackground(new Color(238, 238, 238));
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                ((JMenuItem) (e.getSource())).setBackground(new Color(238, 238, 238));
            }
        }

        public void mousePressed(MouseEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                ((JMenuItem) (e.getSource())).setBackground(new Color(138, 138, 138));
            }
        }

        public void mouseClicked(MouseEvent e) {
            NodeEditMenu.this.setVisible(false);
        }
    }
}
