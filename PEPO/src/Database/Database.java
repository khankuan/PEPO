/* Database class
 * 
 * + IDMap nextIDMap()
 * 
 * + void StoreFile_ItineraryItems(ArrayList<ItineraryItem> PLIlist)
 * + ArrayList<ItineraryItem> RetrieveFile_ItineraryItems()
 * 
 * + void StoreFile_Itinerarys(ArrayList<Itinerary> PLlist, ArrayList<ItineraryItem> PLIlist)
 * + ArrayList<Itinerary> RetrieveFile_Itinerarys()
 * 
 * + void StoreFile_Events(ArrayList<Event> Elist, ArrayList<PersonnelList> PeLlist)
 * + ArrayList<Event> RetrieveFile_Events()
 * 
 * + void StoreFile_EventDays(ArrayList<EventDay> EDlist, ArrayList<Itinerary> PLlist, ArrayList<Event> Elist, ArrayList<VenueBooking> VBlist)
 * + ArrayList<EventDay> RetrieveFile_EventDays()
 * 
 * + void StoreFile_Tasks(ArrayList<Task> Tlist, ArrayList<Event> Elist, ArrayList<VenueBooking> VBlist)
 * + ArrayList<Task> RetrieveFile_Tasks()
 * 
 * + ArrayList<Venue> RetrieveFile_Venues()
 * 
 * + void StoreFile_VenueBookings(ArrayList<VenueBooking> VBlist, ArrayList<Venue> Vlist)
 * + ArrayList<VenueBooking> RetrieveFile_VenueBookings()
 * 
 * + void StoreFile_BudgetCategories(ArrayList<BudgetCategory> BClist, ArrayList<Event> Elist)
 * + ArrayList<BudgetCategory> RetrieveFile_BudgetCategories()
 * 
 * + void StoreFile_Expenses(ArrayList<Expense> Exlist, ArrayList<BudgetCategory> BClist)
 * + ArrayList<Expense> RetrieveFile_Expenses()
 * 
 * + void StoreFile_PersonnelLists(ArrayList<PersonnelList> PeLlist, ArrayList<Personnel> Pelist)
 * + ArrayList<PersonnelList> RetrieveFile_PersonnelLists()
 * 
 * + void StoreFile_Personnels(ArrayList<Personnel> Pelist)
 * + public ArrayList<Personnel> RetrieveFile_Personnels()
 * 
 * + public void StoreFile_ArchiveMap(IDMap ArchiveMap)
 * + IDMap RetrieveFile_ArchiveMap()
 * 
 */
package Database;

import java.util.*;
import Logic.Itinerary.*;
import Logic.Event.*;
import Logic.Memo.*;
import Logic.Venue.*;
import Logic.Budget.*;
import Logic.PersonnelList.*;

/*
 * @author hongwei
 */
public class Database {

    static Stack<IDMap> LoadedIDMap = new Stack<>();

    // retrieve the next ID Mapping
    static public IDMap nextIDMap() {
        return LoadedIDMap.pop();
    }

    // ItineraryItems ------------------------------------------------------------------------
    static public void StoreFile_ItineraryItems(ArrayList<ItineraryItem> PLIlist) throws Exception {
        ItineraryFileHandler.Store_ItineraryItems(PLIlist);
    }

    static public ArrayList<ItineraryItem> RetrieveFile_ItineraryItems() throws Exception {
        ArrayList<ItineraryItem> PLIlist = ItineraryFileHandler.Retrieve_ItineraryItems();
        return PLIlist;
    }

    // Itinerarys ------------------------------------------------------------------------
    static public void StoreFile_Itinerarys(ArrayList<Itinerary> PLlist,
            ArrayList<ItineraryItem> PLIlist) throws Exception {
        ItineraryFileHandler.Store_Itinerarys(PLlist, PLIlist);
    }

    static public ArrayList<Itinerary> RetrieveFile_Itinerarys() throws Exception {
        ArrayList<Itinerary> PLlist = ItineraryFileHandler.Retrieve_Itinerarys();
        LoadedIDMap = ItineraryFileHandler.LoadedIDMap;
        return PLlist;
    }

    // Events ------------------------------------------------------------------------
    static public void StoreFile_Events(ArrayList<Event> Elist,
            ArrayList<PersonnelList> PeLlist) throws Exception {
        EventFileHandler.Store_Events(Elist, PeLlist);
    }

    static public ArrayList<Event> RetrieveFile_Events() throws Exception {
        ArrayList<Event> Elist = EventFileHandler.Retrieve_Events();
        LoadedIDMap = EventFileHandler.LoadedIDMap;
        return Elist;
    }

    // EventDays ------------------------------------------------------------------------
    static public void StoreFile_EventDays(ArrayList<EventDay> EDlist,
            ArrayList<Itinerary> PLlist, ArrayList<Event> Elist,
            ArrayList<VenueBooking> VBlist) throws Exception {
        MemoFileHandler.Store_EventDays(EDlist, PLlist, Elist, VBlist);
    }

    static public ArrayList<EventDay> RetrieveFile_EventDays() throws Exception {
        ArrayList<EventDay> EDlist = MemoFileHandler.Retrieve_EventDays();
        LoadedIDMap = MemoFileHandler.LoadedIDMap;
        return EDlist;
    }

    // Tasks ------------------------------------------------------------------------
    static public void StoreFile_Tasks(ArrayList<Task> Tlist,
            ArrayList<Event> Elist, ArrayList<VenueBooking> VBlist) throws Exception {
        MemoFileHandler.Store_Tasks(Tlist, Elist, VBlist);
    }

    static public ArrayList<Task> RetrieveFile_Tasks() throws Exception {
        ArrayList<Task> Tlist = MemoFileHandler.Retrieve_Tasks();
        LoadedIDMap = MemoFileHandler.LoadedIDMap;
        return Tlist;
    }

    // Venues ------------------------------------------------------------------------
    static public ArrayList<Venue> RetrieveFile_Venues() throws Exception {
        ArrayList<Venue> Vlist = VenueFileHandler.Retrieve_Venues();
        return Vlist;
    }

    // VenueBookings ------------------------------------------------------------------------
    static public void StoreFile_VenueBookings(ArrayList<VenueBooking> VBlist,
            ArrayList<Venue> Vlist) throws Exception {
        MemoFileHandler.Store_VenueBookings(VBlist, Vlist);
    }

    static public ArrayList<VenueBooking> RetrieveFile_VenueBookings() throws Exception {
        ArrayList<VenueBooking> VBlist = MemoFileHandler.Retrieve_VenueBookings();
        LoadedIDMap = MemoFileHandler.LoadedIDMap;
        return VBlist;
    }

    // BudgetCategories ------------------------------------------------------------------------
    static public void StoreFile_BudgetCategories(ArrayList<BudgetCategory> BClist,
            ArrayList<Event> Elist) throws Exception {
        BudgetFileHandler.Store_BudgetCategories(BClist, Elist);
    }

    static public ArrayList<BudgetCategory> RetrieveFile_BudgetCategories() throws Exception {
        ArrayList<BudgetCategory> BClist = BudgetFileHandler.Retrieve_BudgetCategories();
        LoadedIDMap = BudgetFileHandler.LoadedIDMap;
        return BClist;
    }

    // Expenses ------------------------------------------------------------------------
    static public void StoreFile_Expenses(ArrayList<Expense> Exlist,
            ArrayList<BudgetCategory> BClist) throws Exception {
        BudgetFileHandler.Store_Expenses(Exlist, BClist);
    }

    static public ArrayList<Expense> RetrieveFile_Expenses() throws Exception {
        ArrayList<Expense> Exlist = BudgetFileHandler.Retrieve_Expenses();
        LoadedIDMap = BudgetFileHandler.LoadedIDMap;
        return Exlist;
    }

    // PersonnelLists ------------------------------------------------------------------------
    static public void StoreFile_PersonnelLists(ArrayList<PersonnelList> PeLlist,
            ArrayList<Personnel> Pelist) throws Exception {
        PersonnelFileHandler.Store_PersonnelLists(PeLlist, Pelist);
    }

    static public ArrayList<PersonnelList> RetrieveFile_PersonnelLists() throws Exception {
        ArrayList<PersonnelList> PeLlist = PersonnelFileHandler.Retrieve_PersonnelLists();
        LoadedIDMap = PersonnelFileHandler.LoadedIDMap;
        return PeLlist;
    }

    // Personnels ------------------------------------------------------------------------
    static public void StoreFile_Personnels(ArrayList<Personnel> Pelist) throws Exception {
        PersonnelFileHandler.Store_Personnels(Pelist);
    }

    static public ArrayList<Personnel> RetrieveFile_Personnels() throws Exception {
        ArrayList<Personnel> Pelist = PersonnelFileHandler.Retrieve_Personnels();
        return Pelist;
    }

    // ArchiveMap -----------------------------------------------------------------------
    static public void StoreFile_ArchiveMap(IDMap ArchiveMap) throws Exception {
        EventFileHandler.Store_ArchiveMap(ArchiveMap);
    }

    static public IDMap RetrieveFile_ArchiveMap() throws Exception {
        IDMap ArchiveMap = EventFileHandler.Retrieve_ArchiveMap();
        return ArchiveMap;
    }
}
