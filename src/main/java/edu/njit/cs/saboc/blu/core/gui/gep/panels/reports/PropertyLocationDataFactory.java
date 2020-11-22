/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Hao Liu
 */
public interface PropertyLocationDataFactory {
    public Set<InheritableProperty> getPropertiesFromIds(ArrayList<String> ids);
}
