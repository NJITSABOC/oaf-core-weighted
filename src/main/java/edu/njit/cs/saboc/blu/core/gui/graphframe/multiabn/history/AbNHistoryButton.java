package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PopupToggleButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.AbNGraphFrameInitializers;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistoryParser.AbNDerivationHistoryParseException;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Chris O
 */
public class AbNHistoryButton extends PopupToggleButton {

    private final AbNDerivationHistoryPanel derivationHistoryPanel;
    
    private final AbNDerivationHistory derivationHistory;

    private Optional<AbNGraphFrameInitializers> optInitializers = Optional.empty();
    

    public AbNHistoryButton(
            MultiAbNGraphFrame graphFrame, 
            AbNDerivationHistory derivationHistory) {               

        super(graphFrame.getParentFrame(), "History");
                
        JPanel historyPanel = new JPanel(new BorderLayout());
        
        this.derivationHistory = derivationHistory;

        this.derivationHistoryPanel = new AbNDerivationHistoryPanel();
        
        displayHistory(derivationHistory);
        
        this.addActionListener( (ae) -> {
            this.derivationHistoryPanel.showHistory(derivationHistory);
            historyPanel.grabFocus();
        });

        JButton saveBtn = new JButton("Save Selected Entry");
        saveBtn.addActionListener((se) -> {
            
            AbNDerivationHistoryEntry entry = derivationHistoryPanel.getSelectedEntry();

            JSONArray arr = new JSONArray();
            JSONObject abnJSON = entry.toJSON();

            arr.add(abnJSON);
            
            saveHistoryToFile(arr);
        });

        JButton saveAllBtn = new JButton("Save History");
        
        saveAllBtn.addActionListener((ae) -> {
            ArrayList<AbNDerivationHistoryEntry> entries = derivationHistory.getHistory();

            JSONArray arr = new JSONArray();

            entries.forEach((entry) -> {
                arr.add(entry.toJSON());
            });
            
            saveHistoryToFile(arr);
        });

        JButton loadBtn = new JButton("Load History");
        
        loadBtn.addActionListener((ae) -> {
            
            if(!this.optInitializers.isPresent()) {
                return;
            }
            
            loadHistoryFromFile(graphFrame, derivationHistory, this.optInitializers.get().getAbNParser());
        });

        JPanel subPanel = new JPanel();
        subPanel.add(saveBtn);
        subPanel.add(saveAllBtn);
        subPanel.add(loadBtn);

        historyPanel.add(derivationHistoryPanel, BorderLayout.CENTER);
        historyPanel.add(subPanel, BorderLayout.SOUTH);
        
        this.setPopupContent(historyPanel);               
    }
    
    public void setInitializers(AbNGraphFrameInitializers initializers) {
        this.optInitializers = Optional.of(initializers);
        
        this.derivationHistoryPanel.setInitializers(initializers);
    }
    
    public void clearInitializers() {
        this.optInitializers = Optional.empty();
        
        this.derivationHistoryPanel.clearInitializers();
    }
    
    public final void displayHistory(AbNDerivationHistory derivationHistory) {
        this.derivationHistoryPanel.showHistory(derivationHistory);
    }
    
    private void saveHistoryToFile(JSONArray historyArr) {
        
        final JFileChooser chooser = new JFileChooser();

        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                
                if (f.isDirectory()) {
                    return true;
                }

                return f.getName().endsWith(".abnd");
            }

            @Override
            public String getDescription() {
                return "Abstraction Network Derivation (.abnd)";
            }
        });

        int returnVal = chooser.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            String fileName = file.getAbsolutePath();

            if (!fileName.toLowerCase().endsWith(".abnd")) {
                fileName += ".abnd";
            }

            File saveFile = new File(fileName);

            try (FileWriter fileWriter = new FileWriter(saveFile)) {
                fileWriter.write(historyArr.toJSONString());

                fileWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    
    private void loadHistoryFromFile(
            MultiAbNGraphFrame graphFrame,
            AbNDerivationHistory derivationHistory,
            AbNDerivationParser abnParser) {
        
        final JFileChooser chooser = new JFileChooser();

        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                
                if (f.isDirectory()) {
                    return true;
                }

                return f.getName().endsWith(".abnd");
            }

            @Override
            public String getDescription() {
                return "Abstraction Network Derivation (.abnd)";
            }
        });

        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            JSONParser parser = new JSONParser();

            try (FileReader reader = new FileReader(file)) {
                JSONArray json = (JSONArray) parser.parse(reader);

                AbNDerivationHistoryParser historyParser = new AbNDerivationHistoryParser();

                try {
                    ArrayList<AbNDerivationHistoryEntry> entries = historyParser.getDerivationHistory(graphFrame, abnParser, json);

                    this.derivationHistory.setHistory(entries);
                    
                } catch (AbNDerivationHistoryParseException parseException) {

                    parseException.printStackTrace();
                } catch (AbNDerivationParser.AbNParseException abnpe) {

                    abnpe.printStackTrace();
                }

                derivationHistoryPanel.showHistory(derivationHistory);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
    }
}
