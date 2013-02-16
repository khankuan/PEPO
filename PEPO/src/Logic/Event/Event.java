/* Event class
 * 
 * Attributes
 * - String title: Title of the Event
 * - String description: Description of the Event
 * - PersonnelList participants: Participant List of the Event
 * - PersonnelList helpers: Helper List of the Event
 * - PersonnelList sponsors: Sponsor List of the Event
 * - PersonnelList vendors: Vendor List of the Event
 * - long totalBudget: Total Budget of the Event
 * - ArrayList<BudgetCategory> budgetCategoryList: BudgetCategory List of the Event
 * - BudgetCategory uncategorized: Uncategorized budget of the Event
 * - ArrayList<Task> taskList: Task list of the Event
 * - ArrayList<EventDay> eventDayList: EventDay list of the Event
 * 
 * General methods
 * + String getTitle()
 * + String getDescription()
 * ~ setTitle(String title)
 * ~ setDescription(String description)
 * 
 * Budget methods
 * + long getTotalBudget()
 * + long getUnallocatedBudget()
 * + long getRemainingBudget()
 * + ArrayList<BudgetCategory> getBudgetCategoryList()
 * + BudgetCategory getUncategorizedBudget()
 * ~ setTotalBudget(long totalbudget)
 * ~ setBudgetCategoryList(ArrayList<BudgetCategory> budgetCategoryList)
 * ~ addBudgetCategory(BudgetCategory budgetcategory)
 * ~ deleteBudgetCategory(BudgetCategory budgetcategory)
 * ~ setUncategorizedBudget(BudgetCategory budgetcategory)
 * 
 * Task methods
 * + ArrayList<Task> getTaskList()
 * ~ setTaskList(ArrayList<Task> taskList)
 * ~ addTask(Task task)
 * ~ deleteTask(Task task)
 * 
 * EventDay methods
 * + ArrayList<EventDay> getEventDayList()
 * ~ setEventDayList(ArrayList<EventDay> eventDayList)
 * ~ addEventDay(EventDay eventday)
 * ~ deleteEventDay(EventDay eventday)
 * 
 * Participant methods
 * + PersonnelList getParticipantList()
 * ~ setParticipantList(PersonnelList participants)
 * ~ deleteParticipantList()
 * 
 * Helper methods
 * + PersonnelList getHelperList()
 * ~ setHelperList(PersonnelList helpers)
 * ~ deleteHelperList()
 * 
 * Sponsor methods
 * + PersonnelList getSponsorList()
 * ~ setHelperList(PersonnelList sponsors)
 * ~ deleteSponsorList()
 * 
 * Vendor methods
 * + PersonnelList getVendorList()
 * ~ setVendorList(PersonnelList vendors)
 * ~ deleteVendorList()
 */
package Logic.Event;

import Logic.Budget.BudgetCategory;
import Logic.Memo.EventDay;
import Logic.Memo.Task;
import Logic.PersonnelList.PersonnelList;
import java.util.ArrayList;
import java.util.Collections;

/*
 * @author kuan
 */
public class Event {
    //  Attributes

    private String title;
    private String description;
    private PersonnelList participants;
    private PersonnelList helpers;
    private PersonnelList sponsors;
    private PersonnelList vendors;
    private long totalBudget;
    private ArrayList<BudgetCategory> budgetCategoryList;
    private BudgetCategory uncategorized;
    private ArrayList<Task> taskList;
    private ArrayList<EventDay> eventDayList;

    //  Constructor
    public Event(String title, String description, long totalbudget) throws Exception {
        if (title.length() == 0) {
            throw new Exception("Title is invalid");
        }
        if (title.length() > 200) {
            throw new Exception("Title too long");
        }
        if (totalbudget < 0) {
            throw new Exception("Budget cannot be negative");
        }
        if (totalbudget > 999999900) {
            throw new Exception("Budget too large");
        }
        this.title = title;
        this.description = description;
        this.totalBudget = totalbudget;
        uncategorized = new BudgetCategory(this, "Uncategorized", 0);
        budgetCategoryList = new ArrayList<>();
        taskList = new ArrayList<>();
        eventDayList = new ArrayList<>();
        participants = new PersonnelList();
        helpers = new PersonnelList();
        sponsors = new PersonnelList();
        vendors = new PersonnelList();
    }

    //  General
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    void setTitle(String title) throws Exception {
        if (title.length() == 0) {
            throw new Exception("Title is invalid");
        }
        this.title = title;
    }

    void setDescription(String description) {
        this.description = description;
    }

    //  Budget
    public long getTotalBudget() {
        return totalBudget;
    }

    public long getUnallocatedBudget() {
        long remain = totalBudget;
        for (int i = 0; i < budgetCategoryList.size(); i++) {
            remain -= budgetCategoryList.get(i).getAmount();
        }

        return remain;
    }

    public long getRemainingBudget() {
        return totalBudget - getTotalExpense();
    }

    public long getTotalExpense() {
        long total = 0;
        for (int i = 0; i < budgetCategoryList.size(); i++) {
            total += budgetCategoryList.get(i).getAmount();
        }
        for (int i = 0; i < budgetCategoryList.size(); i++) {
            total -= budgetCategoryList.get(i).getRemainingAmount();
        }
        total += -uncategorized.getRemainingAmount();

        return total;
    }

    public ArrayList<BudgetCategory> getBudgetCategoryList() {
        return budgetCategoryList;
    }

    public BudgetCategory getUncategorizedBudget() {
        return uncategorized;
    }

    void setUncategorizedBudget(BudgetCategory budgetcategory) {
        uncategorized = budgetcategory;
    }

    void setTotalBudget(long totalbudget) throws Exception {
        if (totalbudget < 0) {
            throw new Exception("Budget cannot be negative");
        }
        if (totalbudget > 999999900) {
            throw new Exception("Budget too large");
        }
        this.totalBudget = totalbudget;
    }

    void setBudgetCategoryList(ArrayList<BudgetCategory> budgetCategoryList) {
        this.budgetCategoryList = budgetCategoryList;
    }

    void addBudgetCategory(BudgetCategory budgetcategory) throws Exception {
        for (int i = 0; i < budgetCategoryList.size(); i++)
            if (budgetCategoryList.get(i).getName().equals(budgetcategory.getName()))
                throw new Exception("Category already exist");
        budgetCategoryList.add(budgetcategory);
        Collections.sort(budgetCategoryList);
    }

    void deleteBudgetCategory(BudgetCategory budgetcategory) throws Exception {
        if (budgetCategoryList.remove(budgetcategory)) {
            return;
        } else {
            throw new Exception("BudgetCategory not found");
        }
    }

    //  Task
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList);
    }

    void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    void addTask(Task task) {
        taskList.add(task);
    }

    void deleteTask(Task task) throws Exception {
        if (taskList.remove(task)) {
            return;
        } else {
            throw new Exception("Task not found");
        }
    }

    //  EventDay
    public ArrayList<EventDay> getEventDayList() {
        ArrayList toReturn = new ArrayList<>(eventDayList);
        Collections.sort(toReturn);
        return toReturn;
    }

    void setEventDayList(ArrayList<EventDay> eventDayList) {
        this.eventDayList = eventDayList;
    }

    void addEventDay(EventDay eventday) {
        eventDayList.add(eventday);
    }

    void deleteEventDay(EventDay eventday) throws Exception {
        if (eventDayList.remove(eventday)) {
            return;
        } else {
            throw new Exception("EventDay not found");
        }
    }

    //  Participants
    public PersonnelList getParticipantList() {
        return participants;
    }

    void setParticipantList(PersonnelList participants) {
        this.participants = participants;
    }

    void deleteParticipantList() {
        participants = null;
    }

    //  Helpers
    public PersonnelList getHelperList() {
        return helpers;
    }

    void setHelperList(PersonnelList helpers) {
        this.helpers = helpers;
    }

    void deleteHelperList() {
        helpers = null;
    }

    //  Sponsors
    public PersonnelList getSponsorList() {
        return sponsors;
    }

    void setSponsorList(PersonnelList sponsors) {
        this.sponsors = sponsors;
    }

    void deleteSponsorList() {
        sponsors = null;
    }

    //  Vendors
    public PersonnelList getVendorList() {
        return vendors;
    }

    void setVendorList(PersonnelList vendors) {
        this.vendors = vendors;
    }

    void deleteVendorList() {
        vendors = null;
    }
}