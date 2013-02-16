/* EventFileHandler class
 * 
 * + void Store_Events(ArrayList<Event> Elist, ArrayList<PersonnelList> PeLlist)
 * + ArrayList<Event> Retrieve_Events()
 * 
 * + void Store_ArchiveMap(IDMap ArchiveMap)
 * + IDMap Retrieve_ArchiveMap()
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

import Logic.Event.*;
import Logic.PersonnelList.*;

/*
 * @author hongwei
 */
public class EventFileHandler extends FileParser {

    static Stack<IDMap> LoadedIDMap = new Stack<>();
    static final String Efile = "Data" + File.separator + "Event.xml";
    static final String AMfile = "Data" + File.separator + "ArchiveMap.xml";

    // Events ------------------------------------------------------------------------
    static public void Store_Events(ArrayList<Event> Elist, ArrayList<PersonnelList> PeLlist) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = doc.createElement("Event");
            doc.appendChild(root);

            int fid;
            for (int i = 0; i < Elist.size(); i++) {
                // item
                Element item = doc.createElement("Item");
                Attr id = doc.createAttribute("ID");
                id.setValue(Integer.toString(i));
                item.setAttributeNode(id);

                // item - title
                Element title = doc.createElement("Title");
                title.appendChild(doc.createTextNode(Elist.get(i).getTitle()));
                item.appendChild(title);

                // item - description
                Element desc = doc.createElement("Description");
                desc.appendChild(doc.createTextNode(Elist.get(i).getDescription()));
                item.appendChild(desc);

                // item - budget
                Element bud = doc.createElement("Budget");
                bud.appendChild(doc.createTextNode(Long.toString(Elist.get(i).getTotalBudget())));
                item.appendChild(bud);

                // item - participant list ID
                Element Part_PeLID = doc.createElement("Part_PeL_ID");
                for (fid = 0; fid < PeLlist.size(); fid++) {
                    if (Elist.get(i).getParticipantList() == PeLlist.get(fid)) {
                        break;
                    }
                }
                if (fid == PeLlist.size()) {
                    fid = -1;
                }
                Part_PeLID.appendChild(doc.createTextNode(Integer.toString(fid)));
                item.appendChild(Part_PeLID);

                // item - helper list ID
                Element Help_PeLID = doc.createElement("Help_PeL_ID");
                for (fid = 0; fid < PeLlist.size(); fid++) {
                    if (Elist.get(i).getHelperList() == PeLlist.get(fid)) {
                        break;
                    }
                }
                if (fid == PeLlist.size()) {
                    fid = -1;
                }
                Help_PeLID.appendChild(doc.createTextNode(Integer.toString(fid)));
                item.appendChild(Help_PeLID);

                // item - sponsor list ID
                Element Spon_PeLID = doc.createElement("Spon_PeL_ID");
                for (fid = 0; fid < PeLlist.size(); fid++) {
                    if (Elist.get(i).getSponsorList() == PeLlist.get(fid)) {
                        break;
                    }
                }
                if (fid == PeLlist.size()) {
                    fid = -1;
                }
                Spon_PeLID.appendChild(doc.createTextNode(Integer.toString(fid)));
                item.appendChild(Spon_PeLID);

                // item - vendor list ID
                Element Vend_PeLID = doc.createElement("Vend_PeL_ID");
                for (fid = 0; fid < PeLlist.size(); fid++) {
                    if (Elist.get(i).getVendorList() == PeLlist.get(fid)) {
                        break;
                    }
                }
                if (fid == PeLlist.size()) {
                    fid = -1;
                }
                Vend_PeLID.appendChild(doc.createTextNode(Integer.toString(fid)));
                item.appendChild(Vend_PeLID);

                root.appendChild(item);
            }

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Efile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store Event");
        }
    }

    static public ArrayList<Event> Retrieve_Events() throws Exception {
        IDMap mapperP = new IDMap();
        IDMap mapperH = new IDMap();
        IDMap mapperS = new IDMap();
        IDMap mapperV = new IDMap();
        ArrayList<Event> Elist = new ArrayList<>();
        TestFile(Efile, "<Event/>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(Efile));
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("Item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                // create new event
                Event newitem = new Event(getValue(item, "Title"), getValue(item, "Description"),
                        Long.parseLong(getValue(item, "Budget")));
                Elist.add(newitem);

                // map its own ID to a participant list ID
                mapperP.addMapping(Integer.parseInt(getID(item)),
                        Integer.parseInt(getValue(item, "Part_PeL_ID")));

                // map its own ID to a helper list ID
                mapperH.addMapping(Integer.parseInt(getID(item)),
                        Integer.parseInt(getValue(item, "Help_PeL_ID")));

                // map its own ID to a sponsor list ID
                mapperS.addMapping(Integer.parseInt(getID(item)),
                        Integer.parseInt(getValue(item, "Spon_PeL_ID")));

                // map its own ID to a vendor list ID
                mapperV.addMapping(Integer.parseInt(getID(item)),
                        Integer.parseInt(getValue(item, "Vend_PeL_ID")));
            }
        } catch (Exception e) {
            throw new Exception("Event file is corrupted");
        }

        LoadedIDMap.push(mapperV);
        LoadedIDMap.push(mapperS);
        LoadedIDMap.push(mapperH);
        LoadedIDMap.push(mapperP);
        return Elist;
    }

    // ArchiveMap ------------------------------------------------------------------------
    static public void Store_ArchiveMap(IDMap ArchiveMap) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = doc.createElement("ArchiveMap");
            doc.appendChild(root);

            // unarchived
            Element item = doc.createElement("Unarchived");
            while (ArchiveMap.PeekNextPair().First() == 0 && ArchiveMap.hasNext()) {		// 0-Unarchived, 1-Archived
                // unarchived - ID
                Element ID = doc.createElement("id");
                ID.appendChild(doc.createTextNode(Integer.toString(ArchiveMap.NextPair().Second())));
                item.appendChild(ID);
            }
            root.appendChild(item);

            // archived
            Element item2 = doc.createElement("Archived");
            while (ArchiveMap.PeekNextPair().First() == 1 && ArchiveMap.hasNext()) {		// 0-Unarchived, 1-Archived
                // archived - ID
                Element ID = doc.createElement("id");
                ID.appendChild(doc.createTextNode(Integer.toString(ArchiveMap.NextPair().Second())));
                item2.appendChild(ID);
            }
            root.appendChild(item2);

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(AMfile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store ArchiveMap");
        }
    }

    static public IDMap Retrieve_ArchiveMap() throws Exception {
        IDMap ArchiveMap = new IDMap();
        TestFile(AMfile, "<ArchiveMap><Unarchived/><Archived/></ArchiveMap>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(AMfile));
            doc.getDocumentElement().normalize();

            // unarchived
            NodeList archiveItems = doc.getElementsByTagName("Unarchived");
            Element id = (Element) archiveItems.item(0);

            for (int i = 0; i < id.getElementsByTagName("id").getLength(); i++) {
                ArchiveMap.addMapping(0, Integer.parseInt(getValue(id, "id", i)));
            }

            // archived
            NodeList unarchiveItems = doc.getElementsByTagName("Archived");
            id = (Element) unarchiveItems.item(0);

            for (int i = 0; i < id.getElementsByTagName("id").getLength(); i++) {
                ArchiveMap.addMapping(1, Integer.parseInt(getValue(id, "id", i)));
            }
        } catch (Exception obj) {
            throw new Exception("ArchiveMap file is corrupted");
        }

        return ArchiveMap;
    }
}
