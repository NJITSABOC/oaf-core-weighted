package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.diff;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.diff.DiffNodeStatusReport;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuGenerator;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author Chris Ochs
 */
public class DiffNodeStatusReportRightClickGenerator extends EntityRightClickMenuGenerator<DiffNodeStatusReport> {
    
    public DiffNodeStatusReportRightClickGenerator(AbNConfiguration config) {
        
    }

    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(DiffNodeStatusReport item) {
        
        ArrayList<JComponent> menu = new ArrayList<>();
        
        switch(item.getStatus()) {
            
            case MovedExactly:
            case MovedSubset:
            case MovedSuperset:
            case MovedDifference:
            case IntroducedFromOneNode:
            case IntroducedFromMultipleNodes:
            case RemovedToOneNode:
            case RemovedToMultipleNodes:
                
            case IntroducedFromNew:
            case RemovedFromOnt:
        }
        
        return menu;
    }

    @Override
    public ArrayList<JComponent> buildEmptyListRightClickMenu() {
        return new ArrayList<>();
    }
}
