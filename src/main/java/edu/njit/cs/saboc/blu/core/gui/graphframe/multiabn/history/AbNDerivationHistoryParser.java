package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser.AbNParseException;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import java.util.ArrayList;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistoryParser {
    
    public static class AbNDerivationHistoryParseException extends Exception {
        public AbNDerivationHistoryParseException(String message) {
            super(message);
        }
    }
    
    public ArrayList<AbNDerivationHistoryEntry> getDerivationHistory(
            MultiAbNGraphFrame graphFrame,
            AbNDerivationParser parser, 
            JSONArray json) throws AbNDerivationHistoryParseException, AbNParseException {
        
        ArrayList<AbNDerivationHistoryEntry> entries = new ArrayList<>();
        
        for(Object obj : json) {
            JSONObject entryJSON = (JSONObject)obj;
            
            if(!entryJSON.containsKey("TimeViewed")) {
                throw new AbNDerivationHistoryParseException("No view date set.");
            }
            
            if(!entryJSON.containsKey("Derivation")) {
                throw new AbNDerivationHistoryParseException("No Derivation set.");
            }
            
            String dateStr = entryJSON.get("TimeViewed").toString();
            
            JSONObject derivationJSON = (JSONObject)entryJSON.get("Derivation");
            
            try {
                Date date = new Date(Long.parseLong(dateStr));
                
                entries.add(new AbNDerivationHistoryEntry(
                        parser.parseDerivationHistory(derivationJSON),
                        graphFrame,
                        date));
                
            } catch(NumberFormatException nfe) {
                throw new AbNDerivationHistoryParseException("View date improperly formatted.");
            }
        }
        
        return entries;
    }
    
}
