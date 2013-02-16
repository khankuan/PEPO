/* Personnel manager handles all Personnel non-public methods
 * Stores all Personnel in: ArrayList<Personnel> personnels
 * 
 * Personnel methods
 * + SetAttribute(Personnel personnel, String name, String email, ArrayList<String> attributes)
 * 
 * Manager methods
 * + addPersonnel(Personnel personnel)
 * + deletePersonnel(Personnel personnel)
 * + ArrayList<Personnel> getPersonnelList()
 * 
 * Database methods
 * + Ref_Personnel_PersonnelList(IDMap map, ArrayList<PersonnelList> PeLlist, ArrayList<Personnel> Pelist)
 * 
 */
package Logic.PersonnelList;

import java.util.ArrayList;
import Database.*;

/*
 * @author kuan
 */
public class PersonnelManager {
    //  Storage

    private ArrayList<Personnel> personnels;

    //  Constructor
    public PersonnelManager(ArrayList<Personnel> arr) {
        personnels = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            personnels.add(arr.get(i));
        }
    }

    //  Personnel methods
    public void SetAttribute(Personnel personnel, String name, String email, ArrayList<String> attributes) throws Exception {
        personnel.setAttributes(name, email, attributes);
    }

    //  Manager methods
    public void addPersonnel(Personnel personnel) {
        personnels.add(personnel);
    }

    public void deletePersonnel(Personnel personnel) {
        personnels.remove(personnel);
    }

    public ArrayList<Personnel> getPersonnelList() {
        return personnels;
    }

    //  Set Object Reference for Personnel and PersonnelList
    public static void Ref_Personnel_PersonnelList(IDMap map, ArrayList<PersonnelList> PeLlist, ArrayList<Personnel> Pelist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                Pelist.get(mapping.Second()).setPersonnelList(PeLlist.get(mapping.First()));
            }
        }
        map.Reset();
    }
}
