/* Personnel class, used in PersonnelList
 * 
 * Attributes:
 * - String name: Name of Personnel
 * - String email: Email of Personnel
 * - ArrayList<String> attributes: Other attributes of Personnel
 * - PersonnelList plist: The PersonnelList that the Personnel belongs to
 * 
 * Get methods
 * + String getName()
 * + String getEmail()
 * + ArrayList<String> getAttributes()
 * + PersonnelList getPersonnelList()
 * 
 * Set methods
 * ~ setAttributes(String n, String e, ArrayList<String> att)
 * ~ setPersonnelList(PersonnelList plist)
 * 
 */
package Logic.PersonnelList;

import java.util.*;

/*
 * @author kuan
 */
public class Personnel {
    //  Attributes

    private String name;
    private String email;
    private ArrayList<String> attributes = new ArrayList<>();
    private PersonnelList plist = null;

    //  Constructor
    public Personnel() {
    }

    public Personnel(PersonnelList personnellist, String name, String email, ArrayList<String> attributes) {
        this.name = name;
        this.email = email;
        this.attributes = attributes;
        this.plist = personnellist;
    }

    //  Get methods
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public PersonnelList getPersonnelList() {
        return plist;
    }

    //  Set methods
    void setAttributes(String name, String email, ArrayList<String> attributes) {
        this.attributes = attributes;
        this.name = name;
        this.email = email;
    }

    void setPersonnelList(PersonnelList plist) {
        this.plist = plist;
    }
}
