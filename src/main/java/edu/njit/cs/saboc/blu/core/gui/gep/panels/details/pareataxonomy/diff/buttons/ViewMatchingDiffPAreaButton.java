package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.buttons;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
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
public class ViewMatchingDiffPAreaButton extends NodeOptionButton<PArea> {
    
    private final DiffPAreaTaxonomyConfiguration config;
    
    public ViewMatchingDiffPAreaButton(DiffPAreaTaxonomyConfiguration config) {
        
        super("BluViewMatchingNode.png", "View matching diff partial-area");
        
        this.config = config;
        
        this.addActionListener( (ae) -> {
            
            DiffPArea diffPArea = (DiffPArea)this.getCurrentEntity().get();
            
            Optional<DiffPArea> matchedDiffPArea = getMatchingDiffPArea(diffPArea);
            
            if(matchedDiffPArea.isPresent()) {
                
                Optional<AbNNodeEntry> matchingEntry = getDiffPAreaEntry(matchedDiffPArea.get());
                
                if(matchingEntry.isPresent()) {
                    config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(matchingEntry.get());
                    
                    config.getUIConfiguration().getDisplayPanel().highlightSinglyRootedNodes(
                            Collections.singleton(matchedDiffPArea.get()));
                }
            }
        });
    }

    @Override
    public void setEnabledFor(PArea parea) {
        DiffPArea diffPArea = (DiffPArea)parea;
        
        Optional<DiffPArea> optMatchingDiffPArea = this.getMatchingDiffPArea(diffPArea);
        
        if(optMatchingDiffPArea.isPresent()) {
            Optional<AbNNodeEntry> optMatchingEntry = this.getDiffPAreaEntry(diffPArea);
            
            if(optMatchingEntry.isPresent()) {
                this.setEnabled(true);
                
                return;
            }
        }
        
        this.setEnabled(false);
    }
    
    private Optional<DiffPArea> getMatchingDiffPArea(DiffPArea diffPArea) {
        
        DiffPAreaTaxonomy diffTaxonomy = config.getPAreaTaxonomy();
        
        ChangeState oppositeState;
        
        if(diffPArea.getPAreaState() == ChangeState.Introduced) {
            oppositeState = ChangeState.Removed;
        } else if(diffPArea.getPAreaState() == ChangeState.Removed) {
            oppositeState = ChangeState.Introduced;
        } else {
            return Optional.empty();
        }
        
        Optional<DiffPArea> matchedPArea = diffTaxonomy.getPAreas().stream().filter( (otherPArea) -> {
            return otherPArea.getPAreaState() == oppositeState && 
                    diffPArea.getRoot().equals(otherPArea.getRoot());
        }).findFirst();
        
        return matchedPArea;
    }

    private Optional<AbNNodeEntry> getDiffPAreaEntry(DiffPArea diffPArea) {
        AbNDisplayPanel displayPanel = config.getUIConfiguration().getDisplayPanel();

        return Optional.of(displayPanel.getGraph().getNodeEntries().get(diffPArea));
    }
}