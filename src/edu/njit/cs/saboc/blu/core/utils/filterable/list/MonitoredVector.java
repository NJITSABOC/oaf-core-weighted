package edu.njit.cs.saboc.blu.core.utils.filterable.list;

import java.util.Collection;
import java.util.Vector;

/**
 * An extention of {@link Vector} that monitors itself for changes.
 * Whenever the vector is modified, the appropriate elementsXxx is called.
 * Override these methods to take whatever action you would like.
 * 
 * @author Paul Accisano
 * 
 * @param <T>
 */
public class MonitoredVector<T> extends Vector<T> {
    /**
     * Called when one or more elements have been inserted into the
     * array.
     * @param index0 The index of the first new element
     * @param index1 The index of the last new element
     */
    protected void elementsInserted(int index0, int index1) {
    }

    /**
     * Called when one or more elements have been removed from the
     * array.     
     * @param index0 The index of the first element that was removed
     * @param index1 The index of the last element that was removed
     */
    protected void elementsRemoved(int index0, int index1) {
    }

    /**
     * Called when one or more elements have been modified.
     * @param index0 The index of the first element that was modified
     * @param index1 The index of the last element that was modified
     */
    protected void elementsUpdated(int index0, int index1) {
    }

    @Override
    public synchronized boolean add(T e) {
        int index = size();
        super.add(e);
        elementsInserted(index, index);
        return true;
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
        elementsInserted(index, index);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends T> c) {
        if(super.addAll(c)) {
            elementsInserted(size() - c.size(), size() - 1);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public synchronized boolean addAll(int index, Collection<? extends T> c) {
        if(super.addAll(c)) {
            elementsInserted(index, index + c.size() - 1);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public synchronized void addElement(T obj) {
        int index = size();
        super.addElement(obj);
        elementsInserted(index, index);
    }

    @Override
    public void clear() {
        int size = size();
        super.removeAllElements();
        if(size > 0) {
            elementsRemoved(0, size - 1);
        }
    }

    @Override
    public synchronized void insertElementAt(T obj, int index) {
        super.insertElementAt(obj, index);
        elementsInserted(index, index);
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if(index == -1) {
            return false;
        }
        else {
            super.remove(index);
            elementsRemoved(index, index);
            return true;
        }
    }

    @Override
    public synchronized T remove(int index) {
        T ret = super.remove(index);
        elementsRemoved(index, index);
        return ret;
    }

    @Override
    public synchronized void removeAllElements() {
        clear();
    }

    @Override
    public synchronized boolean removeElement(Object obj) {
        int index = indexOf(obj);
        if(index == -1) {
            return false;
        }
        else {
            super.remove(index);
            elementsRemoved(index, index);
            return true;
        }
    }

    @Override
    public synchronized void removeElementAt(int index) {
        remove(index);
    }

    @Override
    protected synchronized void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        if(toIndex > fromIndex) {
            elementsRemoved(fromIndex, toIndex - 1);
        }
    }

    @Override
    public synchronized T set(int index, T element) {
        T ret = super.set(index, element);
        elementsUpdated(index, index);
        return ret;
    }

    @Override
    public synchronized void setElementAt(T obj, int index) {
        super.setElementAt(obj, index);
        elementsUpdated(index, index);
    }

    @Override
    public synchronized void setSize(int newSize) {
        int oldSize = size();
        super.setSize(newSize);
        if(size() < oldSize) {
            elementsRemoved(size(), oldSize - 1);
        }
    }
}
