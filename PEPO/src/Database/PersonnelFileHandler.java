/* PersonnelFileHandler class
 * 
 * + void Store_PersonnelLists(ArrayList<PersonnelList> PeLlist, ArrayList<Personnel> Pelist)
 * + ArrayList<PersonnelList> Retrieve_PersonnelLists()
 * 
 * + void Store_Personnels(ArrayList<Personnel> Pelist)
 * + ArrayList<Personnel> Retrieve_Personnels()
 * 
 */
package Database;

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

import Logic.PersonnelList.*;

/*
 * @author hongwei
 */
public class PersonnelFileHandler extends FileParser {

    static Stack<IDMap> LoadedIDMap = new Stack<>();
    static final String Pefile = "Data" + File.separator + "Personnel.xml";
    static final String PeLfile = "Data" + File.separator + "PersonnelList.xml";

    // PersonnelLists ------------------------------------------------------------------------
    static public void Store_PersonnelLists(ArrayList<PersonnelList> PeLlist, ArrayList<Personnel> Pelist) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = doc.createElement("PersonnelList");
            doc.appendChild(root);

            int fid;
            for (int i = 0; i < PeLlist.size(); i++) {
                // item
                Element item = doc.createElement("Item");
                Attr id = doc.createAttribute("ID");
                id.setValue(Integer.toString(i));
                item.setAttributeNode(id);

                // item - list of header names
                Element headers = doc.createElement("Headers");
                for (int j = 0; j < PeLlist.get(i).getPersonnelHeadersList().size(); j++) {
                    Element name = doc.createElement("Name");
                    name.appendChild(doc.createTextNode(PeLlist.get(i).getPersonnelHeadersList().get(j)));
                    headers.appendChild(name);
                }
                item.appendChild(headers);

                // item - list of personnelID
                Element PeIDs = doc.createElement("Pe_IDs");
                for (fid = 0; fid < Pelist.size(); fid++) {
                    Iterator<Personnel> it = PeLlist.get(i).getPersonnelList().iterator();
                    while (it.hasNext()) {
                        if (it.next() == Pelist.get(fid)) {
                            Element PeID = doc.createElement("id");
                            PeID.appendChild(doc.createTextNode(Integer.toString(fid)));
                            PeIDs.appendChild(PeID);
                        }
                    }
                }
                item.appendChild(PeIDs);
                root.appendChild(item);
            }

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PeLfile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store PersonnelList");
        }
    }

    static public ArrayList<PersonnelList> Retrieve_PersonnelLists() throws Exception {
        IDMap mapper = new IDMap();
        ArrayList<PersonnelList> PeLlist = new ArrayList<>();
        TestFile(PeLfile, "<PersonnelList/>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(PeLfile));
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("Item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                // create headers list
                ArrayList<String> tempheaders = new ArrayList<>();
                for (int j = 0; j < getListLength(item, "Headers"); j++) {
                    tempheaders.add(getValList(item, "Headers", j));
                }

                // create new PersonnelList
                PersonnelList newitem = new PersonnelList(tempheaders);
                PeLlist.add(newitem);

                // map its ID to a list of personnelIDs
                for (int j = 0; j < getListLength(item, "Pe_IDs"); j++) {
                    mapper.addMapping(Integer.parseInt(getID(item)),
                            Integer.parseInt(getValList(item, "Pe_IDs", j)));
                }
            }
        } catch (Exception obj) {
            throw new Exception("PersonnelList file is corrupted");
        }

        LoadedIDMap.push(mapper);
        return PeLlist;
    }

    // Personnels ------------------------------------------------------------------------
    static public void Store_Personnels(ArrayList<Personnel> Pelist) throws Exception {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = doc.createElement("Personnel");
            doc.appendChild(root);

            for (int i = 0; i < Pelist.size(); i++) {
                // item
                Element item = doc.createElement("Item");
                Attr id = doc.createAttribute("ID");
                id.setValue(Integer.toString(i));
                item.setAttributeNode(id);

                // item - name
                Element name = doc.createElement("Name");
                if (Pelist.get(i).getName() != null) {
                    name.appendChild(doc.createTextNode(Pelist.get(i).getName()));
                } else {
                    name.appendChild(doc.createTextNode(""));
                }
                item.appendChild(name);

                // item - email
                Element email = doc.createElement("Email");
                if (Pelist.get(i).getEmail() != null) {
                    email.appendChild(doc.createTextNode(Pelist.get(i).getEmail()));
                } else {
                    email.appendChild(doc.createTextNode(""));
                }
                item.appendChild(email);

                // item - list of attribute
                Element attr = doc.createElement("Attributes");
                for (int j = 0; j < Pelist.get(i).getAttributes().size(); j++) {
                    Element desc = doc.createElement("Description");
                    if (Pelist.get(i).getAttributes().get(j) != null) {
                        desc.appendChild(doc.createTextNode(Pelist.get(i).getAttributes().get(j)));
                    } else {
                        desc.appendChild(doc.createTextNode(" "));
                    }
                    attr.appendChild(desc);
                }
                item.appendChild(attr);

                root.appendChild(item);
            }

            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Pefile));
            TransformerFactory.newInstance().newTransformer().transform(src, result);
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (Exception obj) {
            throw new Exception("Cannot store Personnel");
        }
    }

    static public ArrayList<Personnel> Retrieve_Personnels() throws Exception {
        ArrayList<Personnel> Pelist = new ArrayList<>();
        TestFile(Pefile, "<Personnel/>");

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(Pefile));
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("Item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                // create list of attributes
                ArrayList<String> tempattr = new ArrayList<>();
                for (int j = 0; j < getListLength(item, "Attributes"); j++) {
                    tempattr.add(getValList(item, "Attributes", j));
                }

                // create new personnel
                Personnel newitem = new Personnel(null, getValue(item, "Name"),
                        getValue(item, "Email"), tempattr);
                Pelist.add(newitem);
            }
        } catch (Exception obj) {
            throw new Exception("Personnel file is corrupted");
        }

        return Pelist;
    }
}
