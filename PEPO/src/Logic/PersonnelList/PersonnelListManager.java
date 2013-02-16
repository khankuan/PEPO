/* PersonnelList manager handles all PersonnelList non-public methods
 * Stores all PersonnelList in: ArrayList<PersonnelList> personnelLists
 * 
 * PersonnelList methods
 * + addPersonnel(PersonnelList personnellist, Personnel personnel)
 * + deletePersonnel(PersonnelList personnellist, Personnel personnel)
 * 
 * Manager methods
 * + addPersonnelList(PersonnelList personnellist)
 * + deletePersonnelList(PersonnelList personnellist)
 * + ArrayList<PersonnelList> getPersonnelListList()
 * 
 * Database methods
 * + Ref_PersonnelList_Personnel(IDMap map, ArrayList<PersonnelList> PeLlist, ArrayList<Personnel> Pelist)
 * 
 */
package Logic.PersonnelList;

import java.util.ArrayList;
import Database.IDMap;
import Database.Pair;

/*
 * @author kuan
 */
public class PersonnelListManager {
    //  Storage

    private ArrayList<PersonnelList> personnelLists;

    //  Constructor
    public PersonnelListManager(ArrayList<PersonnelList> arr) {
        personnelLists = arr;
    }

    //  PersonnelList methods
    public void addPersonnel(PersonnelList personnellist, Personnel personnel) {
        personnellist.addPersonnel(personnel);
    }

    public void deletePersonnel(PersonnelList personnellist, Personnel personnel) throws Exception {
        personnellist.deletePersonnel(personnel);
    }

    //  Manager methods
    public void addPersonnelList(PersonnelList personnellist) {
        personnelLists.add(personnellist);
    }

    public void deletePersonnelList(PersonnelList personnellist) {
        personnelLists.remove(personnellist);
    }

    public ArrayList<PersonnelList> getPersonnelListList() {
        return personnelLists;
    }

    //  Set Object Reference for PersonnelList and Personnel
    public static void Ref_PersonnelList_Personnel(IDMap map, ArrayList<PersonnelList> PeLlist, ArrayList<Personnel> Pelist) {
        while (map.hasNext()) {
            Pair mapping = map.NextPair();
            if (mapping.notNull()) {
                PeLlist.get(mapping.First()).addPersonnel(Pelist.get(mapping.Second()));
            }
        }
        map.Reset();
    }
}
