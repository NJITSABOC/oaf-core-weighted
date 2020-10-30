package edu.njit.cs.saboc.blu.core.gui.dialogs;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Kevyn
 * @param <T>
 */
public abstract class AbNCreateAndDisplayDialog<T extends AbstractionNetwork> 
    extends CreateAndDisplayDialog<T> {

    public AbNCreateAndDisplayDialog(String text, AbNDisplayManager displayManager) {
        super(text, displayManager);
    }
}
