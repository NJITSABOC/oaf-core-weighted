package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNUtilities;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class ExportAbNButton<T extends AbstractionNetwork> extends AbNOptionsButton<T> {
    
    private final AbNConfiguration config;
    
    public ExportAbNButton(String toolTip, AbNConfiguration config) {
        super("BluExport.png", toolTip);
        
        this.config = config;
        
        this.addActionListener((ae) -> {
            exportAction();
        });
    }
    
    @Override
    public void setEnabledFor(T abn) {
        this.setEnabled(true);
    }
    
    public void exportAction() {
        if(super.getCurrentAbN().isPresent()) {
            if(super.getCurrentAbN().get() instanceof PartitionedAbstractionNetwork) {
                doPartitionedAbNExport();
            } else {
                doBasicAbNExport();
            }
        }
    }
    
    private void doBasicAbNExport() {
        Optional<File> exportFile = ExportAbNUtilities.displayFileSelectSaveDialog();

        if (exportFile.isPresent()) {
            doNodeExport(exportFile.get());
        }
    }
    
    private void doPartitionedAbNExport() {
        PartitionedAbNConfiguration partitionedConfig = (PartitionedAbNConfiguration)config;
        
        Optional<File> exportFile = ExportAbNUtilities.displayFileSelectSaveDialog();

        if (exportFile.isPresent()) {
            String fullChoice = String.format("Full Export (Hierarchy, Level, %s, %s, %s, %s Unique Identifiers)",
                    partitionedConfig.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(true),
                    config.getTextConfiguration().getNodeTypeName(true),
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true),
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false));

            String containerChoice = String.format("%s and %s Only",
                    partitionedConfig.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(true),
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true));

            String groupChoice = String.format("%s and %s Only",
                    config.getTextConfiguration().getNodeTypeName(true),
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true));

            String[] choices = {fullChoice, containerChoice, groupChoice};

            String input = (String) JOptionPane.showInputDialog(null, "Select Export Option",
                    "Choose an Export Option",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]);

            if (input.equals(choices[0])) {
                doFullExport(exportFile.get());
            } else if (input.equals(choices[1])) {
                doPartitionNodeExport(exportFile.get());
            } else if (input.equals(choices[2])) {
                doNodeExport(exportFile.get());
            } else {

            }
        }
    }
    
    private void doNodeExport(File file) {

        try (PrintWriter writer = new PrintWriter(file)) {

            Set<SinglyRootedNode> nodes = super.getCurrentAbN().get().getNodes();

            nodes.forEach((group) -> {
                String groupName = group.getName();

                Set<Concept> concepts = group.getConcepts();

                concepts.forEach((concept) -> {
                    writer.println(String.format(
                            "%s\t%s\t%s",
                            groupName,
                            group.getRoot().getIDAsString(),
                            concept.getName(),
                            concept.getIDAsString()));
                });
            });

        } catch (IOException ioe) {
            
        }
    }
    
    private void doFullExport(File file) {
        
        PartitionedAbstractionNetwork partitionedAbN = (PartitionedAbstractionNetwork)super.getCurrentAbN().get();
        PartitionedAbNConfiguration partitionedConfig = (PartitionedAbNConfiguration)config;
        
        try (PrintWriter writer = new PrintWriter(file)) {
            
            Set<PartitionedNode> containers = partitionedAbN.getBaseAbstractionNetwork().getNodes();
            
            containers.forEach((container) -> {
                Set<SinglyRootedNode> groups = container.getInternalNodes();
                
                int containerLevel = partitionedConfig.getPartitionedNodeLevel(container);
                
                String containerName = container.getName();
                
                groups.forEach( (group) -> {
                    String groupName = String.format("%s (%d)", group.getName(), group.getConceptCount());
                    
                    Set<Concept> concepts = group.getConcepts();
                    
                    concepts.forEach( (concept) -> {
                        String conceptName = concept.getName();
                        
                        writer.println(String.format("%d\t%s\t%s\t%s\t%s\t%s",
                                containerLevel, 
                                containerName, 
                                groupName, 
                                group.getRoot().getIDAsString(),
                                conceptName,
                                concept.getIDAsString()));
                    });
                });
            });
            
        } catch(IOException ie) {
            
        }
    }
    
    private void doPartitionNodeExport(File file) {
        PartitionedAbstractionNetwork partitionedAbN = (PartitionedAbstractionNetwork)super.getCurrentAbN().get();
        PartitionedAbNConfiguration partitionedConfig = (PartitionedAbNConfiguration)config;
        
        try (PrintWriter writer = new PrintWriter(file)) {

            Set<PartitionedNode> containers = partitionedAbN.getBaseAbstractionNetwork().getNodes();

            containers.forEach((container) -> {
                Set<SinglyRootedNode> groups = container.getInternalNodes();

                Set<Concept> concepts = new HashSet<>();
                groups.forEach( (group) -> {
                    concepts.addAll(group.getConcepts());
                });
                
                String containerName = container.getName();

               
                concepts.forEach( (concept) -> {
                    writer.println(String.format("%s\t%s\t%s",
                            containerName, 
                            concept.getName(),
                            concept.getIDAsString())
                    );
                });
            });
        } catch (IOException ie) {
            
        }
    }
}
