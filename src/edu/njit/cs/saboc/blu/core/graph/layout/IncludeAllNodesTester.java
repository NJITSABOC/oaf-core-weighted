package edu.njit.cs.saboc.blu.core.graph.layout;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class IncludeAllNodesTester<T extends Node> implements NodeInclusionTester<T> {

    @Override
    public boolean includeInLayout(T node) {
        return true;
    }
}
