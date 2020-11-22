
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingDetailsEntry;
import java.util.Comparator;

/**
 *
 * @author Chris O
 */
public class OverlappingDetailsEntryComparator implements Comparator<OverlappingDetailsEntry> {

    @Override
    public int compare(OverlappingDetailsEntry a, OverlappingDetailsEntry b) {
        int aCount = a.getDisjointGroups().size();
        int bCount = b.getDisjointGroups().size();

        if (aCount == bCount) {
            return a.getOverlappingNode().getName().compareToIgnoreCase(b.getOverlappingNode().getName());
        } else {
            return bCount - aCount;
        }
    }
}
