package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.BaseNodeInformationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import java.awt.BorderLayout;
import java.util.ArrayList;

/**
 *
 * @author cro3
 */
public class SourceConceptListPanel extends BaseNodeInformationPanel<TargetGroup> {
    
    private final SourceConceptList sourceConceptList;
    
    public SourceConceptListPanel(TargetAbNConfiguration config) {
        this.sourceConceptList = new SourceConceptList(config);
        
        this.setLayout(new BorderLayout());
        
        this.add(sourceConceptList, BorderLayout.CENTER);
    }

    @Override
    public void setContents(TargetGroup node) {
        sourceConceptList.setCurrentNode(node);
        
        ArrayList<Concept> sortedSourceConcepts = new ArrayList(node.getIncomingRelationshipSources());
        sortedSourceConcepts.sort(new ConceptNameComparator());
        
        sourceConceptList.setContents(sortedSourceConcepts);
    }

    @Override
    public void clearContents() {
        sourceConceptList.clearContents();
    }
}
