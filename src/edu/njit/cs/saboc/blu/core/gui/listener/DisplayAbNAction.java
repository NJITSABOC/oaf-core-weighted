package edu.njit.cs.saboc.blu.core.gui.listener;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 */
public interface DisplayAbNAction<T extends AbstractionNetwork> {
    public void displayAbstractionNetwork(T abstractionNetwork);
}
