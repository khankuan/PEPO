/*
 * Day is used in Memo, for the getFilteredDayScheduleByDate method in MemoSecretary
 * 
 * Attributes:
 * - Date date: The date of the Day
 * - ArrayList<MemoItem> memoitems: The MemoItems involved in this date
 * 
 * Get methods:
 * + Date getDate()
 * + ArrayList<MemoItem> getMemoItemList() 
 * 
 * Other methods:
 * ~ addMemoItem(MemoItem memoable): Adds a MemoItem to the Day
 * + compareTo(): compareTo function for Day sorting, by Date
 * 
 */
package Logic.Memo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/*
 * @author kuan
 */
public class Day implements Comparable {
    //  Attributes

    Date date;
    ArrayList<Memoable> memoitems = new ArrayList<>();

    //  Constructor
    public Day(Date date) {
        this.date = date;
    }

    //  Get methods
    public Date getDate() {
        return date;
    }

    public ArrayList<Memoable> getMemoItemList() {
        return memoitems;
    }

    //  Other methods
    void addMemoItem(Memoable memoable) {
        memoitems.add(memoable);
    }

    @Override
    public int compareTo(Object o) {
        return this.date.compareTo(((Day) o).date);
    }

    void sortItems() {
        Collections.sort(memoitems);
    }
}
