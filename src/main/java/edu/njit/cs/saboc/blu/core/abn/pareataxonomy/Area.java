package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.SimilarityNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Represents an area in a partial-area taxonomy. All of the concepts in an area 
 * are modeled with the same types of semantic relationships.
 * 
 * @author Chris O
 */
public class Area extends SimilarityNode<PArea> {

    // The set of inheritable properties that all concepts in this area
    // share
    private final Set<InheritableProperty> relationships;
    
    // Partial-areas are separated into regions based on the inheritance of
    // their inheritable properties
    private final Set<Region> regions;
    
    public Area(Set<PArea> pareas, Set<InheritableProperty> relationships) {
        super(pareas);
        
        this.relationships = relationships;
        
        HashMap<Set<InheritableProperty>, Set<PArea>> regionPartition = new HashMap<>();
        
        pareas.forEach( (parea) -> {
            final Set<InheritableProperty> pareaRelationships = parea.getRelationships();
            
            Optional<Set<InheritableProperty>> matchedRegion = regionPartition.keySet().stream().filter( (region) -> {
                
                return region.stream().allMatch( (relationship) -> {
                    return pareaRelationships.stream().anyMatch( (pareaRelationship) -> {
                        return relationship.equalsIncludingInheritance(pareaRelationship);
                    });
                });
            }).findAny();

            if(matchedRegion.isPresent()) {
                regionPartition.get(matchedRegion.get()).add(parea);
            } else {
                regionPartition.put(pareaRelationships, new HashSet<>());
                regionPartition.get(pareaRelationships).add(parea);
            }
        });
        
        this.regions = new HashSet<>();
        
        regionPartition.forEach((rels, regionPAreas) -> {
            regions.add(new Region(this, regionPAreas, rels));
        });
    }
    
    public Set<InheritableProperty> getRelationships() {
        return relationships;
    }
    
    public Set<Region> getRegions() {
        return regions;
    }
    
    public Set<PArea> getPAreas() {
        return super.getInternalNodes();
    }

    @Override
    public String getName() {
        return getName(", ");
    }
    
    @Override
    public String getName(String separator) {
        
        if(relationships.isEmpty()) {
            return "\u2205"; // Empty set symbol
        } else {
            ArrayList<String> relNames = new ArrayList<>();
            
            getRelationships().forEach((rel) -> {
                relNames.add(rel.getName());
            });
            
            Collections.sort(relNames);
            
            String name = relNames.get(0);
            
            for(int c = 1; c < relNames.size(); c++) {
                name += separator;
                name += relNames.get(c);
            }
            
            return name;
        }
    }
    
    @Override
    public int hashCode() {
        return relationships.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Area) {
            Area otherArea = (Area)o;
            
            return this.getRelationships().equals(otherArea.getRelationships());
        }
        
        return false;
    }
}
