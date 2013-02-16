/* Itinerary class, used by Eventdays
 * 
 * Attributes
 * - TreeSet<ItineraryItem> ItineraryItemList: Sorted list of ItineraryItem by time
 * 
 * Get methods
 * + ArrayList<ItineraryItem> getSortedItineraryItemList()
 * 
 * Set methods
 * ~ addItineraryItem(ItineraryItem itineraryitem)
 * ~ deleteItineraryItem(ItineraryItem itineraryitem)
 * ~ setItineraryItemList(ArrayList<ItineraryItem> ItineraryItemListInfo)
 * 
 */
package Logic.Itinerary;

import java.util.ArrayList;
import java.util.Collections;

/*
 * @author kuan
 */
public class Itinerary {
    //  Atributes

    private ArrayList<ItineraryItem> ItineraryItemList;

    //  Constructor
    public Itinerary() {
        ItineraryItemList = new ArrayList<>();
    }

    //  Get methods
    public ArrayList<ItineraryItem> getSortedItineraryItemList() {
        return new ArrayList<>(ItineraryItemList);
    }

    //  Set methods
    void addItineraryItem(ItineraryItem itineraryitem) {
        ItineraryItemList.add(itineraryitem);
        Collections.sort(ItineraryItemList);
    }

    void deleteItineraryItem(ItineraryItem itineraryitem) throws Exception {
        if (ItineraryItemList.remove(itineraryitem)) {
            return;
        } else {
            throw new Exception("ItineraryItemNotFound");
        }
    }

    void setItineraryItemList(ArrayList<ItineraryItem> ItineraryItemListInfo) {
        this.ItineraryItemList = new ArrayList(ItineraryItemList);
    }
}
