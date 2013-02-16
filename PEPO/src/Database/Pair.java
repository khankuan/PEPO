/* Pair class
 * 
 * - int first
 * - int second
 * 
 * + Pair(int first, int second)
 * + int First()
 * + int Second()
 * + void printTest()
 * + boolean notNull()
 * 
 */
package Database;

/*
 * @author hongwei
 */
public class Pair {

    private int first;
    private int second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int First() {
        return first;
    }

    public int Second() {
        return second;
    }

    // print first and second (for testing purpose)
    public void printTest() {
        System.out.print(first + " ");
        System.out.println(second);
    }

    // check if first or second is not set
    public boolean notNull() {
        return (first != -1 && second != -1);
    }
}
