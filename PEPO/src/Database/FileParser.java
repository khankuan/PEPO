/* FileParser class
 * 
 * # int getListLength(Element item, String elementName)
 * 
 * # String getValue(Element item, String elementName)
 * # String getValue(Element item, String elementName, int idx)
 * # String getValList(Element item, String elementName, int idx)
 * # String getID(Element item)
 * # boolean TestFile(String filename, String empty)
 * 
 */

package Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.w3c.dom.Element;

/*
 * @author hongwei
 */
public class FileParser {

    // get the number of values in item with the tag "elementName"
    static protected int getListLength(Element item, String elementName) {
        return item.getElementsByTagName(elementName).item(0).getChildNodes().getLength();
    }

    // get the first value of the item with the tag "elementName"
    static protected String getValue(Element item, String elementName) {
        String ans;
        try {
            ans = getValue(item, elementName, 0);
        } catch (Exception obj) {
            return "";
        } // return "" if cannot find

        return ans;
    }

    // get the idx-th value of the item with the tag "elementName"
    static protected String getValue(Element item, String elementName, int idx) {
        String ans;
        try {
            ans = item.getElementsByTagName(elementName).item(idx).getChildNodes().item(0).getNodeValue();
        } catch (Exception obj) {
            return "";
        } // return "" if cannot find

        return ans;
    }

    // get the idx-th value of the first item with the tag "elementName"
    static protected String getValList(Element item, String elementName, int idx) {
        String ans;
        try {
            ans = item.getElementsByTagName(elementName).item(0).getChildNodes().item(idx).getChildNodes().item(0).getNodeValue();
        } catch (Exception obj) {
            return "";
        } // return "" if cannot find

        return ans;
    }

    // retrieve ID attribute of the item
    static protected String getID(Element item) {
        return item.getAttribute("ID");
    }

    // create new file if it is empty
    static protected boolean TestFile(String filename, String empty) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                FileWriter fw = new FileWriter(filename);
                BufferedWriter print = new BufferedWriter(fw);
                print.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + empty);
                print.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return true;
        }
    }
}
