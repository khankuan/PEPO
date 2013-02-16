/* Venue class
 * 
 * Attributes
 * - String name;
 * - String type;
 * - String faculty;
 * - int capacity;
 * - ArrayList<VenueBooking> venueBookingList = new ArrayList<VenueBooking>();
 * 
 * Get methods
 * + String getName()
 * + String getType()
 * + String getFaculty()
 * + int getCapacity()
 * + ArrayList<VenueBooking> getVenueBookingList()
 * 
 * Set methods
 * ~ setName(String name)
 * ~ setType(String type)
 * ~ setFaculty(String faculty)
 * ~ setCapacity(int capacity)
 * ~ addVenueBooking(VenueBooking venuebooking)
 * ~ deleteVenueBooking(VenueBooking venue)
 * 
 */
package Logic.Venue;

import java.util.ArrayList;
import java.util.Date;

/*
 * @author kuan
 */
public class Venue {
    //  Attributes

    private String name;
    private String type;
    private String faculty;
    private int capacity;
    private ArrayList<VenueBooking> venueBookingList = new ArrayList<>();

    //  Constructor
    public Venue(String name, String type, String faculty, int capacity) throws Exception {
        if (name.length() == 0) {
            throw new Exception("Name is invalid");
        }
        if (capacity < 0) {
            throw new Exception("Capacity cannot be negative");
        }
        if (capacity > 99999999) {
            throw new Exception("Capacity is too large");
        }
        this.name = name;
        this.type = type;
        this.faculty = faculty;
        this.capacity = capacity;
    }

    //  Get methods
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<VenueBooking> getVenueBookingList() {
        return venueBookingList;
    }

    //  Set methods
    void setName(String name) throws Exception {
        if (name.length() == 0) {
            throw new Exception("Name is invalid");
        }
        this.name = name;
    }

    void setType(String type) {
        this.type = type;
    }

    void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    void setCapacity(int capacity) throws Exception {
        if (capacity < 0) {
            throw new Exception("Capacity cannot be negative");
        }
        if (capacity > 99999999) {
            throw new Exception("Capacity is too large");
        }
        this.capacity = capacity;
    }

    void addVenueBooking(VenueBooking venuebooking) throws Exception {
        boolean available = true;
        for (int i = 0; i < venueBookingList.size(); i++) {
            Date s = venueBookingList.get(i).getStartDate();
            Date e = venueBookingList.get(i).getEndDate();
            if (venuebooking.getEndDate().after(s) && venuebooking.getStartDate().before(e)
                    || venuebooking.getStartDate().before(s) && venuebooking.getEndDate().after(s)
                    || venuebooking.getStartDate().equals(s) && venuebooking.getEndDate().equals(e)) {
                available = false;
                break;
            }
        }
        if (!available) {
            throw new Exception("VenueBooking clash");
        }
        venueBookingList.add(venuebooking);
    }

    void deleteVenueBooking(VenueBooking venue) throws Exception {
        if (venueBookingList.remove(venue)) {
            return;
        } else {
            throw new Exception("VenueBooking not found");
        }
    }
}
