/* BudgetCategory class, used by Event
 * 
 * Attributes
 * - String name: Name of the BudgetCategory
 * - long amount: amount allocated, in cents
 * - ArrayList<Expense> expenseList: List of expense filed under this BudgetCategory
 * - Event event: Event that this BudgetCategory belongs to
 * 
 * Get methods
 * + String getName()
 * + long getAmount()
 * + ArrayList<Expense> getExpenseList()
 * + long getRemainingAmount()
 * + getEvent()
 * 
 * Set methods
 * ~ setName(String name)
 * ~ setAmount(long amount)
 * ~ addExpense(Expense ex)
 * ~ deleteExpense(Expense ex)
 * ~ setEvent(Event event)
 * ~ setExpenseList(ArrayList<Expense> expenseList)
 * 
 * 
 */
package Logic.Budget;

import Logic.Event.Event;
import java.util.ArrayList;
import java.util.Collections;

/*
 * @author kuan
 */
public class BudgetCategory implements Comparable {
    //  Attributes

    private String name;
    private long amount;
    private ArrayList<Expense> expenseList = new ArrayList<>();
    private Event event;

    //  Constructor
    public BudgetCategory(Event event, String name, long amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Amount cannot be negative");
        }
        if (amount > 999999900) {
            throw new Exception("Amount too large");
        }
        if (name.length() == 0) {
            throw new Exception("Name is invalid");
        }
        if (name.length() > 200) {
            throw new Exception("Name too long");
        }
        this.name = name;
        this.amount = amount;
        this.event = event;
    }

    //  Get methods
    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public ArrayList<Expense> getExpenseList() {
        return new ArrayList<>(expenseList);
    }

    public long getRemainingAmount() {
        long remain = amount;
        for (int i = 0; i < expenseList.size(); i++) {
            remain -= expenseList.get(i).getCost();
        }
        return remain;
    }

    public Event getEvent() {
        return event;
    }

    //  Set methods
    void setName(String name) throws Exception {
        if (name.length() == 0) {
            throw new Exception("Name is invalid");
        }
        if (name.length() > 200) {
            throw new Exception("Name too long");
        }
        this.name = name;
    }

    void setAmount(long amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Amount cannot be negative");
        }
        if (amount > 999999900) {
            throw new Exception("Amount too large");
        }
        this.amount = amount;
    }

    void addExpense(Expense expense) {
        expenseList.add(expense);
        Collections.sort(expenseList);
    }

    void deleteExpense(Expense ex) throws Exception {
        if (expenseList.remove(ex)) {
            return;
        } else {
            throw new Exception("Expense not found");
        }
    }

    void setEvent(Event event) {
        this.event = event;
    }

    void setExpenseList(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((BudgetCategory) o).getName());
    }
}
