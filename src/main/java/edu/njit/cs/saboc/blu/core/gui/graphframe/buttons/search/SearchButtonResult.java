
package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

/**
 *
 * @author Chris O
 */
public class SearchButtonResult<T> {
    private final String resultStr;
    private final T result;
    
    public SearchButtonResult(String resultStr, T result) {
        this.resultStr = resultStr;
        this.result = result;
    }
    
    public String toString() {
        return resultStr;
    }
    
    public T getResult() {
        return result;
    }
}
