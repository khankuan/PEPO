/* VenueBooking manager handles all VenueBooking non-public methods
 * Stores all VenueBooking in: ArrayList<VenueBooking> venueBookings
 * 
 * VenueBooking methods
 * + tagVenueBooking(VenueBooking venue, MemoItem memoitem)
 * + untagVenueBooking(VenueBooking venue, MemoItem memoitem)
 * 
 * Manager methods
 * + addVenueBooking(VenueBooking venuebooking)
 * + deleteVenueBooking(VenueBooking venuebooking)
 * + ArrayList<VenueBooking> getVenueBookingList()
 * 
 * Database methods
 * + Ref_VenueBooking_Venue(IDMap map, ArrayList<VenueBooking> VBlist, ArrayList<Venue> Vlist)
 * + Ref_VenueBooking_EventDay(IDMap map, ArrayList<EventDay> EDlist, ArrayList<VenueBooking> VBlist)
 * + Ref_VenueBooking_Task(IDMap map, ArrayList<Task> Tlist, ArrayList<VenueBooking> VBlist)
 * 
 */
package Logic.Venue;

import java.util.ArrayList;
import Database.IDMap;
import Database.Pair;
import Logic.Memo.EventDay;
import Logic.Memo.MemoItem;
import Logic.Memo.Task;
import java.util.Date;

/*
 * @author kuan
 */
public class VenueBookingManager {
    //  Storage

    private ArrayList<VenueBooking> venueBookings;

    //  Constructor
    public VenueBookingManager(ArrayList<VenueBooking> arr) {
        venueBookings = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            venueBookings.add(arr.get(i));
        }
    }

    //  Task methods
    public void tagVenueBooking(VenueBooking venuebooking, MemoItem memoitem) {
        venuebooking.tagMemoItem(memoitem);
    }

    public void untagVenueBooking(VenueBooking venuebooking, MemoItem memoitem) throws Exception {
        venuebooking.untagMemoItem(memoitem);
    }

    //  Manager methods 
    public void addVenueBooking(VenueBooking venuebooking) {
        venueBookings.add(venuebooking);
    }

    public void deleteVenueBooking(VenueBooking venuebooking) throws Exception {
        if (venueBookings.remove(venuebooking)) {
            return;
        } else {
            throw new Exception("VenueBooking not found");
        }
    }

    public ArrayList<VenueBooking> getVenueBookingList() {
        return new ArrayList<>(venueBookings);
    }

    public ArrayList<VenueBooking> getFutureVenueBookingList(Date date) {
        ArrayList<VenueBooking> results = new ArrayList<>();
        for (int i = 0; i < venueBookings.size(); i++) {
            if (venueBookings.get(i).getEndDate().after(date)) {
                results.add(venueBookings.get(i));
            }
        }
        return results;
    }

    //  Set Object Reference for VenueBooking and Venue
    public static void Ref_VenueBooking_Venue(IDMap map, ArrayList<VenueBooking> VBlist, ArrayList<Venue> Vlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                VBlist.get(mapping.First()).setVenue(Vlist.get(mapping.Second()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for VenueBooking and EventDay
    public static void Ref_VenueBooking_EventDay(IDMap map, ArrayList<EventDay> EDlist, ArrayList<VenueBooking> VBlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                try {
                    VBlist.get(mapping.Second()).tagMemoItem(EDlist.get(mapping.First()));
                } catch (Exception obj) {
                }
            }
        }
        map.Reset();
    }

    //  Set Object Reference for VenueBooking and Task
    public static void Ref_VenueBooking_Task(IDMap map, ArrayList<Task> Tlist, ArrayList<VenueBooking> VBlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                try {
                    VBlist.get(mapping.Second()).tagMemoItem(Tlist.get(mapping.First()));
                } catch (Exception obj) {
                }
            }
        }
        map.Reset();
    }
}
