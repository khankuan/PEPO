/* Task manager handles all Task non-public methods
 * Stores all Task in: ArrayList<Task> tasks
 * 
 * Task methods
 * + addAlert(Task task, Date date)
 * + deleteAlert(Task task)
 * + setEvent(Task task, Event event)
 * + tagVenueBooking(Task task, VenueBooking venuebooking)
 * + untagVenueBooking(MemoItem task, VenueBooking venuebooking)
 * + setPriority(Task task, int priority)
 * + setCompleted(Task task, boolean completed)
 * 
 * Manager methods
 * + addTask(Task task)
 * + deleteTask(Task task)
 * + ArrayList<Task> getTaskList()
 * 
 * Database methods
 * + Ref_Task_Event(IDMap map, ArrayList<Task> Tlist, ArrayList<Event> Elist)
 * + Ref_Task_VenueBooking(IDMap map, ArrayList<Task> Tlist, ArrayList<VenueBooking> VBlist)
 * 
 */
package Logic.Memo;

import Logic.Venue.VenueBooking;
import Database.IDMap;
import Database.Pair;
import Logic.Event.Event;
import java.util.ArrayList;
import java.util.Date;

/*
 * @author kuan
 */
public class TaskManager {
    //  Storage

    private ArrayList<Task> tasks;

    //  Constructor
    public TaskManager(ArrayList<Task> arr) {
        tasks = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            tasks.add(arr.get(i));
        }
    }

    //  Task methods
    public void addAlert(Task task, Date date) {
        task.setAlert(date);
    }

    public void deleteAlert(Task task) {
        task.deleteAlert();
    }

    public void setEvent(Task task, Event event) {
        task.setEvent(event);
    }

    public void tagVenueBooking(Task task, VenueBooking venuebooking) {
        task.tagVenueBooking(venuebooking);
    }

    public void untagVenueBooking(MemoItem task, VenueBooking venuebooking) throws Exception {
        task.untagVenueBooking(venuebooking);
    }

    public void setPriority(Task task, int priority) throws Exception {
        task.setPriority(priority);
    }

    public void setCompleted(Task task, boolean completed) {
        task.setCompleted(completed);
    }

    public void setDescription(Task task, String date) throws Exception {
        task.setDescription(date);
    }

    public void setDate(Task task, Date date) throws Exception {
        task.setDate(date);
    }

    //  Manager methods
    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public ArrayList<Task> getTaskList() {
        return tasks;
    }

    //  Set Object Reference for Task and Event
    public static void Ref_Task_Event(IDMap map, ArrayList<Task> Tlist, ArrayList<Event> Elist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Tlist.get(mapping.First()).setEvent(Elist.get(mapping.Second()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for Task and VenueBooking
    public static void Ref_Task_VenueBooking(IDMap map, ArrayList<Task> Tlist, ArrayList<VenueBooking> VBlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Tlist.get(mapping.First()).tagVenueBooking(VBlist.get(mapping.Second()));
            }
        }
        map.Reset();
    }
}
