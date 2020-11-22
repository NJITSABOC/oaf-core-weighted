package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class NodeOverlapMetrics {

    private final HashMap<Set<Node>, Set<DisjointNode>> intersectionGroups;

    private final Set<Concept> overlappingConcepts = new HashSet<>();

    private final Node overlappingGroup;

    private final DisjointNode disjointBasis;

    public NodeOverlapMetrics(
            Node overlappingGroup,
            DisjointNode disjointBasis,
            HashMap<Set<Node>, Set<DisjointNode>> intersectionGroups) {

        this.intersectionGroups = intersectionGroups;
        this.overlappingGroup = overlappingGroup;
        this.disjointBasis = disjointBasis;

        Set<DisjointNode> disjointGroups = getAllIntersectionNodes();

        disjointGroups.forEach((group) -> {
            overlappingConcepts.addAll(group.getConcepts());
        });
    }

    public Set<Concept> getOverlappingConcepts() {
        return overlappingConcepts;
    }

    public Set<Set<Node>> getIntersections() {
        return intersectionGroups.keySet();
    }

    public Collection<Set<DisjointNode>> intersectionDisjointGroups() {
        return intersectionGroups.values();
    }

    public Node getOverlappingGroup() {
        return overlappingGroup;
    }

    public DisjointNode getBasisDisjointGroup() {
        return disjointBasis;
    }

    public final Set<DisjointNode> getAllIntersectionNodes() {

        Set<DisjointNode> disjointGroups = new HashSet<>();

        intersectionGroups.values().forEach((groups) -> {
            disjointGroups.addAll(groups);
        });

        return disjointGroups;
    }

    public Set<DisjointNode> groupsOverlapWith(Node group) {
        Set<DisjointNode> groups = new HashSet<>();

        for (Set<Node> overlaps : intersectionGroups.keySet()) {
            if (overlaps.contains(group)) {
                groups.addAll(intersectionGroups.get(overlaps));
            }
        }

        return groups;
    }
}
