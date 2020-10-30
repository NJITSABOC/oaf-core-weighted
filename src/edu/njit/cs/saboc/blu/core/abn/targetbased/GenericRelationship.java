package edu.njit.cs.saboc.blu.core.abn.targetbased;

/**
 * Represents a relationship with a target
 * 
 * @author Chris O
 * 
 * @param <V>
 * @param <T>
 */
public class GenericRelationship<V, T> {
    private final V type;
    private final T target;
    
    public GenericRelationship(V type, T target) {
        this.type = type;
        this.target = target;
    }
    
    public V getType() {
        return type;
    }
    
    public T getTarget() {
        return target;
    }
}
