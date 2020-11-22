package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Objects;

/**
 * Captures a change to an inhertiable property's domain  (e.g., its used to 
 * model a concept or its not)
 * 
 * @author Chris O
 */
public class InheritablePropertyDomainChange extends InheritablePropertyChange {
    
   /**
    * Valid states for an inheritable property between two ontology releases
    */
   public static enum PropertyState {
        Added,
        Removed,
        Modified,
        Unmodified
    }

   /**
    * Valid states for a concept in the domain of a property between two 
    * ontology releases
    */
    public static enum DomainModificationType {
        Added,
        Removed
    }
    
    private final PropertyState propertyState;
    
    private final Concept domain;
    
    private final DomainModificationType domainModificationType;

    public InheritablePropertyDomainChange(PropertyState modificationState, 
            InheritableProperty property, 
            ChangeInheritanceType domainType, 
            Concept domain, 
            DomainModificationType domainModificationType) {
        
        super(domain, property, domainType);
        
        this.propertyState = modificationState;
        
        this.domain = domain;
        this.domainModificationType = domainModificationType;
    }
    
    public PropertyState getModificationState() {
        return propertyState;
    }
    
    public Concept getPropertyDomain() {
        return domain;
    }


    public DomainModificationType getModificationType() {
        return domainModificationType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        
        hash = 29 * hash + Objects.hashCode(this.propertyState);
        hash = 29 * hash + Objects.hashCode(this.getProperty());
        hash = 29 * hash + Objects.hashCode(this.domain);
        hash = 29 * hash + Objects.hashCode(this.domainModificationType);
        
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
        
        final InheritablePropertyDomainChange other = (InheritablePropertyDomainChange) obj;
        
        if (this.propertyState != other.propertyState) {
            return false;
        }
        
        if (!Objects.equals(this.getProperty(), other.getProperty())) {
            return false;
        }
        
        if (!Objects.equals(this.domain, other.domain)) {
            return false;
        }
        
        if (this.domainModificationType != other.domainModificationType) {
            return false;
        }
        
        return true;
    }
}
