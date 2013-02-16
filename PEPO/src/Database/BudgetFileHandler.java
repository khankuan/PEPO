/* BudgetFileHandler class
 * 
 * + void Store_BudgetCategories(ArrayList<BudgetCategory> BClist, ArrayList<Event> Elist)
 * + ArrayList<BudgetCategory> Retrieve_BudgetCategories()
 * 
 * + void Store_Expenses(ArrayList<Expense> Exlist, ArrayList<BudgetCategory> BClist)
 * + ArrayList<Expense> Retrieve_Expenses(
 * 
 */
package Database;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Logic.Event.Event;
import Logic.Budget.*;

/*
 * @author hongwei
 */
public class BudgetFileHandler extends FileParser {

    static Stack<IDMap> LoadedIDMap = new Stack<>();
    static final String BCfile = "Data" + File.separator + "BudgetCategory.xml";
    static final String Exfile = "Data" + File.separator + "Expense.xml";

    // BudgetCategories ------------------------------------------------------------------------
    static public void Store_BudgetCategories(ArrayList<BudgetCategory> BClist, ArrayList<Event> Elist) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = doc.createElement("BudgetCategory");
            doc.appendChild(root);

            int fid;
            for (int i = 0; i < BClist.size(); i++) {
                // item
                Element item = doc.createElement("Item");
                Attr id = doc.createAttribute("ID");
                id.setValue(Integer.toString(i));
                item.setAttributeNode(id);

                // item - uncategorized
                Attr uncat = doc.createAttribute("Uncategorized");
                uncat.setValue(Boolean.toString(BClist.get(i) == BClist.get(i).getEvent().getUncategorizedBudget()));
                item.setAttributeNode(uncat);

                // item - name
                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(BClist.get(i).getName()));
                item.appendChild(name);

                // item - amount
                Element amt = doc.createElement("Amount");
                amt.appendChild(doc.createTextNode(Long.toString(BClist.get(i).getAmount())));
                item.appendChild(amt);

                // item - list of eventIDs
                Element EID = doc.createElement("E_ID");
                for (fid = 0; fid < Elist.size(); fid++) {
                    if (BClist.get(i).getEvent() == Elist.get(fid)) {
                        break;
                    }
                }
                if (fid == Elist.size()) {
                    fid = -1;
                }
                EID.appendChild(doc.createTextNode(Integer.toString(fid)));
                item.appendChild(EID);

                root.appendChild(item);
            }

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(BCfile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store BudgetCategory");
        }
    }

    static public ArrayList<BudgetCategory> Retrieve_BudgetCategories() throws Exception {
        IDMap mapperCat = new IDMap();
        IDMap mapperUnCat = new IDMap();

        ArrayList<BudgetCategory> BClist = new ArrayList<>();
        TestFile(BCfile, "<BudgetCategory/>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(BCfile));
            doc.getDocumentElement().normalize();

            // item
            NodeList items = doc.getElementsByTagName("Item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                
                // create new BudgetCategory
                BudgetCategory newitem = new BudgetCategory(null, getValue(item, "Name"),
                        Long.parseLong(getValue(item, "Amount")));
                BClist.add(newitem);
                
                // if uncategorised, add ID mapping to mapperCat, else mapperUnCat
                if (!Boolean.parseBoolean(item.getAttribute("Uncategorized"))) {
                    mapperCat.addMapping(Integer.parseInt(getID(item)),
                            Integer.parseInt(getValue(item, "E_ID")));
                } else {
                    mapperUnCat.addMapping(Integer.parseInt(getID(item)),
                            Integer.parseInt(getValue(item, "E_ID")));
                }
            }
        } catch (Exception obj) {
            throw new Exception("BudgetCategory file is corrupted");
        }

        LoadedIDMap.push(mapperCat);
        LoadedIDMap.push(mapperUnCat);
        return BClist;
    }

    // Expenses ------------------------------------------------------------------------
    static public void Store_Expenses(ArrayList<Expense> Exlist, ArrayList<BudgetCategory> BClist) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = doc.createElement("Expense");
            doc.appendChild(root);

            int fid;
            for (int i = 0; i < Exlist.size(); i++) {
                // item
                Element item = doc.createElement("Item");
                Attr id = doc.createAttribute("ID");
                id.setValue(Integer.toString(i));
                item.setAttributeNode(id);

                // item - title
                Element title = doc.createElement("Title");
                title.appendChild(doc.createTextNode(Exlist.get(i).getTitle()));
                item.appendChild(title);

                // item - cost
                Element cost = doc.createElement("Cost");
                cost.appendChild(doc.createTextNode(Long.toString(Exlist.get(i).getCost())));
                item.appendChild(cost);

                // item - BudgetCategoryID
                Element BCID = doc.createElement("BC_ID");
                for (fid = 0; fid < BClist.size(); fid++) {
                    if (Exlist.get(i).getBudgetCategory() == BClist.get(fid)) {
                        break;
                    }
                }
                if (fid == BClist.size()) {
                    fid = -1;
                }
                BCID.appendChild(doc.createTextNode(Integer.toString(fid)));
                item.appendChild(BCID);

                root.appendChild(item);
            }

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Exfile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store Expense");
        }
    }

    static public ArrayList<Expense> Retrieve_Expenses() throws Exception {
        IDMap mapper = new IDMap();
        ArrayList<Expense> Exlist = new ArrayList<>();
        TestFile(Exfile, "<Expense/>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(Exfile));
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("Item");

            for (int i = 0; i < items.getLength(); i++) {
                // create new Expense
                Element item = (Element) items.item(i);
                Expense newitem = new Expense(null, getValue(item, "Title"),
                        Long.parseLong(getValue(item, "Cost")));
                Exlist.add(newitem);

                // map its ID to the ID of its BudgetCategory
                mapper.addMapping(Integer.parseInt(getID(item)),
                        Integer.parseInt(getValue(item, "BC_ID")));
            }
        } catch (Exception obj) {
            throw new Exception("Expense file is corrupted");
        }

        LoadedIDMap.push(mapper);
        return Exlist;
    }
}
