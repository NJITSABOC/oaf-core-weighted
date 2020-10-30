package edu.njit.cs.saboc.blu.core.abn;

/**
 *
 * An interface that represents an abstraction network that is a 
 * subset of another abstraction network (the "Super AbN")
 * 
 * @author Chris O
 * @param <T>
 */
public interface SubAbstractionNetwork<T extends AbstractionNetwork> {
    public T getSuperAbN();
}
