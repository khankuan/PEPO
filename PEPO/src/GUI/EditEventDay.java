
package GUI;

import Logic.Event.Event;
import Logic.LogicController;
import Logic.Memo.EventDay;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/*
 * @author kuan
 */
public class EditEventDay extends javax.swing.JDialog {

    EventDay myEventDay;
    /** Creates new form AddEventDay */
    public EditEventDay(java.awt.Frame parent, boolean modal, EventDay ed) {
        super(parent, modal);
        initComponents();
        
        myEventDay = ed;
        DescriptionText.setText(myEventDay.getDescription());
        StartCalendar.setDate(myEventDay.getDate());
        GregorianCalendar sd = new GregorianCalendar();
        sd.setTime(ed.getDate());
        StartHour.setSelectedIndex(sd.get(GregorianCalendar.HOUR_OF_DAY));
        StartMinute.setSelectedItem((sd.get(GregorianCalendar.MINUTE)+""));
        EndCalendar.setDate(myEventDay.getEndDate());
        GregorianCalendar d = new GregorianCalendar();
        d.setTime(ed.getEndDate());
        EndHour.setSelectedIndex(d.get(GregorianCalendar.HOUR_OF_DAY));
        EndMinute.setSelectedItem((d.get(GregorianCalendar.MINUTE)+""));
        NotesTextArea.setText(myEventDay.getNotes());
        if (ed.getItinerary() != null)
            GUIUpdater.updateItineraryTable(ed.getItinerary());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        Background = new javax.swing.JPanel();
        DescriptionLabel = new javax.swing.JLabel();
        NotesLabel = new javax.swing.JLabel();
        StartTimeLabel = new javax.swing.JLabel();
        StartHour = new javax.swing.JComboBox();
        StartMinute = new javax.swing.JComboBox();
        ItineraryListTableBackground = new javax.swing.JScrollPane();
        ItineraryTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };
        ItineraryLabel = new javax.swing.JLabel();
        EditItineraryButton = new javax.swing.JButton();
        DescriptionText = new javax.swing.JTextField();
        EndHour = new javax.swing.JComboBox();
        EndMinute = new javax.swing.JComboBox();
        DoneButton = new javax.swing.JButton();
        StartCalendar = new com.toedter.calendar.JDateChooser();
        StartTimeLabel1 = new javax.swing.JLabel();
        EndCalendar = new com.toedter.calendar.JDateChooser();
        DescriptionTextBackground = new javax.swing.JScrollPane();
        NotesTextArea = new javax.swing.JTextArea();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFocusable(false);
        setResizable(false);

        Background.setBackground(new java.awt.Color(227, 227, 227));
        Background.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        Background.setFocusable(false);
        Background.setLayout(null);

        DescriptionLabel.setFont(new java.awt.Font("Ubuntu", 1, 14));
        DescriptionLabel.setText("Title:");
        Background.add(DescriptionLabel);
        DescriptionLabel.setBounds(20, 30, 60, 19);

        NotesLabel.setFont(new java.awt.Font("Ubuntu", 1, 14));
        NotesLabel.setText("Notes:");
        Background.add(NotesLabel);
        NotesLabel.setBounds(20, 60, 60, 19);

        StartTimeLabel.setFont(new java.awt.Font("Ubuntu", 1, 14));
        StartTimeLabel.setText("End:");
        Background.add(StartTimeLabel);
        StartTimeLabel.setBounds(20, 240, 60, 19);

        StartHour.setFont(new java.awt.Font("Ubuntu", 0, 11));
        StartHour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12am", "1am", "2am", "3am", "4am", "5am", "6am", "7am", "8am", "9am", "10am", "11am", "12noon", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm", "7pm", "8pm", "9pm", "10pm", "11pm" }));
        StartHour.setBorder(null);
        StartHour.setFocusable(false);
        Background.add(StartHour);
        StartHour.setBounds(190, 210, 60, 19);

        StartMinute.setFont(new java.awt.Font("Ubuntu", 0, 11));
        StartMinute.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "15", "30", "45" }));
        StartMinute.setBorder(null);
        StartMinute.setFocusable(false);
        Background.add(StartMinute);
        StartMinute.setBounds(260, 210, 61, 19);

        ItineraryListTableBackground.setFocusable(false);

        ItineraryTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
        ItineraryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name"
            }
        ));
        ItineraryTable.setFocusable(false);
        ItineraryTable.setRowSelectionAllowed(false);
        ItineraryTable.setShowHorizontalLines(false);
        ItineraryTable.setShowVerticalLines(false);
        ItineraryTable.setTableHeader(null);
        ItineraryListTableBackground.setViewportView(ItineraryTable);

        Background.add(ItineraryListTableBackground);
        ItineraryListTableBackground.setBounds(340, 40, 210, 160);

        ItineraryLabel.setFont(new java.awt.Font("Ubuntu", 1, 14));
        ItineraryLabel.setText("Itinerary:");
        ItineraryLabel.setFocusable(false);
        Background.add(ItineraryLabel);
        ItineraryLabel.setBounds(339, 14, 120, 19);

        EditItineraryButton.setFont(new java.awt.Font("Ubuntu", 0, 11)); // NOI18N
        EditItineraryButton.setText("View");
        EditItineraryButton.setFocusable(false);
        EditItineraryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditItineraryButtonActionPerformed(evt);
            }
        });
        Background.add(EditItineraryButton);
        EditItineraryButton.setBounds(490, 10, 59, 23);

        DescriptionText.setFont(new java.awt.Font("Ubuntu", 0, 14));
        DescriptionText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        DescriptionText.setMargin(new java.awt.Insets(3, 3, 3, 3));
        DescriptionText.setNextFocusableComponent(NotesTextArea);
        DescriptionText.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        DescriptionText.setSelectionColor(new java.awt.Color(255, 204, 51));
        DescriptionText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                DescriptionTextKeyTyped(evt);
            }
        });
        Background.add(DescriptionText);
        DescriptionText.setBounds(80, 20, 240, 33);
        DescriptionText.setBorder(BorderFactory.createCompoundBorder(
            DescriptionText.getBorder(),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)));

    EndHour.setFont(new java.awt.Font("Ubuntu", 0, 11));
    EndHour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12am", "1am", "2am", "3am", "4am", "5am", "6am", "7am", "8am", "9am", "10am", "11am", "12noon", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm", "7pm", "8pm", "9pm", "10pm", "11pm" }));
    EndHour.setBorder(null);
    EndHour.setFocusable(false);
    Background.add(EndHour);
    EndHour.setBounds(190, 240, 60, 19);

    EndMinute.setFont(new java.awt.Font("Ubuntu", 0, 11));
    EndMinute.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "15", "30", "45" }));
    EndMinute.setBorder(null);
    EndMinute.setFocusable(false);
    Background.add(EndMinute);
    EndMinute.setBounds(260, 240, 61, 19);

    DoneButton.setBackground(new java.awt.Color(255, 255, 255));
    DoneButton.setFont(new java.awt.Font("Segoe Print", 1, 14));
    DoneButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png"))); // NOI18N
    DoneButton.setBorder(null);
    DoneButton.setBorderPainted(false);
    DoneButton.setContentAreaFilled(false);
    DoneButton.setFocusPainted(false);
    DoneButton.setFocusable(false);
    DoneButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
    DoneButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            DoneButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            DoneButtonMousePressed(evt);
        }
    });
    DoneButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            DoneButtonMouseMoved(evt);
        }
    });
    DoneButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            DoneButtonActionPerformed(evt);
        }
    });
    Background.add(DoneButton);
    DoneButton.setBounds(510, 230, 38, 38);
    Background.add(StartCalendar);
    StartCalendar.setBounds(80, 210, 100, 20);

    StartTimeLabel1.setFont(new java.awt.Font("Ubuntu", 1, 14));
    StartTimeLabel1.setText("Start:");
    Background.add(StartTimeLabel1);
    StartTimeLabel1.setBounds(20, 210, 60, 19);
    Background.add(EndCalendar);
    EndCalendar.setBounds(80, 240, 100, 20);

    DescriptionTextBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

    NotesTextArea.setColumns(20);
    NotesTextArea.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
    NotesTextArea.setLineWrap(true);
    NotesTextArea.setRows(1);
    NotesTextArea.setWrapStyleWord(true);
    NotesTextArea.setSelectedTextColor(new java.awt.Color(0, 0, 0));
    NotesTextArea.setSelectionColor(new java.awt.Color(255, 204, 51));
    DescriptionTextBackground.setViewportView(NotesTextArea);
    NotesTextArea.setBorder(BorderFactory.createCompoundBorder(
        NotesTextArea.getBorder(),
        BorderFactory.createEmptyBorder(0, 3, 0, 3)));

Background.add(DescriptionTextBackground);
DescriptionTextBackground.setBounds(80, 60, 240, 140);

javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
getContentPane().setLayout(layout);
layout.setHorizontalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addComponent(Background, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(Background, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DoneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoneButtonActionPerformed
        GregorianCalendar start = new GregorianCalendar();
        start.setTime(StartCalendar.getDate());
        
        start.set(GregorianCalendar.HOUR_OF_DAY, StartHour.getSelectedIndex());
        start.set(GregorianCalendar.MINUTE, Integer.parseInt((String)StartMinute.getSelectedItem()));
        start.set(GregorianCalendar.SECOND,0);
        start.set(GregorianCalendar.MILLISECOND,0); 
        
        GregorianCalendar end = new GregorianCalendar();
        end.setTime(EndCalendar.getDate());
        end.set(GregorianCalendar.HOUR_OF_DAY, EndHour.getSelectedIndex());
        
        end.set(GregorianCalendar.MINUTE, Integer.parseInt((String)EndMinute.getSelectedItem()));
        end.set(GregorianCalendar.SECOND,0);
        end.set(GregorianCalendar.MILLISECOND,0); 
        
        try {
            LogicController.setEventDayDescription(myEventDay, DescriptionText.getText());
            LogicController.setEventDayNotes(myEventDay, NotesTextArea.getText());
            LogicController.setEventDayDate(myEventDay, start.getTime(),end.getTime());
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage()+"..", "Oops!", JOptionPane.PLAIN_MESSAGE);  
            return;
        }
    }//GEN-LAST:event_DoneButtonActionPerformed

    private void EditItineraryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditItineraryButtonActionPerformed
        ViewItinerary viewpl = new ViewItinerary(this, true, myEventDay.getItinerary());
        viewpl.setTitle("View Itinerary");
        viewpl.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        viewpl.setLocationRelativeTo(null);

        //Display the window.
        //Darken.setVisible(true);
        viewpl.pack();
        viewpl.setVisible(true);
        //Darken.setVisible(false);
    }//GEN-LAST:event_EditItineraryButtonActionPerformed

    private void DescriptionTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DescriptionTextKeyTyped
        if (evt.getKeyChar() == 10){
            GregorianCalendar start = new GregorianCalendar();
            start.setTime(StartCalendar.getDate());

            start.set(GregorianCalendar.HOUR_OF_DAY, StartHour.getSelectedIndex());
            start.set(GregorianCalendar.MINUTE, Integer.parseInt((String)StartMinute.getSelectedItem()));
            start.set(GregorianCalendar.SECOND,0);
            start.set(GregorianCalendar.MILLISECOND,0); 

            GregorianCalendar end = new GregorianCalendar();
            end.setTime(EndCalendar.getDate());
            end.set(GregorianCalendar.HOUR_OF_DAY, EndHour.getSelectedIndex());

            end.set(GregorianCalendar.MINUTE, Integer.parseInt((String)EndMinute.getSelectedItem()));
            end.set(GregorianCalendar.SECOND,0);
            end.set(GregorianCalendar.MILLISECOND,0); 

            try {
                LogicController.setEventDayDescription(myEventDay, DescriptionText.getText());
                LogicController.setEventDayNotes(myEventDay, NotesTextArea.getText());
                LogicController.setEventDayDate(myEventDay, start.getTime(), end.getTime());
                
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage()+"..", "Oops!", JOptionPane.PLAIN_MESSAGE);  
                return;
            }
        }
    }//GEN-LAST:event_DescriptionTextKeyTyped

    private void DoneButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DoneButtonMouseExited
        DoneButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png"))); 
    }//GEN-LAST:event_DoneButtonMouseExited

    private void DoneButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DoneButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = DoneButton.getWidth()/2 - x;
        double dy = DoneButton.getHeight()/2 - y;
        double sr = Math.sqrt(dx*dx + dy*dy );
        if (sr <= DoneButton.getWidth()/2)
            DoneButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbuttonhover.png")));
        else
            DoneButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png")));
    }//GEN-LAST:event_DoneButtonMouseMoved

    private void DoneButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DoneButtonMousePressed
        DoneButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbuttonclick.png"))); 
    }//GEN-LAST:event_DoneButtonMousePressed

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
            java.util.logging.Logger.getLogger(AddEventDay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddEventDay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddEventDay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddEventDay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                EditEventDay dialog = new EditEventDay(new javax.swing.JFrame(), true, null);
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
   
    Event myEvent;
    
    public static javax.swing.JTable getItineraryTable(){
        return ItineraryTable;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private javax.swing.JLabel DescriptionLabel;
    private javax.swing.JTextField DescriptionText;
    private javax.swing.JScrollPane DescriptionTextBackground;
    private javax.swing.JButton DoneButton;
    private javax.swing.JButton EditItineraryButton;
    private com.toedter.calendar.JDateChooser EndCalendar;
    private javax.swing.JComboBox EndHour;
    private javax.swing.JComboBox EndMinute;
    private javax.swing.JLabel ItineraryLabel;
    private javax.swing.JScrollPane ItineraryListTableBackground;
    private static javax.swing.JTable ItineraryTable;
    private javax.swing.JLabel NotesLabel;
    private javax.swing.JTextArea NotesTextArea;
    private com.toedter.calendar.JDateChooser StartCalendar;
    private javax.swing.JComboBox StartHour;
    private javax.swing.JComboBox StartMinute;
    private javax.swing.JLabel StartTimeLabel;
    private javax.swing.JLabel StartTimeLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}