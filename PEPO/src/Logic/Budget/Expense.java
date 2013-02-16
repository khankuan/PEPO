/* Expense class, used in BudgetCategory
 * 
 * Attributes
 * - String title: Title of the expense
 * - long cost: Cost of the Expense, in cents
 * - BudgetCategory budgetcat: BudgetCategory that the Expense belongs to
 * 
 * Get methods
 * + String getTitle()
 * + long getCost()
 * + BudgetCategory getBudgetCategory()
 * 
 * Set methods
 * ~ setTitle(String title)
 * ~ setCost(long cost)
 * ~ setBudgetCategory(BudgetCategory budgetcategory)
 * 
 */
package Logic.Budget;

public class Expense implements Comparable {
    //  Attributes

    private String title;
    private long cost;
    private BudgetCategory budgetcat;

    //  Constructor
    public Expense() {
    }

    public Expense(BudgetCategory budgetcategory, String title, long cost) throws Exception {
        if (cost < 0) {
            throw new Exception("Cost cannot be negative");
        }
        if (cost > 999999900) {
            throw new Exception("Cost too large");
        }
        if (title.length() == 0) {
            throw new Exception("Title is invalid");
        }
        if (title.length() > 200) {
            throw new Exception("Title too long");
        }

        this.title = title;
        this.cost = cost;
        this.budgetcat = budgetcategory;
    }

    //  Get methods
    public String getTitle() {
        return title;
    }

    public long getCost() {
        return cost;
    }

    public BudgetCategory getBudgetCategory() {
        return budgetcat;
    }

    //  Set methods
    void setTitle(String title) throws Exception {
        if (title.length() == 0) {
            throw new Exception("Title is invalid");
        }
        if (title.length() > 200) {
            throw new Exception("Title too long");
        }
        this.title = title;
    }

    void setCost(long cost) throws Exception {
        if (cost < 0) {
            throw new Exception("Cost cannot be negative");
        }
        if (cost > 999999900) {
            throw new Exception("Cost too large");
        }
        this.cost = cost;
    }

    void setBudgetCategory(BudgetCategory budgetcategory) {
        budgetcat = budgetcategory;
    }

    @Override
    public int compareTo(Object o) {
        return this.getTitle().compareTo(((Expense) o).getTitle());
    }
}
