package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.AbNChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptAddedToNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedFromNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedToNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptRemovedFromNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author Chris Ochs
 */
public class DiffNodeChangesRightClickMenuGenerator extends EntityRightClickMenuGenerator<AbNChange> {
    
    private final AbNConfiguration config;
    private final AbNTextConfiguration textConfig;
    
    public DiffNodeChangesRightClickMenuGenerator(
            AbNConfiguration config,
            AbNTextConfiguration textConfig) {
        
        this.config = config;
        this.textConfig = textConfig;
    }

    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(AbNChange item) {
        
        ArrayList<JComponent> menu = new ArrayList<>();
        
        if(item instanceof ConceptMovedFromNode) {
            
            ConceptMovedFromNode conceptMovedFromNode = (ConceptMovedFromNode)item;
            
            Optional<AbNNodeEntry> optEntry = getNodeEntryFor(conceptMovedFromNode.getMovedFrom());
            
            if(optEntry.isPresent()) {
                JMenu descItem = new JMenu(String.format("Display Previous %s", 
                    textConfig.getNodeTypeName(false)));
                
                JMenuItem menuItem = new JMenuItem(optEntry.get().getNode().getName());
                
                menuItem.addActionListener( (ae) -> {
                    displayNodeEntry(optEntry.get());
                });
                
                descItem.add(menuItem);
                
                menu.add(descItem);
            } else {
                menu.add(new JLabel(String.format("Previous %s currently hidden...", 
                    textConfig.getNodeTypeName(false).toLowerCase())));
            }

        } else if(item instanceof ConceptMovedToNode) {
            
            ConceptMovedToNode conceptMovedToNode = (ConceptMovedToNode)item;
            
            Optional<AbNNodeEntry> optEntry = getNodeEntryFor(conceptMovedToNode.getMovedTo());

            if (optEntry.isPresent()) {
                JMenu descItem = new JMenu(String.format("Display Current %s",
                        textConfig.getNodeTypeName(false)));

                JMenuItem menuItem = new JMenuItem(optEntry.get().getNode().getName());
                
                menuItem.addActionListener((ae) -> {
                    displayNodeEntry(optEntry.get());
                });
                
                descItem.add(menuItem);

                menu.add(descItem);
            } else {
                menu.add(new JLabel(String.format("Current %s currently hidden...",
                        textConfig.getNodeTypeName(false).toLowerCase())));
            }

        } else if(item instanceof ConceptAddedToNode) {
            
            ConceptAddedToNode conceptAddedToNode = (ConceptAddedToNode)item;
            
            menu.addAll(buildOtherNodeMenu(conceptAddedToNode.getOtherNodes()));
            
        } else if(item instanceof ConceptRemovedFromNode) {
            
            ConceptRemovedFromNode conceptRemovedFromNode = (ConceptRemovedFromNode)item;
            
            menu.addAll(buildOtherNodeMenu(conceptRemovedFromNode.getOtherNodes()));
        }
        
        return menu;
    }
    
    private ArrayList<JComponent> buildOtherNodeMenu(Set<Node> otherNodes) {
        
        ArrayList<JComponent> menu = new ArrayList<>();
        
        ArrayList<AbNNodeEntry> otherNodeEntries = new ArrayList<>();

        otherNodes.forEach( (node) -> {
            Optional<AbNNodeEntry> optEntry = this.getNodeEntryFor(node);

            if (optEntry.isPresent()) {
                otherNodeEntries.add(optEntry.get());
            }
        });

        if (!otherNodeEntries.isEmpty()) {
            
            otherNodeEntries.sort((a, b) -> {
                return a.getNode().getName().compareToIgnoreCase(b.getNode().getName());
            });

            JMenu descItem = new JMenu(String.format("View Other %s with %s",
                    textConfig.getNodeTypeName(false),
                    textConfig.getOntologyEntityNameConfiguration().getConceptTypeName(false)));
            
            otherNodeEntries.forEach( (entry) -> {
                JMenuItem item = new JMenuItem(entry.getNode().getName());
                
                item.addActionListener( (ae) -> {
                    displayNodeEntry(entry);
                });
                
                descItem.add(item);
            });
            
            menu.add(descItem);

        } else {
            menu.add(new JLabel(String.format("Other %s are currently hidden...",
                    textConfig.getNodeTypeName(true).toLowerCase())));
        }
        
        return menu;
    }

    @Override
    public ArrayList<JComponent> buildEmptyListRightClickMenu() {
        return new ArrayList<>();
    }
        
    private Optional<AbNNodeEntry> getNodeEntryFor(Node node) {
        AbNNodeEntry nodeEntry = null;
        
        if(node instanceof SinglyRootedNode) {
            nodeEntry = config.getUIConfiguration().getDisplayPanel().getGraph().getNodeEntries().get((SinglyRootedNode)node);
        } else if(node instanceof PartitionedNode) {
            nodeEntry = config.getUIConfiguration().getDisplayPanel().getGraph().getContainerEntries().get((PartitionedNode)node);
        }
        
        return Optional.of(nodeEntry);
    }
    
    private void displayNodeEntry(AbNNodeEntry entry) {

        if(entry.getNode() instanceof SinglyRootedNode) {
            config.getUIConfiguration().getDisplayPanel().highlightSinglyRootedNodes(Collections.singleton((SinglyRootedNode)entry.getNode()));
        } else if(entry.getNode() instanceof PartitionedNode) {
            config.getUIConfiguration().getDisplayPanel().highlightPartitionedNodes(Collections.singleton((PartitionedNode)entry.getNode()));
        }
        
        config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(entry);
    }
}
