package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.ConceptChange;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.ConceptParentChange;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange.ChangeInheritanceType;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.ChangeExplanationRowEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel;

/**
 *
 * @author Chris O
 */
public class HierarchicalChangeExplanationFactory implements DiffNodeRootChangeExplanationModel.ChangeExplanationRowEntryFactory {
    
    private final AbNConfiguration config;
    
    public HierarchicalChangeExplanationFactory(AbNConfiguration config) {
        this.config = config;
    }

    @Override
    public ChangeExplanationRowEntry getChangeEntry(DiffAbNConceptChange item) {
        final String conceptTypeName = config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false).toLowerCase();
        final String parentTypeName = config.getTextConfiguration().getOntologyEntityNameConfiguration().getParentConceptTypeName(false).toLowerCase();
        
        ChangeInheritanceType inheritance = item.getChangeInheritanceType();
       
        String inheritanceTypeStr;
        
        if(inheritance == DiffAbNConceptChange.ChangeInheritanceType.Direct) {
            inheritanceTypeStr = ChangeExplanationRowEntry.DIRECT;
        } else {
            inheritanceTypeStr = ChangeExplanationRowEntry.INDIRECT;
        }
        
        String changeTypeStr = "[UNKNOWN CHANGE TYPE]";
        String changeSummaryStr = "[NO SUMMARY SET]";
        String changeDescriptionStr = "";
        
        if (item instanceof ConceptChange) {
            ConceptChange conceptChange = (ConceptChange)item;
            
            changeTypeStr = String.format("%s set modified", conceptTypeName);
            
            if(conceptChange.getChangeType() == ConceptChange.ConceptAddedRemovedChangeType.AddedToOnt) {
                changeSummaryStr = String.format("%s added to ontology", item.getAffectedConcept().getName());
            } else if (conceptChange.getChangeType() == ConceptChange.ConceptAddedRemovedChangeType.RemovedFromOnt) {
                changeSummaryStr = String.format("%s removed from ontology", item.getAffectedConcept().getName());
            } else if(conceptChange.getChangeType() == ConceptChange.ConceptAddedRemovedChangeType.AddedToSubhierarchy) {
                changeSummaryStr = String.format("%s added to current subhierarchy", item.getAffectedConcept().getName());
            } else if(conceptChange.getChangeType() == ConceptChange.ConceptAddedRemovedChangeType.RemovedFromSubhierarchy) {
                changeSummaryStr = String.format("%s removed from current subhierarchy (still in ontology)", item.getAffectedConcept().getName());
            }

        } else if (item instanceof ConceptParentChange) {
            ConceptParentChange conceptParentChange = (ConceptParentChange)item;
            
            changeTypeStr = String.format("%s modified", parentTypeName);
            
            if(conceptParentChange.getParentState() == ConceptParentChange.ParentState.Added) {
                changeSummaryStr = String.format("%s added as %s", 
                        conceptParentChange.getParent().getName(), 
                        parentTypeName);
            } else {
                changeSummaryStr = String.format("%s removed as %s", 
                        conceptParentChange.getParent().getName(), 
                        parentTypeName);
            }
        }
        
        return new ChangeExplanationRowEntry(changeTypeStr, inheritanceTypeStr, changeSummaryStr, changeDescriptionStr);
    }
}
