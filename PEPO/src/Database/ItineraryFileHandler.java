/* ItineraryFileHandler class
 * 
 * + void Store_ItineraryItems(ArrayList<ItineraryItem> PLIlist)
 * + ArrayList<ItineraryItem> Retrieve_ItineraryItems()
 * 
 * + void Store_Itinerarys(ArrayList<Itinerary> PLlist, ArrayList<ItineraryItem> PLIlist)
 * + ArrayList<Itinerary> Retrieve_Itinerarys()
 * 
 */
package Database;

import java.text.SimpleDateFormat;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
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

import Logic.Itinerary.*;

/*
 * @author hongwei
 */
public class ItineraryFileHandler extends FileParser {

    static Stack<IDMap> LoadedIDMap = new Stack<>();
    static final String PLfile = "Data" + File.separator + "Itinerary.xml";
    static final String PLIfile = "Data" + File.separator + "ItineraryItem.xml";

    // ItineraryItems ------------------------------------------------------------------------
    static public void Store_ItineraryItems(ArrayList<ItineraryItem> PLIlist) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = doc.createElement("ItineraryItems");
            doc.appendChild(root);

            for (int i = 0; i < PLIlist.size(); i++) {
                // item
                Element item = doc.createElement("Item");
                Attr id = doc.createAttribute("ID");
                id.setValue(Integer.toString(i));
                item.setAttributeNode(id);

                // item - name
                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(PLIlist.get(i).getName()));
                item.appendChild(name);

                // item - date
                Element date = doc.createElement("Date");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd ww 'at' hh:mm:ss a zzz");
                date.appendChild(doc.createTextNode(formatter.format(PLIlist.get(i).getDate())));
                item.appendChild(date);

                // item - enddate
                Element enddate = doc.createElement("EndDate");
                enddate.appendChild(doc.createTextNode(formatter.format(PLIlist.get(i).getEndDate())));
                item.appendChild(enddate);

                root.appendChild(item);
            }

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PLIfile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store ItineraryItem");
        }
    }

    static public ArrayList<ItineraryItem> Retrieve_ItineraryItems() throws Exception {
        ArrayList<ItineraryItem> PLIlist = new ArrayList<>();
        TestFile(PLIfile, "<ItineraryItems/>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(PLIfile));
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("Item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd ww 'at' hh:mm:ss a zzz");
                // create new ItineraryItem
                ItineraryItem newitem = new ItineraryItem(null, getValue(item, "Name"),
                        formatter.parse(getValue(item, "Date")),
                        formatter.parse(getValue(item, "EndDate")));
                PLIlist.add(newitem);
            }
        } catch (Exception e) {
            throw new Exception("ItineraryItem file is corrupted");
        }

        return PLIlist;
    }

    // Itinerarys ------------------------------------------------------------------------
    static public void Store_Itinerarys(ArrayList<Itinerary> PLlist, ArrayList<ItineraryItem> PLIlist) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = doc.createElement("Itinerary");
            doc.appendChild(root);

            int fid;
            for (int i = 0; i < PLlist.size(); i++) {
                // item
                Element item = doc.createElement("Item");
                Attr id = doc.createAttribute("ID");
                id.setValue(Integer.toString(i));
                item.setAttributeNode(id);

                // item - list of ItineraryItemIDs
                Element PLIIDs = doc.createElement("PLI_IDs");
                for (fid = 0; fid < PLIlist.size(); fid++) {
                    Iterator<ItineraryItem> it = PLlist.get(i).getSortedItineraryItemList().iterator();
                    while (it.hasNext()) {
                        if (it.next() == PLIlist.get(fid)) {
                            Element PLIID = doc.createElement("id");
                            PLIID.appendChild(doc.createTextNode(Integer.toString(fid)));
                            PLIIDs.appendChild(PLIID);
                        }
                    }
                }
                item.appendChild(PLIIDs);
                root.appendChild(item);
            }

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PLfile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store Itinerary");
        }
    }

    static public ArrayList<Itinerary> Retrieve_Itinerarys() throws Exception {
        IDMap mapper = new IDMap();
        ArrayList<Itinerary> PLlist = new ArrayList<>();
        TestFile(PLfile, "<Itinerary/>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(PLfile));
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("Item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                // create new Itinerary
                Itinerary newitem = new Itinerary();
                PLlist.add(newitem);

                // map its ID to a list of ItineraryID
                for (int j = 0; j < getListLength(item, "PLI_IDs"); j++) {
                    mapper.addMapping(Integer.parseInt(getID(item)),
                            Integer.parseInt(getValList(item, "PLI_IDs", j)));
                }
            }
        } catch (Exception obj) {
            throw new Exception("Itinerary file is corrupted");
        }

        LoadedIDMap.push(mapper);
        return PLlist;
    }
}
