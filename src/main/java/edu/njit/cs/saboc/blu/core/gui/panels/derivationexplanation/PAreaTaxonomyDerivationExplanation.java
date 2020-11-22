package edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomyDerivationExplanation extends AbNDerivationExplanation {
    
    
    public PAreaTaxonomyDerivationExplanation(PAreaTaxonomyConfiguration config) {
        super(config);
        
        AbNTextFormatter formatter = new AbNTextFormatter(config.getTextConfiguration());
        
        String str1 =  "<html><font size = '6'>A <b>Partial-area Taxonomy</b> "
                + "summarizes sets of <conceptTypeName count=2> that are modeled "
                + "with the same types of <propertyTypeName count=2>. "
                
                + "For example, the <i>Meat Pizza</i> and <i>Pepperoni Pizza</i> "
                + "<conceptTypeName count=2> both have a <i>Has Topping</i> <propertyTypeName>.";
        
        str1 = formatter.format(str1);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample1.png"), str1));
        
        String str2 = "<html><font size = '6'>A Partial-area Taxonomy is created for the source <conceptTypeName count=2> "
                + "of the <propertyTypeName count=2>. Target <conceptTypeName count=2> are disregarded.";
        
        str2 = formatter.format(str2);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample2.png"), str2));
        
        
        String str3 =  "<html><font size = '6'>Sets of <conceptTypeName count=2> that have the exact same "
                + "types of <propertyTypeName count=2> are identified. Every set is shown "
                + "with a dashed line around it. The black dashed line has no <propertyTypeName count=2> "
                + "and therefore an empty set symbol \u00D8 is shown.";
        
        str3 = formatter.format(str3);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample3.png"), str3));
        
        
        String str4 = "<html><font size = '6'>Subhierarchies of <conceptTypeName count=2> that are modeled with the exact "
                + "same types of <propertyTypeName count=2> are summarized by <b>areas</b> and "
                + "<b>partial-areas</b> in a partial-area taxonomy.";
        
        str4 = formatter.format(str4);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample4.png"), str4));
        
        
        String str5 = "<html><font size = '6'>There may be different types of <propertyTypeName count=2> "
                + "used to model <conceptTypeName count=2> in the same hierarchy. "
                + "For example, the above subhierarchy utilizes both <i>Has Crust</i> "
                + "and <i>Has Topping</i> <propertyTypeName count=2>.";
        
        str5 = formatter.format(str5);

        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample5.png"), str5));
        
        
        String str6 = "<html><font size = '6'>Target <conceptTypeName count=2> are disregarded and the sets of "
                + "<conceptTypeName count=2> that are modeled with the same types of "
                        + "<propertyTypeName count=2> are identified.";
        
        str6 = formatter.format(str6);
                
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample6.png"), str6));
        
        String str7 = "<html><font size = '6'>Multiple areas with the same number of <propertyTypeName> types may exist."
                + "An area may contain many partial-areas, according to the introduction of <propertyTypeName count=2> "
                + "in the subhierarchy.";
        
        str7 = formatter.format(str7);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample7.png"), str7));
        
        
        String str8 =  "<html><font size = '6'>Concepts are often defined with multiple types of "
                + "<propertyTypeName count=2>. For example, the <conceptTypeName> "
                + "<i>New York Style Pepperoni Pizza</i> has both a <i>Has Crust</i> "
                + "<propertyTypeName> and a <i>Has Topping</i> <propertyTypeName>.";
        
        str8 = formatter.format(str8);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample8.png"), str8));
        
        String str9 = "<html><font size = '6'>The <conceptTypeName> <i>New York Style Pepperoni Pizza</i>"
                + " is in a different area than its parent <conceptTypeName count=2>, "
                + "because it is modeled with an additional type of "
                        + "<propertyTypeName> relative to its <parentConceptTypeName count=2>.";
        
        str9 = formatter.format(str9);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample9.png"), str9));
    }
}
