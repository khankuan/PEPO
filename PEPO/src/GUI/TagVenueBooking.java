
package GUI;

import Logic.LogicController;
import Logic.Memo.Day;
import Logic.Memo.EventDay;
import Logic.Memo.MemoItem;
import Logic.Memo.Task;
import Logic.Venue.VenueBooking;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * @author kuan
 */
public class TagVenueBooking extends javax.swing.JDialog {

    /** Creates new form AddBudgetCategory */
    public TagVenueBooking(java.awt.Frame parent, boolean modal,VenueBooking vb) throws Exception {
        super(parent, modal);
        initComponents();
        venuebooking = vb;
        GregorianCalendar start = new GregorianCalendar();
        start.setTime(vb.getDate());
        start.set(GregorianCalendar.MINUTE,0);
        start.set(GregorianCalendar.SECOND, 0);
        start.set(GregorianCalendar.HOUR_OF_DAY,0);
        start.set(GregorianCalendar.MILLISECOND,0);

        ArrayList<Day> arr = LogicController.getFilteredDayScheduleByDate(start.getTime(), LogicController.getEventList(),false);
        result = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i).getDate().after(vb.getEndDate()))
                 break;
            for (int j = 0; j < arr.get(i).getMemoItemList().size(); j++){
                if (arr.get(i).getMemoItemList().get(j) instanceof EventDay){
                    if (((EventDay)arr.get(i).getMemoItemList().get(j)).getEndDate().before(vb.getDate()))
                        continue;
                    if (((EventDay)arr.get(i).getMemoItemList().get(j)).getDate().after(vb.getEndDate()))
                        continue;
                }
                result.add(arr.get(i).getMemoItemList().get(j));
            }
            
        }
        
        SimpleDateFormat date = new SimpleDateFormat("h:mma, d MMM");



        for (int i = 0 ; i < result.size(); i++){
            
            // checkbox
            boolean found = false;
            
            ArrayList<VenueBooking> vblist = ((MemoItem)result.get(i)).getTaggedVenueBookingList();
            ((DefaultTableModel) MemoItemTable.getModel()).setRowCount(result.size());
            for (int j = 0; j < vblist.size(); j++){
                if (vblist.get(j) == vb){
                    found = true;
                    break;
                }
            }
            if (found){
                MemoItemTable.getModel().setValueAt(true, i, 0);
                tagged.add(1);
            } else {
                MemoItemTable.getModel().setValueAt(false, i, 0);
                tagged.add(0);
            }
            
            // icon
            if(result.get(i) instanceof Task)
                MemoItemTable.getModel().setValueAt(PEPOGUI.getTaskIcon(), i, 1);
            else if(result.get(i) instanceof EventDay)
                MemoItemTable.getModel().setValueAt(PEPOGUI.getEventdayIcon(), i, 1);
            
            // description
            MemoItemTable.setValueAt(((MemoItem)result.get(i)).getDescription(),i,2);
            
            // event
            if (((MemoItem)result.get(i)).getEvent() != null)
                MemoItemTable.setValueAt(((MemoItem)result.get(i)).getEvent().getTitle(),i,3);
            else
                MemoItemTable.setValueAt("none",i,3);
            
            // date
            String curTime = date.format(((MemoItem)result.get(i)).getDate());
            //if (curTime.charAt(0) == '0')
            //    curTime = "12"+curTime.substring(1);
            MemoItemTable.setValueAt(curTime, i, 4);
        }
        

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
        AttributeTableBackground = new javax.swing.JScrollPane();
        MemoItemTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }};
            SubmitTagsButton = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setResizable(false);

            AddBudgetCategoryBackground.setBackground(new java.awt.Color(255, 255, 255));

            AttributeTableBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

            MemoItemTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
            MemoItemTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "", "", "Task/Event Day", "Event", "Date"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.Boolean.class, ImageIcon.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
                };
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            MemoItemTable.setToolTipText("");
            MemoItemTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            MemoItemTable.setFocusable(false);
            MemoItemTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
            MemoItemTable.setRequestFocusEnabled(false);
            MemoItemTable.setRowHeight(20);
            MemoItemTable.setSelectionBackground(new java.awt.Color(255, 204, 51));
            MemoItemTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
            MemoItemTable.setShowHorizontalLines(false);
            MemoItemTable.setShowVerticalLines(false);
            MemoItemTable.setUpdateSelectionOnSort(false);
            MemoItemTable.setVerifyInputWhenFocusTarget(false);
            MemoItemTable.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    MemoItemTableMouseClicked(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    MemoItemTableMouseExited(evt);
                }
            });
            MemoItemTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseMoved(java.awt.event.MouseEvent evt) {
                    MemoItemTableMouseMoved(evt);
                }
            });
            AttributeTableBackground.setViewportView(MemoItemTable);
            MemoItemTable.getColumnModel().getColumn(0).setPreferredWidth(25);
            MemoItemTable.getColumnModel().getColumn(0).setMinWidth(25);
            MemoItemTable.getColumnModel().getColumn(0).setMaxWidth(25);

            MemoItemTable.getColumnModel().getColumn(1).setPreferredWidth(25);
            MemoItemTable.getColumnModel().getColumn(1).setMinWidth(25);
            MemoItemTable.getColumnModel().getColumn(1).setMaxWidth(25);

            MemoItemTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            MemoItemTable.getColumnModel().getColumn(2).setMinWidth(150);
            MemoItemTable.getColumnModel().getColumn(2).setMaxWidth(150);

            MemoItemTable.getColumnModel().getColumn(3).setPreferredWidth(130);
            MemoItemTable.getColumnModel().getColumn(3).setMinWidth(130);
            MemoItemTable.getColumnModel().getColumn(3).setMaxWidth(130);

            MemoItemTable.getTableHeader().setFont(new java.awt.Font("Ubuntu Bold", 1, 14));
            DefaultTableCellRenderer newrendererEventFilter = new DefaultTableCellRenderer();
            newrendererEventFilter.setBackground(new Color(51,51,51));
            newrendererEventFilter.setForeground(Color.white);
            MemoItemTable.getTableHeader().setDefaultRenderer(newrendererEventFilter);
            MemoItemTable.getTableHeader().setReorderingAllowed(false);

            SubmitTagsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png"))); // NOI18N
            SubmitTagsButton.setBorderPainted(false);
            SubmitTagsButton.setContentAreaFilled(false);
            SubmitTagsButton.setFocusable(false);
            SubmitTagsButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
            SubmitTagsButton.setMaximumSize(new java.awt.Dimension(41, 41));
            SubmitTagsButton.setMinimumSize(new java.awt.Dimension(41, 41));
            SubmitTagsButton.setPreferredSize(new java.awt.Dimension(41, 41));
            SubmitTagsButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    SubmitTagsButtonMouseExited(evt);
                }
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    SubmitTagsButtonMousePressed(evt);
                }
            });
            SubmitTagsButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseMoved(java.awt.event.MouseEvent evt) {
                    SubmitTagsButtonMouseMoved(evt);
                }
            });
            SubmitTagsButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    SubmitTagsButtonActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout AddBudgetCategoryBackgroundLayout = new javax.swing.GroupLayout(AddBudgetCategoryBackground);
            AddBudgetCategoryBackground.setLayout(AddBudgetCategoryBackgroundLayout);
            AddBudgetCategoryBackgroundLayout.setHorizontalGroup(
                AddBudgetCategoryBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddBudgetCategoryBackgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(AttributeTableBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(SubmitTagsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
            AddBudgetCategoryBackgroundLayout.setVerticalGroup(
                AddBudgetCategoryBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AddBudgetCategoryBackgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(AddBudgetCategoryBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(AttributeTableBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SubmitTagsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(14, Short.MAX_VALUE))
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(AddBudgetCategoryBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(AddBudgetCategoryBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

        private void SubmitTagsButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SubmitTagsButtonMouseExited
        SubmitTagsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png")));
    }//GEN-LAST:event_SubmitTagsButtonMouseExited

    private void SubmitTagsButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SubmitTagsButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = SubmitTagsButton.getWidth()/2 - x;
        double dy = SubmitTagsButton.getHeight()/2 - y;
        double sr = Math.sqrt(dx*dx + dy*dy );
        if (sr <= SubmitTagsButton.getWidth()/2)
            SubmitTagsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbuttonhover.png")));
        else
            SubmitTagsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png")));
    }//GEN-LAST:event_SubmitTagsButtonMouseMoved

    private void SubmitTagsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitTagsButtonActionPerformed
        for (int i = 0; i < MemoItemTable.getModel().getRowCount(); i++){
            if (tagged.get(i) == 0 && MemoItemTable.getValueAt(i, 0).equals(true)){
                if (result.get(i) instanceof Task)
                    LogicController.tagTaskVenueBooking((Task)result.get(i), venuebooking);
                else
                    LogicController.tagEventDayVenueBooking((EventDay)result.get(i), venuebooking);
            }
            
            if (tagged.get(i) != 0 && MemoItemTable.getValueAt(i,0).equals(false)){
                if (result.get(i) instanceof Task)
                    try {
                    LogicController.untagTaskVenueBooking((Task)result.get(i), venuebooking);
                } catch (Exception ex) {
                    Logger.getLogger(TagVenueBooking.class.getName()).log(Level.SEVERE, null, ex);
                }
                else
                    try {
                    LogicController.untagEventDayVenueBooking((EventDay)result.get(i), venuebooking);
                } catch (Exception ex) {
                    Logger.getLogger(TagVenueBooking.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }         
        
        this.dispose();     
    }//GEN-LAST:event_SubmitTagsButtonActionPerformed

    private void MemoItemTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MemoItemTableMouseExited
        MemoItemTable.clearSelection();
    }//GEN-LAST:event_MemoItemTableMouseExited

    private void MemoItemTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MemoItemTableMouseMoved

        
        int at = MemoItemTable.rowAtPoint(evt.getPoint());
        if (at != -1)
            MemoItemTable.setRowSelectionInterval(at, at);
        else
            MemoItemTable.clearSelection();
    }//GEN-LAST:event_MemoItemTableMouseMoved

    private void MemoItemTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MemoItemTableMouseClicked
        if (MemoItemTable.getModel().getValueAt(MemoItemTable.rowAtPoint(evt.getPoint()), 0).equals(false)) {
            MemoItemTable.getModel().setValueAt(true, MemoItemTable.rowAtPoint(evt.getPoint()), 0);
        } else {
            MemoItemTable.getModel().setValueAt(false, MemoItemTable.rowAtPoint(evt.getPoint()), 0);
        }
    }//GEN-LAST:event_MemoItemTableMouseClicked

    private void SubmitTagsButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SubmitTagsButtonMousePressed
        SubmitTagsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbuttonclick.png")));
    }//GEN-LAST:event_SubmitTagsButtonMousePressed

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
            java.util.logging.Logger.getLogger(TagVenueBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TagVenueBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TagVenueBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TagVenueBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                TagVenueBooking dialog = null;
                try {
                    dialog = new TagVenueBooking(new javax.swing.JFrame(), true,null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
    
    ArrayList<Integer> tagged = new ArrayList<>();
    ArrayList<Object> result = new ArrayList<>();
    VenueBooking venuebooking;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddBudgetCategoryBackground;
    private javax.swing.JScrollPane AttributeTableBackground;
    private javax.swing.JTable MemoItemTable;
    private static javax.swing.JButton SubmitTagsButton;
    // End of variables declaration//GEN-END:variables
}
