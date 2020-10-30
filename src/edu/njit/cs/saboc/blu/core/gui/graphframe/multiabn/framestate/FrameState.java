/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.framestate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import java.io.Serializable;

/**
 *
 * @author hl395
 */
public class FrameState{
    
    //initialized flag
    private boolean initialized;
    
    //auto aggregate state
    private AggregatedProperty ap;
    
    public FrameState(){
        this.initialized = true;
        this.ap = new AggregatedProperty();
    }
    
    public void setInitialized(boolean flag){
        this.initialized = flag;
    }
    
    public boolean getInitialized(){
        return this.initialized;
    }
    
    public FrameState(AggregatedProperty ap){        
        this.ap = ap;
    }
    
    public void setAggregateProperty(AggregatedProperty ap){
        this.ap = ap;
    }
    
    public AggregatedProperty getAggregateProperty(){
        return  ap;
    }

}
