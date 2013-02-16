/* Itinerary manager handles all Itinerary non-public methods
 * Stores all Itinerary in: ArrayList<Itinerary> Itinerarys
 * 
 * Itinerary methods
 * + addItineraryItem(Itinerary itinerary, ItineraryItem itineraryitem)
 * + deleteItineraryItem(Itinerary itinerary, ItineraryItem itineraryitem)
 * 
 * Manager methods
 * + addItinerary(Itinerary itinerary)
 * + deleteItinerary(Itinerary itinerary)
 * + ArrayList<Itinerary> getItineraryList()
 * 
 * Database methods
 * + Ref_Itinerary_ItineraryItem(IDMap map, ArrayList<Itinerary> PLlist, ArrayList<ItineraryItem> PLIlist)
 * 
 */
package Logic.Itinerary;

import java.util.ArrayList;
import Database.IDMap;
import Database.Pair;

/*
 * @author kuan
 */
public class ItineraryManager {
    //  Storage

    private ArrayList<Itinerary> Itinerarys;

    //  constructor
    public ItineraryManager(ArrayList<Itinerary> arr) {
        Itinerarys = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            Itinerarys.add(arr.get(i));
        }
    }

    //  Itinerary methods
    public void addItineraryItem(Itinerary itinerary, ItineraryItem itineraryitem) {
        itinerary.addItineraryItem(itineraryitem);
    }

    public void deleteItineraryItem(Itinerary itinerary, ItineraryItem itineraryitem) throws Exception {
        itinerary.deleteItineraryItem(itineraryitem);
    }

    //  Manager methods
    public void addItinerary(Itinerary itinerary) {
        Itinerarys.add(itinerary);
    }

    public void deleteItinerary(Itinerary itinerary) {
        Itinerarys.remove(itinerary);
    }

    public ArrayList<Itinerary> getItineraryList() {
        return new ArrayList<>(Itinerarys);
    }

    //  Set Object Reference for Itinerary and ItineraryItem
    public static void Ref_Itinerary_ItineraryItem(IDMap map, ArrayList<Itinerary> PLlist, ArrayList<ItineraryItem> PLIlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                PLlist.get(mapping.First()).addItineraryItem(PLIlist.get(mapping.Second()));
            }
        }
        map.Reset();
    }
}
