/* Logic component
 * 
 * The LogicContoller contains all the Managers that are in charge of each object.
 * 
 * The following are essential routines to call:
 * start(): To load data from Database
 * exit(): Save data to Database
 * 
 * Refer to the javadocs for the API
 * 
 */
package Logic;

import GUI.AlertThread;
import Logic.Venue.VenueBookingManager;
import Logic.Venue.VenueBooking;
import Logic.Budget.ExpenseManager;
import Logic.Budget.BudgetCategoryManager;
import Logic.Budget.Expense;
import Logic.Budget.BudgetCategory;
import Logic.Venue.VenueManager;
import Logic.Itinerary.ItineraryItemManager;
import Logic.Itinerary.ItineraryManager;
import Logic.PersonnelList.PersonnelManager;
import Logic.PersonnelList.PersonnelListManager;
import Logic.Event.EventManager;
import Logic.Event.Event;
import Database.*;
import Logic.Memo.*;
import Logic.PersonnelList.Base64;
import Logic.PersonnelList.MailClient;
import Logic.PersonnelList.Personnel;
import Logic.PersonnelList.PersonnelList;
import Logic.Itinerary.Itinerary;
import Logic.Itinerary.ItineraryItem;
import Logic.Venue.Venue;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

public class LogicController {
    //  Alert Thread

    private static AlertThread alertThread;
    //  Storage of Managers
    private static BudgetCategoryManager budgetcategoryM;
    private static ExpenseManager expenseM;
    private static EventManager eventM;
    private static TaskManager taskM;
    private static VenueBookingManager venuebookingM;
    private static VenueManager venueM;
    private static ItineraryItemManager itineraryitemM;
    private static ItineraryManager itineraryM;
    private static EventDayManager eventdayM;
    private static PersonnelManager personnelM;
    private static PersonnelListManager personnellistM;
    private static AlertManager alertM;

    //  To start and exit Logic
    /**
     * Start the LogicController.
     * @throws Exception
     */
    public static void start() throws Exception {
        // Load from XML and construct all the pointers
        DBController.init();
        LogicController.init();
    }
    
    /**
     * Initialize the LogicController with Database.
     * @throws Exception
     */
    static void init() {
        budgetcategoryM = new BudgetCategoryManager(DBController.loadBudgetCategories());
        expenseM = new ExpenseManager(DBController.loadExpenses());
        eventM = new EventManager(DBController.loadUnArchivedEvents(), DBController.loadArchivedEvents());
        taskM = new TaskManager(DBController.loadTasks());
        venuebookingM = new VenueBookingManager(DBController.loadVenueBookings());
        venueM = new VenueManager(DBController.loadVenues());
        itineraryitemM = new ItineraryItemManager(DBController.loadItineraryItems());
        itineraryM = new ItineraryManager(DBController.loadItinerarys());
        eventdayM = new EventDayManager(DBController.loadEventDays());
        personnelM = new PersonnelManager(DBController.loadPersonnels());
        personnellistM = new PersonnelListManager(DBController.loadPersonnelLists());
        alertM = new AlertManager(taskM.getTaskList());
        alertThread = new AlertThread();
        alertThread.start();
    }

    /**
     * Exit the LogicController.
     * @throws Exception
     */
    public static void exit() throws Exception {
        DBController.saveBudgetCategories(budgetcategoryM.getBudgetCategoryList());
        DBController.saveExpenses(expenseM.getExpenseList());
        DBController.saveUnArchivedEvents(eventM.getEventList());
        DBController.saveArchivedEvents(eventM.getArchivedEventList());
        DBController.saveTasks(taskM.getTaskList());
        DBController.saveVenueBookings(venuebookingM.getVenueBookingList());
        DBController.saveItineraryItems(itineraryitemM.getItineraryItemList());
        DBController.saveItinerarys(itineraryM.getItineraryList());
        DBController.saveEventDays(eventdayM.getEventDayList());
        DBController.savePersonnels(personnelM.getPersonnelList());
        DBController.savePersonnelLists(personnellistM.getPersonnelListList());
        DBController.Store();
        alertThread.interrupt();
    }

    //  Task get method
    /**
     * 
     * @return ArrayList of Event that are not archived.
     */
    public static ArrayList<Task> getTaskList() {
        return taskM.getTaskList();
    }
    
    
    //
    //  Methods for GUI
    //  Event-General
    //
    /**
     * 
     * @return ArrayList of Event that are not archived.
     */
    public static ArrayList<Event> getEventList() {
        return eventM.getEventList();
    }

    /**
     * 
     * @return ArrayList of Event that are archived.
     */
    public static ArrayList<Event> getArchivedEventList() {
        return eventM.getArchivedEventList();
    }

    /**
     * Create a new Event with the specified inputs.
     * @param name
     * @param description
     * @return Created Event
     * @throws Exception
     */
    public static Event addEvent(String name, String description) throws Exception {
        Event e = new Event(name, description, 0);
        eventM.addEvent(e);
        budgetcategoryM.addBudgetCategory(e.getUncategorizedBudget());
        personnellistM.addPersonnelList(e.getHelperList());
        personnellistM.addPersonnelList(e.getParticipantList());
        personnellistM.addPersonnelList(e.getSponsorList());
        personnellistM.addPersonnelList(e.getVendorList());
        return e;
    }

    /**
     * Delete the specified Event.
     * @param event
     * @throws Exception
     */
    public static void deleteEvent(Event event) throws Exception {
        //  Budget
        for (int i = 0; i < event.getBudgetCategoryList().size(); i++) {
            for (int j = 0; j < event.getBudgetCategoryList().get(i).getExpenseList().size(); j++) //  Each expense
            {
                LogicController.expenseM.deleteExpense(event.getBudgetCategoryList().get(i).getExpenseList().get(j));
            }
            LogicController.budgetcategoryM.deleteBudgetCategory(event.getBudgetCategoryList().get(i));
        }
        for (int j = 0; j < event.getUncategorizedBudget().getExpenseList().size(); j++) //  Each expense
        {
            LogicController.expenseM.deleteExpense(event.getUncategorizedBudget().getExpenseList().get(j));
        }
        LogicController.budgetcategoryM.deleteBudgetCategory(event.getUncategorizedBudget());

        //  Memo
        ArrayList<EventDay> deletingEventDay = event.getEventDayList();
        for (int i = 0; i < deletingEventDay.size(); i++) {
            LogicController.deleteEventDay(deletingEventDay.get(i));
        }
        ArrayList<Task> deletingTask = event.getTaskList();
        for (int i = 0; i < deletingTask.size(); i++) {
            LogicController.deleteTask(deletingTask.get(i));
        }

        //  Contacts
        LogicController.deleteParticipantList(event);
        LogicController.deleteHelperList(event);
        LogicController.deleteVendorList(event);
        LogicController.deleteSponsorList(event);
        eventM.deleteEvent(event);
    }

    /**
     * Replace the title of the specified Event to the specified title.
     * @param event
     * @param title
     * @throws Exception
     */
    public static void setEventTitle(Event event, String title) throws Exception {
        eventM.setTitle(event, title);
    }

    /**
     * Replace the description of the specified Event to the specified description.
     * @param event
     * @param description
     */
    public static void setEventDescription(Event event, String description) {
        eventM.setDescription(event, description);
    }

    /**
     * Archive an Event.
     * @param event
     */
    public static void archiveEvent(Event event) {
        eventM.archiveEvent(event);
    }

    /**
     * Un-archive an Event.
     * @param event
     */
    public static void unArchiveEvent(Event event) {
        eventM.restoreEvent(event);
    }

    //Event-Budget
    //
    /**
     * Replace the total budget of the specified Event to the specified amount.
     * @param event
     * @param amount
     * @throws Exception
     */
    public static void setEventTotalBudget(Event event, long amount) throws Exception {
        eventM.setTotalBudget(event, amount);
    }

    /**
     * Create a new BudgetCategory with the specified inputs and adds it to the specified Event.
     * @param event
     * @param name
     * @param budget
     * @throws Exception
     */
    public static void addBudgetCategory(Event event, String name, long budget) throws Exception {
        if (event == null) {
            throw new Exception("BudgetCategory is null");
        }
        BudgetCategory newbc = new BudgetCategory(event, name, budget);
        eventM.addBudgetCategory(event, newbc);
        budgetcategoryM.addBudgetCategory(newbc);
    }

    /**
     * Delete the specified BudgetCategory.
     * @param budgetcategory
     * @throws Exception
     */
    public static void deleteBudgetCategory(BudgetCategory budgetcategory) throws Exception {
        ArrayList<Expense> exp = budgetcategory.getExpenseList();
        for (int i = 0; i < exp.size(); i++) 
            setExpenseBudgetCategory(exp.get(i),budgetcategory.getEvent().getUncategorizedBudget());
        
        budgetcategoryM.deleteBudgetCategory(budgetcategory);
        eventM.deleteBudgetCategory(budgetcategory.getEvent(), budgetcategory);
    }

    //  Event-Task
    //
    /**
     * Create a new Task with the specified inputs and add it to the specified Event.
     * @param event
     * @param description
     * @param date
     * @param priority
     * @return Created Task.
     * @throws Exception
     */
    public static Task addTask(Event event, String description, Date date, int priority) throws Exception {
        Task myTask = new Task(event, description, date, priority, false, null);
        taskM.addTask(myTask);
        if (event != null) {
            eventM.addTask(event, myTask);
        }
        return myTask;
    }

    /**
     * Delete the specified Task.
     * @param task
     * @throws Exception
     */
    public static void deleteTask(Task task) throws Exception {
        if (task.getAlert() != null) 
            alertM.deleteAlert(task);
        for (int i = 0; i < task.getTaggedVenueBookingList().size(); i++) 
            venuebookingM.untagVenueBooking(task.getTaggedVenueBookingList().get(i), task);
        
        eventM.deleteTask(task.getEvent(), task);
        taskM.deleteTask(task);        
    }

    //  Event-EventDay
    //
    /**
     * Create and add a new EventDay with the specified inputs to the specified event with itinerary.
     * @param event
     * @param title
     * @param notes
     * @param startdate
     * @param enddate
     * @param itinerary
     * @return Created EventDay.
     * @throws Exception
     */
    public static EventDay addEventDayWithItinerary(Event event, String title, String notes, Date startdate, Date enddate, Itinerary itinerary) throws Exception {
        EventDay myEventDay = new EventDay(event, title, notes, startdate, enddate, itinerary);
        eventdayM.addEventDay(myEventDay);
        eventM.addEventDay(event, myEventDay);
        itineraryM.addItinerary(myEventDay.getItinerary());
        return myEventDay;
    }

    /**
     * Delete the specified EventDay.
     * @param eventday
     * @throws Exception
     */
    public static void deleteEventDay(EventDay eventday) throws Exception {
        eventM.deleteEventDay(eventday.getEvent(), eventday);
        eventdayM.deleteEventDay(eventday);
        ArrayList<ItineraryItem> itinieraryitemsArr = eventday.getItinerary().getSortedItineraryItemList();
        for (int i = 0; i < itinieraryitemsArr.size(); i++)
            itineraryitemM.deleteItineraryItem(itinieraryitemsArr.get(i));
        itineraryM.deleteItinerary(eventday.getItinerary());
        for (int i = 0; i < eventday.getTaggedVenueBookingList().size(); i++)
            venuebookingM.untagVenueBooking(eventday.getTaggedVenueBookingList().get(i), eventday);
       
    }

    //  Event-Personnel
    //
    /**
     * Set the participant list headers of a with the specified headers.
     * @param event
     * @param headers
     */
    public static void setParticipantList(Event event, ArrayList<String> headers) {
        deleteParticipantList(event);
        PersonnelList pl = new PersonnelList(headers);
        eventM.setParticipantList(event, pl);
        personnellistM.addPersonnelList(pl);
    }

    /**
     * Set the helper list headers of a with the specified headers.
     * @param event
     * @param headers
     */
    public static void setHelperList(Event event, ArrayList<String> headers) {
        deleteHelperList(event);
        PersonnelList pl = new PersonnelList(headers);
        eventM.setHelperList(event, pl);
        personnellistM.addPersonnelList(pl);
    }

    /**
     * Set the sponsor list headers of a with the specified headers.
     * @param event
     * @param headers
     */
    public static void setSponsorList(Event event, ArrayList<String> headers) {
        deleteSponsorList(event);
        PersonnelList pl = new PersonnelList(headers);
        eventM.setSponsorList(event, pl);
        personnellistM.addPersonnelList(pl);
    }

    /**
     * Set the vendor list headers of a with the specified headers.
     * @param event
     * @param headers
     */
    public static void setVendorList(Event event, ArrayList<String> headers) {
        deleteVendorList(event);
        PersonnelList pl = new PersonnelList(headers);
        eventM.setVendorList(event, pl);
        personnellistM.addPersonnelList(pl);
    }

    /**
     * Delete the participant list of a specified Event.
     * @param event
     */
    public static void deleteParticipantList(Event event) {
        ArrayList<Personnel> pl = event.getParticipantList().getPersonnelList();
        for (int i = 0; i < pl.size(); i++) {
            personnelM.deletePersonnel(pl.get(i));
        }
        personnellistM.deletePersonnelList(event.getParticipantList());
        eventM.deleteParticipantList(event);
    }

    /**
     * Delete the helper list of a specified Event.
     * @param event
     */
    public static void deleteHelperList(Event event) {
        ArrayList<Personnel> pl = event.getHelperList().getPersonnelList();
        for (int i = 0; i < pl.size(); i++) {
            personnelM.deletePersonnel(pl.get(i));
        }
        personnellistM.deletePersonnelList(event.getHelperList());
        eventM.deleteHelperList(event);
    }

    /**
     * Delete the sponsor list of a specified Event.
     * @param event
     */
    public static void deleteSponsorList(Event event) {
        ArrayList<Personnel> pl = event.getSponsorList().getPersonnelList();
        for (int i = 0; i < pl.size(); i++) {
            personnelM.deletePersonnel(pl.get(i));
        }
        personnellistM.deletePersonnelList(event.getSponsorList());
        eventM.deleteSponsorList(event);
    }

    /**
     * Delete the vendor list of a specified Event.
     * @param event
     */
    public static void deleteVendorList(Event event) {
        ArrayList<Personnel> pl = event.getVendorList().getPersonnelList();
        for (int i = 0; i < pl.size(); i++) {
            personnelM.deletePersonnel(pl.get(i));
        }
        personnellistM.deletePersonnelList(event.getVendorList());
        eventM.deleteVendorList(event);
    }

    //
    //  BudgetCategory-General
    //
    /**
     * Replace the name of a specified BudgetCategory to the specified name.
     * @param budgetcategory
     * @param name
     * @throws Exception
     */
    public static void setBudgetCategoryName(BudgetCategory budgetcategory, String name) throws Exception {
        if (budgetcategory == null) {
            throw new Exception("BudgetCategory is null");
        }
        if (name == null) {
            throw new Exception("Name is null");
        }
        budgetcategoryM.setName(budgetcategory, name);
    }

    /**
     * Replace the amount of a specified BudgetCategory to the specified amount.
     * @param budgetcategory
     * @param amount
     * @throws Exception
     */
    public static void setBudgetCategoryAmount(BudgetCategory budgetcategory, long amount) throws Exception {
        budgetcategoryM.setAmount(budgetcategory, amount);
    }

    //  BudgetCategory-Expense
    //
    /**
     * Create a new Expense with the specified inputs and add it to the specified BudgetCategory.
     * @param budgetcategory
     * @param title
     * @param cost
     * @throws Exception
     */
    public static void addExpense(BudgetCategory budgetcategory, String title, long cost) throws Exception {
        if (budgetcategory == null) {
            throw new Exception("BudgetCategory is null");
        }
        if (title == null) {
            throw new Exception("Title is null");
        }
        Expense ex = null;
        ex = new Expense(budgetcategory, title, cost);
        budgetcategoryM.addExpense(budgetcategory, ex);
        expenseM.addExpense(ex);
    }

    /**
     * Delete the specified Expense.
     * @param expense
     * @throws Exception
     */
    public static void deleteExpense(Expense expense) throws Exception {
        if (expense == null) {
            throw new Exception("Expense is null");
        }
        budgetcategoryM.deleteExpense(expense.getBudgetCategory(), expense);
        expenseM.deleteExpense(expense);
    }

    //
    //  Expense
    //
    /**
     * Replace the title of a specified Expense to the specified title.
     * @param expense
     * @param title
     * @throws Exception
     */
    public static void setExpenseTitle(Expense expense, String title) throws Exception {
        if (expense == null) {
            throw new Exception("Expense is null");
        }
        if (title == null) {
            throw new Exception("BudgetCategory is null");
        }
        expenseM.setTitle(expense, title);
    }

    /**
     * Replace the cost of a specified Expense to the specified cost.
     * @param expense
     * @param cost
     * @throws Exception
     */
    public static void setExpenseCost(Expense expense, long cost) throws Exception {
        expenseM.setCost(expense, cost);
    }

    /**
     * Replace the BudgetCategory of a specified Expense to the specified BudgetCategory.
     * @param expense
     * @param budgetcategory
     * @throws Exception
     */
    public static void setExpenseBudgetCategory(Expense expense, BudgetCategory budgetcategory) throws Exception {
        if (expense == null) {
            throw new Exception("Expense is null");
        }
        if (budgetcategory == null) {
            throw new Exception("BudgetCategory is null");
        }
        if (expense.getBudgetCategory() != budgetcategory) {
            budgetcategoryM.addExpense(budgetcategory, expense);
            budgetcategoryM.deleteExpense(expense.getBudgetCategory(), expense);
            expenseM.setBudgetCatgeory(expense, budgetcategory);
        }
    }

    //
    //  EventDay-General
    //
    /**
     * Replace the notes of a specified EventDay to the specified notes.
     * @param eventday
     * @param notes
     */
    public static void setEventDayNotes(EventDay eventday, String notes) {
        eventdayM.setNotes(eventday, notes);
    }

    /**
     * Replace the description of a specified EventDay to the specified description.
     * @param eventday
     * @param description
     * @throws Exception
     */
    public static void setEventDayDescription(EventDay eventday, String description) throws Exception {
        eventdayM.setDescription(eventday, description);
    }

    /**
     * Replace the start date and end date of a specified EventDay to the specified dates.
     * @param eventday
     * @param startdate
     * @param enddate
     * @throws Exception
     */
    public static void setEventDayDate(EventDay eventday, Date startdate, Date enddate) throws Exception {
        eventdayM.setDate(eventday, startdate, enddate);
    }

    //  EventDay-VenueBooking
    //
    /**
     * Tag a specified VenueBooking from a specified EventDay.
     * @param eventday
     * @param venuebooking
     */
    public static void tagEventDayVenueBooking(EventDay eventday, VenueBooking venuebooking) {
        eventdayM.tagVenueBooking(eventday, venuebooking);
        venuebookingM.tagVenueBooking(venuebooking, eventday);
    }

    /**
     * Un-tag a specified VenueBooking from a specified EventDay.
     * @param eventday
     * @param venuebooking
     * @throws Exception
     */
    public static void untagEventDayVenueBooking(EventDay eventday, VenueBooking venuebooking) throws Exception {
        eventdayM.untagVenueBooking(eventday, venuebooking);
        venuebookingM.untagVenueBooking(venuebooking, eventday);
    }

    //
    //  Task
    //
    /**
     * Add an Alert to a specified Task with the specified Date.
     * @param task
     * @param alert
     */
    public static void addTaskAlert(Task task, Date alert) {
        taskM.addAlert(task, alert);
        alertM.addAlert(task);
    }
    
    /**
     * Delete the Alert of a specified Task.
     * @param task
     */
    public static void deleteTaskAlert(Task task) {
        if (task.getAlert() != null){
            alertM.deleteAlert(task);   //  must delete before removing task's alert
            taskM.deleteAlert(task);
        }
    }

    /**
     * Replace the event of a specified Task to the specified event.
     * @param t
     * @param event
     * @throws Exception
     */
    public static void setTaskEvent(Task t, Event event) throws Exception {
        if (t.getEvent() != event) {
            if (t.getEvent() != null) {
                eventM.deleteTask(t.getEvent(), t);
            }
            taskM.setEvent(t, event);
            if (event != null) {
                eventM.addTask(event, t);
            }
        }
    }

    /**
     * Replace the priority of a specified Task to the specified priority.
     * @param task
     * @param priority
     * @throws Exception
     */
    public static void setTaskPriority(Task task, int priority) throws Exception {
        taskM.setPriority(task, priority);
    }

    /**
     * Replace the completion status of a specified Task to the specified status.
     * @param task
     * @param completed
     */
    public static void setCompleted(Task task, boolean completed) {
        taskM.setCompleted(task, completed);
    }

    /**
     * Replace the description of a specified Task to the specified description.
     * @param task
     * @param description
     * @throws Exception
     */
    public static void setTaskDescription(Task task, String description) throws Exception {
        taskM.setDescription(task, description);
    }

    /**
     * Replace the date of a specified Task to the specified date.
     * @param task
     * @param date
     * @throws Exception
     */
    public static void setTaskDate(Task task, Date date) throws Exception {
        taskM.setDate(task, date);
    }

    //  Task-VenueBooking
    //
    /**
     * Tag a specified VenueBooking to a specified Task.
     * @param task
     * @param venuebooking
     */
    public static void tagTaskVenueBooking(Task task, VenueBooking venuebooking) {
        taskM.tagVenueBooking(task, venuebooking);
        venuebookingM.tagVenueBooking(venuebooking, task);
    }

    /**
     * Un-tag a specified VenueBooking from a specified Task.
     * @param task
     * @param venuebooking
     * @throws Exception
     */
    public static void untagTaskVenueBooking(Task task, VenueBooking venuebooking) throws Exception {
        taskM.untagVenueBooking(task, venuebooking);
        venuebookingM.untagVenueBooking(venuebooking, task);
    }

    //
    //  VenueBooking
    //
    /**
     * Create and add a new VenueBooking with the specified inputs.
     * @param venue
     * @param startdate
     * @param enddate
     * @throws Exception
     */
    public static void addVenueBooking(Venue venue, Date startdate, Date enddate) throws Exception {
        if (venue == null) {
            throw new Exception("Venue is null");
        }
        if (startdate == null) {
            throw new Exception("Startdate is null");
        }
        if (enddate == null) {
            throw new Exception("Enddate is null");
        }
        VenueBooking vb = new VenueBooking(venue, startdate, enddate);
        venueM.addVenueBooking(venue, vb);
        venuebookingM.addVenueBooking(vb);
    }

    /**
     * Delete the specified VenueBooking.
     * @param venuebooking
     * @throws Exception
     */
    public static void deleteVenueBooking(VenueBooking venuebooking) throws Exception {
        if (venuebooking == null) {
            throw new Exception("Venuebooking is null");
        }
        ArrayList<MemoItem> toUntag = venuebooking.getTaggedMemoItemList();
        for (int i = 0; i < toUntag.size(); i++) {
            MemoItem cur = toUntag.get(i);
            if (cur instanceof Task) {
                taskM.untagVenueBooking(cur, venuebooking);
            } else if (cur instanceof EventDay) {
                eventdayM.untagVenueBooking(cur, venuebooking);
            }
        }
        venuebookingM.deleteVenueBooking(venuebooking);
        venueM.deleteVenueBooking(venuebooking.getVenue(), venuebooking);
    }

    /**
     * 
     * @param date
     * @return ArrayList of VenueBooking sorted and filtered according to date
     */
    public static ArrayList<VenueBooking> getFutureVenueBookingList(Date date) {
        return venuebookingM.getFutureVenueBookingList(date);
    }
    
    /**
     * @return ArrayList of VenueBooking sorted according to date
     */
    public static ArrayList<VenueBooking> getVenueBookingList() {
        return venuebookingM.getVenueBookingList();
    }
    

    //
    //  Personnel
    //
    /**
     * Create and add the Personnel to the specified PersonnelList.
     * @param personnellist
     * @param name
     * @param email
     * @param attributes
     * @throws Exception
     */
    public static void addPersonnel(PersonnelList personnellist, String name, String email, ArrayList<String> attributes) throws Exception {
        if (personnellist == null) {
            throw new Exception("PersonnelList is null");
        }
        if (name == null) {
            name = "";
        }
        if (email == null) {
            email = "";
        }
        if (attributes == null) {
            attributes = new ArrayList<>();
        }
        Personnel p = new Personnel(personnellist, name, email, attributes);
        personnellistM.addPersonnel(personnellist, p);
        personnelM.addPersonnel(p);
    }

    /**
     * Delete the specified Personnel.
     * @param personnel
     * @throws Exception
     */
    public static void deletePersonnel(Personnel personnel) throws Exception {
        if (personnel == null) {
            throw new Exception("Personnel is null");
        }
        personnellistM.deletePersonnel(personnel.getPersonnelList(), personnel);
        personnelM.deletePersonnel(personnel);
    }

    /**
     * Set the name, email and attributes of the specified Personnel.
     * @param personnel
     * @param name
     * @param email
     * @param attributes
     * @throws Exception
     */
    public static void setPersonnelAttribute(Personnel personnel, String name, String email, ArrayList<String> attributes) throws Exception {
        if (personnel == null) {
            throw new Exception("Personnel is null");
        }
        if (name == null) {
            name = "";
        }
        if (email == null) {
            email = "";
        }
        if (attributes == null) {
            attributes = new ArrayList<>();
        }
        personnelM.SetAttribute(personnel, name, email, attributes);
    }

    //
    //  Itinerary
    //
    /**
     * Create and adds an ItineraryItem to the specified Itinerary
     * @param itinerary
     * @param name
     * @param startdate
     * @param enddate
     * @return Created ItineraryItem.
     * @throws Exception
     */
    public static ItineraryItem addItineraryItem(Itinerary itinerary, String name, Date startdate, Date enddate) throws Exception {
        ItineraryItem pli = new ItineraryItem(itinerary, name, startdate, enddate);
        itineraryM.addItineraryItem(itinerary, pli);
        itineraryitemM.addItineraryItem(pli);
        return pli;
    }

    /**
     * Delete the specified ItineraryItem.
     * @param itineraryitem
     * @throws Exception
     */
    public static void deleteItineraryItem(ItineraryItem itineraryitem) throws Exception {
        itineraryM.deleteItineraryItem(itineraryitem.getItinerary(), itineraryitem);
        itineraryitemM.deleteItineraryItem(itineraryitem);
    }

    //  Memo
    /**
     * 
     * @param date
     * @param eventlist
     * @return ArrayList of Task, filtered according to requirements and sorted by priority.
     * @throws Exception
     */
    public static ArrayList<Task> getFilteredTaskByPriority(Date date, ArrayList<Event> eventlist) throws Exception {
        if (date == null) {
            throw new Exception("Date is null");
        }
        return MemoSecretary.getFilteredTaskByPriority(taskM.getTaskList(), date, eventlist);
    }

    /**
     * 
     * @param date
     * @param eventlist
     * @param venuebooking
     * @return ArrayList of Day, filtered according to the requirements and sorted by date.
     * @throws Exception
     */
    public static ArrayList<Day> getFilteredDayScheduleByDate(Date date, ArrayList<Event> eventlist, boolean venuebooking) throws Exception {
        if (date == null) {
            throw new Exception("Date is null");
        }
        return MemoSecretary.getFilteredDayScheduleByDate(taskM.getTaskList(), eventdayM.getEventDayList(),
                venuebookingM.getVenueBookingList(), date, eventlist, venuebooking);
    }

    //  Venue
    /**
     * 
     * @return ArrayList of Venue
     */
    public static ArrayList<Venue> getVenueList() {
        return venueM.getVenueList();
    }

    /**
     * 
     * @param name
     * @param type
     * @param faculty
     * @param minCapacity
     * @return ArrayList of Venue objects filtered by the requirements.
     */
    public static ArrayList<Venue> getFilteredVenueList(String name, String type, String faculty, int minCapacity) {
        return venueM.getFilteredVenueList(name, type, faculty, minCapacity);
    }

    /**
     * 
     * @return ArrayList of Strings that represent venue types
     */
    public static ArrayList<String> getVenueTypeList() {
        return venueM.getTypeList();
    }

    /**
     * 
     * @return ArrayList of Strings that represent faculties.
     */
    public static ArrayList<String> getVenueFacultyList() {
        return venueM.getFacultyList();
    }

    //  Anounncement
    //  Notification
    /**
     * Send an email announcement to every personnel in the personnel lists. Duplicated email addresses are omitted.
     * @param username
     * @param password
     * @param subject
     * @param body
     * @param pl
     * @throws Exception
     */
    public static void sendAnnouncement(String username, String password, String subject, String body, ArrayList<PersonnelList> pl) throws Exception {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        TreeSet<String> to = new TreeSet<>();
        for (int i = 0; i < pl.size(); i++) {
            ArrayList<Personnel> p = pl.get(i).getPersonnelList();
            for (int j = 0; j < p.size(); j++) {
                String nextEmail = p.get(j).getEmail();
                if (nextEmail != null && nextEmail.matches(EMAIL_REGEX)) {
                    to.add(nextEmail);
                }
            }
        }
        //  Start connection with encoded username and password
        MailClient.login(Base64.encodeString(username), Base64.encodeString(password));

        //  Send mail then logout
        String toArr[] = new String[to.size()];
        to.toArray(toArr);
        MailClient.send(username + "@nus.edu.sg", toArr, subject, body);
        MailClient.logout();
    }

    //  Alert
    /**
     * 
     * @return ArrayList of tasks that have their alerts set as the current time (nearest minute).
     */
    public static ArrayList<Task> getCurrentAlerts() {
        return alertM.getCurrentAlerts();
    }
}
