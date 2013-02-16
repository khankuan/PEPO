/* Venue manager handles all Venue non-public methods
 * Stores all Venue in: ArrayList<Venue> venues
 * 
 * Venue methods
 * + addVenueBooking(Venue venue, VenueBooking venuebooking)
 * + deleteVenueBooking(Venue veune, VenueBooking venuebooking)
 * 
 * Manager methods
 * + ArrayList<Venue> getVenueList()
 * 
 * Database methods
 * + Ref_Venue_VenueBooking(IDMap map, ArrayList<VenueBooking> VBlist, ArrayList<Venue> Vlist)
 * 
 */
package Logic.Venue;

import java.util.ArrayList;
import Database.*;
import java.util.TreeSet;

/*
 * @author kuan
 */
public class VenueManager {
    //  Storage

    private ArrayList<Venue> venues;
    private ArrayList<String> typeList;
    private ArrayList<String> facultyList;

    //  Constructor
    public VenueManager(ArrayList<Venue> arr) {
        venues = new ArrayList<>();
        TreeSet<String> typeset = new TreeSet<>();
        TreeSet<String> facultyset = new TreeSet<>();
        for (int i = 0; i < arr.size(); i++) {
            venues.add(arr.get(i));
            typeset.add(arr.get(i).getType());
            facultyset.add(arr.get(i).getFaculty());
        }
        typeList = new ArrayList<>(typeset);
        facultyList = new ArrayList<>(facultyset);
    }

    //  Venue methods
    public void addVenueBooking(Venue venue, VenueBooking venuebooking) throws Exception {
        venue.addVenueBooking(venuebooking);
    }

    public void deleteVenueBooking(Venue venue, VenueBooking venuebooking) throws Exception {
        venue.deleteVenueBooking(venuebooking);
    }

    //  Manager methods
    public ArrayList<String> getTypeList() {
        return new ArrayList<>(typeList);
    }

    public ArrayList<String> getFacultyList() {
        return new ArrayList<>(facultyList);
    }

    public ArrayList<Venue> getVenueList() {
        return new ArrayList<>(venues);
    }

    public ArrayList<Venue> getFilteredVenueList(String name, String type, String faculty, int minCapacity) {
        ArrayList<Venue> result = new ArrayList<>();
        for (int i = 0; i < venues.size(); i++) {
            Venue cur = venues.get(i);
            boolean valid = true;

            if (name != null) {
                if (!cur.getName().toLowerCase().contains(name.toLowerCase())) {
                    valid = false;
                }
            }
            if (type != null) {
                if (!cur.getType().toLowerCase().contains(type.toLowerCase())) {
                    valid = false;
                }
            }
            if (faculty != null) {
                if (!cur.getFaculty().toLowerCase().contains(faculty.toLowerCase())) {
                    valid = false;
                }
            }
            if (minCapacity <= cur.getCapacity() && valid) {
                result.add(cur);
            }
        }
        return result;
    }

    //  Set Object Reference for Venue and VenueBooking
    public static void Ref_Venue_VenueBooking(IDMap map, ArrayList<VenueBooking> VBlist, ArrayList<Venue> Vlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                try {
                    Vlist.get(mapping.Second()).addVenueBooking(VBlist.get(mapping.First()));
                } catch (Exception obj) {
                }
            }
        }
        map.Reset();
    }
}
