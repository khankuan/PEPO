/* PersonnelList class, used in Events
 * 
 * Attributes:
 * - ArrayList<Personnel> personnelList: List of Personnels
 * - ArrayList<String> headers: Headers of the PersonnelList
 * 
 * Get methods
 * + ArrayList<String> getPersonnelHeadersList()
 * + ArrayList<Personnel> getPersonnelList()
 * 
 * Set methods
 * ~ addPersonnel(Personnel personnel)
 * ~ deletePersonnel(Personnel personnel)
 * ~ setPersonnelList(ArrayList<Personnel> personnelList)
 * 
 */
package Logic.PersonnelList;

import java.util.ArrayList;

/*
 * @author kuan
 */
public class PersonnelList {
    //  Attributes

    private ArrayList<Personnel> personnelList = new ArrayList<>();
    private ArrayList<String> headers = new ArrayList<>();

    //  Constructor
    public PersonnelList() {
    }

    public PersonnelList(ArrayList<String> headers) {
        this.headers = headers;
    }

    //  Get methods
    public ArrayList<String> getPersonnelHeadersList() {
        return new ArrayList<>(headers);
    }

    public ArrayList<Personnel> getPersonnelList() {
        return new ArrayList<>(personnelList);
    }

    //  Set methods
    void addPersonnel(Personnel personnel) {
        personnelList.add(personnel);
    }

    void deletePersonnel(Personnel personnel) throws Exception {
        if (personnelList.remove(personnel)) {
            return;
        } else {
            throw new Exception("Personnel not found");
        }
    }

    void setPersonnelList(ArrayList<Personnel> personnelList) {
        this.personnelList = personnelList;
    }
}
