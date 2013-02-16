/* IDMap class
 * 
 * ~ ArrayList<Pair> mapping
 * ~ int position
 * 
 * ~ IDMap()
 * ~ void addMapping(int first, int second)
 * 
 * + void Reset()
 * + Pair PeekNextPair()
 * + Pair NextPair()
 * + boolean hasNext()
 * 
 */
package Database;

import java.util.*;

/*
 * @author hongwei
 */
public class IDMap {

    ArrayList<Pair> mapping;
    int position = 0;

    IDMap() {
        mapping = new ArrayList<>();
    }

    // add a new ID mapping to this instance
    void addMapping(int first, int second) {
        mapping.add(new Pair(first, second));
    }

    // set pointer back to start
    public void Reset() {
        position = 0;
    }

    // check the next mapping
    public Pair PeekNextPair() {
        if (!mapping.isEmpty() && hasNext()) {
            mapping.get(position);
            return mapping.get(position);
        } else {
            return new Pair(-1, -1);
        }
    }

    // return current mapping and move to the next
    public Pair NextPair() {
        return mapping.get(position++);
    }

    // check if there is any more mapping
    public boolean hasNext() {
        return position < mapping.size();
    }
}