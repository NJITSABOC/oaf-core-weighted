
package edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;

/**
 *
 * @author Chris Ochs
 */
public class TANDerivationExplanation extends AbNDerivationExplanation {
    
    public TANDerivationExplanation(TANConfiguration config) {
        super(config);
        
        AbNTextFormatter formatter = new AbNTextFormatter(config.getTextConfiguration());
        
        String str1 =  "<html><font size = '6'>A <b>Tribal Abstraction Network (TAN)</b> "
                + "summarizes sets of <conceptTypeName count=2> that belong to the same "
                + "subhierarchies. These subhierarchies are rooted at user-defined <conceptTypeName count=2> "
                + " called <b>patriarchs</b>.<p><p>"
                
                + "For example, <i>Cheese Pizza</i> and <i>Spicy Pizza</i> are patriarchs. There are "
                + "two <conceptTypeName count=2> that are descendants of these patriarchs (<i>American Hot Pizza</i> and "
                + "<i>Spicy Cheesy Vegetarian Pizza</i>).";
        
        str1 = formatter.format(str1);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("TANExample1.png"), str1));
        
        String str2 = "<html><font size = '6'>A Tribal Abstraction Network is created by identifying "
                + "which <conceptTypeName count=2> belong to which patriarch-defined subhierarchies. "
                + "The dashed lines above indicate which <conceptTypeName count=2> belong to the same "
                + "subhierarhies.";
        
        str2 = formatter.format(str2);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("TANExample2.png"), str2));
        
        
        String str3 =  "<html><font size = '6'>The set of <conceptTypeName count=2> that belong "
                + "to the same set of subhierarchies are summarized by a <b>band</b>. Bands are "
                + "named after the set of patriarchs their <conceptTypeName count=2> are descendants of.";
        
        str3 = formatter.format(str3);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("TANExample3.png"), str3));
        
        
        String str4 = "<html><font size = '6'>Subhierarchies of <conceptTypeName count=2> in each band are "
                + "summarized by <b>clusters</b>. Clusters identify specific points of intersection "
                + "between two or more patriarch-defined subhierarchies. A given band can have multiple clusters. "
                + "A <conceptTypeName> may be summarized by multiple clusters. ";
        
        str4 = formatter.format(str4);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("TANExample4.png"), str4));

    }
}
