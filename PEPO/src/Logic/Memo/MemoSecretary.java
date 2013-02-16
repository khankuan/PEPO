/*
 * MemoSecretary handles the processing of schedule and priority list in the Memo
 * 
 * Methods:
 * + getFilteredTaskByPriority(ArrayList<Task> taskArr, Date date, ArrayList<Event> events):
 *          -> Filters the list of Task by Event and Date
 *          -> Returns ArrayList<Task>
 *
 * ~ getFilteredScheduleByDate(ArrayList<Task> taskArr, ArrayList<EventDay> eventArr, ArrayList<VenueBooking> venuebookingArr, 
 * Date date, ArrayList<Event> events, boolean includeVenuebooking)
 *          -> Filter all EventDays, Task and VenueBooking by Date and Event
 *          -> Returns ArrayList<MemoItem>
 *
 * + getFilteredDayScheduleByDate(ArrayList<Task> taskArr, ArrayList<EventDay> eventArr, ArrayList<VenueBooking> venuebookingArr, 
 * Date date, ArrayList<Event> events, boolean includeVenuebooking)
 *          -> Map each valid MemoItem to a Day
 *          -> Returns ArrayList<Day>
 * 
 * Objects:
 * ~ Comparator priority_comparator for comparing priority of Task
 * 
 */
package Logic.Memo;

import Logic.Venue.VenueBooking;
import Logic.Event.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;

/*
 * @author kuan
 */
public class MemoSecretary {

    //  Comparator for priority, followed by date if same priority
    static Comparator priority_comparator = new Comparator() {

        @Override
        public int compare(Object o1, Object o2) {
            if (((Task) o1).getPriority() != ((Task) o2).getPriority()) {
                return ((Task) o1).getPriority() - ((Task) o2).getPriority();
            } else {
                return ((Task) o1).getDate().compareTo(((Task) o2).getDate());
            }
        }
    };

    //  Sort task by priority and filtered only to desired event list and date
    //  Sort function in the Collections library is used to sort tasks by date
    //  The result is an ArrayList of Task ordered by ascending date
    //  Completed tasks are not shown
    //
    public static ArrayList<Task> getFilteredTaskByPriority(ArrayList<Task> taskArr, Date date, ArrayList<Event> events) {
        ArrayList<Task> result = new ArrayList<>();

        for (int i = 0; i < taskArr.size(); i++) {
            Task cur = taskArr.get(i);
            if (!cur.date.before(date) && (cur.getEvent() == null || events.contains(cur.getEvent())) && !cur.isCompleted()) {
                result.add(cur);
            }
        }

        Collections.sort(result, priority_comparator);   //  sort with priority comparator
        return result;
    }

    //  Filters all EventDay, Task and VenueBooking by desired event list and date
    //  Returns a sorted ArrayList of MemoItems according to date
    //  VenueBookings are converted to EventDay
    //  
    static ArrayList<Memoable> getFilteredScheduleByDate(ArrayList<Task> taskArr,
            ArrayList<EventDay> eventArr, ArrayList<VenueBooking> venuebookingArr, Date date, ArrayList<Event> events, boolean includeVenuebooking) {

        ArrayList<Memoable> result = new ArrayList<>();

        for (int i = 0; i < taskArr.size(); i++) {  //  Add valid tasks
            Task cur = (Task) taskArr.get(i);
            if (cur.getDate() == null) {
                continue;
            }
            if (!(date.after(cur.getDate())) && (events.contains(cur.getEvent()) || cur.getEvent() == null)) {
                result.add(cur);
            }
        }
        for (int i = 0; i < eventArr.size(); i++) {  //  Add valid EventDays
            EventDay cur = (EventDay) eventArr.get(i);
            if (cur.getEndDate().after(date) && (events.contains(cur.getEvent()) || cur.getEvent() == null)) {
                result.add(cur);
            }

        }
        if (includeVenuebooking) {
            for (int i = 0; i < venuebookingArr.size(); i++) {  //  Add valid VenueBookings
                VenueBooking cur = venuebookingArr.get(i);

                if (cur.getEndDate().after(date)) {
                    result.add(cur);
                }
            }
        }

        Collections.sort(result);   //  Sort by date
        return result;
    }

    //  First, get the MemoItems filtered by date and Events from 
    //  getFilteredScheduleByDate() function.
    //  Next, for each MemoItem that is returned, hash them to a Day object
    //  A TreeMap is used to hash the Date to Day
    //  
    public static ArrayList<Day> getFilteredDayScheduleByDate(ArrayList<Task> taskArr, ArrayList<EventDay> eventArr, 
            ArrayList<VenueBooking> venuebookingArr, Date date, ArrayList<Event> events, boolean includeVenuebooking) {

        TreeMap<Date, Day> schedule = new TreeMap<>();
        ArrayList<Memoable> memoitems = getFilteredScheduleByDate(taskArr, eventArr, venuebookingArr, date, 
                events, includeVenuebooking);

        for (int i = 0; i < memoitems.size(); i++) {
            Memoable cur = memoitems.get(i);

            //  Round off to Date, time is removed, to hash to a Day
            GregorianCalendar curDate = new GregorianCalendar();
            curDate.setTime(cur.getDate());
            curDate.set(GregorianCalendar.MINUTE, 0);
            curDate.set(GregorianCalendar.SECOND, 0);
            curDate.set(GregorianCalendar.HOUR_OF_DAY, 0);
            curDate.set(GregorianCalendar.MILLISECOND, 0);

            //  For EventDay
            //  it can span across multiple Days
            //  For each Day included, the EventDay is added to that Day
            //
            if (cur instanceof EventDay) {
                Date ending = ((EventDay) cur).getEndDate();
                while (curDate.getTime().before(ending)) {
                    if (schedule.containsKey(curDate.getTime())) {
                        Day today = schedule.get(curDate.getTime());
                        today.addMemoItem(cur);
                    } else {
                        Day newday = new Day(curDate.getTime());
                        newday.addMemoItem(cur);
                        schedule.put(curDate.getTime(), newday);
                    }
                    curDate.add(GregorianCalendar.DATE, 1);
                }
            } //  For VenueBooking
            else if (cur instanceof VenueBooking) {
                Date ending = ((VenueBooking) cur).getEndDate();
                while (curDate.getTime().before(ending)) {
                    if (schedule.containsKey(curDate.getTime())) {
                        Day today = schedule.get(curDate.getTime());
                        today.addMemoItem(cur);
                    } else {
                        Day newday = new Day(curDate.getTime());
                        newday.addMemoItem(cur);
                        schedule.put(curDate.getTime(), newday);
                    }
                    curDate.add(GregorianCalendar.DATE, 1);
                }
            } //  For task, only 1 day involved
            else {
                if (schedule.containsKey(curDate.getTime())) {
                    Day today = schedule.get(curDate.getTime());
                    today.addMemoItem(cur);
                } else {
                    Day newday = new Day(curDate.getTime());
                    newday.addMemoItem(cur);
                    schedule.put(curDate.getTime(), newday);
                }
            }
        }
        ArrayList<Day> scheduleArr = new ArrayList<>(schedule.values());

        while (!scheduleArr.isEmpty() && scheduleArr.get(0).getDate().before(date)) {
            scheduleArr.remove(0);
        }

        for (int i = 0; i < scheduleArr.size(); i++) //  Sorts all items on the day
        {
            scheduleArr.get(i).sortItems();
        }

        return scheduleArr;
    }
}
