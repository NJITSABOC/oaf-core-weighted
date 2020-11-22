/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.abn.aggregate;

/**
 *
 * @author hl395
 */
public class AggregatedProperty {
    private int bound;
    private boolean isWeighteAggregated;
    private int autoScaleBound;
    private boolean isAutoScaled;

    public AggregatedProperty() {
        this.bound=20;
        this.isWeighteAggregated= false;
        this.autoScaleBound = 25; //default value for auto aggregation
        this.isAutoScaled = true; // turn on/off to auto aggregation as default 
    }

    public AggregatedProperty(int Bound, boolean isWeighted, int autoScaleBound, boolean isAutoScaled) {
        this.bound = Bound;
        this.isWeighteAggregated = isWeighted;
        this.autoScaleBound = autoScaleBound;
        this.isAutoScaled = isAutoScaled;
    }
   
    public int getBound(){
        return bound;
    }
    
    public boolean getWeighted(){
        return isWeighteAggregated;
    }
    
    public int getAutoScaleBound() {
        return autoScaleBound;
    }
    
    public boolean getAutoScaled(){
        return isAutoScaled;
    }
    
    public void setBound(int bound){
        this.bound = bound;
    }
    
    public void setWeighted(boolean isWeighted){
        this.isWeighteAggregated = isWeighted;
    }
    
    public void setAutoScaleBound(int bound){
        this.autoScaleBound = bound;
    }
    
    public void setAutoScaled(boolean isAutoScaled){
        this.isAutoScaled = isAutoScaled;
    }
}
