
package edu.njit.cs.saboc.blu.core.gui.workspace;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNUtilities;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager;
import edu.njit.cs.saboc.blu.core.utils.toolstate.RecentlyOpenedFile;
import java.awt.Image;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

/**
 *
 * @author Chris O
 */
public class AbNWorkspaceButton extends JButton {
    
    private final MultiAbNGraphFrame graphFrame;
    
    public AbNWorkspaceButton(MultiAbNGraphFrame graphFrame) {
        
        this.graphFrame = graphFrame;
        
        Image scaledIcon = ImageManager.getImageManager().
                getIcon("BLUWorkspace.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        
        super.setIcon(new ImageIcon(scaledIcon));
        
        super.setToolTipText("Ontology Abstraction Network Workspaces");
        
        this.addActionListener( (ae) -> {
            showWorkspaceMenu();
        });
    }
    
    public final void showWorkspaceMenu() {
        
        JPopupMenu menu = new JPopupMenu();
        
        if (graphFrame.getWorkspace().isPresent()) {
            AbNWorkspace workspace = graphFrame.getWorkspace().get();

            JLabel workspaceNameLabel = new JLabel(String.format(
                    "<html>Current workpace: <font color='BLUE'>%s</font>", workspace.getName()));

            workspaceNameLabel.setFont(workspaceNameLabel.getFont().deriveFont(14.0f));

            menu.add(workspaceNameLabel);

        } else {
            JLabel noWorkspaceLabel = new JLabel("No workspace loaded...");
            noWorkspaceLabel.setFont(noWorkspaceLabel.getFont().deriveFont(14.0f));
            
            menu.add(noWorkspaceLabel);
        }
        
        menu.add(new JSeparator());
        
        if(graphFrame.getCurrentInitializers().isPresent()) {
            
            OAFRecentlyOpenedFileManager recentWorkspaceFileManager = graphFrame.getCurrentInitializers().get().getRecentAbNWorkspaceFiles();
            
            if (!recentWorkspaceFileManager.getRecentlyOpenedFiles().isEmpty()) {
                ArrayList<RecentlyOpenedFile> recentWorkspaces = recentWorkspaceFileManager.getRecentlyOpenedFiles(5);

                recentWorkspaces.forEach((workspaceFile) -> {

                    SimpleDateFormat dateFormatter = new SimpleDateFormat();
                    String lastOpenedStr = dateFormatter.format(workspaceFile.getDate());

                    JMenuItem item = new JMenuItem(
                            String.format("<html><font size = '4' color = 'blue'><b>%s</b></font> (Last opened: %s)",
                                    workspaceFile.getFile().getName(),
                                    lastOpenedStr));

                    item.addActionListener((ae) -> {
                        loadWorkspaceFromFile(workspaceFile.getFile());
                    });

                    menu.add(item);
                });

                menu.add(new JSeparator());
            }
        }

        JMenuItem saveWorkspace = new JMenuItem("Create New Workspace");
        saveWorkspace.setFont(saveWorkspace.getFont().deriveFont(14.0f));
        saveWorkspace.addActionListener((ae) -> {
            createNewWorkspace();
        });
        
        menu.add(saveWorkspace);
        
        JMenuItem loadWorkspace = new JMenuItem("Load Workspace");
        loadWorkspace.setFont(loadWorkspace.getFont().deriveFont(14.0f));
        loadWorkspace.addActionListener((ae) -> {
            loadWorkspace();
        });

        menu.add(loadWorkspace);

        menu.show(this,
                 10,
                 10);
    }
    
    private void createNewWorkspace() {
        
        Optional<String> workspaceName = promptForWorkspaceName();

        if (workspaceName.isPresent()) {

            Optional<File> workspaceFile = ExportAbNUtilities.displayFileSelectSaveDialog();

            if (workspaceFile.isPresent()) {
                
                AbNWorkspace workspace = graphFrame.getWorkspaceManager().createNewWorkspace(workspaceFile.get(), workspaceName.get());
                graphFrame.getWorkspaceManager().addOrUpdateWorkspace(workspace);
                graphFrame.setWorkspace(workspace);
                
            } else {
                JOptionPane.showMessageDialog(graphFrame.getParentFrame(),
                        "<html>Workspace not created. No file specified.",
                        "Error Creating Workspace",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(graphFrame.getParentFrame(),
                    "<html>Workspace not created. No name specified.",
                    "Error Creating Workspace",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadWorkspace() {
        Optional<File> optFile = ExportAbNUtilities.displayFileSelectOpenDialog();
        
        if(optFile.isPresent()) {
           loadWorkspaceFromFile(optFile.get());
        } else {
            JOptionPane.showMessageDialog(
                    graphFrame.getParentFrame(),
                    "<html>No workspace loaded. No file specified.",
                    "Error Opening Workspace",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadWorkspaceFromFile(File file) {
        AbNWorkspace workspace = graphFrame.getWorkspaceManager().getWorkspaceFor(file);
        graphFrame.getWorkspaceManager().addOrUpdateWorkspace(workspace);
        graphFrame.setWorkspace(workspace);
    }
    
    private Optional<String> promptForWorkspaceName() {
        
        String auditSetName = "";
        
        while (auditSetName != null && auditSetName.isEmpty()) {

            auditSetName = JOptionPane.showInputDialog(
                    graphFrame.getParentFrame(),
                    "Enter the name of the workspace (required)",
                    "Enter Workspace Name",
                    JOptionPane.QUESTION_MESSAGE);
        }
        
        return Optional.ofNullable(auditSetName);
    }
    
}