/* ItineraryItem manager handles all ItineraryItem non-public methods
 * Stores all ItineraryItem in: ArrayList<ItineraryItem> ItineraryItems
 * 
 * ItineraryItem methods
 * + setName(ItineraryItem itineraryitem, String name)
 * + setDate(ItineraryItem itineraryitem, Date date)
 * + setEndDate(ItineraryItem itineraryitem, Date enddate)
 * 
 * Manager methods
 * + addItineraryItem(ItineraryItem itineraryitem)
 * + deleteItineraryItem(ItineraryItem itineraryitem)
 * + ArrayList<ItineraryItem> getItineraryItemList()
 * 
 * Database methods
 * + Ref_Itinerary_ItineraryItem(IDMap map, ArrayList<Itinerary> PLlist, ArrayList<ItineraryItem> PLIlist)
 * 
 */
package Logic.Itinerary;

import java.util.ArrayList;
import java.util.Date;

import Database.IDMap;
import Database.Pair;

/*
 * @author kuan
 */
public class ItineraryItemManager {
    //  Storage

    private ArrayList<ItineraryItem> ItineraryItems;

    //  Constructor
    public ItineraryItemManager(ArrayList<ItineraryItem> arr) {
        ItineraryItems = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            ItineraryItems.add(arr.get(i));
        }
    }

    //  ItineraryItem methods
    public void setName(ItineraryItem itineraryitem, String name) throws Exception {
        itineraryitem.setName(name);
    }

    public void setDate(ItineraryItem itineraryitem, Date date) throws Exception {
        itineraryitem.setDate(date);
    }

    public void setEndDate(ItineraryItem itineraryitem, Date enddate) throws Exception {
        itineraryitem.setEndDate(enddate);
    }

    //  Manager methods
    public void addItineraryItem(ItineraryItem itineraryitem) {
        ItineraryItems.add(itineraryitem);
    }

    public void deleteItineraryItem(ItineraryItem itineraryitem) {
        ItineraryItems.remove(itineraryitem);
    }

    public ArrayList<ItineraryItem> getItineraryItemList() {
        return new ArrayList<>(ItineraryItems);
    }

    //  Set Object Reference for Itinerary and ItineraryItem
    public static void Ref_Itinerary_ItineraryItem(IDMap map, ArrayList<Itinerary> PLlist, ArrayList<ItineraryItem> PLIlist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                PLIlist.get(mapping.Second()).setItinerary(PLlist.get(mapping.First()));
            }
        }
        map.Reset();
    }
}
