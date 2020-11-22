package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.PopoutDetailsButton;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class PopoutAbNDetailsButton<T extends AbstractionNetwork> extends PopoutDetailsButton<T> {
    
    public PopoutAbNDetailsButton(AbNConfiguration config) {
        super(config.getTextConfiguration().getAbNTypeName(false),
                () -> {
                    return config.getUIConfiguration().createAbNDetailsPanel();
                });
    }
}
