
package edu.njit.cs.saboc.blu.core.utils.comparators;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import java.util.Comparator;

/**
 *
 * @author cro3
 * @param <T>
 */
public class SinglyRootedNodeComparator<T extends SinglyRootedNode> implements Comparator<T> {
    
    @Override
    public int compare(T a, T b) {
        if (a.getConceptCount() == b.getConceptCount()) {
            return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
        } else {
            return b.getConceptCount() - a.getConceptCount();
        }
    }
}
