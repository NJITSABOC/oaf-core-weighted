package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import java.util.Objects;

/**
 *
 * @author cro3
 */
public abstract class EntityListRightClickMenuItem<T> {

    private final String itemName;

    public EntityListRightClickMenuItem(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public abstract boolean isEnabledFor(T item);

    public abstract void doActionFor(T item);

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.itemName);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final EntityListRightClickMenuItem other = (EntityListRightClickMenuItem) obj;

        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }

        return true;
    }
}
