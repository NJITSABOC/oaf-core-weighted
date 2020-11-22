package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeCombinationsEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Chris O
 */
public class OverlappingNodeCombinationsComparator implements Comparator<OverlappingNodeCombinationsEntry> {

    @Override
    public int compare(OverlappingNodeCombinationsEntry a, OverlappingNodeCombinationsEntry b) {
        if (a.getOtherOverlappingNodes().size() == b.getOtherOverlappingNodes().size()) {

            // Sort by name of overlaps
            if (a.getOverlappingConcepts().size() == b.getOverlappingConcepts().size()) {
                ArrayList<String> aOverlappingNames = new ArrayList<>();

                a.getOtherOverlappingNodes().forEach((overlappingGroup) -> {
                    aOverlappingNames.add(overlappingGroup.getName());
                });

                ArrayList<String> bOverlappingNames = new ArrayList<>();

                b.getOtherOverlappingNodes().forEach((overlappingGroup) -> {
                    bOverlappingNames.add(overlappingGroup.getName());
                });

                Collections.sort(aOverlappingNames);
                Collections.sort(bOverlappingNames);

                for (int c = 0; c < aOverlappingNames.size(); c++) {
                    String aName = aOverlappingNames.get(c);
                    String bName = bOverlappingNames.get(c);

                    int compare = aName.compareTo(bName);

                    if (compare != 0) {
                        return compare;
                    }
                }

                return 0; // Though this shouldn't happen...

            } else {
                return a.getOverlappingConcepts().size() - b.getOverlappingConcepts().size();
            }
        } else {
            return a.getOtherOverlappingNodes().size() - b.getOtherOverlappingNodes().size();
        }
    }
}
