/* BudgetCategory manager handles all BudgetCategory non-public methods
 * Stores all BudgetCategory in: ArrayList<BudgetCategory> budgetCategories
 * 
 * BudgetCategory methods
 * + setName(BudgetCategory budgetcategory, String name)
 * + setAmount(BudgetCategory budgetcategory, long amount)
 * + getRemainingAmount(BudgetCategory budgetcategory)
 * + addExpense(BudgetCategory budgetcategory, Expense expense)
 * + deleteExpense(BudgetCategory budgetcategory, Expense expense)
 * 
 * Manager methods
 * + addBudgetCategory(BudgetCategory budgetcategory)
 * + deleteBudgetCategory(BudgetCategory budgetcategory)
 * + ArrayList<BudgetCategory> getBudgetCategoryList()
 * 
 * Database methods
 * + Ref_BudgetCategory_Event(IDMap map, ArrayList<BudgetCategory> BClist, ArrayList<Event> Elist)
 * + Ref_BudgetCategory_Expense(IDMap map, ArrayList<Expense> Exlist, ArrayList<BudgetCategory> BClist)
 * 
 */
package Logic.Budget;

import java.util.ArrayList;

import Database.IDMap;
import Database.Pair;
import Logic.Event.Event;

/*
 * @author kuan
 */
public class BudgetCategoryManager {
    //  Storage

    private ArrayList<BudgetCategory> budgetCategories;

    //  Constructor
    public BudgetCategoryManager(ArrayList<BudgetCategory> arr) {
        budgetCategories = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            budgetCategories.add(arr.get(i));
        }
    }

    //  BudgetCategory methods
    public void setName(BudgetCategory budgetcategory, String name) throws Exception {
        budgetcategory.setName(name);
    }

    public void setAmount(BudgetCategory budgetcategory, long amount) throws Exception {
        budgetcategory.setAmount(amount);
    }

    public long getRemainingAmount(BudgetCategory budgetcategory) {
        return budgetcategory.getRemainingAmount();
    }

    public void addExpense(BudgetCategory budgetcategory, Expense expense) {
        budgetcategory.addExpense(expense);
    }

    public void deleteExpense(BudgetCategory budgetcategory, Expense expense) throws Exception {
        budgetcategory.deleteExpense(expense);
    }

    //  Manager methods
    public void addBudgetCategory(BudgetCategory budgetcategory) {
        budgetCategories.add(budgetcategory);
    }

    public void deleteBudgetCategory(BudgetCategory budgetcategory) throws Exception {
        if (budgetCategories.remove(budgetcategory)) {
            return;
        } else {
            throw new Exception("BudgetCategory not found");
        }
    }

    public ArrayList<BudgetCategory> getBudgetCategoryList() {
        return new ArrayList<>(budgetCategories);
    }

    //  Set Object Reference for BudgetCategory and Event
    public static void Ref_BudgetCategory_Event(IDMap map, ArrayList<BudgetCategory> BClist, ArrayList<Event> Elist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                BClist.get(mapping.First()).setEvent(Elist.get(mapping.Second()));
            }
        }
        map.Reset();
    }

    //  Set Object Reference for BudgetCategory and Expense
    public static void Ref_BudgetCategory_Expense(IDMap map, ArrayList<Expense> Exlist, ArrayList<BudgetCategory> BClist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                BClist.get(mapping.Second()).addExpense(Exlist.get(mapping.First()));
            }
        }
        map.Reset();
    }
}