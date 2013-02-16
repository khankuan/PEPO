/*
 * AlertManager is in charge of checking the alerts date of tasks
 * 
 * Attributes:
 * - TreeSet<Task> myAlerts: The tasks sorted by alerts time
 * ~ Comparator alert_comparator: Compares task by alert date, followed by priority, then the object itself
 * 
 * Get methods:
 * + ArrayList<Task> getCurrentAlerts()
 * 
 * Other methods:
 * + addAlert(Task task): Add a Task to myAlerts
 * + deleteAlert(Task task): Delete a Task from myAlerts
 * 
 */

package Logic.Memo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeSet;

/*
 * @author kuan
 */
public class AlertManager {

    private TreeSet<Task> myAlerts = new TreeSet<>(alert_comparator);

    public AlertManager(ArrayList<Task> tasks) {
        ArrayList<Task> hasAlerts = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getAlert() != null && !tasks.get(i).isCompleted()) {
                hasAlerts.add(tasks.get(i));
            }
        }
        myAlerts.addAll(hasAlerts);
    }

    public void addAlert(Task task) {
        myAlerts.add(task);
    }

    public void deleteAlert(Task task) {
        myAlerts.remove(task);
    }

    public ArrayList<Task> getCurrentAlerts() {
        ArrayList<Task> results = new ArrayList<>();
        GregorianCalendar now = new GregorianCalendar();
        now.setTime(new Date(System.currentTimeMillis()));
        now.set(GregorianCalendar.SECOND, 0);
        now.set(GregorianCalendar.MILLISECOND, 0);
        ArrayList<Task> futureAlerts = new ArrayList(myAlerts);
        for (int i = 0; i < futureAlerts.size(); i++) {

            if (now.getTime().equals(futureAlerts.get(i).getAlert()) && !futureAlerts.get(i).isCompleted()) {
                results.add(futureAlerts.get(i));
            } else if (now.before(futureAlerts.get(i).getAlert())) {
                break;
            }
        }
        return results;
    }
    
    
    static Comparator alert_comparator = new Comparator() {

        @Override
        public int compare(Object o1, Object o2) {
            if (((Task) o1).getAlert().equals(((Task) o2).getAlert())) {
                if (((Task) o1).getPriority() != ((Task) o2).getPriority())
                    return ((Task) o1).getPriority() - ((Task) o2).getPriority();
                else
                    return o1 == o2 ? 0:1;
            } else {
                return ((Task) o1).getAlert().compareTo(((Task) o2).getAlert());
            }
        }
    };
}
