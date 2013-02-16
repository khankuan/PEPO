/* Task class extends MemoItem
 * 
 * Attributes
 * - int priority: Priority of the Task
 * - Date alert: Date and time for alert
 * - boolean isCompleted: Completion status of the Task
 * 
 * Get methods
 * + int getPriority()
 * + Date alert
 * + boolean isCompleted
 * 
 * Set method
 * ~ setPriority(int priority)
 * ~ setCompleted(boolean completed)
 * ~ setAlert(Date alert)
 * ~ deleteAlert()
 * 
 */
package Logic.Memo;

import Logic.Event.Event;
import java.util.Date;

/*
 * @author kuan
 */
public class Task extends MemoItem {
    //  Attributes

    private int priority;   // 0-High, 1-Medium, 2-Low
    private Date alert;
    private boolean isCompleted;

    //  Constructor
    public Task() {
    }

    public Task(Event event, String description, Date date, int priority, boolean completed, Date alert) 
            throws Exception {
        if (description.length() == 0) {
            throw new Exception("Description is invalid");
        }
        if (description.length() > 200) {
            throw new Exception("Description too long");
        }
        if (priority < 0 || priority > 2) {
            throw new Exception("Priority out of range");
        }
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.event = event;
        this.isCompleted = completed;
        this.alert = alert;
    }

    //  Get methods
    public int getPriority() {
        return priority;
    }

    public Date getAlert() {
        return alert;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    //  Set methods
    void setPriority(int priority) throws Exception {
        if (priority < 0 || priority > 2) {
            throw new Exception("Priority out of range");
        }
        this.priority = priority;
    }

    void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    void setAlert(Date alert) {
        this.alert = alert;
    }

    void deleteAlert() {
        this.alert = null;
    }
}
