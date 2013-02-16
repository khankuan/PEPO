/* MemoItem used in Memo
 * 
 * Attributes
 * # Date date: The (start) date of the MemoItem
 * # String description: Description of the MemoItem
 * # ArrayList<VenueBooking> venueBookingList: List of VenueBookings tagged to MemoItem
 * # Event event: Event that the MemoItem belongs to, can be null
 * 
 * Get methods
 * + Date getDate()
 * + String getDescription(): String
 * + ArrayList<VenueBooking> getTaggedVenueBookingList()
 * + Event getEvent()
 * 
 * Set methods
 * ~ setDate(Date date)
 * ~ setDescription(String description)
 * ~ setEvent(Event event)
 * ~ tagVenueBooking(VenueBooking venuebooking)
 * ~ untagVenueBooking(VenueBooking venuebooking)
 * ~ setVenueBookingList(ArrayList<VenueBooking> venueBookingList)
 * 
 * Other methods
 * + compareTo(Object o): used for sorting function, compared by date
 * 
 */
package Logic.Memo;

import Logic.Venue.VenueBooking;
import Logic.Event.Event;
import java.util.ArrayList;
import java.util.Date;

/*
 * @author kuan
 */
public class MemoItem implements Memoable {
    //  Attributes

    protected Date date;
    protected String description;
    protected ArrayList<VenueBooking> venueBookingList = new ArrayList<>();
    protected Event event;

    //  Constuctor
    public MemoItem() {
    }

    public MemoItem(Event event, String description, Date date) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Description is invalid");
        }
        if (description.length() > 200) {
            throw new Exception("Description too long");
        }
        if (date == null) {
            throw new Exception("Data is invalid");
        }
        this.event = event;
        this.date = date;
        this.description = description;
    }

    //  Get methods
    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<VenueBooking> getTaggedVenueBookingList() {
        return new ArrayList<>(venueBookingList);
    }

    public Event getEvent() {
        return event;
    }

    //  Set methods
    void setDate(Date date) throws Exception {
        if (date == null) {
            throw new Exception("Date is null");
        }
        this.date = date;
    }

    void setDescription(String description) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Description is invalid");
        }
        if (description.length() > 200) {
            throw new Exception("Description too long");
        }
        this.description = description;
    }

    void setEvent(Event event) {
        this.event = event;
    }

    VenueBooking tagVenueBooking(VenueBooking venuebooking) {
        if (!venueBookingList.contains(venuebooking)) {
            venueBookingList.add(venuebooking);
        }
        return venuebooking;
    }

    VenueBooking untagVenueBooking(VenueBooking venuebooking) throws Exception {
        if (venueBookingList.remove(venuebooking)) {
            return venuebooking;
        } else {
            throw new Exception("VenueBooking not found");
        }
    }

    public void setVenueBookingList(ArrayList<VenueBooking> venueBookingList) {
        this.venueBookingList = venueBookingList;
    }

    //  Other methods
    @Override
    public int compareTo(Memoable o) {
        return this.date.compareTo(o.getDate());
    }
}
