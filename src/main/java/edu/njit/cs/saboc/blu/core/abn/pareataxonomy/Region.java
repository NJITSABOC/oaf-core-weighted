package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.SimilarityNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty.InheritanceType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Represents a region of an area in a partial-area taxonomy. Partitions
 * an area's partial-areas based on the introduction or inheritance 
 * of their inheritable properties
 * 
 * @author Chris O
 */
public class Region extends SimilarityNode<PArea> {
    
    private final Area area;
    
    private final Set<InheritableProperty> relationships;
    
    public Region(Area area, Set<PArea> pareas, Set<InheritableProperty> relationships) {
        super(pareas);
        
        this.relationships = relationships;
        
        this.area = area;
    }
    
    public Area getArea() {
        return area;
    }
    
    public Set<InheritableProperty> getRelationships() {
        return relationships;
    }

    public Set<PArea> getPAreas() { 
        return super.getInternalNodes();
    }
    
    public boolean isPAreaInRegion(PArea parea) {        
        return getPAreas().contains(parea);
    }
    
    @Override
    public int hashCode() {
        return relationships.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Region) {
            Region other = (Region)o;
            
            return relationships.stream().allMatch( (rel) -> {
                return other.relationships.stream().anyMatch( (otherRel) -> {
                    return rel.equalsIncludingInheritance(otherRel);
                });
            });
        }
        
        return false;
    }
    
    public String getName() {
        return getName(", ");
    }
    
    public String getName(String separator) {
        
        if(relationships.isEmpty()) {
            return "\u2205"; // Empty set symbol
        } else {
            ArrayList<String> relNamesAndInheritance = new ArrayList<>();
            
            getRelationships().forEach((rel) -> {
                String inheritanceSymbol;
                
                if(rel.getInheritanceType() == InheritanceType.Introduced) {
                    inheritanceSymbol = "+";
                } else {
                    inheritanceSymbol = "*";
                }
                
                relNamesAndInheritance.add(rel.getName() + inheritanceSymbol);
            });
            
            Collections.sort(relNamesAndInheritance);
            
            String name = relNamesAndInheritance.get(0);
            
            for(int c = 1; c < relNamesAndInheritance.size(); c++) {
                name += separator;
                name += relNamesAndInheritance.get(c);
            }
            
            return name;
        }
    }
}
