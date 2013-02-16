/* VenueBooking class
 * 
 * Attributes
 * - Date startdate: Start date of VenueBooking
 * - Date enddate: End date of VenueBooking
 * - Venue venue: Venue of the VenueBooking
 * - ArrayList<MemoItem> memoItemList: ArrayList of MemoItems tagged to the VenueBooking
 * 
 * Get methods
 * + Date getStartDate()
 * + Date getEndDate()
 * + Venue getVenue()
 * + ArrayList<MemoItem> getTaggedMemoItemList()
 * 
 * Set methods
 * ~ setStartDate(Date startdate)
 * ~ setEndDate(Date enddate)
 * ~ setVenue(Venue venue)
 * ~ tagMemoItem(MemoItem memoitem)
 * ~ untagMemoItem(MemoItem memoitem)
 * 
 */
package Logic.Venue;

import Logic.Memo.MemoItem;
import Logic.Memo.Memoable;
import java.util.ArrayList;
import java.util.Date;

/*
 * @author kuan
 */
public class VenueBooking implements Memoable {
    //  Attributes

    private Date startdate;
    private Date enddate;
    private Venue venue;
    private ArrayList<MemoItem> memoItemList = new ArrayList<>();	// not saved

    //  Constructor
    public VenueBooking() {
    }

    public VenueBooking(Venue venue, Date startdate, Date enddate) throws Exception {
        if (startdate.after(enddate)) {
            throw new Exception("Start date cannot be after end date");
        }
        this.venue = venue;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    //  Get methods
    public Date getStartDate() {
        return startdate;
    }

    public Date getEndDate() {
        return enddate;
    }

    public Venue getVenue() {
        return venue;
    }

    public ArrayList<MemoItem> getTaggedMemoItemList() {
        return memoItemList;
    }

    //  Set methods
    void setStartDate(Date startdate) {
        this.startdate = startdate;
    }

    void setEndDate(Date enddate) {
        this.enddate = enddate;
    }

    void setVenue(Venue venue) {
        this.venue = venue;
    }

    void tagMemoItem(MemoItem memoitem) {
        if (!memoItemList.contains(memoitem)) {
            memoItemList.add(memoitem);
        }
    }

    void untagMemoItem(MemoItem memoitem) throws Exception {
        if (memoItemList.remove(memoitem)) {
            return;
        } else {
            throw new Exception("MemoItem not found");
        }
    }

    @Override
    public int compareTo(Memoable o) {
        return this.date.compareTo(o.date);
    }

    @Override
    public Date getDate() {
        return startdate;
    }
}
