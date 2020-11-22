package edu.njit.cs.saboc.blu.core.gui.dialogs;

import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class CreateAndDisplayDialog<T> {
    
    private final AbNDisplayManager displayManager;
    
    private final String dialogText;

    public CreateAndDisplayDialog(String text, AbNDisplayManager displayManager) {
        this.displayManager = displayManager;

        this.dialogText = text;
    }
    
    public void run() {
        
        Thread loadThread = new Thread(new Runnable() {
            
            private LoadStatusDialog loadStatusDialog = null;
            private boolean loadCancelled = false;

            @Override
            public void run() {

                loadStatusDialog = LoadStatusDialog.display(null, dialogText, () -> {
                    loadCancelled = true;
                });

                T element = create();

                SwingUtilities.invokeLater(() -> {
                    if (!loadCancelled) {
                        display(element);

                        loadStatusDialog.setVisible(false);
                        loadStatusDialog.dispose();
                    }
                });
            }
        });

        loadThread.start();
    }
    
    public AbNDisplayManager getDisplayFrameListener() {
        return displayManager;
    }

    protected abstract void display(T abn);
    protected abstract T create();
}
