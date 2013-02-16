
package GUI;

import Database.Pair;
import Logic.Budget.BudgetCategory;
import Logic.Event.Event;
import Logic.LogicController;
import Logic.Memo.Day;
import Logic.Memo.EventDay;
import Logic.Memo.MemoItem;
import Logic.Memo.Memoable;
import Logic.Memo.Task;
import Logic.PersonnelList.Personnel;
import Logic.PersonnelList.PersonnelList;
import Logic.Itinerary.Itinerary;
import Logic.Itinerary.ItineraryItem;
import Logic.Venue.Venue;
import Logic.Venue.VenueBooking;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class GUIUpdater {

    /**
     * 
     * @return ArrayList of Event Selected in EventFilterTable.
     */
    public static ArrayList<Event> getEventFilterTableSelectedEvent() {
        JTable filter = PEPOGUI.getEventFilterTable();
        ArrayList<Integer> selected = new ArrayList<>();
        for (int i = 0; i < filter.getRowCount(); i++) {
            if (filter.getModel().getValueAt(i, 0).equals(true)) {
                selected.add(i);
            }
        }
        ArrayList<Event> selectedEvents = new ArrayList<>();
        for (int i = 0; i < selected.size(); i++) {
            selectedEvents.add(LogicController.getEventList().get(selected.get(i)));
        }
        return selectedEvents;
    }

    /**
     * Updates the TaskByPriority table.
     * @throws Exception
     */
    public static void updateTaskByPriorityTable() throws Exception {
        JTable t = PEPOGUI.getTaskByPriorityTable();
        Date d = PEPOGUI.memoTime();
        ArrayList<Task> result = LogicController.getFilteredTaskByPriority(d, getEventFilterTableSelectedEvent());
        SimpleDateFormat date = new SimpleDateFormat("d MMM");
        ((DefaultTableModel) t.getModel()).setRowCount(result.size());
        PEPOGUI.PriorityTablePointer.clear();
        for (int i = 0; i < result.size(); i++) {
            t.getModel().setValueAt(PEPOGUI.getTaskIcon(), i, 0);
            PEPOGUI.PriorityTablePointer.add(result.get(i));

            String description = "";

            if (result.get(i).getDescription().length() > 0) {
                description = " " + result.get(i).getDescription();
            }
            if (result.get(i).getEvent() != null) {
                description = description + " (" + result.get(i).getEvent().getTitle() + ")";
            }
            t.getModel().setValueAt(description, i, 1);

            t.getModel().setValueAt(" " + date.format(result.get(i).getDate()), i, 2);
            String[] pchar = {"H", "M", "L"};
            t.getModel().setValueAt(" " + pchar[result.get(i).getPriority()], i, 3);
        }
        t.clearSelection();
        PEPOGUI.getDeleteTaskButton().setVisible(false);
        PEPOGUI.getCompleteTaskButton().setVisible(false);
        PEPOGUI.getEditTaskButton().setVisible(false);
        PEPOGUI.TaskTableClicked = -1;
    }

    /**
     * Updates the ScheduleByDate table.
     * @throws Exception
     */
    public static void updateScheduleByDateTable() throws Exception {
        PEPOGUI.ScheduleTableType.clear();
        JTable t = PEPOGUI.getScheduleByDateTable();
        Date d = PEPOGUI.memoTime();
        ArrayList<Day> result = LogicController.getFilteredDayScheduleByDate(d, getEventFilterTableSelectedEvent(), true);
        SimpleDateFormat date = new SimpleDateFormat("d MMM (EEE)");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        ArrayList<ArrayList<Object>> arr = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            Day cur = result.get(i);
            GregorianCalendar curEnd = new GregorianCalendar();
            curEnd.setTime(cur.getDate());
            curEnd.add(GregorianCalendar.DAY_OF_MONTH, 1);
            curEnd.add(GregorianCalendar.MINUTE, -1);
            ArrayList<Object> curRow = new ArrayList<>();

            curRow.add(date.format(cur.getDate())); // Date of Day
            curRow.add("");
            curRow.add("");
            arr.add(curRow);
            PEPOGUI.ScheduleTableType.add(0);

            for (int j = 0; j < cur.getMemoItemList().size(); j++) {
                curRow = new ArrayList<>();
                Memoable curItem = cur.getMemoItemList().get(j);

                String curTime = new String();
                if (curItem.getDate().before(cur.getDate())) {
                    curTime = "00:00";
                } else {
                    curTime = time.format(curItem.getDate());
                }
                if (curItem instanceof EventDay) {
                    if (((EventDay) curItem).getEndDate().after(curEnd.getTime())) {
                        curTime = curTime + "-" + "23:59";
                    } else {
                        curTime = curTime + "-" + time.format(((EventDay) curItem).getEndDate());
                    }
                }
                if (curItem instanceof VenueBooking) {
                    if (((VenueBooking) curItem).getEndDate().after(curEnd.getTime())) {
                        curTime = curTime + "-" + "23:59";
                    } else {
                        curTime = curTime + "-" + time.format(((VenueBooking) curItem).getEndDate());
                    }
                }
                curRow.add(curTime.toLowerCase());

                String desc;
                if (curItem instanceof VenueBooking) {
                    desc = ((VenueBooking) curItem).getVenue().getName();
                } else {
                    desc = ((MemoItem) curItem).getDescription();
                    if (((MemoItem) curItem).getEvent() != null) {
                        desc = ((MemoItem) curItem).getEvent().getTitle() + " - " + desc;
                    }
                }

                curRow.add(desc);
                arr.add(curRow);
                if (curItem instanceof EventDay) {
                    PEPOGUI.ScheduleTableType.add(1);
                }
                if (curItem instanceof Task) {
                    if (((Task) curItem).isCompleted()) {
                        PEPOGUI.ScheduleTableType.add(3);
                    } else {
                        PEPOGUI.ScheduleTableType.add(4);
                    }
                }
                if (curItem instanceof VenueBooking) {
                    PEPOGUI.ScheduleTableType.add(6);
                }

                // one line of tagged venue

                String venue = new String();
                if (curItem instanceof MemoItem) {   // is a task or eventday
                    if (((MemoItem) curItem).getTaggedVenueBookingList().size() > 0) {   // has venues
                        venue = ((MemoItem) curItem).getTaggedVenueBookingList().get(0).getVenue().getName();
                        for (int k = 1; k < ((MemoItem) curItem).getTaggedVenueBookingList().size(); k++) {
                            venue = venue + ", " + ((MemoItem) curItem).getTaggedVenueBookingList().get(k).getVenue().getName();
                        }
                        venue = "  @ " + venue + "";

                        curRow = new ArrayList<>();
                        curRow.add("");
                        curRow.add(venue);
                        arr.add(curRow);
                        if (curItem instanceof EventDay) {
                            PEPOGUI.ScheduleTableType.add(2);
                        }
                        if (curItem instanceof Task) {
                            PEPOGUI.ScheduleTableType.add(5);
                        }
                    }
                }
            }

            // spacing
            curRow = new ArrayList<>();
            curRow.add("");
            curRow.add("");
            arr.add(curRow);
            PEPOGUI.ScheduleTableType.add(7);
        }


        ((DefaultTableModel) t.getModel()).setRowCount(arr.size());
        for (int i = 0; i < arr.size(); i++) {
            t.getModel().setValueAt(arr.get(i).get(0), i, 0);
            t.getModel().setValueAt(arr.get(i).get(1), i, 2);
        }
    }

    /**
     * Updates the EventList List.
     */
    public static void updateEventList() {
        JList l = PEPOGUI.getEventList();
        DefaultListModel myList = new DefaultListModel();
        ArrayList<Event> events = LogicController.getEventList();
        for (int i = 0; i < events.size(); i++) {
            myList.addElement(" " + events.get(i).getTitle());
        }
        l.setModel(myList);
        if (events.isEmpty()) {
            PEPOGUI.getDeleteEventButton().setVisible(false);
            PEPOGUI.getEditEventButton().setVisible(false);
            PEPOGUI.getEventDescriptionTextArea().setVisible(false);
        } else {
            PEPOGUI.getDeleteEventButton().setVisible(true);
            PEPOGUI.getEditEventButton().setVisible(true);
            PEPOGUI.getEventDescriptionTextArea().setVisible(true);
        }
    }

    /**
     * Updates the EventDescriptionTextArea to the description of the currently selected Event.
     */
    public static void updateEventDescriptionTextArea() {
        if (PEPOGUI.getSelectedEvent() == null) {
            PEPOGUI.getEventDescriptionTextArea().setText("");
        } else {
            PEPOGUI.getEventDescriptionTextArea().setText("" + PEPOGUI.getSelectedEvent().getDescription());
        }
    }

    /**
     * Updates the EventDayTable to the EventDays of currently selected Event.
     */
    public static void updateEventDayTable() {
        if (PEPOGUI.getEventList().getSelectedIndex() < 0) {
            return;
        }
        JTable t = PEPOGUI.getEventDayTable();
        Event e = LogicController.getEventList().get(PEPOGUI.getEventList().getSelectedIndex());
        ArrayList<EventDay> result = e.getEventDayList();
        SimpleDateFormat date = new SimpleDateFormat("d MMM");
        SimpleDateFormat time = new SimpleDateFormat(", HH:mm");
        ((DefaultTableModel) t.getModel()).setRowCount(result.size());
        for (int i = 0; i < result.size(); i++) {
            t.getModel().setValueAt(PEPOGUI.getEventdayIcon(), i, 0);
            EventDay curItem = result.get(i);
            t.getModel().setValueAt(" " + result.get(i).getDescription(), i, 1);
            String sdstring = date.format(curItem.getDate());
            String edstring = date.format(curItem.getEndDate());

            t.getModel().setValueAt(" " + sdstring + time.format(curItem.getDate()).toLowerCase(), i, 2);
            t.getModel().setValueAt(" " + edstring + time.format(curItem.getEndDate()).toLowerCase(), i, 3);
            String venue = " ";
            if (curItem.getTaggedVenueBookingList().size() > 0) {
                venue = curItem.getTaggedVenueBookingList().get(0).getVenue().getName();
            }
            for (int k = 1; k < curItem.getTaggedVenueBookingList().size(); k++) {
                venue = venue + ", " + curItem.getTaggedVenueBookingList().get(k).getVenue().getName();
            }
            if (venue.length() == 1) {
                venue = "";
            }
            t.getModel().setValueAt(venue, i, 4);
        }

        PEPOGUI.getDeleteEventDayButton().setVisible(false);
        PEPOGUI.getEditEventDayButton().setVisible(false);
        PEPOGUI.getEventDayNotesTextBackground().setVisible(false);
        PEPOGUI.getEventDayItineraryTextBackground().setVisible(false);
        PEPOGUI.getEventDayTable().clearSelection();
        PEPOGUI.EventDayTableClicked = -1;
    }

    /**
     * Updates the ExportEventDayTable to the EventDays of currently selected Event.
     */
    public static void updateExportEventDayTable() {
        if (PEPOGUI.getEventList().getSelectedIndex() < 0) {
            return;
        }

        ArrayList<EventDay> list = LogicController.getEventList().get(PEPOGUI.getEventList().getSelectedIndex()).getEventDayList();
        ArrayList<String> arr = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            arr.add(list.get(i).getDescription());
        }

        JTable t = PEPOGUI.getExportEventDayTable();
        if (t != null) {
            ((DefaultTableModel) t.getModel()).setRowCount(arr.size());
            for (int i = 0; i < arr.size(); i++) {
                t.getModel().setValueAt(Boolean.TRUE, i, 0);
                t.getModel().setValueAt(arr.get(i), i, 1);
            }
        }
    }

    /**
     * Updates the EventFilterTable.
     */
    public static void resetEventFilterTable() {
        JTable t = PEPOGUI.getEventFilterTable();
        t.setValueAt(null, -1, 0);
    }
    
    public static void updateEventFilterTable() {
        JTable t = PEPOGUI.getEventFilterTable();
        ArrayList<Event> result = LogicController.getEventList();

        for (int i = 0; i < result.size(); i++) {
            if(t.getModel().getRowCount() <= i)
                t.getModel().setValueAt(true, i, 0);
            t.getModel().setValueAt(result.get(i).getTitle(), i, 1);
        }
        int total = t.getModel().getRowCount();
        //  need to remove the extras
        
        PEPOGUI.getEventFilterTable().clearSelection();
    }

    /**
     * Updates the TotalBudgetTextField with the total budget of the currently selected Event.
     */
    public static void updateTotalBudget() {
        if (PEPOGUI.getEventList().getSelectedIndex() >= 0) {
            BigDecimal amt = new BigDecimal(LogicController.getEventList().get(PEPOGUI.getEventList().getSelectedIndex()).getTotalBudget() + "");
            PEPOGUI.getTotalBudgetText().setText("" + currencyFormat(amt.divide(new BigDecimal("100"))));
        }
    }

    /**
     * Updates the RemainingBudgetTextField with the remaining budget of the currently selected Event.
     */
    public static void updateRemainingBudget() {
        if (PEPOGUI.getEventList().getSelectedIndex() >= 0) {
            BigDecimal amt = new BigDecimal(LogicController.getEventList().get(PEPOGUI.getEventList().getSelectedIndex()).getRemainingBudget() + "");
            PEPOGUI.getRemainingBudgetText().setText("" + currencyFormat(amt.divide(new BigDecimal("100"))));
        }
    }

    /**
     * Updates the TotalExpenseTextField with the total expense of the currently selected Event.
     */
    public static void updateTotalExpense() {

        if (PEPOGUI.getEventList().getSelectedIndex() >= 0) {
            long expense = LogicController.getEventList().get(PEPOGUI.getEventList().getSelectedIndex()).getTotalExpense();

            PEPOGUI.getTotalExpenseText().setText(currencyFormat((new BigDecimal(expense)).divide(new BigDecimal("100"))));
        }
    }

    /**
     * Updates the ExpenseTable with the budget information of the currently selected Event.
     */
    public static String currencyFormat(BigDecimal n) {
        return NumberFormat.getCurrencyInstance().format(n);
    }
    
    public static void updateExpenseTable() {
        Event e = PEPOGUI.getSelectedEvent();
        if (e == null) {
            return;
        }
        ArrayList<ArrayList<Object>> arr = new ArrayList<>();
        ArrayList<Object> row = new ArrayList<>();
        PEPOGUI.ExpenseTablePointer.clear();
        
        for (int i = 0; i < e.getBudgetCategoryList().size(); i++) {
            BudgetCategory bc = e.getBudgetCategoryList().get(i);
            row = new ArrayList<>();
            row.add(" " + bc.getName());
            row.add(" " + currencyFormat(new BigDecimal(bc.getAmount() + "").divide(new BigDecimal("100"))));
            arr.add(row);
            PEPOGUI.ExpenseTablePointer.add(bc);
            for (int j = 0; j < bc.getExpenseList().size(); j++) {
                row = new ArrayList<>();
                row.add("        - " + bc.getExpenseList().get(j).getTitle());
                row.add(" " + currencyFormat(new BigDecimal(bc.getExpenseList().get(j).getCost() + "").divide(new BigDecimal("100"))));
                arr.add(row);
                PEPOGUI.ExpenseTablePointer.add(bc.getExpenseList().get(j));
            }
            row = new ArrayList<>();
            if (bc.getRemainingAmount() < 0) {
                row.add("        - Balance");
            } else {
                row.add("        + Balance");
            }

            row.add(" " + currencyFormat(new BigDecimal(bc.getRemainingAmount() + "").divide(new BigDecimal("100"))));
            arr.add(row);
            PEPOGUI.ExpenseTablePointer.add(bc);
            row = new ArrayList<>();
            row.add("");
            row.add("");
            arr.add(row);
            PEPOGUI.ExpenseTablePointer.add(null);
        }
        row = new ArrayList<>();
        BudgetCategory bc = e.getUncategorizedBudget();
        if (bc.getExpenseList().size() > 0) {
            row.add(" Uncategorized");
            row.add("");
            arr.add(row);
            row = new ArrayList<>();
            PEPOGUI.ExpenseTablePointer.add(null);
        }
        for (int j = 0; j < bc.getExpenseList().size(); j++) {
            row = new ArrayList<>();
            row.add("        " + bc.getExpenseList().get(j).getTitle());
            row.add(" " + currencyFormat(new BigDecimal(bc.getExpenseList().get(j).getCost() + "").divide(new BigDecimal("100"))));
            arr.add(row);
            PEPOGUI.ExpenseTablePointer.add(bc.getExpenseList().get(j));
        }

        JTable t = PEPOGUI.getExpenseTable();
        ((DefaultTableModel) t.getModel()).setRowCount(arr.size());
        for (int i = 0; i < arr.size(); i++) {
            t.getModel().setValueAt(arr.get(i).get(0), i, 0);
            t.getModel().setValueAt(arr.get(i).get(1), i, 1);
        }
    }

    /**
     * Updates the AddExpense with the BudgetCategorys of the currently selected Event.
     */
    public static void updateAddExpense() {
        JComboBox cb = PEPOGUI.getCategoryDropDown();
        cb.removeAllItems();
        ArrayList<BudgetCategory> bc2;
        if (PEPOGUI.getSelectedEvent() != null) {
            bc2 = PEPOGUI.getSelectedEvent().getBudgetCategoryList();
        } else {
            return;
        }
        cb.addItem(" ");
        for (int i = 0; i < bc2.size(); i++) {
            cb.addItem(bc2.get(i).getName());
        }

        cb.setSelectedIndex(0);
        PEPOGUI.ExpenseTableClicked = -1;
        PEPOGUI.getExpenseTable().clearSelection();

        PEPOGUI.getDeleteBudgetExpenseButton().setVisible(false);
        PEPOGUI.getEditBudgetExpenseButton().setVisible(false);
    }

    /**
     * Updates the VenueBookingTable.
     */
    public static void updateVenueBookingTable() {
        ArrayList<VenueBooking> vb = LogicController.getFutureVenueBookingList(new Date(System.currentTimeMillis()));
        JTable t = PEPOGUI.getMyBookingsTable();
        t.removeAll();
        ArrayList<ArrayList<Object>> arr = new ArrayList<>();
        ArrayList<Object> row = new ArrayList<>();
        PEPOGUI.VenueBookingTablePointer.clear();
        for (int i = 0; i < vb.size(); i++) {
            SimpleDateFormat date = new SimpleDateFormat("d MMM, HH:mm");
            row.add(" " + vb.get(i).getVenue().getName());
            String start = date.format(vb.get(i).getStartDate());
            String end = date.format(vb.get(i).getEndDate());
            row.add(" " + start);
            row.add(" " + end);
            arr.add(row);
            row = new ArrayList<>();
            PEPOGUI.VenueBookingTablePointer.add(vb.get(i));
        }

        ((DefaultTableModel) t.getModel()).setRowCount(arr.size());
        for (int i = 0; i < arr.size(); i++) {
            t.getModel().setValueAt(PEPOGUI.getVenueIcon(), i, 0);
            t.getModel().setValueAt(arr.get(i).get(0), i, 1);
            t.getModel().setValueAt(arr.get(i).get(1), i, 2);
            t.getModel().setValueAt(arr.get(i).get(2), i, 3);
        }

        PEPOGUI.MyBookingsTableClicked = -1;
        PEPOGUI.getMyBookingsTable().clearSelection();
        PEPOGUI.getDeleteVenueBookingButton().setVisible(false);
        PEPOGUI.getTagVenueBookingButton().setVisible(false);
    }

    /**
     * Updates the VenueFilterComboBox.
     */
    public static void updateVenueFilter() {
        JComboBox type = PEPOGUI.getTypeDropDown();
        type.removeAllItems();
        JComboBox faculty = PEPOGUI.getFacultyDropDown();
        faculty.removeAllItems();
        ArrayList<String> venuetypes = LogicController.getVenueTypeList();
        ArrayList<String> venuefaculty = LogicController.getVenueFacultyList();

        type.addItem("All");
        for (int i = 0; i < venuetypes.size(); i++) {
            type.addItem(venuetypes.get(i));
        }

        faculty.addItem("All");
        for (int i = 0; i < venuefaculty.size(); i++) {
            faculty.addItem(venuefaculty.get(i));
        }
    }

    /**
     * Updates the VenueTable.
     */
    public static void updateVenueTable() {
        PEPOGUI.VenueTablePointer.clear();

        String n = PEPOGUI.getVenueNameText().getText();
        String t = (String) PEPOGUI.getTypeDropDown().getSelectedItem();
        if (t != null && t.equals("All")) {
            t = null;
        }

        String f = (String) PEPOGUI.getFacultyDropDown().getSelectedItem();
        if (f != null && f.equals("All")) {
            f = null;
        }

        Integer cap = new Integer(0);
        try {
            cap = Integer.parseInt(PEPOGUI.getCapacityText().getText());
        } catch (Exception ex) {
            cap = 0;
        }
        ArrayList<Venue> results = LogicController.getFilteredVenueList(n, t, f, cap);
        JTable vt = PEPOGUI.getVenueTable();
        ArrayList<ArrayList<Object>> arr = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ArrayList<Object> row = new ArrayList<>();
            Venue cur = results.get(i);
            row.add(" " + cur.getName());
            row.add(" " + cur.getType());
            row.add(" " + cur.getFaculty());
            row.add(" " + cur.getCapacity());
            PEPOGUI.VenueTablePointer.add(cur);
            arr.add(row);
        }

        ((DefaultTableModel) vt.getModel()).setRowCount(arr.size());
        for (int i = 0; i < arr.size(); i++) {
            vt.getModel().setValueAt(arr.get(i).get(0), i, 0);
            vt.getModel().setValueAt(arr.get(i).get(1), i, 1);
            vt.getModel().setValueAt(arr.get(i).get(2), i, 2);
            vt.getModel().setValueAt(arr.get(i).get(3), i, 3);
        }
        PEPOGUI.getAddVenueBookingButton().setVisible(false);
        PEPOGUI.getVenueTable().clearSelection();
        PEPOGUI.VenueTableClicked = -1;
    }

    /**
     * Update the Contacts Table of the currently selected Event.
     */
    public static void updateContactsTable() {
        Event e = PEPOGUI.getSelectedEvent();
        if (e == null) {
            return;
        }
        String plType = PEPOGUI.getSelectedPersonnelListType();
        PersonnelList pl = null;
        if (plType == null) {
            plType = "Participants";
        }
        if (plType.equals("Participants")) {
            pl = e.getParticipantList();
        } else if (plType.equals("Helpers")) {
            pl = e.getHelperList();
        } else if (plType.equals("Sponsors")) {
            pl = e.getSponsorList();
        } else if (plType.equals("Vendors")) {
            pl = e.getVendorList();
        }


        ArrayList<Object> headers = new ArrayList<>();
        headers.add("Name");
        headers.add("Email");

        for (int i = 0; i < pl.getPersonnelHeadersList().size(); i++) {
            headers.add(pl.getPersonnelHeadersList().get(i));
        }


        JTable t = PEPOGUI.getContactsTable();

        javax.swing.table.DefaultTableModel newTableModel = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                headers.toArray());
        t.setModel(newTableModel);
        TableRowSorter newTableRowSorter = new TableRowSorter(newTableModel);
        t.setRowSorter(newTableRowSorter);
        ((DefaultTableModel) t.getModel()).setRowCount(pl.getPersonnelList().size());

        ArrayList<Personnel> p = pl.getPersonnelList();
        for (int i = 0; i < p.size(); i++) {
            Personnel cur = p.get(i);
            t.getModel().setValueAt(cur.getName(), i, 0);
            t.getModel().setValueAt(cur.getEmail(), i, 1);

            for (int j = 0; j < cur.getAttributes().size(); j++) {
                t.getModel().setValueAt(cur.getAttributes().get(j), i, j + 2);
            }

        }
        if (PEPOGUI.ContactsEditClicked == -1) {
            PEPOGUI.getAddContactsButton().setVisible(false);
            PEPOGUI.getDeleteContactsButton().setVisible(false);
            PEPOGUI.getImportContactsButton().setVisible(false);
            PEPOGUI.getCancelContactsButton().setVisible(false);
            PEPOGUI.getEditContactsHeaderButton().setVisible(false);
            PEPOGUI.getContactsInstruction().setVisible(false);

        } else {
            PEPOGUI.getAddContactsButton().setVisible(true);
            PEPOGUI.getDeleteContactsButton().setVisible(true);
            PEPOGUI.getImportContactsButton().setVisible(true);
            PEPOGUI.getEditContactsHeaderButton().setVisible(true);
            PEPOGUI.getContactsInstruction().setVisible(true);
        }
        t.getTableHeader().setFont(new java.awt.Font("Ubuntu Bold", 0, 14));

        
        updateContactsCount();
    }

    /**
     * Updates the ContactsCountTextfield of the currently selected contact list.
     */
    public static void updateContactsCount() {
        PEPOGUI.getCountText().setText("" + PEPOGUI.getContactsTable().getRowCount());
    }

    /**
     * Update the EventDayNotesTextArea to the notes of the currently selected EventDay
     */
    public static void updateEventDayNotesTextArea() {
        if (PEPOGUI.getEventDayTableClicked() == -1) {
            PEPOGUI.getEventDayNotesText().setText("");
        } else {
            PEPOGUI.getEventDayNotesText().setText(PEPOGUI.getSelectedEvent().getEventDayList().get(PEPOGUI.getEventDayTableClicked()).getNotes());
        }
    }

    /**
     * Update the EventDayItineraryTextArea to the Itinerary of the currently selected EventDay.
     */
    public static void updateEventDayItineraryTextArea() {
        if (PEPOGUI.getEventDayTableClicked() == -1) {
            PEPOGUI.getEventDayItineraryText().setText("");
            return;
        }
        Itinerary pl = PEPOGUI.getSelectedEvent().getEventDayList().get(PEPOGUI.getEventDayTableClicked()).getItinerary();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        ArrayList<ItineraryItem> list = pl.getSortedItineraryItemList();
        ArrayList<ArrayList<Object>> arr = new ArrayList<>();

        PEPOGUI.ProgrammeStartDuration.clear();
        Calendar beginCal = Calendar.getInstance();
        for (int i = 0; i < list.size(); i++) {
            Date start = list.get(i).getDate();
            Date end = list.get(i).getEndDate();

            beginCal.setTime(start);
            beginCal.set(Calendar.HOUR_OF_DAY, 0);
            beginCal.set(Calendar.MINUTE, 0);

            PEPOGUI.ProgrammeStartDuration.add(new Pair(
                    (int) ((start.getTime() - beginCal.getTimeInMillis()) / 1000.0 / 60.0),
                    (int) ((end.getTime() - start.getTime()) / 1000.0 / 60.0)));

            arr.add(new ArrayList<>());
            arr.get(i).add(formatter.format(start).toLowerCase());
            arr.get(i).add(formatter.format(end).toLowerCase());
            arr.get(i).add(list.get(i).getName());
        }


        JTextArea ta = PEPOGUI.getEventDayItineraryText();
        String str = "";
        if (ta != null) {
            for (int i = 0; i < arr.size(); i++) {
                str = str + arr.get(i).get(0) + "-" + arr.get(i).get(1) + "  " + arr.get(i).get(2) + "\n";
            }
            ta.setText(str);
        }
    }

    /**
     * Update the ItineraryTable to the Itinerary of the currently selected EventDay.
     */
    public static void updateItineraryTable(Itinerary itinerary) {

        if (itinerary == null) {
            PEPOGUI.getEventDayItineraryText().setText("");
            return;
        }


        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        ArrayList<ItineraryItem> list = itinerary.getSortedItineraryItemList();
        ArrayList<ArrayList<Object>> arr = new ArrayList<>();

        PEPOGUI.ProgrammeStartDuration.clear();
        Calendar beginCal = Calendar.getInstance();

        for (int i = 0; i < list.size(); i++) {
            Date start = list.get(i).getDate();
            Date end = list.get(i).getEndDate();

            beginCal.setTime(start);
            beginCal.set(Calendar.HOUR_OF_DAY, 0);
            beginCal.set(Calendar.MINUTE, 0);

            PEPOGUI.ProgrammeStartDuration.add(new Pair(
                    (int) ((start.getTime() - beginCal.getTimeInMillis()) / 1000.0 / 60.0),
                    (int) ((end.getTime() - start.getTime()) / 1000.0 / 60.0)));

            arr.add(new ArrayList<>());
            arr.get(i).add(formatter.format(start).toLowerCase());
            arr.get(i).add(formatter.format(end).toLowerCase());
            arr.get(i).add(list.get(i).getName());
        }

        JTable t = ViewItinerary.getItineraryTable();
        if (t != null) {
            ((DefaultTableModel) t.getModel()).setRowCount(arr.size());
            for (int i = 0; i < arr.size(); i++) {
                t.getModel().setValueAt(arr.get(i).get(0) + "-" + arr.get(i).get(1) + "  " + arr.get(i).get(2), i, 0);   // startTime
            }
        }

        t = EditEventDay.getItineraryTable();
        if (t != null) {
            ((DefaultTableModel) t.getModel()).setRowCount(arr.size());
            for (int i = 0; i < arr.size(); i++) {
                t.getModel().setValueAt(arr.get(i).get(0) + "-" + arr.get(i).get(1) + "  " + arr.get(i).get(2), i, 0);   // startTime
            }
        }

        t = AddEventDay.getItineraryTable();
        if (t != null) {
            ((DefaultTableModel) t.getModel()).setRowCount(arr.size());
            for (int i = 0; i < arr.size(); i++) {
                t.getModel().setValueAt(arr.get(i).get(0) + "-" + arr.get(i).get(1) + "  " + arr.get(i).get(2), i, 0);   // startTime
            }
        }

    }

    /**
     * Update the UnarchivedTable.
     */
    public static void updateUnarchivedTable() {
        ArrayList<Event> list = LogicController.getEventList();
        ArrayList<String> arr = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            arr.add(list.get(i).getTitle());
        }

        JTable t = ArchiveEvent.getUnarchivedTable();
        if (t != null) {
            ((DefaultTableModel) t.getModel()).setRowCount(arr.size());
            for (int i = 0; i < arr.size(); i++) {
                t.getModel().setValueAt(arr.get(i), i, 0);
            }
        }
    }

    /**
     * Update the ArchivedTable.
     */
    public static void updateArchivedTable() {
        ArrayList<Event> list = LogicController.getArchivedEventList();
        ArrayList<String> arr = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            arr.add(list.get(i).getTitle());
        }

        JTable t = ArchiveEvent.getArchivedTable();
        if (t != null) {
            ((DefaultTableModel) t.getModel()).setRowCount(arr.size());
            for (int i = 0; i < arr.size(); i++) {
                t.getModel().setValueAt(arr.get(i), i, 0);
            }
        }
    }

    /**
     * Submits the information in the Contacts Table to LogicController.
     * @throws Exception
     */
    public static void submitContactsTable() throws Exception {
        Event e = LogicController.getEventList().get(PEPOGUI.getEventListPreviousSelected());
        String plType = PEPOGUI.getSelectedPersonnelListType();
        if (plType == null) {
            plType = "Participants";
        }

        ArrayList<String> headers = new ArrayList<>();
        JTable t = PEPOGUI.getContactsTable();
        for (int i = 2; i < t.getColumnCount(); i++) {
            if (t.getColumnModel().getColumn(i).getHeaderValue() == null) {
                headers.add("");
            } else {
                headers.add((String) t.getColumnModel().getColumn(i).getHeaderValue());
            }
        }

        if (plType.equals("Participants")) {
            LogicController.setParticipantList(e, headers);
        } else if (plType.equals("Helpers")) {
            LogicController.setHelperList(e, headers);
        } else if (plType.equals("Sponsors")) {
            LogicController.setSponsorList(e, headers);
        } else if (plType.equals("Vendors")) {
            LogicController.setVendorList(e, headers);
        }


        for (int i = 0; i < t.getRowCount(); i++) {
            ArrayList<String> arr = new ArrayList<>();
            for (int j = 2; j < t.getColumnCount(); j++) {
                if (t.getValueAt(i, j) == null)
                    t.setValueAt("",i, j);
                arr.add((String) t.getValueAt(i, j));
            }

            String name, email;
            if (t.getValueAt(i, 0) == null)
                    t.setValueAt("",i, 0);
            if (t.getValueAt(i, 1) == null)
                    t.setValueAt("",i, 1);
            name = (String) t.getValueAt(i, 0);
            email = (String) t.getValueAt(i, 1);


            if (plType.equals("Participants")) {
                LogicController.addPersonnel(e.getParticipantList(), name, email, arr);
            } else if (plType.equals("Helpers")) {
                LogicController.addPersonnel(e.getHelperList(), name, email, arr);
            } else if (plType.equals("Sponsors")) {
                LogicController.addPersonnel(e.getSponsorList(), name, email, arr);
            } else if (plType.equals("Vendors")) {
                LogicController.addPersonnel(e.getVendorList(), name, email, arr);
            }

        }
    }

    /**
     * Update the AnnouncementSubjectTextField to the title of the currently selected Event.
     */
    public static void updateAnnouncementSubject() {
        if (PEPOGUI.getSelectedEvent() != null) {
            PEPOGUI.getAnnouncementSubjectText().setText(PEPOGUI.getSelectedEvent().getTitle());
        }
    }

    /**
     * Update the AnnouncementContactsListTable according to the currently selected Event.
     */
    public static void updateAnnouncementContactsListTable() {
        JTable t = PEPOGUI.getAnnouncementEventContactListTable();
        ArrayList<Event> events = LogicController.getEventList();
        events.remove(PEPOGUI.getSelectedEvent());
        events.addAll(LogicController.getArchivedEventList());

        ArrayList<ArrayList<Object>> arr = new ArrayList<>();

        for (int i = 0; i < events.size(); i++) {
            ArrayList<Object> curRow = new ArrayList<>();
            curRow.add(events.get(i).getTitle());
            curRow.add(false);
            curRow.add(false);
            curRow.add(false);
            curRow.add(false);
            arr.add(curRow);
        }

        ((DefaultTableModel) t.getModel()).setRowCount(arr.size());
        for (int i = 0; i < arr.size(); i++) {
            t.getModel().setValueAt(arr.get(i).get(0), i, 0);
            t.getModel().setValueAt(arr.get(i).get(1), i, 1);
            t.getModel().setValueAt(arr.get(i).get(2), i, 2);
            t.getModel().setValueAt(arr.get(i).get(3), i, 3);
            t.getModel().setValueAt(arr.get(i).get(4), i, 4);
        }
    }

    /**
     * 
     */
    public static class CSVFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
        }

        public String getDescription() {
            return "Comma-Separated Values Files (.csv)";
        }
    }

    /**
     * Import participant list.
     */
    public static void importParticipants() {
        importPersonnels(PEPOGUI.getSelectedEvent().getParticipantList());
    }

    /**
     * Import helper list.
     */
    public static void importHelpers() {
        importPersonnels(PEPOGUI.getSelectedEvent().getHelperList());
    }

    /**
     * Import sponsor list.
     */
    public static void importSponsors() {
        importPersonnels(PEPOGUI.getSelectedEvent().getSponsorList());
    }

    /**
     * Import vendor list.
     */
    public static void importVendors() {
        importPersonnels(PEPOGUI.getSelectedEvent().getVendorList());
    }

    /**
     * Import the specified PersonnelLsit.
     */
    private static void importPersonnels(PersonnelList list) {
        JTable table = PEPOGUI.getContactsTable();

        JFileChooser filechooser = new JFileChooser();
        filechooser.setFileFilter(new CSVFilter());
        int option = filechooser.showOpenDialog(null);

        if (option == JFileChooser.APPROVE_OPTION) {
            Object options[] = {"Add on", "Overwrite"};
            if (JOptionPane.showOptionDialog(null, "Would you like to add on to the existing list or overwrite it?", "Import",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) == 1) {
                ((DefaultTableModel) table.getModel()).setRowCount(0);   // overwrite 
            }
            try {
                BufferedReader file = new BufferedReader(new FileReader(filechooser.getSelectedFile()));
                String line = file.readLine();
                if (line != null) {
                    String[] headers = line.split(",", -1);
                    while (table.getColumnCount() < headers.length) {
                        ((DefaultTableModel) table.getModel()).setColumnCount(table.getColumnCount() + 1);
                        table.getColumnModel().getColumn(table.getColumnCount() - 1).setHeaderValue("New Attribute");
                    }
                }

                while (line != null) {
                    int row;
                    ((DefaultTableModel) table.getModel()).setRowCount(table.getRowCount() + 1);
                    row = table.getRowCount() - 1;
                    String[] attrs = line.split(",", -1);
                    for (int i = 0; i < attrs.length; i++) {
                        table.setValueAt(attrs[i], row, i);
                    }
                    line = file.readLine();
                }

                file.close();
            } catch (Exception obj) {
            }
        }
    }
}
