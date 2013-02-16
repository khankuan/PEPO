/* Event manager handles all Event non-public methods
 * Stores all Event in: ArrayList<Event> events
 * Stores all archived Event in: ArrayList<Event> archivedEvents
 * 
 * Event methods
 * + setTitle(Event event, String title)
 * + setDescription(Event event, String description)
 * + setTotalBudget(Event event, long totalbudget)
 * + addBudgetCategory(Event event, BudgetCategory budgetcategory)
 * + deleteBudgetCategory(Event event, BudgetCategory budgetcategory)
 * + addTask(Event event, Task task}
 * + deleteTask(Event event, Task task)
 * + addEventDay(Event event, EventDay eventday)
 * + deleteEventDay(Event event, EventDay eventday)
 * + setParticipantList(Event event, PersonnelList participantlist)
 * + setHelperList(Event event, PersonnelList helperlist)
 * + setSponsorList(Event event, PersonnelList sponsorlist)
 * + setVendorList(Event event, PersonnelList vendorlist)
 * + deleteParticipantList(Event event)
 * + deleteHelperList(Event event)
 * + deleteSponsorList(Event event)
 * + deleteVendorList(Event event)
 * + archiveEvent(Event event)
 * + restoreEvent(Event event)
 * 
 * Manager methods
 * + addEvent(Event event)
 * + deleteEvent(Event event)
 * + ArrayList<Event> getEventList() 
 * + ArrayList<Event> getArchivedEventList()
 * 
 * Database methods
 * + Ref_Event_EventDay(IDMap map, ArrayList<EventDay> EDlist, ArrayList<Event> Elist)
 * + Ref_Event_Task(IDMap map, ArrayList<Task> Tlist, ArrayList<Event> Elist)
 * + Ref_Event_BudgetCategory_Uncat(IDMap map, ArrayList<BudgetCategory> BClist_Uncat, ArrayList<Event> Elist)
 * + Ref_Event_BudgetCategory(IDMap map, ArrayList<BudgetCategory> BClist, ArrayList<Event> Elist)
 * + Ref_Event_PersonnelList_Participant(IDMap map, ArrayList<Event> Elist, ArrayList<PersonnelList> PeLlist)
 * + Ref_Event_PersonnelList_Helper(IDMap map, ArrayList<Event> Elist, ArrayList<PersonnelList> PeLlist)
 * 
 */
package Logic.Event;

import Database.IDMap;
import Database.Pair;
import Logic.Budget.BudgetCategory;
import Logic.Memo.EventDay;
import Logic.PersonnelList.PersonnelList;
import Logic.Memo.Task;
import java.util.ArrayList;

/*
 * @author kuan
 */
public class EventManager {
    //  Storage

    private ArrayList<Event> events;
    private ArrayList<Event> archivedEvents;

    //  Constructor
    public EventManager(ArrayList<Event> unarchived, ArrayList<Event> archived) {
        events = new ArrayList<>();
        for (int i = 0; i < unarchived.size(); i++) {
            events.add(unarchived.get(i));
        }

        archivedEvents = new ArrayList<>();
        for (int i = 0; i < archived.size(); i++) {
            archivedEvents.add(archived.get(i));
        }
    }

    //  Event methods
    public void setTitle(Event event, String title) throws Exception {
        event.setTitle(title);
    }

    public void setDescription(Event event, String description) {
        event.setDescription(description);
    }

    public void setTotalBudget(Event event, long totalbudget) throws Exception {
        event.setTotalBudget(totalbudget);
    }

    public void addBudgetCategory(Event event, BudgetCategory budgetcategory) throws Exception {
        event.addBudgetCategory(budgetcategory);
    }

    public void deleteBudgetCategory(Event event, BudgetCategory budgetcategory) throws Exception {
        event.deleteBudgetCategory(budgetcategory);
    }

    public void addTask(Event event, Task task) {
        event.addTask(task);
    }

    public void deleteTask(Event e, Task task) throws Exception {
        if (task.getEvent() != null) {
            e.deleteTask(task);
        }
    }

    public void addEventDay(Event event, EventDay eventday) {
        event.addEventDay(eventday);
    }

    public void deleteEventDay(Event event, EventDay eventday) throws Exception {
        event.deleteEventDay(eventday);
    }

    public void setParticipantList(Event event, PersonnelList participantlist) {
        event.setParticipantList(participantlist);
    }

    public void setHelperList(Event event, PersonnelList helperlist) {
        event.setHelperList(helperlist);
    }

    public void setSponsorList(Event event, PersonnelList sponsorlist) {
        event.setSponsorList(sponsorlist);
    }

    public void setVendorList(Event event, PersonnelList vendorlist) {
        event.setVendorList(vendorlist);
    }

    public void deleteParticipantList(Event event) {
        event.deleteParticipantList();
    }

    public void deleteHelperList(Event event) {
        event.deleteHelperList();
    }

    public void deleteSponsorList(Event event) {
        event.deleteSponsorList();
    }

    public void deleteVendorList(Event event) {
        event.deleteVendorList();
    }

    public void archiveEvent(Event event) {
        archivedEvents.add(event);
        events.remove(event);
    }

    public void restoreEvent(Event event) {
        archivedEvents.remove(event);
        events.add(event);
    }

    //  Manager methods
    public void addEvent(Event event) {
        events.add(event);
    }

    public void deleteEvent(Event event) {
        events.remove(event);
    }

    public ArrayList<Event> getEventList() {
        return new ArrayList<>(events);
    }

    public ArrayList<Event> getArchivedEventList() {
        return new ArrayList<>(archivedEvents);
    }

    //  Set Object Reference for Event and EventDay
    public static void Ref_Event_EventDay(IDMap map, ArrayList<EventDay> EDlist, ArrayList<Event> Elist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Elist.get(mapping.Second()).addEventDay(EDlist.get(mapping.First()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for Event and Task
    public static void Ref_Event_Task(IDMap map, ArrayList<Task> Tlist, ArrayList<Event> Elist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Elist.get(mapping.Second()).addTask(Tlist.get(mapping.First()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for Event and BudgetCategory
    public static void Ref_Event_BudgetCategory(IDMap map, ArrayList<BudgetCategory> BClist, ArrayList<Event> Elist) throws Exception {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Elist.get(mapping.Second()).addBudgetCategory(BClist.get(mapping.First()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for Event and uncategoriesd BudgetCategory
    public static void Ref_Event_BudgetCategory_Uncat(IDMap map, ArrayList<BudgetCategory> BClist_Uncat, ArrayList<Event> Elist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Elist.get(mapping.Second()).setUncategorizedBudget(BClist_Uncat.get(mapping.First()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for Personnel and PersonnelList (Participant)
    public static void Ref_Event_PersonnelList_Participant(IDMap map, ArrayList<Event> Elist, ArrayList<PersonnelList> PeLlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Elist.get(mapping.First()).setParticipantList(PeLlist.get(mapping.Second()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for Personnel and PersonnelList (Helper)
    public static void Ref_Event_PersonnelList_Helper(IDMap map, ArrayList<Event> Elist, ArrayList<PersonnelList> PeLlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Elist.get(mapping.First()).setHelperList(PeLlist.get(mapping.Second()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for Personnel and PersonnelList (Sponsor)
    public static void Ref_Event_PersonnelList_Sponsor(IDMap map, ArrayList<Event> Elist, ArrayList<PersonnelList> PeLlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Elist.get(mapping.First()).setSponsorList(PeLlist.get(mapping.Second()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for Personnel and PersonnelList (Vendor)
    public static void Ref_Event_PersonnelList_Vendor(IDMap map, ArrayList<Event> Elist, ArrayList<PersonnelList> PeLlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Elist.get(mapping.First()).setVendorList(PeLlist.get(mapping.Second()));
            }
        }
        map.Reset();
    }
}
