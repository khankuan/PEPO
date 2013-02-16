
package GUI;

import GUI.AddBudgetCategory;
import Logic.Event.Event;
import Logic.LogicController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/*
 * @author kuan
 */
public class AddBudgetCategory extends javax.swing.JDialog {
    
    /** Creates new form AddBudgetCategory */
    public AddBudgetCategory(java.awt.Frame parent, boolean modal,Event e) {
        super(parent, modal);
        initComponents();
        myEvent = e;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddBudgetCategoryBackground = new javax.swing.JPanel();
        NameLabel = new javax.swing.JLabel();
        AmountLabel = new javax.swing.JLabel();
        AddBudgetCategoryButton = new javax.swing.JButton();
        AmountText = new javax.swing.JTextField();
        NameText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        AddBudgetCategoryBackground.setBackground(new java.awt.Color(227, 227, 227));
        AddBudgetCategoryBackground.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        AddBudgetCategoryBackground.setPreferredSize(new java.awt.Dimension(234, 144));

        NameLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
        NameLabel.setText("Name:");

        AmountLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
        AmountLabel.setText("Amount ($):");

        AddBudgetCategoryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png"))); // NOI18N
        AddBudgetCategoryButton.setBorderPainted(false);
        AddBudgetCategoryButton.setContentAreaFilled(false);
        AddBudgetCategoryButton.setFocusPainted(false);
        AddBudgetCategoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AddBudgetCategoryButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AddBudgetCategoryButtonMousePressed(evt);
            }
        });
        AddBudgetCategoryButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AddBudgetCategoryButtonMouseMoved(evt);
            }
        });
        AddBudgetCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBudgetCategoryButtonActionPerformed(evt);
            }
        });

        AmountText.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        AmountText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        AmountText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AmountTextFocusGained(evt);
            }
        });
        AmountText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                AmountTextKeyTyped(evt);
            }
        });

        NameText.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        NameText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        NameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NameTextFocusGained(evt);
            }
        });
        NameText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                NameTextKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout AddBudgetCategoryBackgroundLayout = new javax.swing.GroupLayout(AddBudgetCategoryBackground);
        AddBudgetCategoryBackground.setLayout(AddBudgetCategoryBackgroundLayout);
        AddBudgetCategoryBackgroundLayout.setHorizontalGroup(
            AddBudgetCategoryBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddBudgetCategoryBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddBudgetCategoryBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddBudgetCategoryBackgroundLayout.createSequentialGroup()
                        .addGroup(AddBudgetCategoryBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(AmountLabel)
                            .addComponent(NameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddBudgetCategoryBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NameText, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(AmountText, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)))
                    .addComponent(AddBudgetCategoryButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        AddBudgetCategoryBackgroundLayout.setVerticalGroup(
            AddBudgetCategoryBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddBudgetCategoryBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddBudgetCategoryBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NameLabel)
                    .addComponent(NameText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(AddBudgetCategoryBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AmountLabel)
                    .addComponent(AmountText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddBudgetCategoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        AmountText.setBorder(BorderFactory.createCompoundBorder(
            AmountText.getBorder(),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)));
    NameText.setBorder(BorderFactory.createCompoundBorder(
        NameText.getBorder(),
        BorderFactory.createEmptyBorder(0, 5, 0, 5)));

javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
getContentPane().setLayout(layout);
layout.setHorizontalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addComponent(AddBudgetCategoryBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(AddBudgetCategoryBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exit(){
        this.dispose();
    }
    
    private void AddBudgetCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBudgetCategoryButtonActionPerformed
    
        BigDecimal amount = null;
        try {
            amount = new BigDecimal(AmountText.getText());
        } catch (Exception exp){
            JOptionPane.showMessageDialog(this, "Amount seems wrong...", "Oops!", JOptionPane.PLAIN_MESSAGE);
            AmountText.setSelectionStart(0);
            AmountText.setSelectionEnd(AmountText.getText().length());
            return;
        }
        try {
            LogicController.addBudgetCategory(myEvent, NameText.getText(), amount.multiply(new BigDecimal(""+100)).toBigInteger().longValue());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage()+"..", "Oops!", JOptionPane.PLAIN_MESSAGE);             
            return; 
        }
        this.dispose();
    }//GEN-LAST:event_AddBudgetCategoryButtonActionPerformed

    private void AmountTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AmountTextKeyTyped
        if (evt.getKeyChar() == 10){
            BigDecimal amount = null;
            try {
                amount = new BigDecimal(AmountText.getText());
            } catch (Exception exp){
                JOptionPane.showMessageDialog(this, "Amount seems wrong...", "Oops!", JOptionPane.PLAIN_MESSAGE);
                AmountText.setSelectionStart(0);
                AmountText.setSelectionEnd(AmountText.getText().length());
                return;
            }
            try {
                LogicController.addBudgetCategory(myEvent, NameText.getText(), amount.multiply(new BigDecimal(""+100)).toBigInteger().longValue());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage()+"..", "Oops!", JOptionPane.PLAIN_MESSAGE);             
                return; 
            }
            this.dispose();
        }
    }//GEN-LAST:event_AmountTextKeyTyped

    private void AddBudgetCategoryButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddBudgetCategoryButtonMouseExited
        AddBudgetCategoryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png"))); 
    }//GEN-LAST:event_AddBudgetCategoryButtonMouseExited

    private void AddBudgetCategoryButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddBudgetCategoryButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = AddBudgetCategoryButton.getWidth()/2 - x;
        double dy = AddBudgetCategoryButton.getHeight()/2 - y;
        double sr = Math.sqrt(dx*dx + dy*dy );
        if (sr <= AddBudgetCategoryButton.getWidth()/2)
            AddBudgetCategoryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbuttonhover.png")));
        else
            AddBudgetCategoryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png")));
    }//GEN-LAST:event_AddBudgetCategoryButtonMouseMoved

    private void NameTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameTextFocusGained
        NameText.setSelectionStart(0);
        NameText.setSelectionEnd(NameText.getText().length());
    }//GEN-LAST:event_NameTextFocusGained

    private void AmountTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AmountTextFocusGained
        AmountText.setSelectionStart(0);
        AmountText.setSelectionEnd(AmountText.getText().length());
    }//GEN-LAST:event_AmountTextFocusGained

    private void NameTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NameTextKeyTyped
        if (evt.getKeyChar() == 10){
            BigDecimal amount = null;
            try {
                amount = new BigDecimal(AmountText.getText());
            } catch (Exception exp){
                JOptionPane.showMessageDialog(this, "Amount seems wrong...", "Oops!", JOptionPane.PLAIN_MESSAGE);
                AmountText.setSelectionStart(0);
                AmountText.setSelectionEnd(AmountText.getText().length());
                return;
            }
            
            try {
                LogicController.addBudgetCategory(myEvent, NameText.getText(), amount.multiply(new BigDecimal(""+100)).toBigInteger().longValue());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage()+"..", "Oops!", JOptionPane.PLAIN_MESSAGE);             
                return; 
            }
            this.dispose();
        }
    }//GEN-LAST:event_NameTextKeyTyped

    private void AddBudgetCategoryButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddBudgetCategoryButtonMousePressed

        AddBudgetCategoryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbuttonclick.png")));     }//GEN-LAST:event_AddBudgetCategoryButtonMousePressed

    Event myEvent;
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddBudgetCategory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddBudgetCategory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddBudgetCategory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddBudgetCategory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                AddBudgetCategory dialog = new AddBudgetCategory(new javax.swing.JFrame(), true,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddBudgetCategoryBackground;
    private javax.swing.JButton AddBudgetCategoryButton;
    private javax.swing.JLabel AmountLabel;
    private javax.swing.JTextField AmountText;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JTextField NameText;
    // End of variables declaration//GEN-END:variables
}