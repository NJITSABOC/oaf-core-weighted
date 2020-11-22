package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeConceptListModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuGenerator;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author Chris Ochs
 */
public class DiffNodeConceptRightClickMenuGenerator extends EntityRightClickMenuGenerator<Concept> {
    
    private final DiffNodeChangesRightClickMenuGenerator changesGenerator;
    
    private final DiffNodeConceptListModel listModel;

    public DiffNodeConceptRightClickMenuGenerator(
            AbNConfiguration config,
            AbNTextConfiguration textConfig, 
            DiffNodeConceptListModel listModel) {
        
        this.changesGenerator = new DiffNodeChangesRightClickMenuGenerator(config, textConfig);
        this.listModel = listModel;
    }

    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(Concept item) {
        return this.changesGenerator.buildRightClickMenuFor(listModel.getChangeForConcept(item));
    }

    @Override
    public ArrayList<JComponent> buildEmptyListRightClickMenu() {
        return this.changesGenerator.buildEmptyListRightClickMenu();
    }
}
