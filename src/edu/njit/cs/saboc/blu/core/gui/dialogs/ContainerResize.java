package edu.njit.cs.saboc.blu.core.gui.dialogs;

import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A JDialog of the About Us HTML page.
 */
public class ContainerResize extends JDialog {

    private GenericPartitionEntry partition;
    
    private AbstractionNetworkGraph graph;

    public ContainerResize(GenericPartitionEntry r, AbstractionNetworkGraph g) {
     
        setModal(true);
        
        setTitle("Resize Container");

        partition = r;
        graph = g;

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Resize Container"));
        mainPanel.add(new JLabel("Rows: "));

        final JTextField txtRows = new JTextField(6);
        final JTextField txtCols = new JTextField(6);

        mainPanel.add(txtRows);
        txtRows.setHorizontalAlignment(JTextField.RIGHT);
        txtRows.setText(Integer.toString(partition.getGroupLevels().size()));
        txtRows.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                txtRows.setText(null);
            }
        });

        txtRows.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if(txtRows.getText().isEmpty()) {
                    return;
                }

                int rowVal = Integer.parseInt(txtRows.getText());

                if(rowVal < 1) {
                    rowVal = 1;
                    txtRows.setText(Integer.toString(1));
                }

                int colVal = (int)Math.ceil((double)partition.getVisibleGroups().size() / (double)rowVal);

                txtCols.setText(Integer.toString(colVal));
            }
        });

        mainPanel.add(new JLabel("Cols: "));
        mainPanel.add(txtCols);
        txtCols.setText(Integer.toString(partition.getGroupLevels().get(0).getGroupEntryCount()));
        txtCols.setHorizontalAlignment(JTextField.RIGHT);
        txtCols.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                txtCols.setText(null);
            }
        });
        txtCols.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if(txtCols.getText().isEmpty()) {
                    return;
                }

                int colVal = Integer.parseInt(txtCols.getText());

                if(colVal < 1) {
                    colVal = 1;
                    txtCols.setText(Integer.toString(1));
                }

                int rowVal = (int) Math.ceil((double)partition.getVisibleGroups().size() / (double)colVal);

                txtRows.setText(Integer.toString(rowVal));
            }
        });

        JButton btnOkay = new JButton("OK");
        btnOkay.setPreferredSize(new Dimension(64, 24));

        btnOkay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                if(txtRows.getText().isEmpty()) {
                    txtRows.setText(Integer.toString(1));
                    txtCols.setText(Integer.toString(partition.getVisibleGroups().size()));
                } else if(txtCols.getText().isEmpty()) {
                    txtCols.setText(Integer.toString(1));
                    txtRows.setText(Integer.toString(partition.getVisibleGroups().size()));
                }

                int rows = Integer.parseInt(txtRows.getText());
                int cols = Integer.parseInt(txtCols.getText());

                if (rows > partition.getVisibleGroups().size()) {
                    rows = partition.getVisibleGroups().size();
                }

                if (cols > partition.getVisibleGroups().size()) {
                    cols = partition.getVisibleGroups().size();
                }

                graph.getGraphLayout().resizePartitionEntry(partition, rows, cols, partition.getVisibleGroups());
                ContainerResize.this.setVisible(false);
                ContainerResize.this.dispose();
            }
        });

        mainPanel.add(btnOkay);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
        
        setMinimumSize(getPreferredSize());
        setResizable(false);
        setVisible(true);
    }
}
