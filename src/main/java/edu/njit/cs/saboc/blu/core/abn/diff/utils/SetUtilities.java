package edu.njit.cs.saboc.blu.core.abn.diff.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Defines various utilities for working with sets. This was defined for diff
 * abstraction networks but is useful for various types of set operations.
 * 
 * These classes are used to provide additional semantics to a series of set
 * operations, in comparison to the default Set operations provided by Java
 * 
 * @author Chris
 */
public class SetUtilities {
    
    /**
     * A intersect B (all of the elements that are in both A and B)
     * 
     * @param <T>
     * @param a
     * @param b
     * @return 
     */
    public static <T> Set<T> getSetIntersection(Set<T> a, Set<T> b) {
        
        if(a == null || b == null) {
            return Collections.emptySet();
        }
        
        Set<T> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        
        return intersection;
    }
    
    /**
     * A union B (all of the elements that are in either A or B).
     * 
     * @param <T>
     * @param a
     * @param b
     * @return 
     */
    public static <T> Set<T> getSetUnion(Set<T> a, Set<T> b) {
        Set<T> union = new HashSet<>(a);
        union.addAll(b);
        
        return union;
    }
    
    /**
     * A - B (All of the elements in A that do not exist in B).
     * 
     * @param <T>
     * @param a
     * @param b
     * @return 
     */
    public static <T> Set<T> getSetDifference(Set<T> a, Set<T> b) {
        Set<T> subtraction = new HashSet<>(a);
        subtraction.removeAll(b);
        
        return subtraction;
    }
    
    /**
     * A = B (all of the elements in A and B are the same)
     * 
     * @param <T>
     * @param a
     * @param b
     * @return 
     */
    public static <T> boolean setsEqual(Set<T> a, Set<T> b) {
        return a.equals(b);
    }
}
