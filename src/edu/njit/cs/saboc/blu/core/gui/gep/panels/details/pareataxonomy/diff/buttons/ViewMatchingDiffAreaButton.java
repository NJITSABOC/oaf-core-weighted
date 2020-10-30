package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.buttons;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import java.util.Collections;
import java.util.Optional;

/**
 *
 * @author Chris Ochs
 */
public class ViewMatchingDiffAreaButton extends NodeOptionButton<Area> {
    
    private final DiffPAreaTaxonomyConfiguration config;
    
    public ViewMatchingDiffAreaButton(DiffPAreaTaxonomyConfiguration config) {
        
        super("BluViewMatchingNode.png", "View matching diff area");
        
        this.config = config;
        
        this.addActionListener( (ae) -> {
            
            DiffArea diffArea = (DiffArea)this.getCurrentEntity().get();
            
            Optional<DiffArea> matchedDiffPArea = getMatchingDiffArea(diffArea);
            
            if(matchedDiffPArea.isPresent()) {
                
                Optional<AbNNodeEntry> matchingEntry = getDiffAreaEntry(matchedDiffPArea.get());
                
                if(matchingEntry.isPresent()) {
                    config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(matchingEntry.get());
                    
                    config.getUIConfiguration().getDisplayPanel().highlightPartitionedNodes(
                             Collections.singleton(matchedDiffPArea.get()));
                }
            }
        });
    }

    @Override
    public void setEnabledFor(Area area) {
        DiffArea diffArea = (DiffArea)area;
        
        Optional<DiffArea> optMatchingDiffPArea = this.getMatchingDiffArea(diffArea);
        
        if(optMatchingDiffPArea.isPresent()) {
            Optional<AbNNodeEntry> optMatchingEntry = this.getDiffAreaEntry(diffArea);
            
            if(optMatchingEntry.isPresent()) {
                this.setEnabled(true);
                
                return;
            }
        }
        
        this.setEnabled(false);
    }
    
    private Optional<DiffArea> getMatchingDiffArea(DiffArea diffArea) {
        
        DiffPAreaTaxonomy diffTaxonomy = config.getPAreaTaxonomy();
        
        ChangeState oppositeState;
        
        if(diffArea.getAreaState() == ChangeState.Introduced) {
            oppositeState = ChangeState.Removed;
        } else if(diffArea.getAreaState() == ChangeState.Removed) {
            oppositeState = ChangeState.Introduced;
        } else {
            return Optional.empty();
        }
        
        Optional<Area> matchedArea = diffTaxonomy.getAreas().stream().filter( (otherArea) -> {
            
            DiffArea otherDiffArea = (DiffArea)otherArea;
            
            return otherDiffArea.getAreaState() == oppositeState && 
                    diffArea.getConcepts().equals(otherArea.getConcepts());
            
        }).findFirst();
        
        if(matchedArea.isPresent()) {
            return Optional.of((DiffArea)matchedArea.get());
        } else {
            return Optional.empty();
        }
    }

    private Optional<AbNNodeEntry> getDiffAreaEntry(DiffArea diffArea) {
        AbNDisplayPanel displayPanel = config.getUIConfiguration().getDisplayPanel();

        return Optional.of(displayPanel.getGraph().getContainerEntries().get(diffArea));
    }
}