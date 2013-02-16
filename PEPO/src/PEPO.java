import GUI.PEPOGUI;
import Logic.LogicController;
import TextUI.TextUI;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.*;

public class PEPO {

    private static void createAndShowGUI() throws UnsupportedLookAndFeelException, Exception {    //  setup gui
        //Create and set up the window.
        final PEPOGUI frame = new PEPOGUI();

        frame.setTitle("PEPO");
        frame.setDefaultCloseOperation(PEPOGUI.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    LogicController.exit();
                } catch(Exception obj) {}
                frame.dispose();
            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;

        frame.setLocation((width - 1000) / 2, (height - 500) / 2);

        // Center JFrame
        frame.setLocationRelativeTo(null);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0){
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Start GUI
                        //TextUI.run();
                        createAndShowGUI();
                    } catch (Exception ex) {
                        Logger.getLogger(PEPO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
            });
        } else if (args[0].equals("-textui")){
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Welcome to PEPO TextUI:");
                        TextUI.run();

                    } catch (Exception ex) {
                        Logger.getLogger(PEPO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
            });
        } 
    }
}
