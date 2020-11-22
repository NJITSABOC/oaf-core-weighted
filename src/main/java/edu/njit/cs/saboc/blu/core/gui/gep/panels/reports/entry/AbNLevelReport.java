package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AbNLevelReport {
    
    private final int level;
    
    private final Set<Concept> conceptsAtLevel;
    
    private final Set<Concept> overlappingConceptsAtLevel;
    
    private final Set<SinglyRootedNode> groupsAtLevel;
    
    private final Set<PartitionedNode> containersAtLevel;

    public AbNLevelReport(int level, 
            Set<Concept> conceptsAtLevel, 
            Set<Concept> overlappingConceptsAtLevel, 
            Set<SinglyRootedNode> groupsAtLevel, 
            Set<PartitionedNode> containersAtLevel) {
        
        this.level = level;
        
        this.conceptsAtLevel = conceptsAtLevel;
        
        this.overlappingConceptsAtLevel = overlappingConceptsAtLevel;
        
        this.groupsAtLevel = groupsAtLevel;
        
        this.containersAtLevel = containersAtLevel;
    }
    
    public int getLevel() {
        return level;
    }

    public Set<Concept> getConceptsAtLevel() {
        return conceptsAtLevel;
    }

    public Set<Concept> getOverlappingConceptsAtLevel() {
        return overlappingConceptsAtLevel;
    }

    public Set<SinglyRootedNode> getGroupsAtLevel() {
        return groupsAtLevel;
    }

    public Set<PartitionedNode> getContainersAtLevel() {
        return containersAtLevel;
    }
}
