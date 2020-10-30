package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.options.IconOptionButton;
import java.util.Optional;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class AbNOptionsButton<T extends AbstractionNetwork> extends IconOptionButton<T> {
    
    public AbNOptionsButton(
            String iconFileName, 
            String toolTipText) {
        
        super(iconFileName, toolTipText); 
    }
    
    public void setCurrentAbN(T abn) {
        super.setCurrentEntity(abn);
    }
    
    public Optional<T> getCurrentAbN() {
        return super.getCurrentEntity();
    }
}
