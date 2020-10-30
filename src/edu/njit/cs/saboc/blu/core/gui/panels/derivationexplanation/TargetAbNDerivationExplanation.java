package edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;

/**
 *
 * @author Chris Ochs
 */
public class TargetAbNDerivationExplanation extends AbNDerivationExplanation {
    
    public TargetAbNDerivationExplanation(TargetAbNConfiguration config) {
        super(config);
        
        AbNTextFormatter formatter = new AbNTextFormatter(config.getTextConfiguration());
        
        String str1 =  "<html><font size = '6'>A <b>Target Abstraction Network (Target AbN)</b> "
                + "summarizes sets of target <conceptTypeName count=2> for a user-selected "
                + "<propertyTypeName>. The input to a Target AbN is a source hierarchy, a "
                + "selected <propertyTypeName>, and the target hierarchy.<p><p>"
                
                + "In the above example, the source hierarchy is rooted at <i>Pizza</i>, "
                + "the target hierarchy is rooted at <i>Pizza Topping</i>, and the "
                + "selected <propertyTypename> is <i>Has Topping</i>. The TargetAbN will summarize the "
                + "targets of the <i>Has Topping</i> <propertyTypeName>.";
        
        str1 = formatter.format(str1);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("TargetExample1.png"), str1));
        
        String str2 = "<html><font size = '6'>The source <conceptTypeName count=2> are disregarded "
                + "when creating a Target Abstraction Network, though they can be recovered later.";

        str2 = formatter.format(str2);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("TargetExample2.png"), str2));
        
        
        String str3 =  "<html><font size = '6'>The set of lowest common ancestor <conceptTypeName count=2> that "
                + "are <b>not</b> the target of any <propertyTypeName> are identified.";
        
        str3 = formatter.format(str3);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("TargetExample3.png"), str3));
        
        
        String str4 = "<html><font size = '6'>The subhierarchies of target <conceptTypeName count=2> "
                + "under the lowest common non-target ancestors are identified.";
        
        str4 = formatter.format(str4);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("TargetExample4.png"), str4));
        
        
        String str5 = "<html><font size = '6'><b>Target Groups</b> summarize the "
                + "subhierarchies of target <conceptTypeName count=2>.";

        str5 = formatter.format(str5);

        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("TargetExample5.png"), str5));

    }
}
