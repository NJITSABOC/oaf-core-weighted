package edu.njit.cs.saboc.blu.core.graph.layout;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 * @param <T>
 */
public interface NodeInclusionTester<T extends Node> {
    public boolean includeInLayout(T node);
}
