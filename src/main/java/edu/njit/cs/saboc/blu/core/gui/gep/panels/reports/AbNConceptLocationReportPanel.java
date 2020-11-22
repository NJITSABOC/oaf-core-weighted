package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNUtilities;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ImportedConceptNodeReport;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.ImportReportTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 *
 * @author Chris O
 */
public class AbNConceptLocationReportPanel extends AbNReportPanel {
    
    private final AbstractEntityList<ImportedConceptNodeReport> conceptReportList; 
    
     public AbNConceptLocationReportPanel(
            AbNConfiguration config, 
            ConceptLocationDataFactory factory) {
         
         this(config, factory, new ImportReportTableModel(config));
     }
    
    public AbNConceptLocationReportPanel(
            AbNConfiguration config, 
            ConceptLocationDataFactory factory,
            OAFAbstractTableModel<ImportedConceptNodeReport> model) {
        
        super(config);
        
        super.setLayout(new BorderLayout());
        
        this.conceptReportList = new AbstractEntityList<ImportedConceptNodeReport>(model) {

            @Override
            protected String getBorderText(Optional<ArrayList<ImportedConceptNodeReport>> entities) {
                String base = String.format("Imported %s' %s", 
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true), 
                        config.getTextConfiguration().getNodeTypeName(true));
                
                if(entities.isPresent()) {
                    return String.format("%s (%d %s found)", base, 
                            entities.get().size(),
                            config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(entities.get().size() != 1).toLowerCase());
                } else {
                    return base;
                }
            }
        };
        
        JPanel loadPanel = new JPanel(new BorderLayout(20,10));
        
        JLabel loadLabel = new JLabel(String.format("<html>Copy and paste %s IDs in the box to find where the %s are summarized in the %s.", 
                config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false).toLowerCase(),
                config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase(),
                config.getTextConfiguration().getAbNTypeName(false)));
        
        loadPanel.add(loadLabel, BorderLayout.CENTER);
        
        JTextArea textArea = new JTextArea(4,3);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JButton openFromFileBtn = new JButton(String.format("Load %s from File", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)));
        openFromFileBtn.addActionListener( (ae) -> {
            createAndDisplayReport(config, factory, loadConceptIdentifiersFromFile());
        });
        
        JButton loadBtn = new JButton(String.format("Load %s IDs", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)));
        loadBtn.addActionListener((ae) -> {
            createAndDisplayReport(config, factory, parseConceptIdentifiers(textArea.getText()));
        });
        
        JPanel eastPanel = new JPanel();
        eastPanel.add(openFromFileBtn);
        eastPanel.add(loadBtn);
        
        loadPanel.add(eastPanel, BorderLayout.EAST);
        
        this.add(loadPanel, BorderLayout.NORTH);
        
        JPanel twoPanel = new JPanel(new BorderLayout(5,5));
        
        twoPanel.add(scrollPane, BorderLayout.CENTER);
        twoPanel.add(conceptReportList, BorderLayout.SOUTH);
        
        loadPanel.add(twoPanel, BorderLayout.SOUTH);
    }
    
    private ArrayList<String> loadConceptIdentifiersFromFile() {
        Optional<File> idFile = ExportAbNUtilities.displayFileSelectOpenDialog();
        
        if(idFile.isPresent()) {

            try(Scanner scanner = new Scanner(idFile.get())) {
                ArrayList<String> conceptIds = new ArrayList<>();
                
                while(scanner.hasNext()) {
                    String [] line = scanner.nextLine().split("\t");
                    
                    conceptIds.add(line[0].trim().toLowerCase());
                }
                
                return conceptIds;
                
            } catch(FileNotFoundException fnfe) {
                
            }
        }
        
        return new ArrayList<>();
    }
    
    private ArrayList<String> parseConceptIdentifiers(String text) {
        String[] lines = text.split("\n");
        
        ArrayList<String> conceptIds = new ArrayList<>();
        
        for (String line : lines) {
            conceptIds.add(line.trim().toLowerCase());
        }
        
        return conceptIds;
    }
    
    private void createAndDisplayReport(
            AbNConfiguration config, 
            ConceptLocationDataFactory factory, 
            ArrayList<String> idStrs) {

        if (!idStrs.isEmpty()) {
            Set<Concept> concepts = factory.getConceptsFromIds(idStrs);

            ArrayList<ImportedConceptNodeReport> reports = new ArrayList<>();

            AbstractionNetwork<? extends Node> abn = config.getAbstractionNetwork();

            Map<Concept, Set<Node>> conceptNodes = new HashMap<>();

            abn.getNodes().forEach((node) -> {
                node.getConcepts().forEach((concept) -> {
                    if (!conceptNodes.containsKey(concept)) {
                        conceptNodes.put(concept, new HashSet<>());
                    }

                    conceptNodes.get(concept).add(node);
                });
            });

            concepts.forEach((concept) -> {
                if (conceptNodes.containsKey(concept)) {
                    reports.add(new ImportedConceptNodeReport(concept, conceptNodes.get(concept)));
                }
            });

            reports.sort((a, b) -> {
                return a.getConcept().getName().compareToIgnoreCase(b.getConcept().getName());
            });

            conceptReportList.setContents(reports);
        }
    }

    
    
    public void displayAbNReport(AbstractionNetwork abn) {
        
    }
}
