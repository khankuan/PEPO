/* Expense manager handles all Expense non-public methods
 * Stores all Expense in: ArrayList<Expense> expenses
 * 
 * Expense methods
 * + setTitle(Expense expense, String title)
 * + setCost(Expense expense, long cost)
 * + setBudgetCatgeory(Expense expense, BudgetCategory budgetcategory)
 * 
 * Manager methods
 * + addExpense(Expense expense)
 * + deleteExpense(Expense expense)
 * + ArrayList<Expense> getExpenseList()
 * 
 * Database methods
 * + Ref_Expense_BudgetCategory(IDMap map, ArrayList<Expense> Exlist, ArrayList<BudgetCategory> BClist)
 * 
 */
package Logic.Budget;

import java.util.ArrayList;
import Database.IDMap;
import Database.Pair;

/*
 * @author kuan
 */
public class ExpenseManager {
    //  Storage

    private ArrayList<Expense> expenses;

    //  Constructor
    public ExpenseManager(ArrayList<Expense> arr) {
        expenses = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            expenses.add(arr.get(i));
        }
    }

    //  Expense methods    
    public void setTitle(Expense expense, String title) throws Exception {
        expense.setTitle(title);
    }

    public void setCost(Expense expense, long cost) throws Exception {
        expense.setCost(cost);
    }

    public void setBudgetCatgeory(Expense expense, BudgetCategory budgetcategory) {
        expense.setBudgetCategory(budgetcategory);
    }

    //  Manager methods
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void deleteExpense(Expense expense) throws Exception {
        if (expenses.remove(expense)) {
            return;
        } else {
            throw new Exception("Expense not found");
        }
    }

    public ArrayList<Expense> getExpenseList() {
        return new ArrayList<>(expenses);
    }

    // Set Object Reference for Expense and BudgetCategory
    public static void Ref_Expense_BudgetCategory(IDMap map, ArrayList<Expense> Exlist, ArrayList<BudgetCategory> BClist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Exlist.get(mapping.First()).setBudgetCategory(BClist.get(mapping.Second()));
            }
        }
        map.Reset();
    }
}