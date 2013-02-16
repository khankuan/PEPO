/* DBController class
 * 
 * + void init()
 * 
 * + ArrayList<ItineraryItem> loadItineraryItems()
 * + ArrayList<Itinerary> loadItinerarys()
 * + ArrayList<Event> loadArchivedEvents()
 * + ArrayList<Event> loadUnArchivedEvents()
 * + ArrayList<EventDay> loadEventDays()
 * + ArrayList<Task> loadTasks()
 * + ArrayList<Venue> loadVenues()
 * + ArrayList<VenueBooking> loadVenueBookings()
 * + ArrayList<BudgetCategory> loadBudgetCategories()
 * + ArrayList<Expense> loadExpenses()
 * + ArrayList<PersonnelList> loadPersonnelLists()
 * + ArrayList<Personnel> loadPersonnels()
 * 
 * + void saveItineraryItems(ArrayList<ItineraryItem> list)
 * + void saveItinerarys(ArrayList<Itinerary> list)
 * + void saveArchivedEvents(ArrayList<Event> list)
 * + void saveUnArchivedEvents(ArrayList<Event> list)
 * + void saveEventDays(ArrayList<EventDay> list)
 * + void saveTasks(ArrayList<Task> list)
 * + void saveVenueBookings(ArrayList<VenueBooking> list)
 * + void saveBudgetCategories(ArrayList<BudgetCategory> list)
 * + void saveExpenses(ArrayList<Expense> list)
 * + void savePersonnelLists(ArrayList<PersonnelList> list)
 * + void savePersonnels(ArrayList<Personnel> list)
 * 
 * + void Store()
 * 
 */
package Database;

import Logic.Itinerary.*;
import Logic.Event.*;
import Logic.Memo.*;
import Logic.Venue.*;
import Logic.Budget.*;
import Logic.PersonnelList.*;
import java.util.*;
import java.io.*;

/*
 * @author hongwei
 */
public class DBController {

    private static ArrayList<Event> Events_Archived;
    private static ArrayList<Event> Events_Unarchived;
    private static ArrayList<Event> Events;	// will be generated with Archived and Unarchived list
    private static ArrayList<Itinerary> Itinerarys;
    private static ArrayList<ItineraryItem> ItineraryItems;
    private static ArrayList<EventDay> EventDays;
    private static ArrayList<Task> Tasks;
    private static ArrayList<Venue> Venues;
    private static ArrayList<VenueBooking> VenueBookings;
    private static ArrayList<BudgetCategory> BudgetCategories;
    private static ArrayList<Expense> Expenses;
    private static ArrayList<PersonnelList> PersonnelLists;
    private static ArrayList<Personnel> Personnels;
    private static IDMap ArchiveMap;

    // load from files and then set references
    public static void init() throws Exception {

        // Create Folder "Data" if it doesnt exist

        File dir = new File("Data");
        dir.mkdir();

        // Retrieve from files

        ItineraryItems = Database.RetrieveFile_ItineraryItems();

        Itinerarys = Database.RetrieveFile_Itinerarys();
        IDMap ItineraryID_ItineraryItemID_mapping = Database.nextIDMap();

        ArchiveMap = Database.RetrieveFile_ArchiveMap();
        Events = Database.RetrieveFile_Events();
        IDMap EventID_PersonnelListID_Participant_mapping = Database.nextIDMap();
        IDMap EventID_PersonnelListID_Helper_mapping = Database.nextIDMap();
        IDMap EventID_PersonnelListID_Sponsor_mapping = Database.nextIDMap();
        IDMap EventID_PersonnelListID_Vendor_mapping = Database.nextIDMap();

        EventDays = Database.RetrieveFile_EventDays();
        IDMap EventDayID_VenueBookingID_mapping = Database.nextIDMap();
        IDMap EventDayID_EventID_mapping = Database.nextIDMap();
        IDMap EventDayID_ItineraryID_mapping = Database.nextIDMap();

        Tasks = Database.RetrieveFile_Tasks();
        IDMap TaskID_VenueBookingID_mapping = Database.nextIDMap();
        IDMap TaskID_EventID_mapping = Database.nextIDMap();

        Venues = Database.RetrieveFile_Venues();

        VenueBookings = Database.RetrieveFile_VenueBookings();
        IDMap VenueBookingID_VenueID_mapping = Database.nextIDMap();

        BudgetCategories = Database.RetrieveFile_BudgetCategories();
        IDMap BudgetCategoryID_EventID_Uncat_mapping = Database.nextIDMap();
        IDMap BudgetCategoryID_EventID_mapping = Database.nextIDMap();

        Expenses = Database.RetrieveFile_Expenses();
        IDMap ExpenseID_BudgetCategoryID_mapping = Database.nextIDMap();

        Personnels = Database.RetrieveFile_Personnels();

        PersonnelLists = Database.RetrieveFile_PersonnelLists();
        IDMap PersonnelListID_PersonnelID_mapping = Database.nextIDMap();

        // Object Referencing

        ItineraryManager.Ref_Itinerary_ItineraryItem(ItineraryID_ItineraryItemID_mapping, Itinerarys, ItineraryItems);
        ItineraryItemManager.Ref_Itinerary_ItineraryItem(ItineraryID_ItineraryItemID_mapping, Itinerarys, ItineraryItems);

        EventDayManager.Ref_EventDay_Itinerary(EventDayID_ItineraryID_mapping, EventDays, Itinerarys);

        EventDayManager.Ref_EventDay_Event(EventDayID_EventID_mapping, EventDays, Events);
        EventManager.Ref_Event_EventDay(EventDayID_EventID_mapping, EventDays, Events);

        EventDayManager.Ref_EventDay_VenueBooking(EventDayID_VenueBookingID_mapping, EventDays, VenueBookings);
        VenueBookingManager.Ref_VenueBooking_EventDay(EventDayID_VenueBookingID_mapping, EventDays, VenueBookings);

        TaskManager.Ref_Task_Event(TaskID_EventID_mapping, Tasks, Events);
        EventManager.Ref_Event_Task(TaskID_EventID_mapping, Tasks, Events);

        TaskManager.Ref_Task_VenueBooking(TaskID_VenueBookingID_mapping, Tasks, VenueBookings);
        VenueBookingManager.Ref_VenueBooking_Task(TaskID_VenueBookingID_mapping, Tasks, VenueBookings);

        VenueBookingManager.Ref_VenueBooking_Venue(VenueBookingID_VenueID_mapping, VenueBookings, Venues);
        VenueManager.Ref_Venue_VenueBooking(VenueBookingID_VenueID_mapping, VenueBookings, Venues);

        BudgetCategoryManager.Ref_BudgetCategory_Event(BudgetCategoryID_EventID_Uncat_mapping, BudgetCategories, Events);
        EventManager.Ref_Event_BudgetCategory_Uncat(BudgetCategoryID_EventID_Uncat_mapping, BudgetCategories, Events);

        BudgetCategoryManager.Ref_BudgetCategory_Event(BudgetCategoryID_EventID_mapping, BudgetCategories, Events);
        EventManager.Ref_Event_BudgetCategory(BudgetCategoryID_EventID_mapping, BudgetCategories, Events);

        ExpenseManager.Ref_Expense_BudgetCategory(ExpenseID_BudgetCategoryID_mapping, Expenses, BudgetCategories);
        BudgetCategoryManager.Ref_BudgetCategory_Expense(ExpenseID_BudgetCategoryID_mapping, Expenses, BudgetCategories);

        PersonnelListManager.Ref_PersonnelList_Personnel(PersonnelListID_PersonnelID_mapping, PersonnelLists, Personnels);
        PersonnelManager.Ref_Personnel_PersonnelList(PersonnelListID_PersonnelID_mapping, PersonnelLists, Personnels);

        EventManager.Ref_Event_PersonnelList_Participant(EventID_PersonnelListID_Participant_mapping, Events, PersonnelLists);
        EventManager.Ref_Event_PersonnelList_Helper(EventID_PersonnelListID_Helper_mapping, Events, PersonnelLists);
        EventManager.Ref_Event_PersonnelList_Sponsor(EventID_PersonnelListID_Sponsor_mapping, Events, PersonnelLists);
        EventManager.Ref_Event_PersonnelList_Vendor(EventID_PersonnelListID_Vendor_mapping, Events, PersonnelLists);

        // General Events from Archival

        ArchiveMap.Reset();
        Events_Archived = new ArrayList<>();
        Events_Unarchived = new ArrayList<>();

        while (ArchiveMap.PeekNextPair().First() == 0) // 0-Unarchived, 1-Archived
        {
            Events_Unarchived.add(Events.get(ArchiveMap.NextPair().Second()));
        }
        while (ArchiveMap.PeekNextPair().First() == 1) // 0-Unarchived, 1-Archived
        {
            Events_Archived.add(Events.get(ArchiveMap.NextPair().Second()));
        }
    }

    public static ArrayList<ItineraryItem> loadItineraryItems() {
        return ItineraryItems;
    }

    public static ArrayList<Itinerary> loadItinerarys() {
        return Itinerarys;
    }

    public static ArrayList<Event> loadArchivedEvents() {
        return Events_Archived;
    }

    public static ArrayList<Event> loadUnArchivedEvents() {
        return Events_Unarchived;
    }

    public static ArrayList<EventDay> loadEventDays() {
        return EventDays;
    }

    public static ArrayList<Task> loadTasks() {
        return Tasks;
    }

    public static ArrayList<Venue> loadVenues() {
        return Venues;
    }

    public static ArrayList<VenueBooking> loadVenueBookings() {
        return VenueBookings;
    }

    public static ArrayList<BudgetCategory> loadBudgetCategories() {
        return BudgetCategories;
    }

    public static ArrayList<Expense> loadExpenses() {
        return Expenses;
    }

    public static ArrayList<PersonnelList> loadPersonnelLists() {
        return PersonnelLists;
    }

    public static ArrayList<Personnel> loadPersonnels() {
        return Personnels;
    }

    // Save
    public static void saveItineraryItems(ArrayList<ItineraryItem> list) {
        ItineraryItems = list;
    }

    public static void saveItinerarys(ArrayList<Itinerary> list) {
        Itinerarys = list;
    }

    public static void saveArchivedEvents(ArrayList<Event> list) {
        Events_Archived = list;
    }

    public static void saveUnArchivedEvents(ArrayList<Event> list) {
        Events_Unarchived = list;
    }

    public static void saveEventDays(ArrayList<EventDay> list) {
        EventDays = list;
    }

    public static void saveTasks(ArrayList<Task> list) {
        Tasks = list;
    }

    public static void saveVenueBookings(ArrayList<VenueBooking> list) {
        VenueBookings = list;
    }

    public static void saveBudgetCategories(ArrayList<BudgetCategory> list) {
        BudgetCategories = list;
    }

    public static void saveExpenses(ArrayList<Expense> list) {
        Expenses = list;
    }

    public static void savePersonnelLists(ArrayList<PersonnelList> list) {
        PersonnelLists = list;
    }

    public static void savePersonnels(ArrayList<Personnel> list) {
        Personnels = list;
    }

    // store all attributes into files
    public static void Store() throws Exception {
        // for Archival & Generation of Events

        IDMap ArchiveMap = new IDMap();
        Events.clear();
        for (int i = 0; i < Events_Unarchived.size(); i++) {
            ArchiveMap.addMapping(0, i);
            Events.add(Events_Unarchived.get(i));
        }
        for (int i = 0; i < Events_Archived.size(); i++) {
            ArchiveMap.addMapping(1, Events.size());
            Events.add(Events_Archived.get(i));
        }
        Database.StoreFile_ArchiveMap(ArchiveMap);

        // actual save into XML, all lists must be saved before this can be used

        Database.StoreFile_Events(Events, PersonnelLists);
        Database.StoreFile_ItineraryItems(ItineraryItems);
        Database.StoreFile_Itinerarys(Itinerarys, ItineraryItems);
        Database.StoreFile_EventDays(EventDays, Itinerarys, Events, VenueBookings);
        Database.StoreFile_Tasks(Tasks, Events, VenueBookings);
        Database.StoreFile_VenueBookings(VenueBookings, Venues);
        Database.StoreFile_BudgetCategories(BudgetCategories, Events);
        Database.StoreFile_Expenses(Expenses, BudgetCategories);
        Database.StoreFile_Personnels(Personnels);
        Database.StoreFile_PersonnelLists(PersonnelLists, Personnels);
    }
}
