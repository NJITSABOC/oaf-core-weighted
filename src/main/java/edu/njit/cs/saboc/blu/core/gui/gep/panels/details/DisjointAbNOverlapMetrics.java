package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DisjointAbNOverlapMetrics {

    private final HashMap<Node, NodeOverlapMetrics> groupMetrics = new HashMap<>();

    public DisjointAbNOverlapMetrics(DisjointAbstractionNetwork disjointAbN) {
        Set<Node> overlappingGroups = disjointAbN.getOverlappingNodes();

        Set<DisjointNode> disjointGroups = disjointAbN.getAllDisjointNodes();

        overlappingGroups.forEach( (group) -> {
            DisjointNode disjointBasis = null;

            HashMap<Set<Node>, Set<DisjointNode>> intersectionGroups = new HashMap<>();

            for (DisjointNode disjointGroup : disjointGroups) {
                if (disjointGroup.getOverlaps().contains(group)) {
                    
                    if (disjointGroup.getOverlaps().size() == 1) {
                        disjointBasis = disjointGroup;
                    } else {
                        Set<Node> overlaps = disjointGroup.getOverlaps();

                        if (!intersectionGroups.containsKey(overlaps)) {
                            intersectionGroups.put(overlaps, new HashSet<>());
                        }

                        intersectionGroups.get(overlaps).add(disjointGroup);
                    }
                }
            }

            groupMetrics.put(group, new NodeOverlapMetrics(group, disjointBasis, intersectionGroups));
        });
    }

    public HashMap<Node, NodeOverlapMetrics> getGroupMetrics() {
        return groupMetrics;
    }
}
