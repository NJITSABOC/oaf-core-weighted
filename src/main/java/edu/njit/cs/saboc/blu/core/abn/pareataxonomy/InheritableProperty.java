

package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

/**
 *  A generic class representing a property or relationship that can be inherited
 * 
 * @author Chris O
 * 
 * @param <ID_T> The type of the id (e.g., a long or an iri)
 * @param <TYPE_T> The type of the inheritable property (e.g., a kind of attribute relationship or object property)
 */
public abstract class InheritableProperty<ID_T, TYPE_T> {
    
    /**
     * Represents an inheritable property being inherited or introduced
     */
    public static enum InheritanceType {
        Inherited,
        Introduced
    }
    
    private final ID_T id;
    private final TYPE_T propertyType;
    private final InheritanceType inheritanceType;
    
    public InheritableProperty(ID_T id, TYPE_T propertyType, InheritanceType inheritanceType) {
        this.id = id;
        this.propertyType = propertyType;
        this.inheritanceType = inheritanceType;
    }
    
    public final ID_T getID() {
        return id;
    }
    
    public final TYPE_T getPropertyType() {
        return propertyType;
    }
    
    public final InheritanceType getInheritanceType() {
        return inheritanceType;
    }
    
    /**
     * Determines if the two kinds of inheritable properties are the same WITHOUT considering inheritance type
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof InheritableProperty) {
            InheritableProperty other = (InheritableProperty)o;

            return this.id.equals(other.id);
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    /**
     * Determines if the two kinds of inheritable properties are the same WITH considering inheritance type
     * 
     * @param other
     * @return 
     */
    public boolean equalsIncludingInheritance(InheritableProperty other) {
        return equals(other) && (this.inheritanceType == other.inheritanceType);
    }
    
    public abstract String getName();
    public abstract String getIDAsString();
}
