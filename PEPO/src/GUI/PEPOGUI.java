package GUI;

import Database.Pair;
import Export.Exporter;
import Logic.Budget.BudgetCategory;
import Logic.Budget.Expense;
import Logic.Event.Event;
import Logic.LogicController;
import Logic.Memo.EventDay;
import Logic.Memo.Task;
import Logic.PersonnelList.PersonnelList;
import Logic.Venue.Venue;
import Logic.Venue.VenueBooking;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.table.TableRowSorter;

/*
 * @author kuan, hongwei
 */
public class PEPOGUI extends javax.swing.JFrame {

    /** Creates new form PEPOGUI */
    public static Font STEFont;
    public static BufferedImage programmeDuration;
    public static BufferedImage programmeDuration2;
    public static ImageIcon venueIcon;
    public static ImageIcon eventdayIcon;
    public static ImageIcon taskIcon;
    public static ImageIcon taskCompletedIcon;
    public static Point mouseDownCompCoords;
    public static TrayIcon myIcon;
    public int counter = 0;
    
    public PEPOGUI() throws UnsupportedLookAndFeelException, Exception {
        try {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("OptionPane.background", new Color(255, 255, 255));
            UIManager.put("Viewport.background", new Color(255, 255, 255));
            UIManager.put("TabbedPane.background", new Color(255, 255, 255));
            UIManager.put("ScrollPane.background", new Color(255, 255, 255));
            UIManager.put("Panel.background", new Color(255, 255, 255));
            UIManager.put("controlShadow", new Color(255, 255, 255));
            UIManager.put("Button.shadow", new Color(255, 202, 51));
            UIManager.put("", new Color(255, 255, 255));
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            
        } 
        
        try {
            LogicController.start();
        } catch (Exception obj) {
            JOptionPane.showMessageDialog(this, obj.getMessage() + "... Remove folder \"Data\" and restart PEPO.", "Initialisation Error", JOptionPane.PLAIN_MESSAGE);
        }
        
        InputStream is = this.getClass().getResourceAsStream("/font/Ubuntu-R.ttf");
        STEFont = Font.createFont(Font.TRUETYPE_FONT, is);
        STEFont = STEFont.deriveFont(15.0f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(STEFont);
        
        is = this.getClass().getResourceAsStream("/font/Ubuntu-B.ttf");
        STEFont = Font.createFont(Font.TRUETYPE_FONT, is);
        STEFont = STEFont.deriveFont(15.0f);
        ge.registerFont(STEFont);
        
        
        boolean canTranslucentboolean = true;
        // check if there is support for translucency
        try {
            canTranslucent.init();
        } catch (Throwable obj) {
            canTranslucentboolean = false;
        }
        if (canTranslucentboolean) {
            if (canTranslucent.check()) {
                setUndecorated(true);
            }
        }
        
        try {
            programmeDuration = ImageIO.read(new File("src/images/box.png"));
            programmeDuration2 = ImageIO.read(new File("src/images/box2.png"));
            venueIcon = new javax.swing.ImageIcon(getClass().getResource("/images/venue.png"));
            eventdayIcon = new javax.swing.ImageIcon(getClass().getResource("/images/eventday.png"));
            taskIcon = new javax.swing.ImageIcon(getClass().getResource("/images/task.png"));
            taskCompletedIcon = new javax.swing.ImageIcon(getClass().getResource("/images/taskdone.png"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot load images. Please reinstall PEPO.", "Initialisation Error", JOptionPane.PLAIN_MESSAGE);
        }
        
        initComponents();

        // set background and frame
        if (canTranslucentboolean) {
            if (canTranslucent.check()) {
                Background.setOpaque(false);
                setBackground(new Color(0, 0, 0, 0));
            }
        }
        
        updateMemo();
        
        myIcon = new TrayIcon(ImageIO.read(new File("src/images/trayicon.png")), "PEPO");
        myIcon.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPEPOVisible) 
                    showPEPO(false);                   
                else 
                    showPEPO(true);  
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        SystemTray.getSystemTray().add(myIcon);
        for (int i = 0; i < myIcon.getActionListeners().length; i++)
            System.out.println(myIcon.getActionListeners().toString());
        
    }
    
    private void showPEPO(boolean type) {
        isPEPOVisible = type;
        ((JFrame) this).setVisible(type);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new javax.swing.JPanel();
        CloseButton = new javax.swing.JButton();
        LayoutButton = new javax.swing.JButton();
        Logo = new javax.swing.JLabel();
        Darken = new javax.swing.JLabel();
        MainTab = new javax.swing.JTabbedPane();
        MemoTab = new javax.swing.JLayeredPane();
        MemoTabBackground = new javax.swing.JPanel();
        ScheduleByDateArea = new javax.swing.JPanel();
        ScheduleByDateBackground = new javax.swing.JScrollPane();
        ScheduleByDateTable = new javax.swing.JTable(){
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
        EventFilterTableBackground = new javax.swing.JScrollPane();
        class CheckboxModel extends AbstractTableModel {
            private String[] columnNames = {"", "Event"};
            private ArrayList<ArrayList<Object>> data = new ArrayList<>();

            public int getColumnCount() {
                return columnNames.length;
            }

            public int getRowCount() {
                return data.size();
            }

            public String getColumnName(int col) {
                return columnNames[col];
            }

            public Object getValueAt(int row, int col) {
                return data.get(row).get(col);
            }

            public Class getColumnClass(int c) {
                if(c==0) return Boolean.class;
                else return String.class;
            }

            public void setValueAt(Object value, int row, int col) {
                if(row == -1) { // clear table
                    data = new ArrayList<>();
                } else {
                    while(row>=getRowCount())
                    data.add(new ArrayList<>());
                    data.get(row).add(new Object());
                    data.get(row).add(new Object());
                    data.get(row).set(col, value);
                }
                fireTableDataChanged();
            }

            public void setRowCount(int s) {
                data.clear();
                for (int i = 0; i < s; i++)
                data.add(new ArrayList<>());
            }
        }
        EventFilterTable = new javax.swing.JTable(){
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
        MemoCalendar = new com.toedter.calendar.JDateChooser();
        TaskToDoLabel = new javax.swing.JLabel();
        MemoSeparator = new javax.swing.JLabel();
        TaskByPriorityArea = new javax.swing.JPanel();
        TaskByPriorityAreaBackground = new javax.swing.JScrollPane();
        TaskByPriorityTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                if(column == 0) return null;

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };
        AddTaskButton = new javax.swing.JButton();
        CompleteTaskButton = new javax.swing.JButton();
        DeleteTaskButton = new javax.swing.JButton();
        EventFilterBackground = new javax.swing.JPanel();
        EditTaskButton = new javax.swing.JButton();
        TaskToDoLabel1 = new javax.swing.JLabel();
        EventTab = new javax.swing.JLayeredPane();
        EventTabBackground = new javax.swing.JPanel();
        EventListBackground = new javax.swing.JScrollPane();
        EventList = new javax.swing.JList();
        EventListLabel = new javax.swing.JLabel();
        AddEventButton = new javax.swing.JButton();
        EventDetailsTab = new javax.swing.JTabbedPane();
        EventDayTab = new javax.swing.JLayeredPane();
        EventDayBackground = new javax.swing.JPanel();
        EventDayTableBackground = new javax.swing.JScrollPane();
        EventDayTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                if(column == 0) return null;

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };
        AddEventDayButton = new javax.swing.JButton();
        EditEventDayButton = new javax.swing.JButton();
        DeleteEventDayButton = new javax.swing.JButton();
        EventDayNotesTextBackground = new javax.swing.JScrollPane();
        EventDayNotesText = new javax.swing.JTextArea();
        EventDayItineraryTextBackground = new javax.swing.JScrollPane();
        EventDayItineraryText = new javax.swing.JTextArea();
        BudgetTab = new javax.swing.JLayeredPane();
        BudgetBackground = new javax.swing.JPanel();
        RemainingBudgetLabel = new javax.swing.JLabel();
        TotalBudgetText = new javax.swing.JTextField();
        TotalExpenseLabel = new javax.swing.JLabel();
        TotalExpenseText = new javax.swing.JTextField();
        AddExpenseBackground = new javax.swing.JPanel();
        ExpenseTableBackground = new javax.swing.JScrollPane();
        ExpenseTable = new javax.swing.JTable(){
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
        DeleteBudgetExpenseButton = new javax.swing.JButton();
        EditBudgetButton = new javax.swing.JButton();
        CategoryLabel = new javax.swing.JLabel();
        DescriptionLabel = new javax.swing.JLabel();
        AmountLabel = new javax.swing.JLabel();
        ExpenseAmountText = new javax.swing.JTextField();
        ExpenseDescriptionText = new javax.swing.JTextField();
        CategoryDropDown = new javax.swing.JComboBox();
        AddExpenseButton = new javax.swing.JButton();
        EditBudgetExpenseButton = new javax.swing.JButton();
        EventListLabel2 = new javax.swing.JLabel();
        RemainingBudgetText = new javax.swing.JTextField();
        Separator2 = new javax.swing.JLabel();
        Separator = new javax.swing.JLabel();
        ParticipantTab = new javax.swing.JLayeredPane();
        ParticipantArea = new javax.swing.JPanel();
        ParticipantTableBackground = new javax.swing.JScrollPane();
        ContactsTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                if (ContactsEditClicked == -1)
                return false;   //Disallow the editing of any cell
                else
                return true;
            }
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };
        EditContactsButton = new javax.swing.JButton();
        EditContactsHeaderButton = new javax.swing.JButton();
        AddContactsButton = new javax.swing.JButton();
        DeleteContactsButton = new javax.swing.JButton();
        ImportContactsButton = new javax.swing.JButton();
        ContactsListDropDown = new javax.swing.JComboBox();
        CancelContactsButton = new javax.swing.JButton();
        CountLabel = new javax.swing.JLabel();
        CountText = new javax.swing.JTextField();
        ContactsInstruction = new javax.swing.JLabel();
        EmailTab = new javax.swing.JLayeredPane();
        AnnouncementButton = new javax.swing.JButton();
        AnnouncementSubjectText = new javax.swing.JTextField();
        AnnouncementUsernameText = new javax.swing.JTextField();
        AnnouncementPasswordText = new javax.swing.JPasswordField();
        BodyTextBackground = new javax.swing.JScrollPane();
        AnnouncementBodyText = new javax.swing.JTextArea();
        AnnouncementContactListTableBackground = new javax.swing.JScrollPane();
        class EmailCheckboxModel extends AbstractTableModel {
            private String[] columnNames = {"", "Event"};
            private ArrayList<ArrayList<Object>> data = new ArrayList<>();

            public int getColumnCount() {
                return columnNames.length;
            }

            public int getRowCount() {
                return data.size();
            }

            public String getColumnName(int col) {
                return columnNames[col];
            }

            public Object getValueAt(int row, int col) {
                return data.get(row).get(col);
            }

            public Class getColumnClass(int c) {
                if(c==0) return Boolean.class;
                else return String.class;
            }

            public void setValueAt(Object value, int row, int col) {
                if(row == -1) { // clear table
                    data = new ArrayList<>();
                } else {
                    while(row>=getRowCount())
                    data.add(new ArrayList<>());
                    data.get(row).add(new Object());
                    data.get(row).add(new Object());
                    data.get(row).set(col, value);
                }
                fireTableDataChanged();
            }
        }
        AnnouncementEventContactListTable = new javax.swing.JTable(){
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
        OtherContactsLabel = new javax.swing.JLabel();
        BodyLabel = new javax.swing.JLabel();
        ContactsLabel = new javax.swing.JLabel();
        UsernameLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        SubjectLabel = new javax.swing.JLabel();
        AnnouncementHelpersCheckBox = new javax.swing.JCheckBox();
        AnnouncementParticipantsCheckBox = new javax.swing.JCheckBox();
        AnnouncementSponsorsCheckBox = new javax.swing.JCheckBox();
        AnnouncementVendorsCheckBox = new javax.swing.JCheckBox();
        ExportTab = new javax.swing.JLayeredPane();
        ExportButton = new javax.swing.JButton();
        GeneralExportBackground = new javax.swing.JScrollPane();
        GeneralExportTable = new javax.swing.JTable();
        EventDayExportBackground = new javax.swing.JScrollPane();
        ExportEventDayTable = new javax.swing.JTable();
        EventListLabel3 = new javax.swing.JLabel();
        EventListLabel4 = new javax.swing.JLabel();
        EventListLabel5 = new javax.swing.JLabel();
        exportSeparator = new javax.swing.JLabel();
        EditEventButton = new javax.swing.JButton();
        DeleteEventButton = new javax.swing.JButton();
        EventDescriptionTextAreaBackground = new javax.swing.JScrollPane();
        EventDescriptionTextArea = new javax.swing.JTextArea();
        ArchiveEventButton = new javax.swing.JButton();
        noEvent = new javax.swing.JLabel();
        VenueTab = new javax.swing.JLayeredPane();
        VenueTabBackground = new javax.swing.JPanel();
        NameLabel = new javax.swing.JLabel();
        TypeLabel = new javax.swing.JLabel();
        FacultyLabel = new javax.swing.JLabel();
        MinimumCapacityLabel = new javax.swing.JLabel();
        MyBookingsTableBackground = new javax.swing.JScrollPane();
        VenueBookingTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                if(column == 0) return null;

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };
        VenueTableBackground = new javax.swing.JScrollPane();
        VenueTable = new javax.swing.JTable(){
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
        CapacityText = new javax.swing.JTextField();
        TypeDropDown = new javax.swing.JComboBox();
        FacultyDropDown = new javax.swing.JComboBox();
        AddVenueBookingButton = new javax.swing.JButton();
        NameText = new javax.swing.JTextField();
        DeleteVenueBookingButton = new javax.swing.JButton();
        TagVenueBookingButton = new javax.swing.JButton();
        MyBookingsLabel1 = new javax.swing.JLabel();
        LeftSide = new javax.swing.JLabel();
        MinimiseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(800, 450));
        setName("Background\n"); // NOI18N
        setResizable(false);

        Background.setBackground(new java.awt.Color(51, 51, 0));
        Background.setPreferredSize(new java.awt.Dimension(1000, 500));
        Background.setLayout(null);

        CloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/closebutton.png"))); // NOI18N
        CloseButton.setBorderPainted(false);
        CloseButton.setContentAreaFilled(false);
        CloseButton.setFocusPainted(false);
        CloseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                CloseButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CloseButtonMousePressed(evt);
            }
        });
        CloseButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                CloseButtonMouseMoved(evt);
            }
        });
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });
        Background.add(CloseButton);
        CloseButton.setBounds(960, 20, 28, 28);

        LayoutButton.setBorderPainted(false);
        LayoutButton.setContentAreaFilled(false);
        LayoutButton.setFocusPainted(false);
        LayoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LayoutButtonActionPerformed(evt);
            }
        });
        Background.add(LayoutButton);
        LayoutButton.setBounds(4, 315, 50, 180);

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/headerlogo.png"))); // NOI18N
        Logo.setText("jLabel1");
        Logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                LogoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LogoMouseReleased(evt);
            }
        });
        Logo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                LogoMouseDragged(evt);
            }
        });
        Background.add(Logo);
        Logo.setBounds(40, 20, 210, 42);

        Darken.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/darken.png"))); // NOI18N
        Background.add(Darken);
        Darken.setBounds(62, 60, 920, 420);
        Darken.setVisible(false);

        MainTab.setBackground(new java.awt.Color(255, 255, 255));
        MainTab.setForeground(new java.awt.Color(51, 51, 51));
        MainTab.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        MainTab.setFocusable(false);
        MainTab.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        MainTab.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                MainTabStateChanged(evt);
            }
        });

        MemoTabBackground.setBackground(new java.awt.Color(255, 255, 255));
        MemoTabBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ScheduleByDateArea.setBackground(new java.awt.Color(255, 255, 255));
        ScheduleByDateArea.setPreferredSize(new java.awt.Dimension(536, 164));
        ScheduleByDateArea.setLayout(null);

        ScheduleByDateBackground.setBackground(new java.awt.Color(51, 51, 51));
        ScheduleByDateBackground.setBorder(BorderFactory.createEmptyBorder()
        );

        ScheduleByDateTable.setFont(new java.awt.Font("Ubuntu", 0, 12));
        ScheduleByDateTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        ScheduleByDateTable.setFocusable(false);
        ScheduleByDateTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        ScheduleByDateTable.setRequestFocusEnabled(false);
        ScheduleByDateTable.setRowHeight(23);
        ScheduleByDateTable.setRowSelectionAllowed(false);
        ScheduleByDateTable.setSelectionBackground(new java.awt.Color(255, 204, 51));
        ScheduleByDateTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        ScheduleByDateTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ScheduleByDateTable.setShowHorizontalLines(false);
        ScheduleByDateTable.setShowVerticalLines(false);
        ScheduleByDateTable.setTableHeader(null);
        ScheduleByDateTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ScheduleByDateTableMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ScheduleByDateTableMouseExited(evt);
            }
        });
        ScheduleByDateTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ScheduleByDateTableMouseMoved(evt);
            }
        });
        ScheduleByDateBackground.setViewportView(ScheduleByDateTable);
        ScheduleByDateTable.getColumnModel().getColumn(0).setMinWidth(100);
        ScheduleByDateTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        ScheduleByDateTable.getColumnModel().getColumn(0).setMaxWidth(100);

        ScheduleByDateTable.getColumnModel().getColumn(1).setMinWidth(35);
        ScheduleByDateTable.getColumnModel().getColumn(1).setPreferredWidth(35);
        ScheduleByDateTable.getColumnModel().getColumn(1).setMaxWidth(35);

        DefaultTableCellRenderer newrendererSchedule = new DefaultTableCellRenderer();
        newrendererSchedule.setBackground(new Color(51,51,51));
        newrendererSchedule.setForeground(Color.white);

        class scheduleTimeRenderer extends DefaultTableCellRenderer
        {
            public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column)
            {
                JLabel cell = (JLabel)super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);

                int curr = PEPOGUI.ScheduleTableType.get(row);
                if(curr == 0) {   // Date
                    cell.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
                    cell.setHorizontalAlignment(JLabel.LEFT);
                    cell.setForeground(Color.black);
                }
                else
                {
                    cell.setFont(new java.awt.Font("Ubuntu", 0, 12));
                    cell.setHorizontalAlignment(JLabel.RIGHT);
                    cell.setForeground(Color.GRAY);
                }

                return cell;
            }
        }
        ScheduleByDateTable.getColumnModel().getColumn(0).setCellRenderer(new scheduleTimeRenderer());

        class scheduleIconRenderer extends DefaultTableCellRenderer
        {
            public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column)
            {
                JLabel cell = (JLabel)super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
                cell.setHorizontalAlignment(JLabel.CENTER);
                int curr = PEPOGUI.ScheduleTableType.get(row);

                switch(curr) {
                    case 1: // eventday
                    cell.setIcon(PEPOGUI.getEventdayIcon());
                    break;
                    case 3: // task(completed)
                    cell.setIcon(PEPOGUI.getTaskCompletedIcon());
                    break;
                    case 4: // task(not completed)
                    cell.setIcon(PEPOGUI.getTaskIcon());
                    break;
                    case 6: // venuebooking
                    cell.setIcon(PEPOGUI.getVenueIcon());
                    break;
                    default:
                    cell.setIcon(null);
                }

                return cell;
            }
        }
        ScheduleByDateTable.getColumnModel().getColumn(1).setCellRenderer(new scheduleIconRenderer());

        class scheduleDescRenderer extends DefaultTableCellRenderer
        {
            public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column)
            {
                JLabel cell = (JLabel)super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
                int curr = PEPOGUI.ScheduleTableType.get(row);
                cell.setFont(new java.awt.Font("Ubuntu", 0, 12));

                switch(curr) {
                    case 1: // eventday
                    cell.setForeground(Color.red);
                    break;
                    case 2: // eventday venue
                    cell.setForeground(Color.red);
                    cell.setFont(new java.awt.Font("Ubuntu Italic", 0, 12));
                    break;
                    case 3: // task(completed)
                    case 4: // task(not completed)
                    cell.setForeground(Color.black);
                    break;
                    case 5: // task venue
                    cell.setForeground(Color.black);
                    cell.setFont(new java.awt.Font("Ubuntu Italic", 0, 12));
                    break;
                    case 6: // venuebooking
                    cell.setForeground(Color.gray);
                    break;
                }
                return cell;
            }
        }
        ScheduleByDateTable.getColumnModel().getColumn(2).setCellRenderer(new scheduleDescRenderer());

        ScheduleByDateArea.add(ScheduleByDateBackground);
        ScheduleByDateBackground.setBounds(0, 0, 430, 290);

        EventFilterTableBackground.setBorder(null
        );

        EventFilterTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
        EventFilterTable.setModel(new CheckboxModel());
        EventFilterTable.setFocusable(false);
        EventFilterTable.setGridColor(new java.awt.Color(0, 0, 0));
        EventFilterTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        EventFilterTable.setRequestFocusEnabled(false);
        EventFilterTable.setRowHeight(20);
        EventFilterTable.setSelectionBackground(new java.awt.Color(255, 204, 0));
        EventFilterTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        EventFilterTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        EventFilterTable.setShowHorizontalLines(false);
        EventFilterTable.setShowVerticalLines(false);
        EventFilterTable.setTableHeader(null);
        EventFilterTable.setUpdateSelectionOnSort(false);
        EventFilterTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EventFilterTableMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                EventFilterTableMouseExited(evt);
            }
        });
        EventFilterTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                EventFilterTableMouseMoved(evt);
            }
        });
        EventFilterTableBackground.setViewportView(EventFilterTable);
        TableColumn col = EventFilterTable.getColumnModel().getColumn(0);
        int width = 25;
        col.setMaxWidth(width);
        col.setMinWidth(width);
        col.setPreferredWidth(width);
        DefaultTableCellRenderer newrendererEventFilter = new DefaultTableCellRenderer();
        newrendererEventFilter.setBackground(new Color(51,51,51));
        newrendererEventFilter.setForeground(Color.white);

        ScheduleByDateArea.add(EventFilterTableBackground);
        EventFilterTableBackground.setBounds(160, 307, 270, 62);

        MemoCalendar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                MemoCalendarPropertyChange(evt);
            }
        });
        ScheduleByDateArea.add(MemoCalendar);
        MemoCalendar.setBounds(0, 340, 150, 30);
        MemoCalendar.setDate(new Date());
        MemoCalendar.validate();

        TaskToDoLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
        TaskToDoLabel.setText("From :");
        ScheduleByDateArea.add(TaskToDoLabel);
        TaskToDoLabel.setBounds(0, 320, 60, 20);

        MemoSeparator.setBackground(new java.awt.Color(204, 204, 204));
        MemoSeparator.setOpaque(true);
        ScheduleByDateArea.add(MemoSeparator);
        MemoSeparator.setBounds(0, 295, 430, 1);

        MemoTabBackground.add(ScheduleByDateArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 430, 370));

        TaskByPriorityArea.setBackground(new java.awt.Color(255, 255, 255));
        TaskByPriorityArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TaskByPriorityAreaBackground.setBackground(new java.awt.Color(51, 51, 51));
        TaskByPriorityAreaBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 2));

        TaskByPriorityTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
        TaskByPriorityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Task", "Deadline", ""
            }
        ) {
            Class[] types = new Class [] {
                ImageIcon.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TaskByPriorityTable.setFocusable(false);
        TaskByPriorityTable.setGridColor(new java.awt.Color(204, 204, 204));
        TaskByPriorityTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        TaskByPriorityTable.setRowHeight(20);
        TaskByPriorityTable.setSelectionBackground(new java.awt.Color(255, 204, 51));
        TaskByPriorityTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TaskByPriorityTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TaskByPriorityTable.setShowHorizontalLines(false);
        TaskByPriorityTable.setShowVerticalLines(false);
        TaskByPriorityTable.setTableHeader(null);
        TaskByPriorityTable.setUpdateSelectionOnSort(false);
        TaskByPriorityTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TaskByPriorityTableMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TaskByPriorityTableMouseExited(evt);
            }
        });
        TaskByPriorityTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TaskByPriorityTableMouseMoved(evt);
            }
        });
        TaskByPriorityAreaBackground.setViewportView(TaskByPriorityTable);
        TaskByPriorityTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        TaskByPriorityTable.getColumnModel().getColumn(0).setMinWidth(25);
        TaskByPriorityTable.getColumnModel().getColumn(0).setMaxWidth(25);

        TaskByPriorityTable.getColumnModel().getColumn(1).setPreferredWidth(240);
        TaskByPriorityTable.getColumnModel().getColumn(1).setMinWidth(240);
        TaskByPriorityTable.getColumnModel().getColumn(1).setMaxWidth(240);

        TaskByPriorityTable.getColumnModel().getColumn(2).setMinWidth(70);
        TaskByPriorityTable.getColumnModel().getColumn(2).setPreferredWidth(70);
        TaskByPriorityTable.getColumnModel().getColumn(2).setMaxWidth(70);

        DefaultTableCellRenderer newrendererPriority = new DefaultTableCellRenderer();
        newrendererPriority.setBackground(new Color(51,51,51));
        newrendererPriority.setForeground(Color.white);

        javax.swing.GroupLayout TaskByPriorityAreaLayout = new javax.swing.GroupLayout(TaskByPriorityArea);
        TaskByPriorityArea.setLayout(TaskByPriorityAreaLayout);
        TaskByPriorityAreaLayout.setHorizontalGroup(
            TaskByPriorityAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TaskByPriorityAreaBackground, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        );
        TaskByPriorityAreaLayout.setVerticalGroup(
            TaskByPriorityAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TaskByPriorityAreaBackground, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );

        MemoTabBackground.add(TaskByPriorityArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 380, 350));

        AddTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png"))); // NOI18N
        AddTaskButton.setBorderPainted(false);
        AddTaskButton.setContentAreaFilled(false);
        AddTaskButton.setFocusable(false);
        AddTaskButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AddTaskButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AddTaskButtonMousePressed(evt);
            }
        });
        AddTaskButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AddTaskButtonMouseMoved(evt);
            }
        });
        AddTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddTaskButtonActionPerformed(evt);
            }
        });
        MemoTabBackground.add(AddTaskButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 41, 41));

        CompleteTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png"))); // NOI18N
        CompleteTaskButton.setBorderPainted(false);
        CompleteTaskButton.setContentAreaFilled(false);
        CompleteTaskButton.setFocusable(false);
        CompleteTaskButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
        CompleteTaskButton.setMaximumSize(new java.awt.Dimension(41, 41));
        CompleteTaskButton.setMinimumSize(new java.awt.Dimension(41, 41));
        CompleteTaskButton.setPreferredSize(new java.awt.Dimension(41, 41));
        CompleteTaskButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                CompleteTaskButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CompleteTaskButtonMousePressed(evt);
            }
        });
        CompleteTaskButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                CompleteTaskButtonMouseMoved(evt);
            }
        });
        CompleteTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompleteTaskButtonActionPerformed(evt);
            }
        });
        MemoTabBackground.add(CompleteTaskButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, -1, -1));

        DeleteTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png"))); // NOI18N
        DeleteTaskButton.setBorderPainted(false);
        DeleteTaskButton.setContentAreaFilled(false);
        DeleteTaskButton.setFocusable(false);
        DeleteTaskButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
        DeleteTaskButton.setMaximumSize(new java.awt.Dimension(41, 41));
        DeleteTaskButton.setMinimumSize(new java.awt.Dimension(41, 41));
        DeleteTaskButton.setPreferredSize(new java.awt.Dimension(41, 41));
        DeleteTaskButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DeleteTaskButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DeleteTaskButtonMousePressed(evt);
            }
        });
        DeleteTaskButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                DeleteTaskButtonMouseMoved(evt);
            }
        });
        DeleteTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteTaskButtonActionPerformed(evt);
            }
        });
        MemoTabBackground.add(DeleteTaskButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 360, -1, -1));

        EventFilterBackground.setBackground(new java.awt.Color(255, 255, 255));
        EventFilterBackground.setPreferredSize(new java.awt.Dimension(200, 100));

        javax.swing.GroupLayout EventFilterBackgroundLayout = new javax.swing.GroupLayout(EventFilterBackground);
        EventFilterBackground.setLayout(EventFilterBackgroundLayout);
        EventFilterBackgroundLayout.setHorizontalGroup(
            EventFilterBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );
        EventFilterBackgroundLayout.setVerticalGroup(
            EventFilterBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        MemoTabBackground.add(EventFilterBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 60, 230, 290));

        EditTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png"))); // NOI18N
        EditTaskButton.setBorderPainted(false);
        EditTaskButton.setContentAreaFilled(false);
        EditTaskButton.setFocusable(false);
        EditTaskButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
        EditTaskButton.setMaximumSize(new java.awt.Dimension(41, 41));
        EditTaskButton.setMinimumSize(new java.awt.Dimension(41, 41));
        EditTaskButton.setPreferredSize(new java.awt.Dimension(41, 41));
        EditTaskButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                EditTaskButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EditTaskButtonMousePressed(evt);
            }
        });
        EditTaskButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                EditTaskButtonMouseMoved(evt);
            }
        });
        EditTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditTaskButtonActionPerformed(evt);
            }
        });
        MemoTabBackground.add(EditTaskButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 320, -1, -1));

        TaskToDoLabel1.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
        TaskToDoLabel1.setText("Your Tasks:");
        MemoTabBackground.add(TaskToDoLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        MemoTabBackground.setBounds(0, 0, 940, 440);
        MemoTab.add(MemoTabBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

        MainTab.addTab("", new javax.swing.ImageIcon(getClass().getResource("/images/memotab.png")), MemoTab); // NOI18N

        EventTabBackground.setBackground(new java.awt.Color(255, 255, 255));
        EventTabBackground.setLayout(null);

        EventListBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 2));

        EventList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        EventList.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
        EventList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        EventList.setFocusable(false);
        EventList.setRequestFocusEnabled(false);
        EventList.setSelectedIndex(0);
        EventList.setSelectionBackground(new java.awt.Color(255, 51, 0));
        EventList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                EventListValueChanged(evt);
            }
        });
        EventListBackground.setViewportView(EventList);

        EventTabBackground.add(EventListBackground);
        EventListBackground.setBounds(10, 63, 217, 219);

        EventListLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
        EventListLabel.setText("Events:");
        EventTabBackground.add(EventListLabel);
        EventListLabel.setBounds(10, 20, 60, 30);

        AddEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png"))); // NOI18N
        AddEventButton.setBorderPainted(false);
        AddEventButton.setContentAreaFilled(false);
        AddEventButton.setDefaultCapable(false);
        AddEventButton.setFocusPainted(false);
        AddEventButton.setFocusable(false);
        AddEventButton.setMaximumSize(new java.awt.Dimension(38, 38));
        AddEventButton.setMinimumSize(new java.awt.Dimension(38, 38));
        AddEventButton.setPreferredSize(new java.awt.Dimension(38, 38));
        AddEventButton.setRolloverEnabled(false);
        AddEventButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AddEventButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AddEventButtonMousePressed(evt);
            }
        });
        AddEventButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AddEventButtonMouseMoved(evt);
            }
        });
        AddEventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEventButtonActionPerformed(evt);
            }
        });
        EventTabBackground.add(AddEventButton);
        AddEventButton.setBounds(190, 20, 38, 41);

        EventDetailsTab.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        EventDetailsTab.setFocusable(false);
        EventDetailsTab.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
        EventDetailsTab.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                EventDetailsTabStateChanged(evt);
            }
        });

        EventDayBackground.setBackground(new java.awt.Color(255, 255, 255));

        EventDayTableBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 3));

        EventDayTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
        EventDayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", " Day of Event", " From", " To", " Venue"
            }
        ) {
            Class[] types = new Class [] {
                ImageIcon.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        EventDayTable.setDragEnabled(true);
        EventDayTable.setFocusable(false);
        EventDayTable.setGridColor(new java.awt.Color(51, 51, 51));
        EventDayTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        EventDayTable.setRowHeight(20);
        EventDayTable.setSelectionBackground(new java.awt.Color(255, 204, 51));
        EventDayTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        EventDayTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        EventDayTable.setShowHorizontalLines(false);
        EventDayTable.setShowVerticalLines(false);
        EventDayTable.setUpdateSelectionOnSort(false);
        EventDayTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EventDayTableMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                EventDayTableMouseExited(evt);
            }
        });
        EventDayTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                EventDayTableMouseMoved(evt);
            }
        });
        EventDayTableBackground.setViewportView(EventDayTable);
        EventDayTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        EventDayTable.getColumnModel().getColumn(0).setMinWidth(25);
        EventDayTable.getColumnModel().getColumn(0).setMaxWidth(25);

        EventDayTable.getColumnModel().getColumn(1).setPreferredWidth(220);
        EventDayTable.getColumnModel().getColumn(1).setMinWidth(220);
        EventDayTable.getColumnModel().getColumn(1).setMaxWidth(220);

        EventDayTable.getColumnModel().getColumn(2).setMaxWidth(110);
        EventDayTable.getColumnModel().getColumn(2).setMinWidth(110);
        EventDayTable.getColumnModel().getColumn(2).setPreferredWidth(110);

        EventDayTable.getColumnModel().getColumn(3).setMaxWidth(110);
        EventDayTable.getColumnModel().getColumn(3).setMinWidth(110);
        EventDayTable.getColumnModel().getColumn(3).setPreferredWidth(110);

        EventDayTable.getTableHeader().setFont(new java.awt.Font("Ubuntu Bold", 1, 14));
        DefaultTableCellRenderer newrendererEventDay = new DefaultTableCellRenderer();
        newrendererEventDay.setBackground(new Color(51,51,51));
        newrendererEventDay.setForeground(Color.white);
        EventDayTable.getTableHeader().setDefaultRenderer(newrendererEventDay);
        EventDayTable.getTableHeader().setReorderingAllowed(false);
        EventDayTable.getTableHeader().setResizingAllowed(false);

        AddEventDayButton.setBackground(new java.awt.Color(255, 255, 255));
        AddEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png"))); // NOI18N
        AddEventDayButton.setBorder(null);
        AddEventDayButton.setBorderPainted(false);
        AddEventDayButton.setContentAreaFilled(false);
        AddEventDayButton.setFocusable(false);
        AddEventDayButton.setPreferredSize(new java.awt.Dimension(38, 38));
        AddEventDayButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AddEventDayButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AddEventDayButtonMousePressed(evt);
            }
        });
        AddEventDayButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AddEventDayButtonMouseMoved(evt);
            }
        });
        AddEventDayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEventDayButtonActionPerformed(evt);
            }
        });

        EditEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png"))); // NOI18N
        EditEventDayButton.setBorderPainted(false);
        EditEventDayButton.setContentAreaFilled(false);
        EditEventDayButton.setFocusable(false);
        EditEventDayButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
        EditEventDayButton.setMaximumSize(new java.awt.Dimension(41, 41));
        EditEventDayButton.setMinimumSize(new java.awt.Dimension(41, 41));
        EditEventDayButton.setPreferredSize(new java.awt.Dimension(41, 41));
        EditEventDayButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                EditEventDayButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EditEventDayButtonMousePressed(evt);
            }
        });
        EditEventDayButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                EditEventDayButtonMouseMoved(evt);
            }
        });
        EditEventDayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditEventDayButtonActionPerformed(evt);
            }
        });

        DeleteEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png"))); // NOI18N
        DeleteEventDayButton.setBorderPainted(false);
        DeleteEventDayButton.setContentAreaFilled(false);
        DeleteEventDayButton.setFocusable(false);
        DeleteEventDayButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
        DeleteEventDayButton.setMaximumSize(new java.awt.Dimension(41, 41));
        DeleteEventDayButton.setMinimumSize(new java.awt.Dimension(41, 41));
        DeleteEventDayButton.setPreferredSize(new java.awt.Dimension(41, 41));
        DeleteEventDayButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DeleteEventDayButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DeleteEventDayButtonMousePressed(evt);
            }
        });
        DeleteEventDayButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                DeleteEventDayButtonMouseMoved(evt);
            }
        });
        DeleteEventDayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteEventDayButtonActionPerformed(evt);
            }
        });

        EventDayNotesTextBackground.setBorder(null);

        EventDayNotesText.setBackground(new java.awt.Color(51, 51, 51));
        EventDayNotesText.setColumns(20);
        EventDayNotesText.setEditable(false);
        EventDayNotesText.setFont(new java.awt.Font("Ubuntu", 0, 14));
        EventDayNotesText.setForeground(new java.awt.Color(255, 255, 255));
        EventDayNotesText.setLineWrap(true);
        EventDayNotesText.setRows(1);
        EventDayNotesText.setWrapStyleWord(true);
        EventDayNotesText.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        EventDayNotesText.setSelectionColor(new java.awt.Color(255, 204, 51));
        EventDayNotesTextBackground.setViewportView(EventDayNotesText);
        EventDayNotesText.setBorder(BorderFactory.createCompoundBorder(
            EventDayNotesText.getBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    EventDayItineraryTextBackground.setBorder(null);

    EventDayItineraryText.setBackground(new java.awt.Color(51, 51, 51));
    EventDayItineraryText.setColumns(20);
    EventDayItineraryText.setEditable(false);
    EventDayItineraryText.setFont(new java.awt.Font("Ubuntu", 0, 14));
    EventDayItineraryText.setForeground(new java.awt.Color(255, 255, 255));
    EventDayItineraryText.setLineWrap(true);
    EventDayItineraryText.setRows(1);
    EventDayItineraryText.setWrapStyleWord(true);
    EventDayItineraryText.setSelectedTextColor(new java.awt.Color(0, 0, 0));
    EventDayItineraryText.setSelectionColor(new java.awt.Color(255, 204, 51));
    EventDayItineraryTextBackground.setViewportView(EventDayItineraryText);
    EventDayItineraryText.setBorder(BorderFactory.createCompoundBorder(
        EventDayItineraryText.getBorder(),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));

javax.swing.GroupLayout EventDayBackgroundLayout = new javax.swing.GroupLayout(EventDayBackground);
EventDayBackground.setLayout(EventDayBackgroundLayout);
EventDayBackgroundLayout.setHorizontalGroup(
    EventDayBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(EventDayBackgroundLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(EventDayBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EventDayTableBackground, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
            .addGroup(EventDayBackgroundLayout.createSequentialGroup()
                .addComponent(EventDayNotesTextBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EventDayItineraryTextBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EventDayBackgroundLayout.createSequentialGroup()
                .addGap(0, 498, Short.MAX_VALUE)
                .addComponent(DeleteEventDayButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EditEventDayButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddEventDayButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
    EventDayBackgroundLayout.setVerticalGroup(
        EventDayBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EventDayBackgroundLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(EventDayBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(AddEventDayButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(EditEventDayButton, 0, 0, Short.MAX_VALUE)
                .addComponent(DeleteEventDayButton, 0, 0, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(EventDayTableBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(EventDayBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(EventDayNotesTextBackground)
                .addComponent(EventDayItineraryTextBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
            .addContainerGap())
    );

    EventDayBackground.setBounds(0, 0, 650, 360);
    EventDayTab.add(EventDayBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

    EventDetailsTab.addTab("Day of Event", EventDayTab);

    BudgetTab.setNextFocusableComponent(ExpenseDescriptionText);

    BudgetBackground.setBackground(new java.awt.Color(255, 255, 255));
    BudgetBackground.setNextFocusableComponent(ExpenseDescriptionText);
    BudgetBackground.setLayout(null);

    RemainingBudgetLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    RemainingBudgetLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    RemainingBudgetLabel.setText("Remaining:");
    BudgetBackground.add(RemainingBudgetLabel);
    RemainingBudgetLabel.setBounds(0, 90, 120, 20);

    TotalBudgetText.setEditable(false);
    TotalBudgetText.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    TotalBudgetText.setText("1");
    TotalBudgetText.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
    TotalBudgetText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
    TotalBudgetText.setDisabledTextColor(new java.awt.Color(51, 51, 51));
    TotalBudgetText.setMargin(new java.awt.Insets(5, 5, 5, 5));
    TotalBudgetText.setMinimumSize(new java.awt.Dimension(12, 21));
    TotalBudgetText.setPreferredSize(new java.awt.Dimension(12, 21));
    TotalBudgetText.setSelectedTextColor(new java.awt.Color(0, 0, 0));
    TotalBudgetText.setSelectionColor(new java.awt.Color(255, 204, 51));
    TotalBudgetText.setVerifyInputWhenFocusTarget(false);
    BudgetBackground.add(TotalBudgetText);
    TotalBudgetText.setBounds(130, 60, 90, 21);
    TotalBudgetText.setBorder(BorderFactory.createCompoundBorder(
        TotalBudgetText.getBorder(),
        BorderFactory.createEmptyBorder(0, 5, 0, 5)));
TotalBudgetText.setHorizontalAlignment(JTextField.CENTER);

TotalExpenseLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
TotalExpenseLabel.setText("Total Expense:");
BudgetBackground.add(TotalExpenseLabel);
TotalExpenseLabel.setBounds(330, 340, 120, 20);

TotalExpenseText.setEditable(false);
TotalExpenseText.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
TotalExpenseText.setText("1");
TotalExpenseText.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
TotalExpenseText.setMargin(new java.awt.Insets(5, 5, 5, 5));
TotalExpenseText.setSelectedTextColor(new java.awt.Color(0, 0, 0));
TotalExpenseText.setSelectionColor(new java.awt.Color(255, 204, 51));
BudgetBackground.add(TotalExpenseText);
TotalExpenseText.setBounds(440, 340, 90, 21);
TotalExpenseText.setBorder(BorderFactory.createCompoundBorder(
    TotalExpenseText.getBorder(),
    BorderFactory.createEmptyBorder(0, 5, 0, 5)));
    TotalExpenseText.setHorizontalAlignment(JTextField.CENTER);

    AddExpenseBackground.setBackground(new java.awt.Color(255, 255, 255));
    AddExpenseBackground.setLayout(null);

    ExpenseTableBackground.setBorder(BorderFactory.createEmptyBorder());

    ExpenseTable.setBorder(BorderFactory.createEmptyBorder());
    ExpenseTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
    ExpenseTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            " Category", " Amount"
        }
    ));
    ExpenseTable.setFocusable(false);
    ExpenseTable.setGridColor(new java.awt.Color(0, 153, 51));
    ExpenseTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
    ExpenseTable.setNextFocusableComponent(ExpenseDescriptionText);
    ExpenseTable.setRowHeight(20);
    ExpenseTable.setSelectionBackground(new java.awt.Color(240, 240, 240));
    ExpenseTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
    ExpenseTable.setShowHorizontalLines(false);
    ExpenseTable.setShowVerticalLines(false);
    ExpenseTable.setUpdateSelectionOnSort(false);
    ExpenseTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            ExpenseTableMouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            ExpenseTableMouseExited(evt);
        }
    });
    ExpenseTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            ExpenseTableMouseMoved(evt);
        }
    });
    ExpenseTableBackground.setViewportView(ExpenseTable);
    ExpenseTable.setTableHeader(null);
    ExpenseTable.getColumnModel().getColumn(0).setPreferredWidth(190);
    ExpenseTable.getColumnModel().getColumn(0).setMinWidth(190);
    ExpenseTable.getColumnModel().getColumn(0).setMaxWidth(190);

    ExpenseTable.getColumnModel().getColumn(1).setPreferredWidth(180);
    ExpenseTable.getColumnModel().getColumn(1).setMinWidth(180);
    ExpenseTable.getColumnModel().getColumn(1).setMaxWidth(180);
    class newrendererExpenseTitle_cell extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column)
        {
            Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
            Object curr = ExpenseTablePointer.get(row);

            if(curr != null)
            {
                if (curr instanceof BudgetCategory){
                    cell.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
                    int yellowlimit = (int)((BudgetCategory)curr).getAmount()/4;
                    int remains = (int)((BudgetCategory)curr).getRemainingAmount();
                    if (remains < 0)
                    cell.setForeground(new java.awt.Color(255, 0, 51));
                    else if (remains < yellowlimit)
                    cell.setForeground(new java.awt.Color(255, 153, 0));
                    else
                    cell.setForeground(new java.awt.Color(0, 153, 0));
                } else {
                    cell.setFont(new java.awt.Font("Ubuntu", 0, 14));
                    cell.setForeground(new java.awt.Color(0, 0, 0));
                }

            } else {
                cell.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
                cell.setForeground(new java.awt.Color(255, 0, 51));
            }

            return cell;
        }
    }
    ExpenseTable.getColumnModel().getColumn(0).setCellRenderer(new newrendererExpenseTitle_cell());
    ExpenseTable.getColumnModel().getColumn(1).setCellRenderer(new newrendererExpenseTitle_cell());

    AddExpenseBackground.add(ExpenseTableBackground);
    ExpenseTableBackground.setBounds(10, 0, 280, 290);

    BudgetBackground.add(AddExpenseBackground);
    AddExpenseBackground.setBounds(330, 30, 300, 290);
    AddExpenseBackground.setMaximumSize(new Dimension(255,261));

    DeleteBudgetExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png"))); // NOI18N
    DeleteBudgetExpenseButton.setBorderPainted(false);
    DeleteBudgetExpenseButton.setContentAreaFilled(false);
    DeleteBudgetExpenseButton.setFocusable(false);
    DeleteBudgetExpenseButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
    DeleteBudgetExpenseButton.setMaximumSize(new java.awt.Dimension(41, 41));
    DeleteBudgetExpenseButton.setMinimumSize(new java.awt.Dimension(41, 41));
    DeleteBudgetExpenseButton.setPreferredSize(new java.awt.Dimension(41, 41));
    DeleteBudgetExpenseButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            DeleteBudgetExpenseButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            DeleteBudgetExpenseButtonMousePressed(evt);
        }
    });
    DeleteBudgetExpenseButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            DeleteBudgetExpenseButtonMouseMoved(evt);
        }
    });
    DeleteBudgetExpenseButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            DeleteBudgetExpenseButtonActionPerformed(evt);
        }
    });
    BudgetBackground.add(DeleteBudgetExpenseButton);
    DeleteBudgetExpenseButton.setBounds(540, 330, 41, 41);

    EditBudgetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/budgetbutton.png"))); // NOI18N
    EditBudgetButton.setBorderPainted(false);
    EditBudgetButton.setContentAreaFilled(false);
    EditBudgetButton.setFocusable(false);
    EditBudgetButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
    EditBudgetButton.setMaximumSize(new java.awt.Dimension(41, 41));
    EditBudgetButton.setMinimumSize(new java.awt.Dimension(41, 41));
    EditBudgetButton.setPreferredSize(new java.awt.Dimension(41, 41));
    EditBudgetButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            EditBudgetButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            EditBudgetButtonMousePressed(evt);
        }
    });
    EditBudgetButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            EditBudgetButtonMouseMoved(evt);
        }
    });
    EditBudgetButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            EditBudgetButtonActionPerformed(evt);
        }
    });
    BudgetBackground.add(EditBudgetButton);
    EditBudgetButton.setBounds(230, 60, 41, 40);

    CategoryLabel.setFont(new java.awt.Font("Ubuntu", 0, 14));
    CategoryLabel.setText("Category:");
    BudgetBackground.add(CategoryLabel);
    CategoryLabel.setBounds(40, 160, 62, 17);

    DescriptionLabel.setFont(new java.awt.Font("Ubuntu", 0, 14));
    DescriptionLabel.setText("Description:");
    BudgetBackground.add(DescriptionLabel);
    DescriptionLabel.setBounds(30, 200, 75, 17);

    AmountLabel.setFont(new java.awt.Font("Ubuntu", 0, 14));
    AmountLabel.setText("Cost ($):");
    BudgetBackground.add(AmountLabel);
    AmountLabel.setBounds(50, 240, 54, 17);

    ExpenseAmountText.setFont(new java.awt.Font("Ubuntu", 0, 14));
    ExpenseAmountText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
    ExpenseAmountText.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
            ExpenseAmountTextKeyTyped(evt);
        }
    });
    BudgetBackground.add(ExpenseAmountText);
    ExpenseAmountText.setBounds(110, 230, 160, 34);
    ExpenseAmountText.setBorder(BorderFactory.createCompoundBorder(
        ExpenseAmountText.getBorder(),
        BorderFactory.createEmptyBorder(0, 5, 0, 5)));

ExpenseDescriptionText.setFont(new java.awt.Font("Ubuntu", 0, 14));
ExpenseDescriptionText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
ExpenseDescriptionText.setMargin(new java.awt.Insets(5, 5, 5, 5));
ExpenseDescriptionText.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyTyped(java.awt.event.KeyEvent evt) {
        ExpenseDescriptionTextKeyTyped(evt);
    }
    });
    BudgetBackground.add(ExpenseDescriptionText);
    ExpenseDescriptionText.setBounds(110, 190, 160, 34);
    ExpenseDescriptionText.setMargin(new java.awt.Insets(5, 5, 5, 5));
    ExpenseDescriptionText.setBorder(BorderFactory.createCompoundBorder(
        ExpenseDescriptionText.getBorder(),
        BorderFactory.createEmptyBorder(0, 5, 0, 5)));

CategoryDropDown.setFont(new java.awt.Font("Ubuntu", 0, 14));
CategoryDropDown.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
CategoryDropDown.setFocusable(false);
CategoryDropDown.addItemListener(new java.awt.event.ItemListener() {
    public void itemStateChanged(java.awt.event.ItemEvent evt) {
        CategoryDropDownItemStateChanged(evt);
    }
    });
    CategoryDropDown.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            CategoryDropDownActionPerformed(evt);
        }
    });
    BudgetBackground.add(CategoryDropDown);
    CategoryDropDown.setBounds(110, 150, 160, 34);

    AddExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png"))); // NOI18N
    AddExpenseButton.setBorderPainted(false);
    AddExpenseButton.setContentAreaFilled(false);
    AddExpenseButton.setFocusable(false);
    AddExpenseButton.setMaximumSize(new java.awt.Dimension(41, 41));
    AddExpenseButton.setMinimumSize(new java.awt.Dimension(41, 41));
    AddExpenseButton.setPreferredSize(new java.awt.Dimension(41, 41));
    AddExpenseButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            AddExpenseButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            AddExpenseButtonMousePressed(evt);
        }
    });
    AddExpenseButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            AddExpenseButtonMouseMoved(evt);
        }
    });
    AddExpenseButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            AddExpenseButtonActionPerformed(evt);
        }
    });
    BudgetBackground.add(AddExpenseButton);
    AddExpenseButton.setBounds(230, 270, 41, 41);

    EditBudgetExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png"))); // NOI18N
    EditBudgetExpenseButton.setBorderPainted(false);
    EditBudgetExpenseButton.setContentAreaFilled(false);
    EditBudgetExpenseButton.setFocusable(false);
    EditBudgetExpenseButton.setMargin(new java.awt.Insets(1, 0, 1, 0));
    EditBudgetExpenseButton.setMaximumSize(new java.awt.Dimension(41, 41));
    EditBudgetExpenseButton.setMinimumSize(new java.awt.Dimension(41, 41));
    EditBudgetExpenseButton.setPreferredSize(new java.awt.Dimension(41, 41));
    EditBudgetExpenseButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            EditBudgetExpenseButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            EditBudgetExpenseButtonMousePressed(evt);
        }
    });
    EditBudgetExpenseButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            EditBudgetExpenseButtonMouseMoved(evt);
        }
    });
    EditBudgetExpenseButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            EditBudgetExpenseButtonActionPerformed(evt);
        }
    });
    BudgetBackground.add(EditBudgetExpenseButton);
    EditBudgetExpenseButton.setBounds(580, 330, 41, 41);

    EventListLabel2.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    EventListLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    EventListLabel2.setText("Total Budget:");
    BudgetBackground.add(EventListLabel2);
    EventListLabel2.setBounds(0, 60, 120, 20);

    RemainingBudgetText.setEditable(false);
    RemainingBudgetText.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    RemainingBudgetText.setText("1");
    RemainingBudgetText.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
    RemainingBudgetText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
    RemainingBudgetText.setDisabledTextColor(new java.awt.Color(51, 51, 51));
    RemainingBudgetText.setMargin(new java.awt.Insets(5, 5, 5, 5));
    RemainingBudgetText.setMinimumSize(new java.awt.Dimension(12, 21));
    RemainingBudgetText.setPreferredSize(new java.awt.Dimension(12, 21));
    RemainingBudgetText.setSelectedTextColor(new java.awt.Color(0, 0, 0));
    RemainingBudgetText.setSelectionColor(new java.awt.Color(255, 204, 51));
    RemainingBudgetText.setVerifyInputWhenFocusTarget(false);
    BudgetBackground.add(RemainingBudgetText);
    RemainingBudgetText.setBounds(130, 90, 90, 21);
    RemainingBudgetText.setBorder(BorderFactory.createCompoundBorder(
        RemainingBudgetText.getBorder(),
        BorderFactory.createEmptyBorder(0, 5, 0, 5)));
RemainingBudgetText.setHorizontalAlignment(JTextField.CENTER);

Separator2.setBackground(new java.awt.Color(204, 204, 204));
Separator2.setOpaque(true);
BudgetBackground.add(Separator2);
Separator2.setBounds(330, 320, 300, 1);

Separator.setBackground(new java.awt.Color(204, 204, 204));
Separator.setOpaque(true);
BudgetBackground.add(Separator);
Separator.setBounds(330, 29, 300, 1);

BudgetBackground.setBounds(0, 0, 650, 370);
BudgetTab.add(BudgetBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

EventDetailsTab.addTab("Budget", BudgetTab);

ParticipantArea.setBackground(new java.awt.Color(255, 255, 255));
ParticipantArea.setLayout(null);

ParticipantTableBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

ContactsTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
ContactsTable.setModel(new javax.swing.table.DefaultTableModel(
    new Object [][] {

    },
    new String [] {
        "Name", "Email"
    }
    ));
    ContactsTable.setInheritsPopupMenu(true);
    ContactsTable.setRowHeight(20);
    ContactsTable.setSelectionBackground(new java.awt.Color(255, 204, 51));
    ContactsTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
    ContactsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    ParticipantTableBackground.setViewportView(ContactsTable);
    ContactsTable.getTableHeader().setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    DefaultTableCellRenderer newrendererParticipant = new DefaultTableCellRenderer();
    newrendererParticipant.setBackground(new Color(51,51,51));
    newrendererParticipant.setForeground(Color.white);
    ContactsTable.getTableHeader().setDefaultRenderer(newrendererParticipant);
    ContactsTable.getTableHeader().setReorderingAllowed(false);

    ContactsTable.setPreferredScrollableViewportSize(new Dimension(600, 70));

    ParticipantArea.add(ParticipantTableBackground);
    ParticipantTableBackground.setBounds(10, 63, 650, 270);

    EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png"))); // NOI18N
    EditContactsButton.setBorderPainted(false);
    EditContactsButton.setContentAreaFilled(false);
    EditContactsButton.setFocusable(false);
    EditContactsButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
    EditContactsButton.setMaximumSize(new java.awt.Dimension(41, 41));
    EditContactsButton.setMinimumSize(new java.awt.Dimension(41, 41));
    EditContactsButton.setPreferredSize(new java.awt.Dimension(41, 41));
    EditContactsButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            EditContactsButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            EditContactsButtonMousePressed(evt);
        }
    });
    EditContactsButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            EditContactsButtonMouseMoved(evt);
        }
    });
    EditContactsButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            EditContactsButtonActionPerformed(evt);
        }
    });
    ParticipantArea.add(EditContactsButton);
    EditContactsButton.setBounds(620, 340, 41, 41);

    EditContactsHeaderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settingsbutton.png"))); // NOI18N
    EditContactsHeaderButton.setBorderPainted(false);
    EditContactsHeaderButton.setContentAreaFilled(false);
    EditContactsHeaderButton.setFocusable(false);
    EditContactsHeaderButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
    EditContactsHeaderButton.setMaximumSize(new java.awt.Dimension(41, 41));
    EditContactsHeaderButton.setMinimumSize(new java.awt.Dimension(41, 41));
    EditContactsHeaderButton.setPreferredSize(new java.awt.Dimension(41, 41));
    EditContactsHeaderButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            EditContactsHeaderButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            EditContactsHeaderButtonMousePressed(evt);
        }
    });
    EditContactsHeaderButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            EditContactsHeaderButtonMouseMoved(evt);
        }
    });
    EditContactsHeaderButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            EditContactsHeaderButtonActionPerformed(evt);
        }
    });
    ParticipantArea.add(EditContactsHeaderButton);
    EditContactsHeaderButton.setBounds(540, 10, 41, 46);

    AddContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png"))); // NOI18N
    AddContactsButton.setBorderPainted(false);
    AddContactsButton.setContentAreaFilled(false);
    AddContactsButton.setDefaultCapable(false);
    AddContactsButton.setFocusPainted(false);
    AddContactsButton.setFocusable(false);
    AddContactsButton.setMaximumSize(new java.awt.Dimension(38, 38));
    AddContactsButton.setMinimumSize(new java.awt.Dimension(38, 38));
    AddContactsButton.setPreferredSize(new java.awt.Dimension(38, 38));
    AddContactsButton.setRolloverEnabled(false);
    AddContactsButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            AddContactsButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            AddContactsButtonMousePressed(evt);
        }
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            AddContactsButtonMouseReleased(evt);
        }
    });
    AddContactsButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            AddContactsButtonMouseMoved(evt);
        }
    });
    AddContactsButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            AddContactsButtonActionPerformed(evt);
        }
    });
    ParticipantArea.add(AddContactsButton);
    AddContactsButton.setBounds(622, 11, 38, 46);

    DeleteContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png"))); // NOI18N
    DeleteContactsButton.setBorderPainted(false);
    DeleteContactsButton.setContentAreaFilled(false);
    DeleteContactsButton.setFocusable(false);
    DeleteContactsButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
    DeleteContactsButton.setMaximumSize(new java.awt.Dimension(41, 41));
    DeleteContactsButton.setMinimumSize(new java.awt.Dimension(41, 41));
    DeleteContactsButton.setPreferredSize(new java.awt.Dimension(41, 41));
    DeleteContactsButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            DeleteContactsButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            DeleteContactsButtonMousePressed(evt);
        }
    });
    DeleteContactsButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            DeleteContactsButtonMouseMoved(evt);
        }
    });
    DeleteContactsButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            DeleteContactsButtonActionPerformed(evt);
        }
    });
    ParticipantArea.add(DeleteContactsButton);
    DeleteContactsButton.setBounds(580, 10, 41, 46);

    ImportContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/importbutton.png"))); // NOI18N
    ImportContactsButton.setBorderPainted(false);
    ImportContactsButton.setContentAreaFilled(false);
    ImportContactsButton.setFocusable(false);
    ImportContactsButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
    ImportContactsButton.setMaximumSize(new java.awt.Dimension(41, 41));
    ImportContactsButton.setMinimumSize(new java.awt.Dimension(41, 41));
    ImportContactsButton.setPreferredSize(new java.awt.Dimension(41, 41));
    ImportContactsButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            ImportContactsButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            ImportContactsButtonMousePressed(evt);
        }
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            ImportContactsButtonMouseReleased(evt);
        }
    });
    ImportContactsButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            ImportContactsButtonMouseMoved(evt);
        }
    });
    ImportContactsButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ImportContactsButtonActionPerformed(evt);
        }
    });
    ParticipantArea.add(ImportContactsButton);
    ImportContactsButton.setBounds(140, 10, 41, 46);

    ContactsListDropDown.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    ContactsListDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Participants", "Helpers", "Sponsors", "Vendors" }));
    ContactsListDropDown.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
    ContactsListDropDown.setFocusable(false);
    ContactsListDropDown.setLightWeightPopupEnabled(false);
    ContactsListDropDown.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ContactsListDropDownActionPerformed(evt);
        }
    });
    ParticipantArea.add(ContactsListDropDown);
    ContactsListDropDown.setBounds(10, 17, 120, 30);

    CancelContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelbutton.png"))); // NOI18N
    CancelContactsButton.setBorderPainted(false);
    CancelContactsButton.setContentAreaFilled(false);
    CancelContactsButton.setFocusable(false);
    CancelContactsButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            CancelContactsButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            CancelContactsButtonMousePressed(evt);
        }
    });
    CancelContactsButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            CancelContactsButtonMouseMoved(evt);
        }
    });
    CancelContactsButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            CancelContactsButtonActionPerformed(evt);
        }
    });
    ParticipantArea.add(CancelContactsButton);
    CancelContactsButton.setBounds(580, 340, 38, 41);

    CountLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    CountLabel.setText("Count:");
    ParticipantArea.add(CountLabel);
    CountLabel.setBounds(20, 350, 70, 20);

    CountText.setEditable(false);
    CountText.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    CountText.setText("0");
    CountText.setBorder(null);
    CountText.setSelectionColor(new java.awt.Color(255, 204, 51));
    ParticipantArea.add(CountText);
    CountText.setBounds(80, 350, 80, 20);
    CountText.setBackground(new java.awt.Color(255, 255, 255));

    ContactsInstruction.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    ContactsInstruction.setForeground(new java.awt.Color(153, 153, 153));
    ContactsInstruction.setText("click '+' to add an entry, double click to edit");
    ParticipantArea.add(ContactsInstruction);
    ContactsInstruction.setBounds(190, 340, 320, 20);

    ParticipantArea.setBounds(0, 0, 670, 390);
    ParticipantTab.add(ParticipantArea, javax.swing.JLayeredPane.DEFAULT_LAYER);

    EventDetailsTab.addTab("Contacts", ParticipantTab);

    AnnouncementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/emailbutton.png"))); // NOI18N
    AnnouncementButton.setBorderPainted(false);
    AnnouncementButton.setContentAreaFilled(false);
    AnnouncementButton.setFocusPainted(false);
    AnnouncementButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            AnnouncementButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            AnnouncementButtonMousePressed(evt);
        }
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            AnnouncementButtonMouseReleased(evt);
        }
    });
    AnnouncementButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            AnnouncementButtonMouseMoved(evt);
        }
    });
    AnnouncementButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            AnnouncementButtonActionPerformed(evt);
        }
    });
    AnnouncementButton.setBounds(620, 340, 38, 38);
    EmailTab.add(AnnouncementButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

    AnnouncementSubjectText.setFont(new java.awt.Font("Ubuntu", 0, 14));
    AnnouncementSubjectText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
    AnnouncementSubjectText.setMargin(new java.awt.Insets(5, 5, 5, 5));
    AnnouncementSubjectText.setNextFocusableComponent(AnnouncementBodyText);
    AnnouncementSubjectText.setBounds(390, 100, 260, 30);
    EmailTab.add(AnnouncementSubjectText, javax.swing.JLayeredPane.DEFAULT_LAYER);
    AnnouncementSubjectText.setBorder(BorderFactory.createCompoundBorder(
        AnnouncementSubjectText.getBorder(),
        BorderFactory.createEmptyBorder(0, 5, 0, 5)));

AnnouncementUsernameText.setFont(new java.awt.Font("Ubuntu", 0, 14));
AnnouncementUsernameText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
AnnouncementUsernameText.setMargin(new java.awt.Insets(5, 5, 5, 5));
AnnouncementUsernameText.setNextFocusableComponent(AnnouncementPasswordText);
AnnouncementUsernameText.setBounds(490, 20, 160, 30);
EmailTab.add(AnnouncementUsernameText, javax.swing.JLayeredPane.DEFAULT_LAYER);
AnnouncementUsernameText.setBorder(BorderFactory.createCompoundBorder(
    AnnouncementUsernameText.getBorder(),
    BorderFactory.createEmptyBorder(0, 5, 0, 5)));

    AnnouncementPasswordText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
    AnnouncementPasswordText.setMargin(new java.awt.Insets(0, 2, 0, 2));
    AnnouncementPasswordText.setNextFocusableComponent(AnnouncementSubjectText);
    AnnouncementPasswordText.setBounds(490, 60, 160, 30);
    EmailTab.add(AnnouncementPasswordText, javax.swing.JLayeredPane.DEFAULT_LAYER);
    AnnouncementPasswordText.setBorder(BorderFactory.createCompoundBorder(
        AnnouncementPasswordText.getBorder(),
        BorderFactory.createEmptyBorder(0, 5, 0, 5)));

BodyTextBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

AnnouncementBodyText.setColumns(20);
AnnouncementBodyText.setFont(new java.awt.Font("Ubuntu", 0, 14));
AnnouncementBodyText.setLineWrap(true);
AnnouncementBodyText.setRows(5);
AnnouncementBodyText.setWrapStyleWord(true);
BodyTextBackground.setViewportView(AnnouncementBodyText);
AnnouncementBodyText.setBorder(BorderFactory.createCompoundBorder(
    AnnouncementBodyText.getBorder(),
    BorderFactory.createEmptyBorder(2, 2, 2, 2)));

    BodyTextBackground.setBounds(390, 140, 260, 190);
    EmailTab.add(BodyTextBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

    AnnouncementEventContactListTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
    AnnouncementEventContactListTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Event", "   P", "   H", "   V", "   S"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }
    });
    AnnouncementEventContactListTable.setSelectionBackground(new java.awt.Color(255, 204, 51));
    AnnouncementEventContactListTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
    AnnouncementEventContactListTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            AnnouncementEventContactListTableMouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            AnnouncementEventContactListTableMouseExited(evt);
        }
    });
    AnnouncementEventContactListTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            AnnouncementEventContactListTableMouseMoved(evt);
        }
    });
    AnnouncementContactListTableBackground.setViewportView(AnnouncementEventContactListTable);
    AnnouncementEventContactListTable.getColumnModel().getColumn(0).setMinWidth(150);
    AnnouncementEventContactListTable.getColumnModel().getColumn(0).setPreferredWidth(150);
    AnnouncementEventContactListTable.getColumnModel().getColumn(0).setMaxWidth(150);

    AnnouncementEventContactListTable.getTableHeader().setFont(new java.awt.Font("Ubuntu Bold", 1, 14));
    DefaultTableCellRenderer newrendererContact = new DefaultTableCellRenderer();
    newrendererContact.setBackground(new Color(51,51,51));
    newrendererContact.setForeground(Color.white);
    AnnouncementEventContactListTable.getTableHeader().setDefaultRenderer(newrendererContact);

    AnnouncementEventContactListTable.getTableHeader().setReorderingAllowed(false);
    AnnouncementEventContactListTable.getTableHeader().setResizingAllowed(false);

    AnnouncementContactListTableBackground.setBounds(20, 140, 270, 190);
    EmailTab.add(AnnouncementContactListTableBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

    OtherContactsLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    OtherContactsLabel.setText("Other Contacts:");
    OtherContactsLabel.setBounds(10, 100, 170, 30);
    EmailTab.add(OtherContactsLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

    BodyLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    BodyLabel.setText("Body:");
    BodyLabel.setBounds(320, 140, 50, 20);
    EmailTab.add(BodyLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

    ContactsLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    ContactsLabel.setText("Contacts:");
    ContactsLabel.setBounds(10, 10, 100, 20);
    EmailTab.add(ContactsLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

    UsernameLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    UsernameLabel.setText("NUSNET Domain/ID:");
    UsernameLabel.setBounds(320, 20, 160, 30);
    EmailTab.add(UsernameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

    PasswordLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    PasswordLabel.setText("Password:");
    PasswordLabel.setBounds(320, 60, 130, 30);
    EmailTab.add(PasswordLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

    SubjectLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    SubjectLabel.setText("Subject:");
    SubjectLabel.setBounds(320, 100, 130, 30);
    EmailTab.add(SubjectLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

    AnnouncementHelpersCheckBox.setBackground(new java.awt.Color(255, 255, 255));
    AnnouncementHelpersCheckBox.setFont(new java.awt.Font("Ubuntu", 0, 14));
    AnnouncementHelpersCheckBox.setText("Helpers");
    AnnouncementHelpersCheckBox.setBounds(20, 70, 120, 23);
    EmailTab.add(AnnouncementHelpersCheckBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

    AnnouncementParticipantsCheckBox.setBackground(new java.awt.Color(255, 255, 255));
    AnnouncementParticipantsCheckBox.setFont(new java.awt.Font("Ubuntu", 0, 14));
    AnnouncementParticipantsCheckBox.setText("Participants");
    AnnouncementParticipantsCheckBox.setBounds(20, 40, 130, 23);
    EmailTab.add(AnnouncementParticipantsCheckBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

    AnnouncementSponsorsCheckBox.setBackground(new java.awt.Color(255, 255, 255));
    AnnouncementSponsorsCheckBox.setFont(new java.awt.Font("Ubuntu", 0, 14));
    AnnouncementSponsorsCheckBox.setText("Sponsor");
    AnnouncementSponsorsCheckBox.setBounds(150, 70, 130, 23);
    EmailTab.add(AnnouncementSponsorsCheckBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

    AnnouncementVendorsCheckBox.setBackground(new java.awt.Color(255, 255, 255));
    AnnouncementVendorsCheckBox.setFont(new java.awt.Font("Ubuntu", 0, 14));
    AnnouncementVendorsCheckBox.setText("Vendors");
    AnnouncementVendorsCheckBox.setBounds(150, 40, 120, 23);
    EmailTab.add(AnnouncementVendorsCheckBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

    EventDetailsTab.addTab("Announcement", EmailTab);

    ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exportbutton.png"))); // NOI18N
    ExportButton.setBorderPainted(false);
    ExportButton.setContentAreaFilled(false);
    ExportButton.setFocusPainted(false);
    ExportButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            ExportButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            ExportButtonMousePressed(evt);
        }
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            ExportButtonMouseReleased(evt);
        }
    });
    ExportButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            ExportButtonMouseMoved(evt);
        }
    });
    ExportButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ExportButtonActionPerformed(evt);
        }
    });
    ExportButton.setBounds(590, 310, 38, 38);
    ExportTab.add(ExportButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

    GeneralExportBackground.setBorder(BorderFactory.createEmptyBorder());

    GeneralExportTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
    GeneralExportTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {true, "Event Description"},
            {true, "Budget Information"},
            {true, "Participant Details"},
            {true, "Helper Details"},
            {true, "Sponsor Details"},
            {true, "Vendor Details"},
            {true, "Tasks"}
        },
        new String [] {
            "Checkbox", "General"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.Boolean.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    GeneralExportTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    GeneralExportTable.setFocusable(false);
    GeneralExportTable.setGridColor(new java.awt.Color(255, 255, 255));
    GeneralExportTable.setIntercellSpacing(new java.awt.Dimension(0, 3));
    GeneralExportTable.setRowHeight(30);
    GeneralExportTable.setSelectionBackground(new java.awt.Color(255, 204, 51));
    GeneralExportTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
    GeneralExportTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    GeneralExportTable.setTableHeader(null);
    GeneralExportTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            GeneralExportTableMouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            GeneralExportTableMouseExited(evt);
        }
    });
    GeneralExportTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            GeneralExportTableMouseMoved(evt);
        }
    });
    GeneralExportBackground.setViewportView(GeneralExportTable);
    GeneralExportTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    GeneralExportTable.getColumnModel().getColumn(0).setPreferredWidth(25);
    GeneralExportTable.getColumnModel().getColumn(0).setMinWidth(25);
    GeneralExportTable.getColumnModel().getColumn(0).setMaxWidth(25);

    GeneralExportBackground.setBounds(80, 100, 200, 220);
    ExportTab.add(GeneralExportBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

    EventDayExportBackground.setBorder(BorderFactory.createEmptyBorder());

    ExportEventDayTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
    ExportEventDayTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {true, "Event Description"},
            {true, "Budget Information"},
            {true, "Participant Details"},
            {true, "Helper Details"}
        },
        new String [] {
            "Checkbox", "General"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.Boolean.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    ExportEventDayTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    ExportEventDayTable.setFocusable(false);
    ExportEventDayTable.setGridColor(new java.awt.Color(255, 255, 255));
    ExportEventDayTable.setIntercellSpacing(new java.awt.Dimension(0, 3));
    ExportEventDayTable.setRowHeight(30);
    ExportEventDayTable.setSelectionBackground(new java.awt.Color(255, 204, 51));
    ExportEventDayTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
    ExportEventDayTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    ExportEventDayTable.setTableHeader(null);
    ExportEventDayTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            ExportEventDayTableMouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            ExportEventDayTableMouseExited(evt);
        }
    });
    ExportEventDayTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            ExportEventDayTableMouseMoved(evt);
        }
    });
    EventDayExportBackground.setViewportView(ExportEventDayTable);
    ExportEventDayTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    ExportEventDayTable.getColumnModel().getColumn(0).setPreferredWidth(25);
    ExportEventDayTable.getColumnModel().getColumn(0).setMinWidth(25);
    ExportEventDayTable.getColumnModel().getColumn(0).setMaxWidth(25);

    EventDayExportBackground.setBounds(400, 100, 200, 190);
    ExportTab.add(EventDayExportBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

    EventListLabel3.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    EventListLabel3.setText("Day of Event :");
    EventListLabel3.setBounds(390, 70, 141, 20);
    ExportTab.add(EventListLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

    EventListLabel4.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    EventListLabel4.setForeground(new java.awt.Color(153, 153, 153));
    EventListLabel4.setText("Select what to export :");
    EventListLabel4.setBounds(20, 20, 260, 30);
    ExportTab.add(EventListLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

    EventListLabel5.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    EventListLabel5.setText("General :");
    EventListLabel5.setBounds(70, 70, 141, 20);
    ExportTab.add(EventListLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

    exportSeparator.setBackground(new java.awt.Color(204, 204, 204));
    exportSeparator.setOpaque(true);
    exportSeparator.setBounds(340, 60, 1, 280);
    ExportTab.add(exportSeparator, javax.swing.JLayeredPane.DEFAULT_LAYER);

    EventDetailsTab.addTab("Export", ExportTab);

    EventTabBackground.add(EventDetailsTab);
    EventDetailsTab.setBounds(245, 11, 675, 418);

    EditEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png"))); // NOI18N
    EditEventButton.setBorderPainted(false);
    EditEventButton.setContentAreaFilled(false);
    EditEventButton.setFocusable(false);
    EditEventButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
    EditEventButton.setMaximumSize(new java.awt.Dimension(41, 41));
    EditEventButton.setMinimumSize(new java.awt.Dimension(41, 41));
    EditEventButton.setPreferredSize(new java.awt.Dimension(41, 41));
    EditEventButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            EditEventButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            EditEventButtonMousePressed(evt);
        }
    });
    EditEventButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            EditEventButtonMouseMoved(evt);
        }
    });
    EditEventButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            EditEventButtonActionPerformed(evt);
        }
    });
    EventTabBackground.add(EditEventButton);
    EditEventButton.setBounds(110, 20, 41, 41);

    DeleteEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png"))); // NOI18N
    DeleteEventButton.setBorderPainted(false);
    DeleteEventButton.setContentAreaFilled(false);
    DeleteEventButton.setFocusable(false);
    DeleteEventButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
    DeleteEventButton.setMaximumSize(new java.awt.Dimension(41, 41));
    DeleteEventButton.setMinimumSize(new java.awt.Dimension(41, 41));
    DeleteEventButton.setPreferredSize(new java.awt.Dimension(41, 41));
    DeleteEventButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            DeleteEventButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            DeleteEventButtonMousePressed(evt);
        }
    });
    DeleteEventButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            DeleteEventButtonMouseMoved(evt);
        }
    });
    DeleteEventButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            DeleteEventButtonActionPerformed(evt);
        }
    });
    EventTabBackground.add(DeleteEventButton);
    DeleteEventButton.setBounds(70, 20, 41, 41);

    EventDescriptionTextAreaBackground.setBorder(null);

    EventDescriptionTextArea.setBackground(new java.awt.Color(51, 51, 51));
    EventDescriptionTextArea.setColumns(20);
    EventDescriptionTextArea.setEditable(false);
    EventDescriptionTextArea.setFont(new java.awt.Font("Ubuntu", 0, 14));
    EventDescriptionTextArea.setForeground(new java.awt.Color(255, 255, 255));
    EventDescriptionTextArea.setLineWrap(true);
    EventDescriptionTextArea.setRows(1);
    EventDescriptionTextArea.setWrapStyleWord(true);
    EventDescriptionTextArea.setBorder(null);
    EventDescriptionTextArea.setSelectedTextColor(new java.awt.Color(0, 0, 0));
    EventDescriptionTextArea.setSelectionColor(new java.awt.Color(255, 204, 51));
    EventDescriptionTextAreaBackground.setViewportView(EventDescriptionTextArea);
    EventDescriptionTextArea.setBorder(BorderFactory.createCompoundBorder(
        EventDescriptionTextArea.getBorder(),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));

EventTabBackground.add(EventDescriptionTextAreaBackground);
EventDescriptionTextAreaBackground.setBounds(10, 288, 217, 141);

ArchiveEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/archivebutton.png"))); // NOI18N
ArchiveEventButton.setBorderPainted(false);
ArchiveEventButton.setContentAreaFilled(false);
ArchiveEventButton.setFocusable(false);
ArchiveEventButton.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseExited(java.awt.event.MouseEvent evt) {
        ArchiveEventButtonMouseExited(evt);
    }
    public void mousePressed(java.awt.event.MouseEvent evt) {
        ArchiveEventButtonMousePressed(evt);
    }
    });
    ArchiveEventButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            ArchiveEventButtonMouseMoved(evt);
        }
    });
    ArchiveEventButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ArchiveEventButtonActionPerformed(evt);
        }
    });
    EventTabBackground.add(ArchiveEventButton);
    ArchiveEventButton.setBounds(150, 20, 38, 41);

    noEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/emptyevent.png"))); // NOI18N
    noEvent.setToolTipText("");
    EventTabBackground.add(noEvent);
    noEvent.setBounds(245, 11, 675, 418);

    EventTabBackground.setBounds(0, 0, 930, 440);
    EventTab.add(EventTabBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

    MainTab.addTab("", new javax.swing.ImageIcon(getClass().getResource("/images/eventtab.png")), EventTab); // NOI18N

    VenueTabBackground.setBackground(new java.awt.Color(255, 255, 255));
    VenueTabBackground.setLayout(null);

    NameLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    NameLabel.setText("Name :");
    VenueTabBackground.add(NameLabel);
    NameLabel.setBounds(110, 40, 60, 30);

    TypeLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    TypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    TypeLabel.setText(" Type :");
    VenueTabBackground.add(TypeLabel);
    TypeLabel.setBounds(110, 80, 60, 30);

    FacultyLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    FacultyLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    FacultyLabel.setText("Faculty :");
    VenueTabBackground.add(FacultyLabel);
    FacultyLabel.setBounds(100, 120, 70, 30);

    MinimumCapacityLabel.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    MinimumCapacityLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    MinimumCapacityLabel.setText("Minimum Capacity :");
    VenueTabBackground.add(MinimumCapacityLabel);
    MinimumCapacityLabel.setBounds(40, 160, 130, 30);

    MyBookingsTableBackground.setBackground(new java.awt.Color(51, 51, 51));
    MyBookingsTableBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 3));

    VenueBookingTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
    VenueBookingTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "", " Venue", " From", " To"
        }
    ) {
        Class[] types = new Class [] {
            ImageIcon.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }
    });
    VenueBookingTable.setFocusable(false);
    VenueBookingTable.setGridColor(new java.awt.Color(204, 204, 204));
    VenueBookingTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
    VenueBookingTable.setRowHeight(20);
    VenueBookingTable.setSelectionBackground(new java.awt.Color(255, 204, 51));
    VenueBookingTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
    VenueBookingTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    VenueBookingTable.setShowHorizontalLines(false);
    VenueBookingTable.setShowVerticalLines(false);
    VenueBookingTable.getTableHeader().setReorderingAllowed(false);
    VenueBookingTable.setUpdateSelectionOnSort(false);
    VenueBookingTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            VenueBookingTableMouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            VenueBookingTableMouseExited(evt);
        }
    });
    VenueBookingTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            VenueBookingTableMouseMoved(evt);
        }
    });
    MyBookingsTableBackground.setViewportView(VenueBookingTable);
    VenueBookingTable.getColumnModel().getColumn(0).setPreferredWidth(25);
    VenueBookingTable.getColumnModel().getColumn(0).setMinWidth(25);
    VenueBookingTable.getColumnModel().getColumn(0).setMaxWidth(25);

    VenueBookingTable.getColumnModel().getColumn(1).setPreferredWidth(120);
    VenueBookingTable.getColumnModel().getColumn(1).setMinWidth(120);
    VenueBookingTable.getColumnModel().getColumn(1).setMaxWidth(120);

    VenueBookingTable.getColumnModel().getColumn(2).setMinWidth(125);
    VenueBookingTable.getColumnModel().getColumn(2).setPreferredWidth(125);
    VenueBookingTable.getColumnModel().getColumn(2).setMaxWidth(125);

    VenueBookingTable.getColumnModel().getColumn(3).setMinWidth(125);
    VenueBookingTable.getColumnModel().getColumn(3).setPreferredWidth(125);
    VenueBookingTable.getColumnModel().getColumn(3).setMaxWidth(125);

    DefaultTableCellRenderer newrendererBookings = new DefaultTableCellRenderer();
    newrendererBookings.setBackground(new Color(51,51,51));
    newrendererBookings.setForeground(Color.white);
    VenueBookingTable.getTableHeader().setDefaultRenderer(newrendererBookings);
    VenueBookingTable.getTableHeader().setReorderingAllowed(false);
    VenueBookingTable.getTableHeader().setResizingAllowed(false);

    VenueTabBackground.add(MyBookingsTableBackground);
    MyBookingsTableBackground.setBounds(530, 60, 380, 350);

    VenueTableBackground.setBackground(new java.awt.Color(51, 51, 51));
    VenueTableBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 3));

    VenueTable.setFont(new java.awt.Font("Ubuntu", 0, 14));
    VenueTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            " Venue", " Type", " Faculty", " Capacity"
        }
    ));
    VenueTable.setFocusable(false);
    VenueTable.setGridColor(new java.awt.Color(204, 204, 204));
    VenueTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
    VenueTable.setRowHeight(20);
    VenueTable.setSelectionBackground(new java.awt.Color(255, 204, 51));
    VenueTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
    VenueTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    VenueTable.setShowHorizontalLines(false);
    VenueTable.setShowVerticalLines(false);
    VenueTable.getTableHeader().setReorderingAllowed(false);
    VenueTable.setUpdateSelectionOnSort(false);
    VenueTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            VenueTableMouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            VenueTableMouseExited(evt);
        }
    });
    VenueTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            VenueTableMouseMoved(evt);
        }
    });
    VenueTableBackground.setViewportView(VenueTable);
    VenueTable.getColumnModel().getColumn(0).setPreferredWidth(162);
    VenueTable.getColumnModel().getColumn(0).setMinWidth(162);
    VenueTable.getColumnModel().getColumn(0).setMaxWidth(162);

    VenueTable.getColumnModel().getColumn(1).setMinWidth(120);
    VenueTable.getColumnModel().getColumn(1).setPreferredWidth(120);
    VenueTable.getColumnModel().getColumn(1).setMaxWidth(120);

    VenueTable.getColumnModel().getColumn(2).setMinWidth(100);
    VenueTable.getColumnModel().getColumn(2).setPreferredWidth(100);
    VenueTable.getColumnModel().getColumn(2).setMaxWidth(100);

    VenueTable.getColumnModel().getColumn(3).setMaxWidth(120);
    VenueTable.getColumnModel().getColumn(3).setMinWidth(120);
    VenueTable.getColumnModel().getColumn(3).setPreferredWidth(120);

    VenueTable.getTableHeader().setFont(new java.awt.Font("Segoe Print", 1, 14));
    DefaultTableCellRenderer newrendererVenue = new DefaultTableCellRenderer();
    newrendererVenue.setBackground(new Color(51,51,51));
    newrendererVenue.setForeground(Color.white);
    VenueTable.getTableHeader().setDefaultRenderer(newrendererVenue);
    VenueTable.getTableHeader().setReorderingAllowed(false);
    VenueTable.getTableHeader().setResizingAllowed(false);

    VenueTabBackground.add(VenueTableBackground);
    VenueTableBackground.setBounds(30, 201, 470, 210);

    CapacityText.setFont(new java.awt.Font("Ubuntu", 0, 14));
    CapacityText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
    CapacityText.setMargin(new java.awt.Insets(5, 5, 5, 5));
    CapacityText.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            CapacityTextMouseClicked(evt);
        }
    });
    CapacityText.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            CapacityTextKeyReleased(evt);
        }
    });
    VenueTabBackground.add(CapacityText);
    CapacityText.setBounds(180, 160, 240, 30);
    CapacityText.setBorder(BorderFactory.createCompoundBorder(
        CapacityText.getBorder(),
        BorderFactory.createEmptyBorder(0, 3, 0, 3)));

TypeDropDown.setFont(new java.awt.Font("Ubuntu", 0, 14));
TypeDropDown.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
TypeDropDown.setFocusable(false);
TypeDropDown.addItemListener(new java.awt.event.ItemListener() {
    public void itemStateChanged(java.awt.event.ItemEvent evt) {
        TypeDropDownItemStateChanged(evt);
    }
    });
    VenueTabBackground.add(TypeDropDown);
    TypeDropDown.setBounds(180, 80, 240, 30);

    FacultyDropDown.setFont(new java.awt.Font("Ubuntu", 0, 14));
    FacultyDropDown.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
    FacultyDropDown.setFocusable(false);
    FacultyDropDown.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            FacultyDropDownItemStateChanged(evt);
        }
    });
    VenueTabBackground.add(FacultyDropDown);
    FacultyDropDown.setBounds(180, 120, 240, 30);

    AddVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png"))); // NOI18N
    AddVenueBookingButton.setBorderPainted(false);
    AddVenueBookingButton.setContentAreaFilled(false);
    AddVenueBookingButton.setDefaultCapable(false);
    AddVenueBookingButton.setFocusPainted(false);
    AddVenueBookingButton.setFocusable(false);
    AddVenueBookingButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
    AddVenueBookingButton.setMaximumSize(new java.awt.Dimension(41, 41));
    AddVenueBookingButton.setMinimumSize(new java.awt.Dimension(41, 41));
    AddVenueBookingButton.setPreferredSize(new java.awt.Dimension(38, 38));
    AddVenueBookingButton.setRolloverEnabled(false);
    AddVenueBookingButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            AddVenueBookingButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            AddVenueBookingButtonMousePressed(evt);
        }
    });
    AddVenueBookingButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            AddVenueBookingButtonMouseMoved(evt);
        }
    });
    AddVenueBookingButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            AddVenueBookingButtonActionPerformed(evt);
        }
    });
    VenueTabBackground.add(AddVenueBookingButton);
    AddVenueBookingButton.setBounds(460, 160, 38, 38);

    NameText.setFont(new java.awt.Font("Ubuntu", 0, 14));
    NameText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
    NameText.setMargin(new java.awt.Insets(5, 5, 5, 5));
    NameText.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            NameTextMouseClicked(evt);
        }
    });
    NameText.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            NameTextKeyReleased(evt);
        }
    });
    VenueTabBackground.add(NameText);
    NameText.setBounds(180, 40, 240, 30);
    NameText.setBorder(BorderFactory.createCompoundBorder(
        NameText.getBorder(),
        BorderFactory.createEmptyBorder(0, 3, 0, 3)));

DeleteVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png"))); // NOI18N
DeleteVenueBookingButton.setBorderPainted(false);
DeleteVenueBookingButton.setContentAreaFilled(false);
DeleteVenueBookingButton.setFocusable(false);
DeleteVenueBookingButton.setMargin(new java.awt.Insets(1, 0, 1, 0));
DeleteVenueBookingButton.setMaximumSize(new java.awt.Dimension(41, 41));
DeleteVenueBookingButton.setMinimumSize(new java.awt.Dimension(41, 41));
DeleteVenueBookingButton.setPreferredSize(new java.awt.Dimension(41, 41));
DeleteVenueBookingButton.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseExited(java.awt.event.MouseEvent evt) {
        DeleteVenueBookingButtonMouseExited(evt);
    }
    public void mousePressed(java.awt.event.MouseEvent evt) {
        DeleteVenueBookingButtonMousePressed(evt);
    }
    });
    DeleteVenueBookingButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            DeleteVenueBookingButtonMouseMoved(evt);
        }
    });
    DeleteVenueBookingButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            DeleteVenueBookingButtonActionPerformed(evt);
        }
    });
    VenueTabBackground.add(DeleteVenueBookingButton);
    DeleteVenueBookingButton.setBounds(830, 20, 40, 41);

    TagVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tagbutton.png"))); // NOI18N
    TagVenueBookingButton.setBorderPainted(false);
    TagVenueBookingButton.setContentAreaFilled(false);
    TagVenueBookingButton.setFocusable(false);
    TagVenueBookingButton.setMargin(new java.awt.Insets(1, 14, 1, 14));
    TagVenueBookingButton.setMaximumSize(new java.awt.Dimension(41, 41));
    TagVenueBookingButton.setMinimumSize(new java.awt.Dimension(41, 41));
    TagVenueBookingButton.setPreferredSize(new java.awt.Dimension(41, 41));
    TagVenueBookingButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            TagVenueBookingButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            TagVenueBookingButtonMousePressed(evt);
        }
    });
    TagVenueBookingButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            TagVenueBookingButtonMouseMoved(evt);
        }
    });
    TagVenueBookingButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            TagVenueBookingButtonActionPerformed(evt);
        }
    });
    VenueTabBackground.add(TagVenueBookingButton);
    TagVenueBookingButton.setBounds(870, 20, 41, 41);

    MyBookingsLabel1.setFont(new java.awt.Font("Ubuntu Bold", 0, 14));
    MyBookingsLabel1.setText("Your Venue Bookings");
    VenueTabBackground.add(MyBookingsLabel1);
    MyBookingsLabel1.setBounds(530, 30, 140, 26);

    VenueTabBackground.setBounds(0, 0, 940, 420);
    VenueTab.add(VenueTabBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

    MainTab.addTab("", new javax.swing.ImageIcon(getClass().getResource("/images/venuetab.png")), VenueTab); // NOI18N

    Background.add(MainTab);
    MainTab.setBounds(10, 48, 980, 441);

    LeftSide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/leftside.png"))); // NOI18N
    Background.add(LeftSide);
    LeftSide.setBounds(19, 157, 40, 340);

    MinimiseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimisebutton.png"))); // NOI18N
    MinimiseButton.setBorderPainted(false);
    MinimiseButton.setContentAreaFilled(false);
    MinimiseButton.setFocusPainted(false);
    MinimiseButton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
            MinimiseButtonMouseExited(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            MinimiseButtonMousePressed(evt);
        }
    });
    MinimiseButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            MinimiseButtonMouseMoved(evt);
        }
    });
    MinimiseButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            MinimiseButtonActionPerformed(evt);
        }
    });
    Background.add(MinimiseButton);
    MinimiseButton.setBounds(930, 20, 28, 28);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(Background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(Background, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddEventButtonActionPerformed
        
        int at = EventList.getSelectedIndex();
        AddEvent addevent = new AddEvent(this, true);
        addevent.setTitle("Add Event");
        addevent.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addevent.setLocationRelativeTo(null);

        //Display the window.
        Darken.setVisible(true);
        addevent.pack();
        addevent.setVisible(true);
        Darken.setVisible(false);
        GUIUpdater.updateEventList();
        if (at < 0) {
            at = 0;
        }
        EventList.setSelectedIndex(at);
        updateEvent();
    }//GEN-LAST:event_AddEventButtonActionPerformed
    
    private void AddEventDayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddEventDayButtonActionPerformed
        
        AddEventDay addeventday = new AddEventDay(this, true);
        addeventday.setTitle("Add Event Day");
        addeventday.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addeventday.setLocationRelativeTo(null);
        addeventday.myEvent = getSelectedEvent();

        //Display the window.
        Darken.setVisible(true);
        addeventday.pack();
        addeventday.setVisible(true);
        Darken.setVisible(false);
        GUIUpdater.updateEventDayTable();
    }//GEN-LAST:event_AddEventDayButtonActionPerformed
    
    private void MainTabStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_MainTabStateChanged

        if (MainTab.getSelectedIndex() == 0) {
            updateMemo();
        } else if (MainTab.getSelectedIndex() == 1) {
            updateEvent();
        } else if (MainTab.getSelectedIndex() == 2) {
            updateVenue();
        }
        
        if (MainTab.getSelectedIndex() != 1) {
            ContactsListPromptSave(false);
        }
    }//GEN-LAST:event_MainTabStateChanged
    
    private void EventListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_EventListValueChanged
        
        if (EventList.getSelectedIndex() < 0) {
            EventList.setSelectedIndex(0);
        } else {
            ContactsListPromptSave(false);
        }
        EventListPreviousSelected = EventList.getSelectedIndex();
        updateEventDay();
        updateExportEventDay();
        updateBudget();
        updateAnnouncement();
        updateContacts();
        GUIUpdater.updateExportEventDayTable();
        GUIUpdater.updateEventDescriptionTextArea();
    }//GEN-LAST:event_EventListValueChanged
    
    private void EventFilterTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventFilterTableMouseClicked
        if (EventFilterTable.getModel().getValueAt(EventFilterTable.rowAtPoint(evt.getPoint()), 0).equals(false)) {
            EventFilterTable.getModel().setValueAt(true, EventFilterTable.rowAtPoint(evt.getPoint()), 0);
        } else {
            EventFilterTable.getModel().setValueAt(false, EventFilterTable.rowAtPoint(evt.getPoint()), 0);
        }
        if (MemoCalendar.getDate() == null) {
            MemoCalendar.setDate(new Date(System.currentTimeMillis()));
        }
        
        try {
            GUIUpdater.updateScheduleByDateTable();
            GUIUpdater.updateTaskByPriorityTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Unexpected error!", JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_EventFilterTableMouseClicked
    
    private void EventDayTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventDayTableMouseClicked
        
        if (EventDayTableClicked == -1) {
            EventDayTableClicked = EventDayTable.rowAtPoint(evt.getPoint());
            DeleteEventDayButton.setVisible(true);
            EditEventDayButton.setVisible(true);
            EventDayNotesTextBackground.setVisible(true);
            EventDayItineraryTextBackground.setVisible(true);
            
        } else {
            if (EventDayTable.rowAtPoint(evt.getPoint()) == EventDayTableClicked) {
                EventDayTableClicked = -1;
                EventDayTable.clearSelection();
                DeleteEventDayButton.setVisible(false);
                EditEventDayButton.setVisible(false);
                EventDayNotesTextBackground.setVisible(false);
                EventDayItineraryTextBackground.setVisible(false);
                return;
            } else {
                EventDayTableClicked = EventDayTable.rowAtPoint(evt.getPoint());
            }
            
        }
        GUIUpdater.updateEventDayNotesTextArea();
        GUIUpdater.updateEventDayItineraryTextArea();
    }//GEN-LAST:event_EventDayTableMouseClicked
    int currEventTab = 0;
    private void EventDetailsTabStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_EventDetailsTabStateChanged

        int cur = EventDetailsTab.getSelectedIndex();
        switch (cur) {
            case 0:
                updateEventDay();
                break;
            case 1:
                updateBudget();
                break;
            case 2:
                GUIUpdater.updateContactsTable();
                break;
            case 3:
                updateAnnouncement();
                break;
            case 4:
                updateExportEventDay();
                break;
            default:
        }
        
        if (currEventTab == 2 && cur != 2) {   // prompt save
            currEventTab = cur;
            ContactsListPromptSave(false);
        } else {
            currEventTab = cur;
        }
    }//GEN-LAST:event_EventDetailsTabStateChanged
    
    private void ExpenseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExpenseTableMouseClicked
        
        if (ExpenseTableClicked == -1 && ExpenseTablePointer.get(ExpenseTable.rowAtPoint(evt.getPoint())) != null) {
            ExpenseTableClicked = ExpenseTable.rowAtPoint(evt.getPoint());
            DeleteBudgetExpenseButton.setVisible(true);
            EditBudgetExpenseButton.setVisible(true);
        } else {
            if (ExpenseTable.rowAtPoint(evt.getPoint()) == ExpenseTableClicked || ExpenseTablePointer.get(ExpenseTable.rowAtPoint(evt.getPoint())) == null) {
                ExpenseTableClicked = -1;
                ExpenseTable.clearSelection();
                DeleteBudgetExpenseButton.setVisible(false);
                EditBudgetExpenseButton.setVisible(false);
                
                return;
            } else {
                ExpenseTableClicked = ExpenseTable.rowAtPoint(evt.getPoint());
            }
            
        }
        
    }//GEN-LAST:event_ExpenseTableMouseClicked
    
    private void EditEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditEventButtonActionPerformed
        
        int at = EventList.getSelectedIndex();
        EditEvent editevent = new EditEvent(this, true, getSelectedEvent());
        editevent.setTitle("Edit Event");
        editevent.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        editevent.setLocationRelativeTo(null);

        //Display the window.
        Darken.setVisible(true);
        editevent.pack();
        editevent.setVisible(true);
        Darken.setVisible(false);
        GUIUpdater.updateEventList();
        EventList.setSelectedIndex(at);
        GUIUpdater.updateEventDayTable();
    }//GEN-LAST:event_EditEventButtonActionPerformed
    
    private void AddTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddTaskButtonActionPerformed
        AddTask addtask = new AddTask(this, true);
        addtask.setTitle("Add Task");
        addtask.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addtask.setLocationRelativeTo(null);

        //Display the window.
        Darken.setVisible(true);
        addtask.pack();
        addtask.setVisible(true);
        Darken.setVisible(false);
        updateMemo();
    }//GEN-LAST:event_AddTaskButtonActionPerformed
    
    private void TaskByPriorityTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TaskByPriorityTableMouseClicked
        if (TaskTableClicked == -1 && PriorityTablePointer.get(TaskByPriorityTable.rowAtPoint(evt.getPoint())) != null) {
            TaskTableClicked = TaskByPriorityTable.rowAtPoint(evt.getPoint());
            DeleteTaskButton.setVisible(true);
            CompleteTaskButton.setVisible(true);
            EditTaskButton.setVisible(true);
        } else {
            if (TaskByPriorityTable.rowAtPoint(evt.getPoint()) == TaskTableClicked || PriorityTablePointer.get(TaskByPriorityTable.rowAtPoint(evt.getPoint())) == null) {
                TaskTableClicked = -1;
                TaskByPriorityTable.clearSelection();
                DeleteTaskButton.setVisible(false);
                CompleteTaskButton.setVisible(false);
                EditTaskButton.setVisible(false);
                return;
            } else {
                TaskTableClicked = TaskByPriorityTable.rowAtPoint(evt.getPoint());
            }
            
        }
    }//GEN-LAST:event_TaskByPriorityTableMouseClicked
    
    private void ScheduleByDateTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScheduleByDateTableMouseClicked
        
        /*
        int at = ScheduleByDateTable.rowAtPoint(evt.getPoint());                  
        if (ScheduleTablePointer.get(at) instanceof Task) {             
        EditTask edittask = new EditTask(this, true, (Task) ScheduleTablePointer.get(at));      
        edittask.setTitle("Edit Task");             
        edittask.setDefaultCloseOperation(DISPOSE_ON_CLOSE);              
        //Display the window.             
        edittask.pack();             
        edittask.setVisible(true);         
        } else if (ScheduleTablePointer.get(at) instanceof EventDay) {             
        EditEventDay editeventday = new EditEventDay(this, true, (EventDay) ScheduleTablePointer.get(at));             
        editeventday.setTitle("Edit Event Day");             
        editeventday.setDefaultCloseOperation(DISPOSE_ON_CLOSE);              
        //Display the window.            
        editeventday.pack();            
        editeventday.setVisible(true);         
        }        
        this.updateMemo();     
         */
    }//GEN-LAST:event_ScheduleByDateTableMouseClicked
    
    private void ScheduleByDateTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScheduleByDateTableMouseMoved
        
        /*
        int at = ScheduleByDateTable.rowAtPoint(evt.getPoint());
        if (at != -1 && ScheduleTablePointer.get(at) != null)
        ScheduleByDateTable.setRowSelectionInterval(at, at);
        else
        ScheduleByDateTable.clearSelection();
        
         */
    }//GEN-LAST:event_ScheduleByDateTableMouseMoved
    
    private void ScheduleByDateTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScheduleByDateTableMouseExited
        
        // ScheduleByDateTable.clearSelection();
    }//GEN-LAST:event_ScheduleByDateTableMouseExited
    
    private void TaskByPriorityTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TaskByPriorityTableMouseMoved
        
        if (TaskTableClicked == -1 && PriorityTablePointer.get(TaskByPriorityTable.rowAtPoint(evt.getPoint())) != null) {
            int at = TaskByPriorityTable.rowAtPoint(evt.getPoint());
            if (at != -1) {
                TaskByPriorityTable.setRowSelectionInterval(at, at);
            } else if (TaskTableClicked == -1) {
                TaskByPriorityTable.clearSelection();
            }
        }
    }//GEN-LAST:event_TaskByPriorityTableMouseMoved
    
    private void TaskByPriorityTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TaskByPriorityTableMouseExited
        if (TaskTableClicked == -1) {
            TaskByPriorityTable.clearSelection();
        }
    }//GEN-LAST:event_TaskByPriorityTableMouseExited
    
    private void EventFilterTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventFilterTableMouseExited
        EventFilterTable.clearSelection();
    }//GEN-LAST:event_EventFilterTableMouseExited
    
    private void EventFilterTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventFilterTableMouseMoved
        int at = EventFilterTable.rowAtPoint(evt.getPoint());
        if (at != -1) {
            EventFilterTable.setRowSelectionInterval(at, at);
        } else {
            EventFilterTable.clearSelection();
        }
    }//GEN-LAST:event_EventFilterTableMouseMoved
    
    private void AddTaskButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddTaskButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = AddTaskButton.getWidth() / 2 - x;
        double dy = AddTaskButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= AddTaskButton.getWidth() / 2) {
            AddTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonhover.png")));
        } else {
            AddTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
        }
        
        
    }//GEN-LAST:event_AddTaskButtonMouseMoved
    
    private void AddTaskButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddTaskButtonMouseExited
        AddTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
    }//GEN-LAST:event_AddTaskButtonMouseExited
    
    private void EventDayTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventDayTableMouseMoved
        
        if (EventDayTableClicked == -1) {
            int at = EventDayTable.rowAtPoint(evt.getPoint());
            if (at != -1) {
                EventDayTable.setRowSelectionInterval(at, at);
            } else if (EventDayTableClicked == -1) {
                EventDayTable.clearSelection();
            }
        }
    }//GEN-LAST:event_EventDayTableMouseMoved
    
    private void EventDayTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EventDayTableMouseExited
        
        if (EventDayTableClicked == -1) {
            EventDayTable.clearSelection();
        }
    }//GEN-LAST:event_EventDayTableMouseExited
    
    private void AddEventDayButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEventDayButtonMouseMoved
        
        int x = evt.getX();
        int y = evt.getY();
        double dx = AddEventDayButton.getWidth() / 2 - x;
        double dy = AddEventDayButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= AddEventDayButton.getWidth() / 2) {
            AddEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonhover.png")));
        } else {
            AddEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
        }
    }//GEN-LAST:event_AddEventDayButtonMouseMoved
    
    private void AddEventButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEventButtonMouseMoved
        
        int x = evt.getX();
        int y = evt.getY();
        double dx = AddEventButton.getWidth() / 2 - x;
        double dy = AddEventButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= AddEventDayButton.getWidth() / 2) {
            AddEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonhover.png")));
        } else {
            AddEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
        }
    }//GEN-LAST:event_AddEventButtonMouseMoved
    
    private void AddEventButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEventButtonMouseExited
        
        AddEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
    }//GEN-LAST:event_AddEventButtonMouseExited
    
    private void AddEventDayButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEventDayButtonMouseExited
        
        AddEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
        
    }//GEN-LAST:event_AddEventDayButtonMouseExited
    
    private void EditEventButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditEventButtonMouseMoved
        
        int x = evt.getX();
        int y = evt.getY();
        double dx = EditEventButton.getWidth() / 2 - x;
        double dy = EditEventButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= EditEventButton.getWidth() / 2) {
            EditEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonhover.png")));
        } else {
            EditEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
        }
    }//GEN-LAST:event_EditEventButtonMouseMoved
    
    private void EditEventButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditEventButtonMouseExited
        
        EditEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
    }//GEN-LAST:event_EditEventButtonMouseExited
    
        private void CompleteTaskButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CompleteTaskButtonMouseExited
            
            CompleteTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png")));
    }//GEN-LAST:event_CompleteTaskButtonMouseExited
    
    private void CompleteTaskButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CompleteTaskButtonMouseMoved
        
        int x = evt.getX();
        int y = evt.getY();
        double dx = CompleteTaskButton.getWidth() / 2 - x;
        double dy = CompleteTaskButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= CompleteTaskButton.getWidth() / 2) {
            CompleteTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbuttonhover.png")));
        } else {
            CompleteTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png")));
        }
    }//GEN-LAST:event_CompleteTaskButtonMouseMoved
    
    private void CompleteTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CompleteTaskButtonActionPerformed
        
        Task t = (Task) PriorityTablePointer.get(TaskByPriorityTable.getSelectedRow());
        int wasAt = EventList.getSelectedIndex();
        int n = JOptionPane.showConfirmDialog(this,
                "Mark " + t.getDescription() + " as completed?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            try {
                LogicController.setCompleted(t, true);
            } catch (Exception ex) {
                Logger.getLogger(EditEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateMemo();
        }
    }//GEN-LAST:event_CompleteTaskButtonActionPerformed
    
    private void AddExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddExpenseButtonActionPerformed
        BigDecimal amount = null;
        try {
            amount = new BigDecimal(ExpenseAmountText.getText());
        } catch (Exception exp) {
            JOptionPane.showMessageDialog(this, "Amount seems wrong..", "Oops!", JOptionPane.PLAIN_MESSAGE);
            ExpenseAmountText.setSelectionStart(0);
            ExpenseAmountText.setSelectionEnd(ExpenseAmountText.getText().length());
            return;
        }
        
        BudgetCategory b;
        if (CategoryDropDown.getSelectedIndex() > 0) {
            b = getSelectedEvent().getBudgetCategoryList().get(CategoryDropDown.getSelectedIndex() - 1);
        } else {
            b = getSelectedEvent().getUncategorizedBudget();
        }
        try {
            LogicController.addExpense(b, ExpenseDescriptionText.getText(), amount.multiply(new BigDecimal("100")).toBigInteger().longValue());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Oops!", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        GUIUpdater.updateTotalExpense();
        GUIUpdater.updateRemainingBudget();
        GUIUpdater.updateExpenseTable();
        ExpenseDescriptionText.setText("");
        ExpenseAmountText.setText("");
    }//GEN-LAST:event_AddExpenseButtonActionPerformed
    
    private void EditBudgetExpenseButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditBudgetExpenseButtonMouseExited
        
        EditBudgetExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
    }//GEN-LAST:event_EditBudgetExpenseButtonMouseExited
    
    private void EditBudgetExpenseButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditBudgetExpenseButtonMouseMoved
        
        int x = evt.getX();
        int y = evt.getY();
        double dx = EditBudgetExpenseButton.getWidth() / 2 - x;
        double dy = EditBudgetExpenseButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= EditBudgetExpenseButton.getWidth() / 2) {
            EditBudgetExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonhover.png")));
        } else {
            EditBudgetExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
        }
    }//GEN-LAST:event_EditBudgetExpenseButtonMouseMoved
    
    private void EditBudgetExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBudgetExpenseButtonActionPerformed
        
        int at = ExpenseTable.getSelectedRow();
        if (ExpenseTablePointer.get(at) instanceof BudgetCategory) {
            EditBudgetCategory editbc = new EditBudgetCategory(this, true, (BudgetCategory) ExpenseTablePointer.get(at));
            editbc.setTitle("Edit Budget Category");
            editbc.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            editbc.setLocationRelativeTo(null);

            //Display the window.
            Darken.setVisible(true);
            editbc.pack();
            editbc.setVisible(true);
            Darken.setVisible(false);
            GUIUpdater.updateExpenseTable();
            GUIUpdater.updateTotalExpense();
            GUIUpdater.updateRemainingBudget();
        } else if (ExpenseTablePointer.get(at) instanceof Expense) {
            EditExpense editexp = new EditExpense(this, true, (Expense) ExpenseTablePointer.get(at));
            editexp.setTitle("Edit Expense");
            editexp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            editexp.setLocationRelativeTo(null);

            //Display the window.
            Darken.setVisible(true);
            editexp.pack();
            editexp.setVisible(true);
            Darken.setVisible(false);
            GUIUpdater.updateExpenseTable();
            GUIUpdater.updateTotalExpense();
        }
        ExpenseTable.clearSelection();
        ExpenseTableClicked = -1;
        updateBudget();
    }//GEN-LAST:event_EditBudgetExpenseButtonActionPerformed
    
    private void AddExpenseButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddExpenseButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = AddExpenseButton.getWidth() / 2 - x;
        double dy = AddExpenseButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= AddExpenseButton.getWidth() / 2) {
            AddExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonhover.png")));
        } else {
            AddExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
        }
    }//GEN-LAST:event_AddExpenseButtonMouseMoved
    
    private void AddExpenseButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddExpenseButtonMouseExited
        
        AddExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
    }//GEN-LAST:event_AddExpenseButtonMouseExited
    
    private void DeleteEventButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteEventButtonMouseExited
        
        DeleteEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
    }//GEN-LAST:event_DeleteEventButtonMouseExited
    
    private void DeleteEventButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteEventButtonMouseMoved
        
        int x = evt.getX();
        int y = evt.getY();
        double dx = DeleteEventButton.getWidth() / 2 - x;
        double dy = DeleteEventButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= DeleteEventButton.getWidth() / 2) {
            DeleteEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonhover.png")));
        } else {
            DeleteEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
        }
        
    }//GEN-LAST:event_DeleteEventButtonMouseMoved
    
    private void DeleteEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteEventButtonActionPerformed
        ContactsEditClicked = -1;
        Event e = getSelectedEvent();
        int wasAt = EventList.getSelectedIndex();
        int n = JOptionPane.showConfirmDialog(this,
                "Delete " + e.getTitle() + "?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            try {
                LogicController.deleteEvent(e);
            } catch (Exception ex) {
                Logger.getLogger(EditEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
            GUIUpdater.resetEventFilterTable();
            GUIUpdater.updateEventFilterTable();
        }
        updateEvent();
        if (wasAt >= EventList.getModel().getSize()) {
            EventList.setSelectedIndex(wasAt - 1);
        } else {
            EventList.setSelectedIndex(wasAt);
        }
        
    }//GEN-LAST:event_DeleteEventButtonActionPerformed
    
    private void DeleteTaskButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteTaskButtonMouseExited
        
        DeleteTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
    }//GEN-LAST:event_DeleteTaskButtonMouseExited
    
    private void DeleteTaskButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteTaskButtonMouseMoved
        
        int x = evt.getX();
        int y = evt.getY();
        double dx = DeleteTaskButton.getWidth() / 2 - x;
        double dy = DeleteTaskButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= DeleteTaskButton.getWidth() / 2) {
            DeleteTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonhover.png")));
        } else {
            DeleteTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
        }
    }//GEN-LAST:event_DeleteTaskButtonMouseMoved
    
    private void DeleteTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteTaskButtonActionPerformed
        
        Task t = (Task) PriorityTablePointer.get(TaskByPriorityTable.getSelectedRow());
        int wasAt = EventList.getSelectedIndex();
        int n = JOptionPane.showConfirmDialog(this,
                "Delete " + t.getDescription() + "?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            try {
                LogicController.deleteTask(t);
            } catch (Exception ex) {
                Logger.getLogger(EditEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateMemo();
        }
    }//GEN-LAST:event_DeleteTaskButtonActionPerformed
    
    private void EditEventDayButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditEventDayButtonMouseExited
        
        EditEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
    }//GEN-LAST:event_EditEventDayButtonMouseExited
    
    private void EditEventDayButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditEventDayButtonMouseMoved
        
        int x = evt.getX();
        int y = evt.getY();
        double dx = EditEventDayButton.getWidth() / 2 - x;
        double dy = EditEventDayButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= EditEventDayButton.getWidth() / 2) {
            EditEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonhover.png")));
        } else {
            EditEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
        }
    }//GEN-LAST:event_EditEventDayButtonMouseMoved
    
    private void EditEventDayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditEventDayButtonActionPerformed
        
        EventDay toEdit = getSelectedEvent().getEventDayList().get(EventDayTable.getSelectedRow());
        EditEventDay editeventday = new EditEventDay(this, true, toEdit);
        editeventday.setTitle("Edit Event Day");
        editeventday.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        editeventday.setLocationRelativeTo(null);

        //Display the window.
        Darken.setVisible(true);
        editeventday.pack();
        editeventday.setVisible(true);
        Darken.setVisible(false);
        GUIUpdater.updateEventDayTable();
    }//GEN-LAST:event_EditEventDayButtonActionPerformed
    
    private void DeleteEventDayButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteEventDayButtonMouseExited
        
        DeleteEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
    }//GEN-LAST:event_DeleteEventDayButtonMouseExited
    
    private void DeleteEventDayButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteEventDayButtonMouseMoved
        
        int x = evt.getX();
        int y = evt.getY();
        double dx = DeleteEventDayButton.getWidth() / 2 - x;
        double dy = DeleteEventDayButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= DeleteEventDayButton.getWidth() / 2) {
            DeleteEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonhover.png")));
        } else {
            DeleteEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
        }
    }//GEN-LAST:event_DeleteEventDayButtonMouseMoved
    
    private void DeleteEventDayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteEventDayButtonActionPerformed
        EventDay todelete = getSelectedEvent().getEventDayList().get(EventDayTable.getSelectedRow());
        int n = JOptionPane.showConfirmDialog(this,
                "Delete " + todelete.getDescription() + "?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            try {
                LogicController.deleteEventDay(todelete);
            } catch (Exception ex) {
                Logger.getLogger(PEPOGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            GUIUpdater.updateEventDayTable();
        }
    }//GEN-LAST:event_DeleteEventDayButtonActionPerformed
    
    private void CategoryDropDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CategoryDropDownActionPerformed
        
    }//GEN-LAST:event_CategoryDropDownActionPerformed
    
    private void CategoryDropDownItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CategoryDropDownItemStateChanged
        /*
        if (CategoryDropDown.getItemCount() > 1 && CategoryDropDown.getSelectedIndex() == CategoryDropDown.getItemCount()-1){
        AddBudgetCategory addbc = new AddBudgetCategory(this, true,getSelectedEvent());
        addbc.setTitle("Add Budget Category");
        addbc.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addbc.setLocationRelativeTo(null);
        
        //Display the window.
        Darken.setVisible(true);
        addbc.pack();
        addbc.setVisible(true);
        Darken.setVisible(false);
        updateBudget();
        }
        
         */
    }//GEN-LAST:event_CategoryDropDownItemStateChanged
    
    private void ExpenseTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExpenseTableMouseMoved
        

        if (ExpenseTableClicked == -1 && ExpenseTable.rowAtPoint(evt.getPoint()) >= 0 && ExpenseTablePointer.get(ExpenseTable.rowAtPoint(evt.getPoint())) != null) {
            int at = ExpenseTable.rowAtPoint(evt.getPoint());
            if (at != -1) {
                ExpenseTable.setRowSelectionInterval(at, at);
            } else if (ExpenseTableClicked == -1) {
                ExpenseTable.clearSelection();
            }
        }
    }//GEN-LAST:event_ExpenseTableMouseMoved
    
    private void ExpenseTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExpenseTableMouseExited
        if (ExpenseTableClicked == -1) {
            ExpenseTable.clearSelection();
        }
    }//GEN-LAST:event_ExpenseTableMouseExited
    
    private void DeleteBudgetExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBudgetExpenseButtonActionPerformed
        Object cur = ExpenseTablePointer.get(ExpenseTable.getSelectedRow());
        String del = "Delete ";
        if (cur instanceof Expense) {
            del = del + ((Expense) cur).getTitle() + " expense";
        } else if (cur instanceof BudgetCategory) {
            del = del + ((BudgetCategory) cur).getName() + " category";
        }
        int n = JOptionPane.showConfirmDialog(this,
                del + "?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            try {
                if (ExpenseTablePointer.get(ExpenseTable.getSelectedRow()) instanceof Expense) {
                    LogicController.deleteExpense((Expense) ExpenseTablePointer.get(ExpenseTable.getSelectedRow()));
                } else if (ExpenseTablePointer.get(ExpenseTable.getSelectedRow()) instanceof BudgetCategory) {
                    LogicController.deleteBudgetCategory((BudgetCategory) ExpenseTablePointer.get(ExpenseTable.getSelectedRow()));
                }
                updateBudget();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Oops!", JOptionPane.PLAIN_MESSAGE);
                return;
            }
        }
    }//GEN-LAST:event_DeleteBudgetExpenseButtonActionPerformed
    
    private void DeleteBudgetExpenseButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteBudgetExpenseButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = DeleteBudgetExpenseButton.getWidth() / 2 - x;
        double dy = DeleteBudgetExpenseButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= DeleteBudgetExpenseButton.getWidth() / 2) {
            DeleteBudgetExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonhover.png")));
        } else {
            DeleteBudgetExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
        }
    }//GEN-LAST:event_DeleteBudgetExpenseButtonMouseMoved
    
    private void DeleteBudgetExpenseButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteBudgetExpenseButtonMouseExited
        DeleteBudgetExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
    }//GEN-LAST:event_DeleteBudgetExpenseButtonMouseExited
    
    private void VenueBookingTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VenueBookingTableMouseClicked
        if (MyBookingsTableClicked == -1) {
            MyBookingsTableClicked = VenueBookingTable.rowAtPoint(evt.getPoint());
            DeleteVenueBookingButton.setVisible(true);
            TagVenueBookingButton.setVisible(true);
        } else {
            if (VenueBookingTable.rowAtPoint(evt.getPoint()) == MyBookingsTableClicked) {
                MyBookingsTableClicked = -1;
                VenueBookingTable.clearSelection();
                DeleteVenueBookingButton.setVisible(false);
                TagVenueBookingButton.setVisible(false);
                return;
            } else {
                MyBookingsTableClicked = VenueBookingTable.rowAtPoint(evt.getPoint());
            }
        }
    }//GEN-LAST:event_VenueBookingTableMouseClicked
    
    private void VenueBookingTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VenueBookingTableMouseExited
        if (MyBookingsTableClicked == -1) {
            VenueBookingTable.clearSelection();
        }
    }//GEN-LAST:event_VenueBookingTableMouseExited
    
    private void VenueBookingTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VenueBookingTableMouseMoved
        if (MyBookingsTableClicked == -1) {
            int at = VenueBookingTable.rowAtPoint(evt.getPoint());
            if (at != -1) {
                VenueBookingTable.setRowSelectionInterval(at, at);
            } else if (MyBookingsTableClicked == -1) {
                VenueBookingTable.clearSelection();
            }
        }
    }//GEN-LAST:event_VenueBookingTableMouseMoved
    
    private void VenueTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VenueTableMouseClicked
        if (VenueTableClicked == -1) {
            VenueTableClicked = VenueTable.rowAtPoint(evt.getPoint());
            AddVenueBookingButton.setVisible(true);
        } else {
            if (VenueTable.rowAtPoint(evt.getPoint()) == VenueTableClicked) {
                VenueTableClicked = -1;
                VenueTable.clearSelection();
                AddVenueBookingButton.setVisible(false);
                
                return;
            } else {
                VenueTableClicked = VenueTable.rowAtPoint(evt.getPoint());
            }
        }
    }//GEN-LAST:event_VenueTableMouseClicked
    
    private void VenueTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VenueTableMouseExited
        if (VenueTableClicked == -1) {
            VenueTable.clearSelection();
        }
        
    }//GEN-LAST:event_VenueTableMouseExited
    
    private void VenueTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VenueTableMouseMoved
        if (VenueTableClicked == -1) {
            int at = VenueTable.rowAtPoint(evt.getPoint());
            if (at != -1) {
                VenueTable.setRowSelectionInterval(at, at);
            } else if (VenueTableClicked == -1) {
                VenueTable.clearSelection();
            }
        }
    }//GEN-LAST:event_VenueTableMouseMoved
    
    private void TypeDropDownItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TypeDropDownItemStateChanged
        GUIUpdater.updateVenueTable();
    }//GEN-LAST:event_TypeDropDownItemStateChanged
    
    private void FacultyDropDownItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FacultyDropDownItemStateChanged
        GUIUpdater.updateVenueTable();
    }//GEN-LAST:event_FacultyDropDownItemStateChanged
    
    private void AddVenueBookingButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddVenueBookingButtonMouseExited
        AddVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
    }//GEN-LAST:event_AddVenueBookingButtonMouseExited
    
    private void AddVenueBookingButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddVenueBookingButtonMouseMoved
        
        int x = evt.getX();
        int y = evt.getY();
        double dx = AddVenueBookingButton.getWidth() / 2 - x;
        double dy = AddVenueBookingButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= AddVenueBookingButton.getWidth() / 2) {
            AddVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonhover.png")));
        } else {
            AddVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
        }
    }//GEN-LAST:event_AddVenueBookingButtonMouseMoved
    
    private void AddVenueBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddVenueBookingButtonActionPerformed
        
        int at = VenueTable.getSelectedRow();
        AddVenueBooking addeventbooking = new AddVenueBooking(this, true, (Venue) VenueTablePointer.get(at));
        addeventbooking.setTitle("Book a Venue");
        addeventbooking.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addeventbooking.setLocationRelativeTo(null);

        //Display the window.
        Darken.setVisible(true);
        addeventbooking.pack();
        addeventbooking.setVisible(true);
        Darken.setVisible(false);
        updateVenue();
        VenueTable.clearSelection();
        VenueTableClicked = -1;
    }//GEN-LAST:event_AddVenueBookingButtonActionPerformed
    
    private void DeleteVenueBookingButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteVenueBookingButtonMouseExited
        
        DeleteVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
    }//GEN-LAST:event_DeleteVenueBookingButtonMouseExited
    
    private void DeleteVenueBookingButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteVenueBookingButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = DeleteVenueBookingButton.getWidth() / 2 - x;
        double dy = DeleteVenueBookingButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= DeleteVenueBookingButton.getWidth() / 2) {
            DeleteVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonhover.png")));
        } else {
            DeleteVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
        }
    }//GEN-LAST:event_DeleteVenueBookingButtonMouseMoved
    
    private void DeleteVenueBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteVenueBookingButtonActionPerformed
        VenueBooking vb = LogicController.getFutureVenueBookingList(new Date(System.currentTimeMillis())).get(VenueBookingTable.getSelectedRow());
        
        int n = JOptionPane.showConfirmDialog(this,
                "Delete " + vb.getVenue().getName() + " Booking?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            try {
                LogicController.deleteVenueBooking(vb);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Oops!", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            updateVenue();
        }
    }//GEN-LAST:event_DeleteVenueBookingButtonActionPerformed
    
    private void NameTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NameTextKeyReleased
        GUIUpdater.updateVenueTable();
    }//GEN-LAST:event_NameTextKeyReleased
    
    private void CapacityTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CapacityTextKeyReleased
        GUIUpdater.updateVenueTable();
    }//GEN-LAST:event_CapacityTextKeyReleased
    
    private void EditContactsButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditContactsButtonMouseExited
        if (ContactsEditClicked == -1) {
            EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
        } else {
            EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png")));
        }
    }//GEN-LAST:event_EditContactsButtonMouseExited
    
    private void EditContactsButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditContactsButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = EditContactsButton.getWidth() / 2 - x;
        double dy = EditContactsButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (ContactsEditClicked == -1) {
            if (sr <= EditContactsButton.getWidth() / 2) {
                EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonhover.png")));
            } else {
                EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
            }
        } else {
            if (sr <= EditContactsButton.getWidth() / 2) {
                EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbuttonhover.png")));
            } else {
                EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbutton.png")));
            }
        }
    }//GEN-LAST:event_EditContactsButtonMouseMoved
    
    private void EditContactsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditContactsButtonActionPerformed
        if (ContactsEditClicked == -1) {
            AddContactsButton.setVisible(true);
            DeleteContactsButton.setVisible(true);
            ImportContactsButton.setVisible(true);
            CancelContactsButton.setVisible(true);
            EditContactsHeaderButton.setVisible(true);
            ContactsInstruction.setVisible(true);
            ContactsEditClicked = 1;
            EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbuttonhover.png")));
            ContactsTable.setFocusable(true);
            ContactsTable.setBackground(Color.yellow);
        } else {
            AddContactsButton.setVisible(false);
            DeleteContactsButton.setVisible(false);
            ImportContactsButton.setVisible(false);
            CancelContactsButton.setVisible(false);
            EditContactsHeaderButton.setVisible(false);
            ContactsInstruction.setVisible(false);
            ContactsEditClicked = -1;
            EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonhover.png")));
            ContactsTable.setFocusable(false);
            ContactsTable.setBackground(Color.white);
            if (ContactsTable.getCellEditor() != null) {
                ContactsTable.getCellEditor().stopCellEditing();
            }
            try {
                GUIUpdater.submitContactsTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Unexpected error!", JOptionPane.PLAIN_MESSAGE);
                return;
            }
        }
        
        GUIUpdater.updateContactsTable();
    }//GEN-LAST:event_EditContactsButtonActionPerformed
    
    private void EditContactsHeaderButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditContactsHeaderButtonMouseExited
        EditContactsHeaderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settingsbutton.png")));
    }//GEN-LAST:event_EditContactsHeaderButtonMouseExited
    
    private void EditContactsHeaderButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditContactsHeaderButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = EditContactsHeaderButton.getWidth() / 2 - x;
        double dy = EditContactsHeaderButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        
        if (sr <= EditContactsHeaderButton.getWidth() / 2) {
            EditContactsHeaderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settingsbuttonhover.png")));
        } else {
            EditContactsHeaderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settingsbutton.png")));
        }
    }//GEN-LAST:event_EditContactsHeaderButtonMouseMoved
    
    private void EditContactsHeaderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditContactsHeaderButtonActionPerformed
        ArrayList<String> myattribute = new ArrayList<>();
        for (int i = 2; i < ContactsTable.getColumnModel().getColumnCount(); i++) {
            myattribute.add((String) ContactsTable.getColumnModel().getColumn(i).getHeaderValue());
        }
        EditContactsHeader editcontactsheader = null;
        if (SelectedPersonnelListType == null) {
            SelectedPersonnelListType = "Participants";
        }
        if (SelectedPersonnelListType.equals("Participants")) {
            editcontactsheader = new EditContactsHeader(this, true, myattribute);
            editcontactsheader.setTitle("Set Participant Attributes");
        } else if (SelectedPersonnelListType.equals("Helpers")) {
            editcontactsheader = new EditContactsHeader(this, true, myattribute);
            editcontactsheader.setTitle("Set Helper Attributes");
        } else if (SelectedPersonnelListType.equals("Sponsors")) {
            editcontactsheader = new EditContactsHeader(this, true, myattribute);
            editcontactsheader.setTitle("Set Sponsor Attributes");
        } else if (SelectedPersonnelListType.equals("Vendors")) {
            editcontactsheader = new EditContactsHeader(this, true, myattribute);
            editcontactsheader.setTitle("Set Vendor Attributes");
        }
        
        editcontactsheader.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        editcontactsheader.setLocationRelativeTo(null);

        //Display the window.
        Darken.setVisible(true);
        editcontactsheader.pack();
        editcontactsheader.setVisible(true);
        Darken.setVisible(false);
        
    }//GEN-LAST:event_EditContactsHeaderButtonActionPerformed
    
    private void AddContactsButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddContactsButtonMouseExited
        AddContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
    }//GEN-LAST:event_AddContactsButtonMouseExited
    
    private void AddContactsButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddContactsButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = AddContactsButton.getWidth() / 2 - x;
        double dy = AddContactsButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        
        if (sr <= AddContactsButton.getWidth() / 2) {
            AddContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonhover.png")));
        } else {
            AddContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbutton.png")));
        }
        
    }//GEN-LAST:event_AddContactsButtonMouseMoved
    
    private void AddContactsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddContactsButtonActionPerformed
        
        ((DefaultTableModel) ContactsTable.getModel()).setRowCount(ContactsTable.getRowCount() + 1);
        GUIUpdater.updateContactsCount();
    }//GEN-LAST:event_AddContactsButtonActionPerformed
    
    private void DeleteContactsButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteContactsButtonMouseExited
        DeleteContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
    }//GEN-LAST:event_DeleteContactsButtonMouseExited
    
    private void DeleteContactsButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteContactsButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = DeleteContactsButton.getWidth() / 2 - x;
        double dy = DeleteContactsButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        
        if (sr <= DeleteContactsButton.getWidth() / 2) {
            DeleteContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonhover.png")));
        } else {
            DeleteContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebutton.png")));
        }
    }//GEN-LAST:event_DeleteContactsButtonMouseMoved
    
    private void DeleteContactsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteContactsButtonActionPerformed
        if (ContactsTable.getSelectedRow() < 0) {
            return;
        }
        String name = (String) ContactsTable.getValueAt(ContactsTable.getSelectedRow(), 0);
        if (name == null) {
            name = "";
        }
        int n = JOptionPane.showConfirmDialog(this,
                "Delete " + name + "?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            ((DefaultTableModel) ContactsTable.getModel()).removeRow(ContactsTable.getSelectedRow());
            GUIUpdater.updateContactsCount();
            ContactsTable.clearSelection();
        }
    }//GEN-LAST:event_DeleteContactsButtonActionPerformed
    
    private void TagVenueBookingButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TagVenueBookingButtonMouseExited
        TagVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tagbutton.png")));
    }//GEN-LAST:event_TagVenueBookingButtonMouseExited
    
    private void TagVenueBookingButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TagVenueBookingButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = TagVenueBookingButton.getWidth() / 2 - x;
        double dy = TagVenueBookingButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        
        if (sr <= TagVenueBookingButton.getWidth() / 2) {
            TagVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tagbuttonhover.png")));
        } else {
            TagVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tagbutton.png")));
        }
    }//GEN-LAST:event_TagVenueBookingButtonMouseMoved
    
    private void TagVenueBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TagVenueBookingButtonActionPerformed
        
        TagVenueBooking tagvb = null;
        try {
            tagvb = new TagVenueBooking(this, true, (VenueBooking) VenueBookingTablePointer.get(VenueBookingTable.getSelectedRow()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Unexpected error!", JOptionPane.PLAIN_MESSAGE);
        }
        tagvb.setTitle("Tag VenueBookings");
        tagvb.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tagvb.setLocationRelativeTo(null);

        //Display the window.
        Darken.setVisible(true);
        tagvb.pack();
        tagvb.setVisible(true);
        Darken.setVisible(false);
        GUIUpdater.updateVenueBookingTable();
        
    }//GEN-LAST:event_TagVenueBookingButtonActionPerformed
    
    private void NameTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NameTextMouseClicked
        NameText.setSelectionStart(0);
        NameText.setSelectionEnd(NameText.getText().length());
    }//GEN-LAST:event_NameTextMouseClicked
    
    private void CapacityTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CapacityTextMouseClicked
        CapacityText.setSelectionStart(0);
        CapacityText.setSelectionEnd(CapacityText.getText().length());
    }//GEN-LAST:event_CapacityTextMouseClicked
    
    private void EditTaskButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditTaskButtonMouseExited
        EditTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
    }//GEN-LAST:event_EditTaskButtonMouseExited
    
    private void EditTaskButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditTaskButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = EditTaskButton.getWidth() / 2 - x;
        double dy = EditTaskButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= EditTaskButton.getWidth() / 2) {
            EditTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonhover.png")));
        } else {
            EditTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
        }
    }//GEN-LAST:event_EditTaskButtonMouseMoved
    
    private void EditTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditTaskButtonActionPerformed
        
        int at = TaskByPriorityTable.getSelectedRow();
        
        EditTask edittask = new EditTask(this, true, (Task) PEPOGUI.PriorityTablePointer.get(at));
        edittask.setTitle("Edit Task");
        edittask.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        edittask.setLocationRelativeTo(null);

        //Display the window.
        Darken.setVisible(true);
        edittask.pack();
        edittask.setVisible(true);
        Darken.setVisible(false);
        updateMemo();
        TaskByPriorityTable.clearSelection();
        TaskTableClicked = -1;
    }//GEN-LAST:event_EditTaskButtonActionPerformed
    
    private void EditBudgetButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditBudgetButtonMouseExited
        EditBudgetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/budgetbutton.png")));
    }//GEN-LAST:event_EditBudgetButtonMouseExited
    
    private void EditBudgetButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditBudgetButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = EditBudgetButton.getWidth() / 2 - x;
        double dy = EditBudgetButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= EditBudgetButton.getWidth() / 2) {
            EditBudgetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/budgetbuttonhover.png")));
        } else {
            EditBudgetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/budgetbutton.png")));
        }
    }//GEN-LAST:event_EditBudgetButtonMouseMoved
    
    private void EditBudgetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBudgetButtonActionPerformed
        EditBudget editbudget = new EditBudget(this, true, getSelectedEvent());
        editbudget.setTitle("Edit Budget");
        editbudget.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        editbudget.setLocationRelativeTo(null);

        //Display the window.
        Darken.setVisible(true);
        editbudget.pack();
        editbudget.setVisible(true);
        Darken.setVisible(false);
        updateBudget();
    }//GEN-LAST:event_EditBudgetButtonActionPerformed
    
    private void CompleteTaskButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CompleteTaskButtonMousePressed
        CompleteTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submitbuttonclick.png")));
    }//GEN-LAST:event_CompleteTaskButtonMousePressed
    
    private void DeleteTaskButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteTaskButtonMousePressed
        DeleteTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonclick.png")));
    }//GEN-LAST:event_DeleteTaskButtonMousePressed
    
    private void EditTaskButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditTaskButtonMousePressed
        EditTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonclick.png")));
    }//GEN-LAST:event_EditTaskButtonMousePressed
    
    private void AddTaskButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddTaskButtonMousePressed
        AddTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonclick.png")));
    }//GEN-LAST:event_AddTaskButtonMousePressed
    
    private void AddEventButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEventButtonMousePressed
        AddEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonclick.png")));
    }//GEN-LAST:event_AddEventButtonMousePressed
    
    private void EditEventButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditEventButtonMousePressed
        EditEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonclick.png")));
    }//GEN-LAST:event_EditEventButtonMousePressed
    
    private void DeleteEventButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteEventButtonMousePressed
        DeleteEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonclick.png")));
    }//GEN-LAST:event_DeleteEventButtonMousePressed
    
    private void DeleteEventDayButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteEventDayButtonMousePressed
        DeleteEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonclick.png")));
    }//GEN-LAST:event_DeleteEventDayButtonMousePressed
    
    private void EditEventDayButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditEventDayButtonMousePressed
        EditEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonclick.png")));
    }//GEN-LAST:event_EditEventDayButtonMousePressed
    
    private void AddEventDayButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEventDayButtonMousePressed
        AddEventDayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonclick.png")));
    }//GEN-LAST:event_AddEventDayButtonMousePressed
    
    private void EditBudgetButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditBudgetButtonMousePressed
        EditBudgetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/budgetbuttonclick.png")));
    }//GEN-LAST:event_EditBudgetButtonMousePressed
    
    private void DeleteBudgetExpenseButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteBudgetExpenseButtonMousePressed
        DeleteBudgetExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonclick.png")));
    }//GEN-LAST:event_DeleteBudgetExpenseButtonMousePressed
    
    private void EditBudgetExpenseButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditBudgetExpenseButtonMousePressed
        EditBudgetExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonclick.png")));
    }//GEN-LAST:event_EditBudgetExpenseButtonMousePressed
    
    private void AddExpenseButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddExpenseButtonMousePressed
        AddExpenseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonclick.png")));
    }//GEN-LAST:event_AddExpenseButtonMousePressed
    
    private void DeleteContactsButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteContactsButtonMousePressed
        DeleteContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonclick.png")));
    }//GEN-LAST:event_DeleteContactsButtonMousePressed
    
    private void AddContactsButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddContactsButtonMousePressed
        AddContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonclick.png")));
    }//GEN-LAST:event_AddContactsButtonMousePressed
    
    private void EditContactsHeaderButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditContactsHeaderButtonMousePressed
        EditContactsHeaderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settingsbuttonclick.png")));
    }//GEN-LAST:event_EditContactsHeaderButtonMousePressed
    
    private void EditContactsButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditContactsButtonMousePressed
        EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonclick.png")));
    }//GEN-LAST:event_EditContactsButtonMousePressed
    
    private void DeleteVenueBookingButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteVenueBookingButtonMousePressed
        DeleteVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deletebuttonclick.png")));
    }//GEN-LAST:event_DeleteVenueBookingButtonMousePressed
    
    private void TagVenueBookingButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TagVenueBookingButtonMousePressed
        TagVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tagbuttonclick.png")));
    }//GEN-LAST:event_TagVenueBookingButtonMousePressed
    
    private void AddVenueBookingButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddVenueBookingButtonMousePressed
        AddVenueBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonclick.png")));
    }//GEN-LAST:event_AddVenueBookingButtonMousePressed
    
    private void AnnouncementButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnnouncementButtonMouseExited
        AnnouncementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/emailbutton.png")));
    }//GEN-LAST:event_AnnouncementButtonMouseExited
    
    private void AnnouncementButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnnouncementButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = AnnouncementButton.getWidth() / 2 - x;
        double dy = AnnouncementButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= AnnouncementButton.getWidth() / 2) {
            AnnouncementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/emailbuttonhover.png")));
        } else {
            AnnouncementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/emailbutton.png")));
        }
    }//GEN-LAST:event_AnnouncementButtonMouseMoved
    
    private void AnnouncementButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnnouncementButtonMousePressed
        AnnouncementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/emailbuttonclick.png")));
    }//GEN-LAST:event_AnnouncementButtonMousePressed
    
    private void AnnouncementButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnnouncementButtonMouseReleased
        AnnouncementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/emailbuttonhover.png")));
    }//GEN-LAST:event_AnnouncementButtonMouseReleased
    
    private void AddContactsButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddContactsButtonMouseReleased
        AddContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addbuttonhover.png")));
    }//GEN-LAST:event_AddContactsButtonMouseReleased
    
    private void ExportButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExportButtonMouseExited
        ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exportbutton.png")));
    }//GEN-LAST:event_ExportButtonMouseExited
    
    private void ExportButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExportButtonMousePressed
        ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exportbuttonclick.png")));
    }//GEN-LAST:event_ExportButtonMousePressed
    
    private void ExportButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExportButtonMouseReleased
        ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exportbuttonhover.png")));
    }//GEN-LAST:event_ExportButtonMouseReleased
    
    private void ExportButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExportButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = ExportButton.getWidth() / 2 - x;
        double dy = ExportButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= ExportButton.getWidth() / 2) {
            ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exportbuttonhover.png")));
        } else {
            ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exportbutton.png")));
        }
    }//GEN-LAST:event_ExportButtonMouseMoved
    
    private void ImportContactsButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImportContactsButtonMouseExited
        ImportContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/importbutton.png")));
    }//GEN-LAST:event_ImportContactsButtonMouseExited
    
    private void ImportContactsButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImportContactsButtonMousePressed
        ImportContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/importbuttonclick.png")));
    }//GEN-LAST:event_ImportContactsButtonMousePressed
    
    private void ImportContactsButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImportContactsButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = ImportContactsButton.getWidth() / 2 - x;
        double dy = ImportContactsButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= ImportContactsButton.getWidth() / 2) {
            ImportContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/importbuttonhover.png")));
        } else {
            ImportContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/importbutton.png")));
        }
    }//GEN-LAST:event_ImportContactsButtonMouseMoved
    
    private void ImportContactsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportContactsButtonActionPerformed
        Darken.setVisible(true);
        switch (ContactsListDropDown.getSelectedIndex()) {
            case 0:
                GUIUpdater.importParticipants();
                break;
            case 1:
                GUIUpdater.importHelpers();
                break;
            case 2:
                GUIUpdater.importSponsors();
                break;
            case 3:
                GUIUpdater.importVendors();
        }
        GUIUpdater.updateContactsCount();
        Darken.setVisible(false);
    }//GEN-LAST:event_ImportContactsButtonActionPerformed
    
    private void ImportContactsButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImportContactsButtonMouseReleased
        ImportContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/importbuttonhover.png")));
    }//GEN-LAST:event_ImportContactsButtonMouseReleased
    
    private void CloseButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloseButtonMouseExited
        CloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/closebutton.png")));
    }//GEN-LAST:event_CloseButtonMouseExited
    
    private void CloseButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloseButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = CloseButton.getWidth() / 2 - x;
        double dy = CloseButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= CloseButton.getWidth() / 2) {
            CloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/closebuttonhover.png")));
        } else {
            CloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/closebutton.png")));
        }
    }//GEN-LAST:event_CloseButtonMouseMoved
    
    private void CloseButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloseButtonMousePressed
        CloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/closebuttonclick.png")));
    }//GEN-LAST:event_CloseButtonMousePressed
    
    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        try {
            LogicController.exit();   // save before exit
        } catch (Exception obj) {
            JOptionPane.showMessageDialog(this, obj.getMessage() + "..", "Cannot save.", JOptionPane.PLAIN_MESSAGE);
        }
        SystemTray.getSystemTray().remove(myIcon);
        this.dispose();
    }//GEN-LAST:event_CloseButtonActionPerformed
    private int theme = 0;
    private void LayoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LayoutButtonActionPerformed
        
        switch (theme) {
            case 0:
                LeftSide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/leftside2.png")));
                Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/headerlogo2.png")));
                Background.setBackground(new Color(51, 0, 51));
                break;
            case 1:
                LeftSide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/leftside3.png")));
                Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/headerlogo3.png")));
                Background.setBackground(new Color(0, 51, 51));
                break;
            case 2:
                LeftSide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/leftside4.png")));
                Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/headerlogo4.png")));
                Background.setBackground(new Color(51, 0, 0));
                break;
            case 3:
                LeftSide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/leftside.png")));
                Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/headerlogo.png")));
                Background.setBackground(new Color(51, 51, 0));
                theme = -1;
                break;
        }
        
        theme++;
    }//GEN-LAST:event_LayoutButtonActionPerformed
    
    private void MemoCalendarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_MemoCalendarPropertyChange
        try {
            GUIUpdater.updateScheduleByDateTable();
            GUIUpdater.updateTaskByPriorityTable();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_MemoCalendarPropertyChange
    
    private void ArchiveEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArchiveEventButtonActionPerformed
        if (ContactsEditClicked != -1) {
            if (ContactsTable.getCellEditor() != null) {
                ContactsTable.getCellEditor().stopCellEditing();
            }
            ContactsListPromptSave(true);
        }
        int at = EventList.getSelectedIndex();
        ArchiveEvent archiveevent = new ArchiveEvent(this, true);
        archiveevent.setTitle("Archive Event");
        archiveevent.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        archiveevent.setLocationRelativeTo(null);

        //Display the window
        Darken.setVisible(true);
        archiveevent.pack();
        archiveevent.setVisible(true);
        Darken.setVisible(false);
        GUIUpdater.updateEventList();
        GUIUpdater.resetEventFilterTable();
        GUIUpdater.updateEventFilterTable();
        if (at < 0) {
            at = 0;
        }
        EventList.setSelectedIndex(at);
        updateEvent();
    }//GEN-LAST:event_ArchiveEventButtonActionPerformed
    
    private void GeneralExportTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GeneralExportTableMouseMoved
        int at = GeneralExportTable.rowAtPoint(evt.getPoint());
        if (at != -1) {
            GeneralExportTable.setRowSelectionInterval(at, at);
        } else {
            GeneralExportTable.clearSelection();
        }
    }//GEN-LAST:event_GeneralExportTableMouseMoved
    
    private void GeneralExportTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GeneralExportTableMouseExited
        GeneralExportTable.clearSelection();
    }//GEN-LAST:event_GeneralExportTableMouseExited
    
    private void GeneralExportTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GeneralExportTableMouseClicked
        if (GeneralExportTable.getModel().getValueAt(GeneralExportTable.rowAtPoint(evt.getPoint()), 0).equals(false)) {
            GeneralExportTable.getModel().setValueAt(true, GeneralExportTable.rowAtPoint(evt.getPoint()), 0);
        } else {
            GeneralExportTable.getModel().setValueAt(false, GeneralExportTable.rowAtPoint(evt.getPoint()), 0);
        }
    }//GEN-LAST:event_GeneralExportTableMouseClicked
    
    private void ExportEventDayTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExportEventDayTableMouseClicked
        if (ExportEventDayTable.getModel().getValueAt(ExportEventDayTable.rowAtPoint(evt.getPoint()), 0).equals(false)) {
            ExportEventDayTable.getModel().setValueAt(true, ExportEventDayTable.rowAtPoint(evt.getPoint()), 0);
        } else {
            ExportEventDayTable.getModel().setValueAt(false, ExportEventDayTable.rowAtPoint(evt.getPoint()), 0);
        }
    }//GEN-LAST:event_ExportEventDayTableMouseClicked
    
    private void ExportEventDayTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExportEventDayTableMouseExited
        ExportEventDayTable.clearSelection();
    }//GEN-LAST:event_ExportEventDayTableMouseExited
    
    private void ExportEventDayTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExportEventDayTableMouseMoved
        int at = ExportEventDayTable.rowAtPoint(evt.getPoint());
        if (at != -1) {
            ExportEventDayTable.setRowSelectionInterval(at, at);
        } else {
            ExportEventDayTable.clearSelection();
        }
    }//GEN-LAST:event_ExportEventDayTableMouseMoved
    
    private void ExportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportButtonActionPerformed
        Event currevent = LogicController.getEventList().get(EventList.getSelectedIndex());
        ArrayList<EventDay> toPrint = new ArrayList<>();
        for (int i = 0; i < ExportEventDayTable.getRowCount(); i++) {
            if ((Boolean) ExportEventDayTable.getValueAt(i, 0)) {
                toPrint.add(currevent.getEventDayList().get(i));
            }
        }
        
        Exporter.Export(getSelectedEvent().getTitle() + " Report", currevent,
                (Boolean) GeneralExportTable.getValueAt(0, 0), // event description
                (Boolean) GeneralExportTable.getValueAt(1, 0), // budget
                (Boolean) GeneralExportTable.getValueAt(2, 0), // participant
                (Boolean) GeneralExportTable.getValueAt(3, 0), // helper
                (Boolean) GeneralExportTable.getValueAt(4, 0), // sponsor
                (Boolean) GeneralExportTable.getValueAt(5, 0), // vendor
                (Boolean) GeneralExportTable.getValueAt(6, 0), // task
                toPrint);
        JOptionPane.showMessageDialog(this, getSelectedEvent().getTitle() + " Report.html has been generated successfully.", "Export", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_ExportButtonActionPerformed
    
    private void CancelContactsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelContactsButtonActionPerformed
        AddContactsButton.setVisible(false);
        DeleteContactsButton.setVisible(false);
        ImportContactsButton.setVisible(false);
        CancelContactsButton.setVisible(false);
        ContactsEditClicked = -1;
        EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonhover.png")));
        ContactsTable.setFocusable(false);
        ContactsTable.setBackground(Color.white);
        if (ContactsTable.getCellEditor() != null) {
            ContactsTable.getCellEditor().stopCellEditing();
        }
        
        GUIUpdater.updateContactsTable();
    }//GEN-LAST:event_CancelContactsButtonActionPerformed
    
    private void ContactsListDropDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContactsListDropDownActionPerformed
        ContactsListPromptSave(false);
    }//GEN-LAST:event_ContactsListDropDownActionPerformed
    
    private void ContactsListPromptSave(boolean force) {
        if (SelectedPersonnelListType == null) {
            SelectedPersonnelListType = "Participants";
        }
        if (ContactsEditClicked != -1
                && (currEventTab != 2 || MainTab.getSelectedIndex() != 1 || !SelectedPersonnelListType.equals(ContactsListDropDown.getSelectedItem())
                || EventList.getSelectedIndex() != EventListPreviousSelected || force)) {
            Darken.setVisible(true);
            int n = JOptionPane.showConfirmDialog(this.getParent(),
                    "Do you want to save " + SelectedPersonnelListType + " list?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (n == -1) {
                ContactsListDropDown.setSelectedItem(SelectedPersonnelListType);
                return;
            }
            if (n == 0) {
                try {
                    GUIUpdater.submitContactsTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Unexpected error!", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
            }
            AddContactsButton.setVisible(false);
            DeleteContactsButton.setVisible(false);
            ImportContactsButton.setVisible(false);
            CancelContactsButton.setVisible(false);
            ContactsEditClicked = -1;
            EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbuttonhover.png")));
            ContactsTable.setFocusable(false);
            ContactsTable.setBackground(Color.white);
            if (ContactsTable.getCellEditor() != null) {
                ContactsTable.getCellEditor().stopCellEditing();
            }
            Darken.setVisible(false);
        }
        
        SelectedPersonnelListType = (String) ContactsListDropDown.getSelectedItem();
        GUIUpdater.updateContactsTable();
    }
    
    private void ExpenseAmountTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ExpenseAmountTextKeyTyped
        if (evt.getKeyChar() == 10) {
            BigDecimal amount = null;
            try {
                amount = new BigDecimal(ExpenseAmountText.getText());
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(this, "Amount seems wrong..", "Oops!", JOptionPane.PLAIN_MESSAGE);
                ExpenseAmountText.setSelectionStart(0);
                ExpenseAmountText.setSelectionEnd(ExpenseAmountText.getText().length());
                return;
            }
            
            BudgetCategory b;
            if (CategoryDropDown.getSelectedIndex() > 0) {
                b = getSelectedEvent().getBudgetCategoryList().get(CategoryDropDown.getSelectedIndex() - 1);
            } else {
                b = getSelectedEvent().getUncategorizedBudget();
            }
            try {
                LogicController.addExpense(b, ExpenseDescriptionText.getText(), amount.multiply(new BigDecimal("100")).toBigInteger().longValue());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Oops!", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            GUIUpdater.updateTotalExpense();
            GUIUpdater.updateRemainingBudget();
            GUIUpdater.updateExpenseTable();
            ExpenseDescriptionText.setText("");
            ExpenseAmountText.setText("");
        }
    }//GEN-LAST:event_ExpenseAmountTextKeyTyped
    
    private void ExpenseDescriptionTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ExpenseDescriptionTextKeyTyped
        if (evt.getKeyChar() == 10) {
            BigDecimal amount = null;
            try {
                amount = new BigDecimal(ExpenseAmountText.getText());
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(this, "Amount seems wrong..", "Oops!", JOptionPane.PLAIN_MESSAGE);
                ExpenseAmountText.setSelectionStart(0);
                ExpenseAmountText.setSelectionEnd(ExpenseAmountText.getText().length());
                return;
            }
            
            BudgetCategory b;
            if (CategoryDropDown.getSelectedIndex() > 0) {
                b = getSelectedEvent().getBudgetCategoryList().get(CategoryDropDown.getSelectedIndex() - 1);
            } else {
                b = getSelectedEvent().getUncategorizedBudget();
            }
            try {
                LogicController.addExpense(b, ExpenseDescriptionText.getText(), amount.multiply(new BigDecimal("100")).toBigInteger().longValue());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Oops!", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            GUIUpdater.updateTotalExpense();
            GUIUpdater.updateExpenseTable();
            ExpenseDescriptionText.setText("");
            ExpenseAmountText.setText("");
        }
    }//GEN-LAST:event_ExpenseDescriptionTextKeyTyped
    
    private void CancelContactsButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelContactsButtonMouseExited
        CancelContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelbutton.png")));
    }//GEN-LAST:event_CancelContactsButtonMouseExited
    
    private void CancelContactsButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelContactsButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = CancelContactsButton.getWidth() / 2 - x;
        double dy = CancelContactsButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= CancelContactsButton.getWidth() / 2) {
            CancelContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelbuttonhover.png")));
        } else {
            CancelContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelbutton.png")));
        }
    }//GEN-LAST:event_CancelContactsButtonMouseMoved
    
    private void CancelContactsButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelContactsButtonMousePressed
        CancelContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelbuttonclick.png")));
    }//GEN-LAST:event_CancelContactsButtonMousePressed
    
    private void ArchiveEventButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ArchiveEventButtonMouseExited
        ArchiveEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/archivebutton.png")));
    }//GEN-LAST:event_ArchiveEventButtonMouseExited
    
    private void ArchiveEventButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ArchiveEventButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = ArchiveEventButton.getWidth() / 2 - x;
        double dy = ArchiveEventButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= ArchiveEventButton.getWidth() / 2) {
            ArchiveEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/archivebuttonhover.png")));
        } else {
            ArchiveEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/archivebutton.png")));
        }
    }//GEN-LAST:event_ArchiveEventButtonMouseMoved
    
    private void ArchiveEventButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ArchiveEventButtonMousePressed
        ArchiveEventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/archivebuttonclick.png")));
    }//GEN-LAST:event_ArchiveEventButtonMousePressed
    
    private void AnnouncementEventContactListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnnouncementEventContactListTableMouseClicked
        if (AnnouncementEventContactListTable.columnAtPoint(evt.getPoint()) == 0) {
            return;
        }
        if (AnnouncementEventContactListTable.getModel().getValueAt(AnnouncementEventContactListTable.rowAtPoint(evt.getPoint()), AnnouncementEventContactListTable.columnAtPoint(evt.getPoint())).equals(false)) {
            AnnouncementEventContactListTable.getModel().setValueAt(true, AnnouncementEventContactListTable.rowAtPoint(evt.getPoint()), AnnouncementEventContactListTable.columnAtPoint(evt.getPoint()));
        } else {
            AnnouncementEventContactListTable.getModel().setValueAt(false, AnnouncementEventContactListTable.rowAtPoint(evt.getPoint()), AnnouncementEventContactListTable.columnAtPoint(evt.getPoint()));
        }
    }//GEN-LAST:event_AnnouncementEventContactListTableMouseClicked
    
    private void AnnouncementEventContactListTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnnouncementEventContactListTableMouseMoved
        int at = AnnouncementEventContactListTable.rowAtPoint(evt.getPoint());
        if (at != -1) {
            AnnouncementEventContactListTable.setRowSelectionInterval(at, at);
        } else {
            AnnouncementEventContactListTable.clearSelection();
        }
    }//GEN-LAST:event_AnnouncementEventContactListTableMouseMoved
    
    private void AnnouncementEventContactListTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnnouncementEventContactListTableMouseExited
        AnnouncementEventContactListTable.clearSelection();
    }//GEN-LAST:event_AnnouncementEventContactListTableMouseExited
    
    private void AnnouncementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnouncementButtonActionPerformed
        ArrayList<PersonnelList> plist = new ArrayList<>();
        if (AnnouncementParticipantsCheckBox.isSelected()) {
            plist.add(getSelectedEvent().getParticipantList());
        }
        if (AnnouncementHelpersCheckBox.isSelected()) {
            plist.add(getSelectedEvent().getHelperList());
        }
        if (AnnouncementVendorsCheckBox.isSelected()) {
            plist.add(getSelectedEvent().getVendorList());
        }
        if (AnnouncementSponsorsCheckBox.isSelected()) {
            plist.add(getSelectedEvent().getSponsorList());
        }
        
        ArrayList<Event> events = LogicController.getEventList();
        events.remove(getSelectedEvent());
        for (int i = 0; i < events.size(); i++) {
            if ((boolean) AnnouncementEventContactListTable.getValueAt(i, 1)) {
                plist.add(events.get(i).getParticipantList());
            }
            if ((boolean) AnnouncementEventContactListTable.getValueAt(i, 2)) {
                plist.add(events.get(i).getHelperList());
            }
            if ((boolean) AnnouncementEventContactListTable.getValueAt(i, 3)) {
                plist.add(events.get(i).getVendorList());
            }
            if ((boolean) AnnouncementEventContactListTable.getValueAt(i, 4)) {
                plist.add(events.get(i).getSponsorList());
            }
        }
        try {
            LogicController.sendAnnouncement(AnnouncementUsernameText.getText(), new String(AnnouncementPasswordText.getPassword()), AnnouncementSubjectText.getText(), AnnouncementBodyText.getText(), plist);
            JOptionPane.showMessageDialog(this, "Announcement sent!", "Done!", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Oops!", JOptionPane.PLAIN_MESSAGE);
            return;
        }
    }//GEN-LAST:event_AnnouncementButtonActionPerformed
    
    private void LogoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoMousePressed
        mouseDownCompCoords = evt.getPoint();
    }//GEN-LAST:event_LogoMousePressed
    
    private void LogoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoMouseReleased
        mouseDownCompCoords = null;
    }//GEN-LAST:event_LogoMouseReleased
    
    private void LogoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoMouseDragged
        Point currCoords = evt.getLocationOnScreen();
        this.setLocation(currCoords.x - mouseDownCompCoords.x - 40, currCoords.y - mouseDownCompCoords.y - 20);
    }//GEN-LAST:event_LogoMouseDragged
    
    private void MinimiseButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinimiseButtonMouseExited
        MinimiseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimisebutton.png")));
    }//GEN-LAST:event_MinimiseButtonMouseExited
    
    private void MinimiseButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinimiseButtonMousePressed
        MinimiseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimisebuttonclick.png")));
    }//GEN-LAST:event_MinimiseButtonMousePressed
    
    private void MinimiseButtonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinimiseButtonMouseMoved
        int x = evt.getX();
        int y = evt.getY();
        double dx = MinimiseButton.getWidth() / 2 - x;
        double dy = MinimiseButton.getHeight() / 2 - y;
        double sr = Math.sqrt(dx * dx + dy * dy);
        if (sr <= MinimiseButton.getWidth() / 2) {
            MinimiseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimisebuttonhover.png")));
        } else {
            MinimiseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimisebutton.png")));
        }
    }//GEN-LAST:event_MinimiseButtonMouseMoved
    
    private void MinimiseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimiseButtonActionPerformed
        showPEPO(false);
        //isPEPOVisible = false;
        //((JFrame) this).setVisible(false);
    }//GEN-LAST:event_MinimiseButtonActionPerformed

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
            java.util.logging.Logger.getLogger(PEPOGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PEPOGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PEPOGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PEPOGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                try {
                    new PEPOGUI().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(PEPOGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void updateMemo() {
        try {
            GUIUpdater.updateEventFilterTable();
            GUIUpdater.updateTaskByPriorityTable();
            GUIUpdater.updateScheduleByDateTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage() + "..", "Unexpected error!", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public static void updateEvent() {
        GUIUpdater.updateEventList();
        GUIUpdater.updateEventDescriptionTextArea();
        if (EventList.getModel().getSize() > 0) {
            EventDetailsTab.setVisible(true);
            noEvent.setVisible(false);
            if (EventList.getSelectedIndex() < 0) {
                EventList.setSelectedIndex(0);
            }
            updateEventDay();
            updateExportEventDay();
        } else {
            EventDetailsTab.setVisible(false);
            noEvent.setVisible(true);
        }
    }
    
    public static void updateVenue() {
        if (CapacityText.getText().length() == 0) {
            CapacityText.setText("0");
        }
        GUIUpdater.updateVenueFilter();
        GUIUpdater.updateVenueTable();
        GUIUpdater.updateVenueBookingTable();
    }
    
    public static void updateEventDay() {
        if (EventList.getModel().getSize() > 0) {
            if (EventList.getSelectedIndex() < 0) {
                EventList.setSelectedIndex(0);
            }
            GUIUpdater.updateEventDayTable();
        }
    }
    
    public static void updateExportEventDay() {
        if (EventList.getModel().getSize() > 0) {
            if (EventList.getSelectedIndex() < 0) {
                EventList.setSelectedIndex(0);
            }
            GUIUpdater.updateExportEventDayTable();
        }
    }
    
    public static void updateBudget() {
        GUIUpdater.updateTotalBudget();
        GUIUpdater.updateRemainingBudget();
        GUIUpdater.updateTotalExpense();
        GUIUpdater.updateAddExpense();
        GUIUpdater.updateExpenseTable();
    }
    
    public static void updateAnnouncement() {
        GUIUpdater.updateAnnouncementSubject();
        GUIUpdater.updateAnnouncementContactsListTable();
    }
    
    public void updateContacts() {
        GUIUpdater.updateContactsTable();
        EditContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editbutton.png")));
        ContactsTable.setFocusable(false);
        ContactsTable.setBackground(Color.white);
    }

    //
    // Memo
    public static javax.swing.JTable getScheduleByDateTable() {
        return ScheduleByDateTable;
    }
    
    public static javax.swing.JTable getTaskByPriorityTable() {
        return TaskByPriorityTable;
    }
    
    public static javax.swing.JTable getEventFilterTable() {
        return EventFilterTable;
    }
    
    public static javax.swing.JTextArea getEventDescriptionTextArea() {
        return EventDescriptionTextArea;
    }
    
    public static Date memoTime() {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(MemoCalendar.getDate());
        c.set(GregorianCalendar.HOUR_OF_DAY, 0);
        c.set(GregorianCalendar.MINUTE, 0);
        c.set(GregorianCalendar.SECOND, 0);
        c.set(GregorianCalendar.MILLISECOND, 0);
        return c.getTime();
    }
    
    public static Event getSelectedEvent() {
        if (EventList.getSelectedIndex() < 0) {
            return null;
        } else {
            return LogicController.getEventList().get(EventList.getSelectedIndex());
        }
    }

    //  Event
    public static javax.swing.JTable getEventDayTable() {
        return EventDayTable;
    }
    
    public static javax.swing.JList getEventList() {
        return EventList;
    }

    //  Budget
    public static javax.swing.JTable getExpenseTable() {
        return ExpenseTable;
    }
    
    public static javax.swing.JTextField getTotalBudgetText() {
        return TotalBudgetText;
    }
    
    public static javax.swing.JTextField getRemainingBudgetText() {
        return RemainingBudgetText;
    }
    
    public static javax.swing.JTextField getTotalExpenseText() {
        return TotalExpenseText;
    }
    
    public static javax.swing.JButton getDeleteTaskButton() {
        return DeleteTaskButton;
    }
    
    public static javax.swing.JButton getEditTaskButton() {
        return EditTaskButton;
    }
    
    public static javax.swing.JComboBox getCategoryDropDown() {
        return CategoryDropDown;
    }
    
    public static javax.swing.JButton getEditEventButton() {
        return EditEventButton;
    }
    
    public static javax.swing.JButton getArchiveEventButton() {
        return ArchiveEventButton;
    }
    
    public static javax.swing.JButton getDeleteEventButton() {
        return DeleteEventButton;
    }
    
    public static javax.swing.JButton getEditEventDayButton() {
        return EditEventDayButton;
    }
    
    public static javax.swing.JButton getDeleteEventDayButton() {
        return DeleteEventDayButton;
    }
    
    public static javax.swing.JTable getMyBookingsTable() {
        return VenueBookingTable;
    }
    
    public static javax.swing.JButton getDeleteVenueBookingButton() {
        return DeleteVenueBookingButton;
    }
    
    public static javax.swing.JTable getVenueTable() {
        return VenueTable;
    }
    
    public static javax.swing.JTextField getVenueNameText() {
        return NameText;
    }
    
    public static javax.swing.JComboBox getTypeDropDown() {
        return TypeDropDown;
    }
    
    public static javax.swing.JComboBox getFacultyDropDown() {
        return FacultyDropDown;
    }
    
    public static javax.swing.JTextField getCapacityText() {
        return CapacityText;
    }
    
    public static javax.swing.JButton getEditBudgetExpenseButton() {
        return EditBudgetExpenseButton;
    }
    
    public static javax.swing.JButton getDeleteBudgetExpenseButton() {
        return DeleteBudgetExpenseButton;
    }
    
    public static javax.swing.JButton getAddVenueBookingButton() {
        return AddVenueBookingButton;
    }
    
    public static javax.swing.JButton getTagVenueBookingButton() {
        return TagVenueBookingButton;
    }
    
    public static javax.swing.JTable getContactsTable() {
        return ContactsTable;
    }
    
    public static int getContactsEditClicked() {
        return ContactsEditClicked;
    }
    
    public static javax.swing.JButton getAddContactsButton() {
        return AddContactsButton;
    }
    
    public static javax.swing.JButton getDeleteContactsButton() {
        return DeleteContactsButton;
    }
    
    public static javax.swing.JButton getImportContactsButton() {
        return ImportContactsButton;
    }
    
    public static javax.swing.JButton getCancelContactsButton() {
        return CancelContactsButton;
    }
    
    public static javax.swing.JTable getExportEventDayTable() {
        return ExportEventDayTable;
    }
    
    public static int getEventDayTableClicked() {
        return EventDayTableClicked;
    }
    
    public static javax.swing.JScrollPane getEventDayNotesTextBackground() {
        return EventDayNotesTextBackground;
    }
    
    public static javax.swing.JScrollPane getEventDayItineraryTextBackground() {
        return EventDayItineraryTextBackground;
    }
    
    public static javax.swing.JButton getSubmitTotalBudgetButton() {
        return EditBudgetButton;
    }
    
    public static javax.swing.JButton getCompleteTaskButton() {
        return CompleteTaskButton;
    }
    
    public static javax.swing.JButton getEditContactsHeaderButton() {
        return EditContactsHeaderButton;
    }
    
    public static String getSelectedPersonnelListType() {
        return SelectedPersonnelListType;
    }
    
    public static javax.swing.JTextField getCountText() {
        return CountText;
    }
    
    public static ImageIcon getVenueIcon() {
        return venueIcon;
    }
    
    public static ImageIcon getEventdayIcon() {
        return eventdayIcon;
    }
    
    public static ImageIcon getTaskIcon() {
        return taskIcon;
    }
    
    public static ImageIcon getTaskCompletedIcon() {
        return taskCompletedIcon;
    }
    
    public static JTextArea getEventDayItineraryText() {
        return EventDayItineraryText;
    }
    
    public static JTextArea getEventDayNotesText() {
        return EventDayNotesText;
    }
    
    public static JLabel getContactsInstruction() {
        return ContactsInstruction;
    }
    
    public static JTable getAnnouncementEventContactListTable() {
        return AnnouncementEventContactListTable;
    }
    
    public static JTextField getAnnouncementSubjectText() {
        return AnnouncementSubjectText;
    }
    
    public static int getEventListPreviousSelected() {
        return EventListPreviousSelected;
    }
    public static String SelectedPersonnelListType = "Participants";
    public static int ContactsEditClicked = -1;
    public static ArrayList<Object> VenueBookingTablePointer = new ArrayList<>();
    public static ArrayList<Object> VenueTablePointer = new ArrayList<>();
    public static int VenueTableClicked = -1;
    public static int EventDayTableClicked = -1;
    public static int ExpenseTableClicked = -1;
    public static int TaskTableClicked = -1;
    public static int MyBookingsTableClicked = -1;
    public static int EventListPreviousSelected = -1;
    public static ArrayList<Object> PriorityTablePointer = new ArrayList<>();
    public static ArrayList<Object> ExpenseTablePointer = new ArrayList<>();
    // 0-day, 1-eventday, 2-eventdayvenue, 3-task, 4-taskvenue, 5-venuebooking, 6-blankspace
    public static ArrayList<Integer> ScheduleTableType = new ArrayList<>();
    public static ArrayList<Pair> ProgrammeStartDuration = new ArrayList<>();
    public static boolean isPEPOVisible = true;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton AddContactsButton;
    private static javax.swing.JButton AddEventButton;
    private static javax.swing.JButton AddEventDayButton;
    private javax.swing.JPanel AddExpenseBackground;
    private static javax.swing.JButton AddExpenseButton;
    private static javax.swing.JButton AddTaskButton;
    private static javax.swing.JButton AddVenueBookingButton;
    private static javax.swing.JLabel AmountLabel;
    private javax.swing.JTextArea AnnouncementBodyText;
    private javax.swing.JButton AnnouncementButton;
    private javax.swing.JScrollPane AnnouncementContactListTableBackground;
    private static javax.swing.JTable AnnouncementEventContactListTable;
    private javax.swing.JCheckBox AnnouncementHelpersCheckBox;
    private javax.swing.JCheckBox AnnouncementParticipantsCheckBox;
    private javax.swing.JPasswordField AnnouncementPasswordText;
    private javax.swing.JCheckBox AnnouncementSponsorsCheckBox;
    private static javax.swing.JTextField AnnouncementSubjectText;
    private static javax.swing.JTextField AnnouncementUsernameText;
    private javax.swing.JCheckBox AnnouncementVendorsCheckBox;
    private static javax.swing.JButton ArchiveEventButton;
    private static javax.swing.JPanel Background;
    private static javax.swing.JLabel BodyLabel;
    private javax.swing.JScrollPane BodyTextBackground;
    private static javax.swing.JPanel BudgetBackground;
    private static javax.swing.JLayeredPane BudgetTab;
    private static javax.swing.JButton CancelContactsButton;
    private static javax.swing.JTextField CapacityText;
    private static javax.swing.JComboBox CategoryDropDown;
    private static javax.swing.JLabel CategoryLabel;
    private javax.swing.JButton CloseButton;
    private static javax.swing.JButton CompleteTaskButton;
    private static javax.swing.JLabel ContactsInstruction;
    private static javax.swing.JLabel ContactsLabel;
    private javax.swing.JComboBox ContactsListDropDown;
    private static javax.swing.JTable ContactsTable;
    private static javax.swing.JLabel CountLabel;
    private static javax.swing.JTextField CountText;
    private javax.swing.JLabel Darken;
    private static javax.swing.JButton DeleteBudgetExpenseButton;
    private static javax.swing.JButton DeleteContactsButton;
    private static javax.swing.JButton DeleteEventButton;
    private static javax.swing.JButton DeleteEventDayButton;
    private static javax.swing.JButton DeleteTaskButton;
    private static javax.swing.JButton DeleteVenueBookingButton;
    private static javax.swing.JLabel DescriptionLabel;
    private static javax.swing.JButton EditBudgetButton;
    private static javax.swing.JButton EditBudgetExpenseButton;
    private static javax.swing.JButton EditContactsButton;
    private static javax.swing.JButton EditContactsHeaderButton;
    private static javax.swing.JButton EditEventButton;
    private static javax.swing.JButton EditEventDayButton;
    private static javax.swing.JButton EditTaskButton;
    private static javax.swing.JLayeredPane EmailTab;
    private static javax.swing.JPanel EventDayBackground;
    private javax.swing.JScrollPane EventDayExportBackground;
    private static javax.swing.JTextArea EventDayItineraryText;
    private static javax.swing.JScrollPane EventDayItineraryTextBackground;
    private static javax.swing.JTextArea EventDayNotesText;
    private static javax.swing.JScrollPane EventDayNotesTextBackground;
    private static javax.swing.JLayeredPane EventDayTab;
    private static javax.swing.JTable EventDayTable;
    private static javax.swing.JScrollPane EventDayTableBackground;
    private static javax.swing.JTextArea EventDescriptionTextArea;
    private javax.swing.JScrollPane EventDescriptionTextAreaBackground;
    private static javax.swing.JTabbedPane EventDetailsTab;
    private static javax.swing.JPanel EventFilterBackground;
    private static javax.swing.JTable EventFilterTable;
    private static javax.swing.JScrollPane EventFilterTableBackground;
    private static javax.swing.JList EventList;
    private static javax.swing.JScrollPane EventListBackground;
    private static javax.swing.JLabel EventListLabel;
    private static javax.swing.JLabel EventListLabel2;
    private static javax.swing.JLabel EventListLabel3;
    private static javax.swing.JLabel EventListLabel4;
    private static javax.swing.JLabel EventListLabel5;
    private static javax.swing.JLayeredPane EventTab;
    private static javax.swing.JPanel EventTabBackground;
    private static javax.swing.JTextField ExpenseAmountText;
    private static javax.swing.JTextField ExpenseDescriptionText;
    private static javax.swing.JTable ExpenseTable;
    private static javax.swing.JScrollPane ExpenseTableBackground;
    private javax.swing.JButton ExportButton;
    private static javax.swing.JTable ExportEventDayTable;
    private static javax.swing.JLayeredPane ExportTab;
    private static javax.swing.JComboBox FacultyDropDown;
    private static javax.swing.JLabel FacultyLabel;
    private javax.swing.JScrollPane GeneralExportBackground;
    private static javax.swing.JTable GeneralExportTable;
    private static javax.swing.JButton ImportContactsButton;
    private javax.swing.JButton LayoutButton;
    private javax.swing.JLabel LeftSide;
    private javax.swing.JLabel Logo;
    private static javax.swing.JTabbedPane MainTab;
    private static com.toedter.calendar.JDateChooser MemoCalendar;
    private javax.swing.JLabel MemoSeparator;
    private static javax.swing.JLayeredPane MemoTab;
    private static javax.swing.JPanel MemoTabBackground;
    private javax.swing.JButton MinimiseButton;
    private static javax.swing.JLabel MinimumCapacityLabel;
    private static javax.swing.JLabel MyBookingsLabel1;
    private static javax.swing.JScrollPane MyBookingsTableBackground;
    private static javax.swing.JLabel NameLabel;
    private static javax.swing.JTextField NameText;
    private static javax.swing.JLabel OtherContactsLabel;
    private javax.swing.JPanel ParticipantArea;
    private static javax.swing.JLayeredPane ParticipantTab;
    private javax.swing.JScrollPane ParticipantTableBackground;
    private static javax.swing.JLabel PasswordLabel;
    private static javax.swing.JLabel RemainingBudgetLabel;
    private static javax.swing.JTextField RemainingBudgetText;
    private static javax.swing.JPanel ScheduleByDateArea;
    private static javax.swing.JScrollPane ScheduleByDateBackground;
    private static javax.swing.JTable ScheduleByDateTable;
    private javax.swing.JLabel Separator;
    private javax.swing.JLabel Separator2;
    private static javax.swing.JLabel SubjectLabel;
    private static javax.swing.JButton TagVenueBookingButton;
    private static javax.swing.JPanel TaskByPriorityArea;
    private static javax.swing.JScrollPane TaskByPriorityAreaBackground;
    private static javax.swing.JTable TaskByPriorityTable;
    private static javax.swing.JLabel TaskToDoLabel;
    private static javax.swing.JLabel TaskToDoLabel1;
    private static javax.swing.JTextField TotalBudgetText;
    private static javax.swing.JLabel TotalExpenseLabel;
    private static javax.swing.JTextField TotalExpenseText;
    private static javax.swing.JComboBox TypeDropDown;
    private static javax.swing.JLabel TypeLabel;
    private static javax.swing.JLabel UsernameLabel;
    private static javax.swing.JTable VenueBookingTable;
    private static javax.swing.JLayeredPane VenueTab;
    private static javax.swing.JPanel VenueTabBackground;
    private static javax.swing.JTable VenueTable;
    private static javax.swing.JScrollPane VenueTableBackground;
    private javax.swing.JLabel exportSeparator;
    private static javax.swing.JLabel noEvent;
    // End of variables declaration//GEN-END:variables
}
