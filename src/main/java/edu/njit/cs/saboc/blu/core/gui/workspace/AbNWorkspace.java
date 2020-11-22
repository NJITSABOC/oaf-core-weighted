package edu.njit.cs.saboc.blu.core.gui.workspace;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser.AbNParseException;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistory;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistoryEntry;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistoryParser;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistoryParser.AbNDerivationHistoryParseException;
import edu.njit.cs.saboc.blu.core.utils.toolstate.FileUtilities;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Chris O
 */
public class AbNWorkspace extends OAFWorkspace {
    
    private final MultiAbNGraphFrame graphFrame;
    
    private final AbNDerivationHistory derivationHistory;
    
    private final AbNDerivationParser abnParser;
    
    public static AbNWorkspace createNewWorkspaceFromCurrent(MultiAbNGraphFrame graphFrame,
            String name,
            File file,
            AbNDerivationParser abnParser) {
        
        return new AbNWorkspace(graphFrame, name, file, abnParser);
    }
        
    /**
     * Constructor for initializing a new workspace
     * 
     * @param graphFrame
     * @param name
     * @param file
     * @param abnParser 
     */
    private AbNWorkspace(
            MultiAbNGraphFrame graphFrame,
            String name,
            File file,
            AbNDerivationParser abnParser) {

        super(file);

        this.graphFrame = graphFrame;

        this.setName(name);
        this.abnParser = abnParser;

        this.derivationHistory = new AbNDerivationHistory(graphFrame);
        this.derivationHistory.setHistory(graphFrame.getDerivationHistory().getHistory());

        this.derivationHistory.addHistoryChangedListener(() -> {
            saveWorkspace();
        });
        
        saveWorkspace();
    }
    
    /**
     * Constructor for initializing from a file
     * 
     * @param graphFrame
     * @param file
     * @param abnParser 
     */
    public AbNWorkspace(
            MultiAbNGraphFrame graphFrame,
            File file, 
            AbNDerivationParser abnParser) {
        
        super(file);
        
        this.graphFrame = graphFrame;
        this.abnParser = abnParser;
        
        this.derivationHistory = new AbNDerivationHistory(graphFrame);
        
        this.derivationHistory.addHistoryChangedListener( () -> {
            saveWorkspace();
        });
        
        loadWorkspace();
    }

    public AbNDerivationHistory getWorkspaceHistory() {
        return derivationHistory;
    }
    
    @Override
    public final void loadWorkspace() {
        
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(super.getFile())) {
            
            JSONObject obj = (JSONObject) parser.parse(reader);
            
            String name = obj.get("name").toString();
            super.setName(name);
            
            JSONArray json = (JSONArray)obj.get("history");

            AbNDerivationHistoryParser historyParser = new AbNDerivationHistoryParser();

            try {
                ArrayList<AbNDerivationHistoryEntry> entries = historyParser.getDerivationHistory(graphFrame, abnParser, json);

                this.derivationHistory.setHistory(entries);

            } catch (AbNDerivationHistoryParseException parseException) {
                parseException.printStackTrace();
            } catch (AbNParseException abnpe) {
                abnpe.printStackTrace();
            }
        } catch (IOException ioe) {
            
        } catch (ParseException pe) {
            
        }
    }
    
    @Override
    public final void saveWorkspace() {
        
        JSONObject obj = new JSONObject();
        
        obj.put("name", super.getName());
        obj.put("history", derivationHistory.toJSON(25));
        
        if (FileUtilities.ensureFileExistsAndWritable(super.getFile())) {
            try (FileWriter fileWriter = new FileWriter(super.getFile())) {
                fileWriter.write(obj.toJSONString());

                fileWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            // TODO: Error
        }
    }
}
