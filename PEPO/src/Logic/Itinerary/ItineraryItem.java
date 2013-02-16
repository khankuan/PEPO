/* ItineraryItem class, used by Itinerary
 * 
 * Attributes
 * - String name: Name of item
 * - Date date: Date of item
 * - Itinerary itinerary: Itinerary that the item belongs to
 * 
 * Get methods
 * + String getName()
 * + Date getDate()
 * + Date getEndDate()
 * + Itinerary getItinerary()
 * 
 * Set methods
 * ~ setName(String name)
 * ~ setDate(Date date)
 * ~ setEndDate(Date enddate)
 * ~ setItinerary(Itinerary itinerary)
 * 
 * Other methods
 * + compareTo(): compareTo function for sorting, by date
 * 
 */
package Logic.Itinerary;

import java.util.Date;

/*
 * @author kuan
 */
public class ItineraryItem implements Comparable {
    //  Attributes

    private String name;
    private Date date;
    private Date enddate;
    private Itinerary itinerary;

    //  Constructor
    public ItineraryItem(Itinerary itinerary, String name, Date startdate, Date enddate) throws Exception {
        if (name.length() == 0) {
            throw new Exception("Name is invalid");
        }
        if (name.length() > 200) {
            throw new Exception("Name too long");
        }
        if (startdate.after(enddate)) {
            throw new Exception("StartDate is after Enddate");
        }
        this.name = name;
        this.date = startdate;
        this.enddate = enddate;
        this.itinerary = itinerary;
    }

    //  Get methods
    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Date getEndDate() {
        return enddate;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    //  Set methods
    void setName(String name) throws Exception {
        this.name = name;
    }

    void setDate(Date date) throws Exception {
        this.date = date;
    }

    void setEndDate(Date enddate) throws Exception {
        this.enddate = enddate;
    }

    void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    //  Other methods
    @Override
    public int compareTo(Object o) {
        return this.date.compareTo(((ItineraryItem) o).date);
    }
}
