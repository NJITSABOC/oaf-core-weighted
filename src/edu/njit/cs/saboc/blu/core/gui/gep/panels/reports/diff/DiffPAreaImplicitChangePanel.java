package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.diff;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff.DiffNodeList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNReportPanel;
import java.awt.BorderLayout;
import java.util.Collections;
import java.util.Optional;

/**
 *
 * @author Chris Ochs
 */
public class DiffPAreaImplicitChangePanel extends AbNReportPanel<DiffPAreaTaxonomy> {
    
    private final DiffNodeList<DiffPArea> diffNodeList;
    
    public DiffPAreaImplicitChangePanel(DiffPAreaTaxonomyConfiguration config) {
        super(config);
        
        this.diffNodeList = new DiffNodeList(config);
        this.diffNodeList.addEntitySelectionListener(new EntitySelectionAdapter<DiffPArea>() {
            
            @Override
            public void entityDoubleClicked(DiffPArea entity) {
                
                Optional<AbNNodeEntry> optEntry = getEntryFor(entity);
                
                if(optEntry.isPresent()) {
                    displayEntry(optEntry.get());
                }
            }
            
        });
        
        this.setLayout(new BorderLayout());
        
        this.add(diffNodeList, BorderLayout.CENTER);
    }

    @Override
    public void displayAbNReport(DiffPAreaTaxonomy abn) {
        DiffPAreaImplicitChangeReport report = new DiffPAreaImplicitChangeReport(abn);       
        
        diffNodeList.setContents(report.getPAreasWithOnlyImplicitChanges());
    }
    

    private Optional<AbNNodeEntry> getEntryFor(SinglyRootedNode node) {
        return Optional.of(super.getConfiguration().getUIConfiguration().
                getDisplayPanel().getGraph().getNodeEntries().get(node));
    }
    
    private void displayEntry(AbNNodeEntry entry) {
        AbNConfiguration config = super.getConfiguration();
        
        config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(entry);
        
        config.getUIConfiguration().getDisplayPanel().highlightSinglyRootedNodes(
                Collections.singleton((SinglyRootedNode)entry.getNode()));
    } 
    
}
