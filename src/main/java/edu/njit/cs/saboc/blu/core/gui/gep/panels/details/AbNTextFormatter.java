package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;

public class AbNTextFormatter {
    
    private final AbNTextConfiguration AbNObj;

    public AbNTextFormatter(AbNTextConfiguration AbNObj){
        this.AbNObj = AbNObj;
    }
    public String format(String text){
        
        if (text.length() == 0){
            return "";
        }
        
        String str = text;
        
        str = str.replaceAll("<conceptTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase());    
        str = str.replaceAll("<conceptTypeName( count=1)?>", AbNObj.getOntologyEntityNameConfiguration().getConceptTypeName(false).toLowerCase());
        
        str = str.replaceAll("<propertyTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getOntologyEntityNameConfiguration().getPropertyTypeName(true).toLowerCase());
        str = str.replaceAll("<propertyTypeName( count=1)?>", AbNObj.getOntologyEntityNameConfiguration().getPropertyTypeName(false).toLowerCase());
        
        str = str.replaceAll("<parentConceptTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getOntologyEntityNameConfiguration().getParentConceptTypeName(true).toLowerCase());
        str = str.replaceAll("<parentConceptTypeName( count=1| count=0)?>", AbNObj.getOntologyEntityNameConfiguration().getParentConceptTypeName(false).toLowerCase());
        
        str = str.replaceAll("<childConceptTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getOntologyEntityNameConfiguration().getChildConceptTypeName(true).toLowerCase());
        str = str.replaceAll("<childConceptTypeName( count=1 )?>", AbNObj.getOntologyEntityNameConfiguration().getChildConceptTypeName(false).toLowerCase());
        
        str = str.replaceAll("<AbNTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getAbNTypeName(true).toLowerCase());
        str = str.replaceAll("<AbNTypeName( count=1| count=0)?>", AbNObj.getAbNTypeName(false).toLowerCase());
        
        str = str.replaceAll("<nodeTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getNodeTypeName(true).toLowerCase());
        str = str.replaceAll("<nodeTypeName( count=1| count=0)?>", AbNObj.getNodeTypeName(false).toLowerCase());
        
        return str;
    }
}
