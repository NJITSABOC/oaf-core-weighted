package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public abstract class TargetAbNTextConfiguration extends AbNTextConfiguration<TargetGroup> {
    
    private final TargetAbstractionNetwork<?> targetAbN;
    
    public TargetAbNTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig, 
            TargetAbstractionNetwork<?> taxonomy) {
        
        super(ontologyEntityNameConfig);
        
        this.targetAbN = taxonomy;
    }
    
    public TargetAbstractionNetwork getTargetAbN() {
        return targetAbN;
    }

    @Override
    public String getAbNName() {
        return targetAbN.getDerivation().getName();
    }

    @Override
    public String getAbNSummary() {
        
        String desc = "<html>The <b><abnName></b> target abstraction network summarizes the "
                + "<selectedPropertyName> <propertyTypeName count=2> between the "
                + "<sourceHierarchyName> hierarchy and <targetHierarchyName> hierarchy."
                + "<p>"
                + "A total of <font color = 'RED'><targetConceptCount></font> target <conceptTypeName count=<targetConceptCount>> "
                + "are summarized by <font color = 'RED'><targetGroupCount></font> <nodeTypeName count=<targetGroupCount>>."
                + "<p>"
                + "A total of <font color = 'RED'><sourceConceptCount></font> source <conceptTypeName count=<sourceConceptCount>> "
                + "have <propertyTypeName count=2> to these targets.";
        
        Set<Concept> sourceConcepts = new HashSet<>();
        
        Set<Concept> targetConcepts = new HashSet<>();
        
        targetAbN.getTargetGroups().forEach( (targetGroup) -> {
            targetConcepts.addAll(targetGroup.getIncomingRelationshipTargets());
            sourceConcepts.addAll(targetGroup.getIncomingRelationshipSources());
        });
        
        desc = desc.replaceAll("<abnName>", this.getAbNName());
        desc = desc.replaceAll("<selectedPropertyName>", targetAbN.getDerivation().getPropertyType().getName());
        desc = desc.replaceAll("<sourceHierarchyName>", targetAbN.getDerivation().getSourceHierarchyRoot().getName());
        desc = desc.replaceAll("<targetHierarchyName>", targetAbN.getDerivation().getTargetHierarchyRoot().getName());
        
        desc = desc.replaceAll("<targetGroupCount>", Integer.toString(targetAbN.getNodes().size()));
        desc = desc.replaceAll("<targetConceptCount>", Integer.toString(targetConcepts.size()));
        desc = desc.replaceAll("<sourceConceptCount>", Integer.toString(sourceConcepts.size()));
        
        AbNTextFormatter formatter = new AbNTextFormatter(this);
        
        return formatter.format(desc);
    }
    
    @Override
    public String getAbNHelpDescription() {
        
        String desc = "A <b><abnTypeName></b> is an an abstraction network that "
                + "summarizes the hierarchy of target <conceptTypeName count=2>"
                + " for a selected <propertyTypeName>. Subhierarchies of target <conceptTypeName count=2> "
                + "are summarized as <b>target groups</b>, which are named after the lowest common ancestor <conceptTypeName> "
                + "that is not the target of any <propertyTypeName>.";
        
        desc = desc.replaceAll("<abnTypeName>", this.getAbNTypeName(false));
         
        AbNTextFormatter formatter = new AbNTextFormatter(this);
        
        return formatter.format(desc);
    }

    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Target Abstraction Networks";
        } else {
            return "Target Abstraction Network";
        }
    }

    @Override
    public String getNodeTypeName(boolean plural) {
        if(plural) {
            return "Target Groups";
        } else {
            return "Target Group";
        }
    }

    @Override
    public String getNodeHelpDescription(TargetGroup node) {
        
        String desc = ""
                + "<html>A <b>target group</b> summarizes a subhierarchy of <conceptTypeName count=2> "
                + "that are targets of one or more <propertyTypeName count=2>. The target group is named after "
                + "the lowest common ancestor <conceptTypeName> that is not a target but has descendents that are targets.";
        
         AbNTextFormatter formatter = new AbNTextFormatter(this);
        
        return formatter.format(desc);
    }
}
