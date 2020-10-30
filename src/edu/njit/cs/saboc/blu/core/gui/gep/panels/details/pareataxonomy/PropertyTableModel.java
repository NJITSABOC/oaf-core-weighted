package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class PropertyTableModel extends OAFAbstractTableModel<InheritableProperty> {
    
    private static String [] generateColumnNames(PAreaTaxonomyConfiguration config, boolean forArea) {
        
        String propertyType = config.getTextConfiguration().getOntologyEntityNameConfiguration().getPropertyTypeName(false);
        
        if(forArea) {
            return new String[] {
                String.format("%s Name", propertyType)
            };
        } else {
            return new String[] {
                String.format("%s Name", propertyType),
                "Inheritance"
            };
        }
    }
    
    private final PAreaTaxonomyConfiguration config;
    
    private final boolean forArea;
    
    public PropertyTableModel(PAreaTaxonomyConfiguration config, boolean forArea) {
        super(PropertyTableModel.generateColumnNames(config, forArea));
        
        this.config = config;
        this.forArea = forArea;
    }

    @Override
    protected Object[] createRow(InheritableProperty item) {
        if (forArea) {
            return new Object[]{
                item.getName()
            };
        } else {
            return new Object[]{
                item.getName(),
                item.getInheritanceType().toString()
            };
        }
    }
}
