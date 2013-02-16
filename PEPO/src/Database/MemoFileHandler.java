/* MemoFileHandler class
 * 
 * + void Store_Tasks(ArrayList<Task> Tlist, ArrayList<Event> Elist, ArrayList<VenueBooking> VBlist)
 * + ArrayList<Task> Retrieve_Tasks()
 * 
 * + void Store_EventDays(ArrayList<EventDay> EDlist, ArrayList<Itinerary> PLlist, ArrayList<Event> Elist, ArrayList<VenueBooking> VBlist)
 * + ArrayList<EventDay> Retrieve_EventDays()
 * 
 * + void Store_VenueBookings(ArrayList<VenueBooking> VBlist, ArrayList<Venue> Vlist)
 * + ArrayList<VenueBooking> Retrieve_VenueBookings()
 * 
 */
package Database;

import java.text.SimpleDateFormat;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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

import Logic.Event.Event;
import Logic.Memo.*;
import Logic.Itinerary.*;
import Logic.Venue.*;

/*
 * @author hongwei
 */
public class MemoFileHandler extends FileParser {

    static Stack<IDMap> LoadedIDMap = new Stack<>();
    static final String Tfile = "Data" + File.separator + "Task.xml";
    static final String VBfile = "Data" + File.separator + "VenueBooking.xml";
    static final String EDfile = "Data" + File.separator + "EventDay.xml";

    // Tasks ------------------------------------------------------------------------
    static public void Store_Tasks(ArrayList<Task> Tlist,
            ArrayList<Event> Elist,
            ArrayList<VenueBooking> VBlist) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            
            // root element
            Element root = doc.createElement("Task");
            doc.appendChild(root);

            int fid;
            for (int i = 0; i < Tlist.size(); i++) {
                // item
                Element item = doc.createElement("Item");
                Attr id = doc.createAttribute("ID");
                id.setValue(Integer.toString(i));
                item.setAttributeNode(id);

                // item - description
                Element desc = doc.createElement("Description");
                desc.appendChild(doc.createTextNode(Tlist.get(i).getDescription()));
                item.appendChild(desc);

                // item - priority
                Element prior = doc.createElement("Priority");
                prior.appendChild(doc.createTextNode(Integer.toString(Tlist.get(i).getPriority())));
                item.appendChild(prior);

                // item - iscompleted
                Element isCom = doc.createElement("IsCompleted");
                isCom.appendChild(doc.createTextNode(Boolean.toString(Tlist.get(i).isCompleted())));
                item.appendChild(isCom);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd ww 'at' hh:mm:ss a zzz");

                // item - date
                if (Tlist.get(i).getDate() != null) {
                    Element date = doc.createElement("Date");
                    date.appendChild(doc.createTextNode(formatter.format(Tlist.get(i).getDate())));
                    item.appendChild(date);
                }

                // item - alert
                if (Tlist.get(i).getAlert() != null) {
                    Element alert = doc.createElement("Alert");
                    if (Tlist.get(i).getAlert() != null) {
                        alert.appendChild(doc.createTextNode(formatter.format(Tlist.get(i).getAlert())));
                        item.appendChild(alert);
                    }
                }

                // item - EventID
                Element EID = doc.createElement("E_ID");
                for (fid = 0; fid < Elist.size(); fid++) {
                    if (Tlist.get(i).getEvent() == Elist.get(fid)) {
                        break;
                    }
                }
                if (fid == Elist.size()) {
                    fid = -1;
                }
                EID.appendChild(doc.createTextNode(Integer.toString(fid)));
                item.appendChild(EID);

                // item - list of EventIDs
                Element VBIDs = doc.createElement("VB_IDs");
                for (fid = 0; fid < VBlist.size(); fid++) {
                    Iterator<VenueBooking> it = Tlist.get(i).getTaggedVenueBookingList().iterator();
                    while (it.hasNext()) {
                        if (it.next() == VBlist.get(fid)) {
                            Element VBID = doc.createElement("id");
                            VBID.appendChild(doc.createTextNode(Integer.toString(fid)));
                            VBIDs.appendChild(VBID);
                        }
                    }
                }
                item.appendChild(VBIDs);

                root.appendChild(item);
            }

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Tfile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store Task");
        }
    }

    static public ArrayList<Task> Retrieve_Tasks() throws Exception {
        IDMap mapperE = new IDMap();
        IDMap mapperVB = new IDMap();
        ArrayList<Task> Tlist = new ArrayList<>();
        TestFile(Tfile, "<Task/>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(Tfile));
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("Item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd ww 'at' hh:mm:ss a zzz");

                // retrieve date
                Date date = null;
                try {
                    date = formatter.parse(getValue(item, "Date"));
                } catch (Exception obj) {
                }

                // retrieve alert
                Date alert = null;
                try {
                    alert = formatter.parse(getValue(item, "Alert"));
                } catch (Exception obj) {
                }

                // create new Task
                Task newitem = new Task(null, getValue(item, "Description"),
                        date,
                        Integer.parseInt(getValue(item, "Priority")),
                        Boolean.parseBoolean(getValue(item, "IsCompleted")),
                        alert);

                Tlist.add(newitem);

                // map its ID to an eventID
                mapperE.addMapping(Integer.parseInt(getID(item)),
                        Integer.parseInt(getValue(item, "E_ID")));

                // map its ID to a list of VenueIDs
                for (int j = 0; j < getListLength(item, "VB_IDs"); j++) {
                    mapperVB.addMapping(Integer.parseInt(getID(item)),
                            Integer.parseInt(getValList(item, "VB_IDs", j)));
                }
            }
        } catch (Exception e) {
            throw new Exception("Task file is corrupted");
        }

        LoadedIDMap.push(mapperE);
        LoadedIDMap.push(mapperVB);
        return Tlist;
    }

    // EventDays ------------------------------------------------------------------------
    static public void Store_EventDays(ArrayList<EventDay> EDlist,
            ArrayList<Itinerary> PLlist,
            ArrayList<Event> Elist,
            ArrayList<VenueBooking> VBlist) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = doc.createElement("EventDay");
            doc.appendChild(root);

            int fid;
            for (int i = 0; i < EDlist.size(); i++) {
                // item
                Element item = doc.createElement("Item");
                Attr id = doc.createAttribute("ID");
                id.setValue(Integer.toString(i));
                item.setAttributeNode(id);

                // item - description
                Element desc = doc.createElement("Description");
                desc.appendChild(doc.createTextNode(EDlist.get(i).getDescription()));
                item.appendChild(desc);

                // item - notes
                Element notes = doc.createElement("Notes");
                notes.appendChild(doc.createTextNode(EDlist.get(i).getNotes()));
                item.appendChild(notes);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd ww 'at' hh:mm:ss a zzz");

                // item - date
                Element date = doc.createElement("Date");
                date.appendChild(doc.createTextNode(formatter.format(EDlist.get(i).getDate())));
                item.appendChild(date);

                // item - enddate
                Element enddate = doc.createElement("EndDate");
                enddate.appendChild(doc.createTextNode(formatter.format(EDlist.get(i).getEndDate())));
                item.appendChild(enddate);

                // item - ItineraryID
                Element PLID = doc.createElement("PL_ID");
                for (fid = 0; fid < PLlist.size(); fid++) {
                    if (EDlist.get(i).getItinerary() == PLlist.get(fid)) {
                        break;
                    }
                }
                if (fid == PLlist.size()) {
                    fid = -1;
                }
                PLID.appendChild(doc.createTextNode(Integer.toString(fid)));
                item.appendChild(PLID);

                // item - EventID
                Element EID = doc.createElement("E_ID");
                for (fid = 0; fid < Elist.size(); fid++) {
                    if (EDlist.get(i).getEvent() == Elist.get(fid)) {
                        break;
                    }
                }
                if (fid == Elist.size()) {
                    fid = -1;
                }
                EID.appendChild(doc.createTextNode(Integer.toString(fid)));
                item.appendChild(EID);

                // item - list of VenueBookingIDs
                Element VBIDs = doc.createElement("VB_IDs");
                for (fid = 0; fid < VBlist.size(); fid++) {
                    Iterator<VenueBooking> it = EDlist.get(i).getTaggedVenueBookingList().iterator();
                    while (it.hasNext()) {
                        if (it.next() == VBlist.get(fid)) {
                            Element VBID = doc.createElement("id");
                            VBID.appendChild(doc.createTextNode(Integer.toString(fid)));
                            VBIDs.appendChild(VBID);
                        }
                    }
                }
                item.appendChild(VBIDs);

                root.appendChild(item);
            }

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(EDfile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store EventDay");
        }
    }

    static public ArrayList<EventDay> Retrieve_EventDays() throws Exception {
        IDMap mapperPL = new IDMap();
        IDMap mapperE = new IDMap();
        IDMap mapperVB = new IDMap();
        ArrayList<EventDay> EDlist = new ArrayList<>();
        TestFile(EDfile, "<EventDay/>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(EDfile));
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("Item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                // create new EventDay
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd ww 'at' hh:mm:ss a zzz");
                EventDay newitem = new EventDay(null, getValue(item, "Description"),
                        getValue(item, "Notes"),
                        formatter.parse(getValue(item, "Date")),
                        formatter.parse(getValue(item, "EndDate")));
                EDlist.add(newitem);

                // map its ID to an ItineraryID
                mapperPL.addMapping(Integer.parseInt(getID(item)),
                        Integer.parseInt(getValue(item, "PL_ID")));

                // map its ID to a eventID
                mapperE.addMapping(Integer.parseInt(getID(item)),
                        Integer.parseInt(getValue(item, "E_ID")));

                // map its ID to a list of VenueBookingIDs
                for (int j = 0; j < getListLength(item, "VB_IDs"); j++) {
                    mapperVB.addMapping(Integer.parseInt(getID(item)),
                            Integer.parseInt(getValList(item, "VB_IDs", j)));
                }
            }
        } catch (Exception e) {
            throw new Exception("EventDay file is corrupted");
        }

        LoadedIDMap.push(mapperPL);
        LoadedIDMap.push(mapperE);
        LoadedIDMap.push(mapperVB);
        return EDlist;
    }

    // VenueBookings ------------------------------------------------------------------------
    static public void Store_VenueBookings(ArrayList<VenueBooking> VBlist,
            ArrayList<Venue> Vlist) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = doc.createElement("VenueBooking");
            doc.appendChild(root);

            int fid;
            for (int i = 0; i < VBlist.size(); i++) {
                // item
                Element item = doc.createElement("Item");
                Attr id = doc.createAttribute("ID");
                id.setValue(Integer.toString(i));
                item.setAttributeNode(id);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd ww 'at' hh:mm:ss a zzz");

                // item - startdate
                Element startdate = doc.createElement("StartDate");
                startdate.appendChild(doc.createTextNode(formatter.format(VBlist.get(i).getStartDate())));
                item.appendChild(startdate);

                // item - enddate
                Element enddate = doc.createElement("EndDate");
                enddate.appendChild(doc.createTextNode(formatter.format(VBlist.get(i).getEndDate())));
                item.appendChild(enddate);

                // item - venueID
                Element VID = doc.createElement("V_ID");
                for (fid = 0; fid < Vlist.size(); fid++) {
                    if (VBlist.get(i).getVenue() == Vlist.get(fid)) {
                        break;
                    }
                }
                if (fid == Vlist.size()) {
                    fid = -1;
                }
                VID.appendChild(doc.createTextNode(Integer.toString(fid)));
                item.appendChild(VID);

                root.appendChild(item);
            }

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(VBfile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store VenueBooking");
        }
    }

    static public ArrayList<VenueBooking> Retrieve_VenueBookings() throws Exception {
        IDMap mapper = new IDMap();
        ArrayList<VenueBooking> VBlist = new ArrayList<>();
        TestFile(VBfile, "<VenueBooking/>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(VBfile));
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("Item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd ww 'at' hh:mm:ss a zzz");
                
                // create new VenueBooking
                VenueBooking newitem = new VenueBooking(null,
                        formatter.parse(getValue(item, "StartDate")),
                        formatter.parse(getValue(item, "EndDate")));
                VBlist.add(newitem);

                // map its ID to a VenueID
                mapper.addMapping(Integer.parseInt(getID(item)),
                        Integer.parseInt(getValue(item, "V_ID")));
            }
        } catch (Exception e) {
            throw new Exception("VenueBooking file is corrupted");
        }

        LoadedIDMap.push(mapper);
        return VBlist;
    }
}
