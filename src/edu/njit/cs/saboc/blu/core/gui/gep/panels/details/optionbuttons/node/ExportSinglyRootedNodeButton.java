package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNUtilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author Chris O
 */
public class ExportSinglyRootedNodeButton<T extends SinglyRootedNode> extends ExportNodeButton<T> {
    
    private static final String getToolTipStr(AbNConfiguration config) {
        return String.format("Export %s's %s.", 
                config.getTextConfiguration().getNodeTypeName(false).toLowerCase(),
                config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true));
    }
    
    private final AbNConfiguration config;
    
    public ExportSinglyRootedNodeButton(AbNConfiguration config) {
        super(ExportSinglyRootedNodeButton.getToolTipStr(config));
        
        this.config = config;
    }

    @Override
    public void exportAction() {
        Optional<File> exportFile = ExportAbNUtilities.displayFileSelectSaveDialog();

        if (exportFile.isPresent()) {
            
            String firstChoice = String.format("%s ID and Name", 
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false));
            
            String secondChoice = String.format("%s Name Only", 
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false));
            
            String thirdChoice = String.format("%s ID Only",
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false));
            
            String[] choices = {firstChoice, secondChoice, thirdChoice};

            String description = String.format("Select %s export option", 
                    config.getTextConfiguration().getNodeTypeName(false));
            
            String input = (String) JOptionPane.showInputDialog(null, 
                    description,
                    "Choose an Export Option",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]);

            try (PrintWriter writer = new PrintWriter(exportFile.get())) {
                
                SinglyRootedNode node = (SinglyRootedNode)super.getCurrentNode().get();
                
                if(input.equals(firstChoice)) {
                     node.getConcepts().forEach( (c) -> {
                        writer.println(String.format("%d\t%s",
                                c.getID(),
                                c.getName()));
                     });
                     
                } else if(input.equals(secondChoice)) {
                    node.getConcepts().forEach((c) -> {
                        writer.println(c.getName());
                    });
                    
                } else if(input.equals(thirdChoice)) {
                    node.getConcepts().forEach((c) -> {
                        writer.println(c.getID());
                    });
                }

            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
                
                // TODO: Add an error dialog
            }
        }
    }
}
