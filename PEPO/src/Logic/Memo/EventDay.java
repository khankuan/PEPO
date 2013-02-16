/* EventDay class extends MemoItem, used by Event class
 * 
 * Attributes:
 * - String notes: Notes or comments for an EventDay
 * - Date enddate: Ending date and time of an EventDay
 * - Itinerary Itinerary: Itinerary of the EventDay
 * 
 * Get methods:
 * + String getNotes()
 * + Date getEndDate()
 * + Itinerary getItinerary()
 * 
 * Set methods:
 * ~ setNotes(String notes)
 * ~ setItinerary(Itinerary Itinerary)
 * ~ setDate(Date startdate, Date enddate)
 * 
 */
package Logic.Memo;

import Logic.Event.Event;
import Logic.Itinerary.Itinerary;
import java.util.Date;

/*
 * @author kuan
 */
public class EventDay extends MemoItem {
    //  Attributes

    private String notes;
    private Date enddate;
    private Itinerary Itinerary;

    //  Constructor
    public EventDay() {
    }

    public EventDay(Event event, String description, String notes, Date startdate, Date enddate) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Description is invalid");
        }
        if (description.length() > 200) {
            throw new Exception("Description too long");
        }
        if (startdate.after(enddate)) {
            throw new Exception("StartDate is after Enddate");
        }
        this.description = description;
        this.notes = notes;
        this.date = startdate;
        this.enddate = enddate;
        this.event = event;
        this.Itinerary = new Itinerary();
    }

    public EventDay(Event event, String description, String notes, Date startdate, Date enddate, Itinerary itinerary) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Description is invalid");
        }
        if (description.length() > 200) {
            throw new Exception("Description too long");
        }
        if (startdate.after(enddate)) {
            throw new Exception("StartDate is after Enddate");
        }
        this.description = description;
        this.notes = notes;
        this.date = startdate;
        this.enddate = enddate;
        this.event = event;
        this.Itinerary = itinerary;
    }

    //  Get methods
    public String getNotes() {
        return notes;
    }

    public Date getEndDate() {
        return enddate;
    }

    public Itinerary getItinerary() {
        return Itinerary;
    }

    //  Set methods
    void setNotes(String notes) {
        this.notes = notes;
    }

    void setDate(Date startdate, Date enddate) throws Exception {
        if (startdate.after(enddate)) {
            throw new Exception("StartDate is after Enddate");
        }
        this.date = startdate;
        this.enddate = enddate;    }

    void setItinerary(Itinerary Itinerary) {
        this.Itinerary = Itinerary;
    }
}
