/* EventDay manager handles all EventDay non-public methods
 * Stores all EventDay in: ArrayList<EventDay> eventDays
 * 
 * EventDay methods
 * + setNotes(EventDay eventday, String notes)
 * + setDescription(EventDay eventday, String description)
 * + setDate(EventDay eventday, Date startdate, Date enddate)
 * + tagVenueBooking(EventDay eventday, VenueBooking venuebooking)
 * + untagVenueBooking(MemoItem eventday, VenueBooking venuebooking)
 * 
 * Manager methods
 * + addEventDay(EventDay eventday)
 * + deleteEventDay(EventDay eventday)
 * + getEventDayList(): Returns ArrayList<EventDay>
 * 
 * Database methods
 * + Ref_EventDay_Itinerary(IDMap map, ArrayList<EventDay> EDlist, ArrayList<Itinerary> PLlist)
 * + Ref_EventDay_Event(IDMap map, ArrayList<EventDay> EDlist, ArrayList<Event> Elist)
 * + Ref_EventDay_VenueBooking(IDMap map, ArrayList<EventDay> EDlist, ArrayList<VenueBooking> VBlist)
 * 
 */
package Logic.Memo;

import Logic.Venue.VenueBooking;
import java.util.ArrayList;
import java.util.Date;

import Database.IDMap;
import Database.Pair;
import Logic.Event.Event;
import Logic.Itinerary.Itinerary;

/*
 * @author kuan
 */
public class EventDayManager {
    //  Storage

    private ArrayList<EventDay> eventDays;

    //  Constructor
    public EventDayManager(ArrayList<EventDay> arr) {
        eventDays = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            eventDays.add(arr.get(i));
        }
    }

    //  EventDay methods
    public void setNotes(EventDay eventday, String notes) {
        eventday.setNotes(notes);
    }

    public void setDescription(EventDay eventday, String description) throws Exception {
        eventday.setDescription(description);
    }

    public void setDate(EventDay eventday, Date startdate, Date enddate) throws Exception {
        eventday.setDate(startdate,enddate);
    }

    public void tagVenueBooking(EventDay eventday, VenueBooking venuebooking) {
        eventday.tagVenueBooking(venuebooking);
    }

    public void untagVenueBooking(MemoItem eventday, VenueBooking venuebooking) throws Exception {
        eventday.untagVenueBooking(venuebooking);
    }

    //  Manager methods
    public void addEventDay(EventDay eventday) {
        eventDays.add(eventday);
    }

    public void deleteEventDay(EventDay eventday) {
        eventDays.remove(eventday);
    }

    public ArrayList<EventDay> getEventDayList() {
        return eventDays;
    }

    //  Set Object Reference for EventDay and Itinerary
    public static void Ref_EventDay_Itinerary(IDMap map, ArrayList<EventDay> EDlist, ArrayList<Itinerary> PLlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                EDlist.get(mapping.First()).setItinerary(PLlist.get(mapping.Second()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for EventDay and Event
    public static void Ref_EventDay_Event(IDMap map, ArrayList<EventDay> EDlist, ArrayList<Event> Elist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                EDlist.get(mapping.First()).setEvent(Elist.get(mapping.Second()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for EventDay and VenueBooking
    public static void Ref_EventDay_VenueBooking(IDMap map, ArrayList<EventDay> EDlist, ArrayList<VenueBooking> VBlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                EDlist.get(mapping.First()).tagVenueBooking(VBlist.get(mapping.Second()));
            }
        }
        map.Reset();
    }
}
