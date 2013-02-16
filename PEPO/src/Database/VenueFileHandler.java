/* VenueFileHandler class
 * 
 * + ArrayList<Venue> Retrieve_Venues()
 * 
 */
package Database;

import java.util.ArrayList;
import Logic.Venue.*;
import java.io.BufferedReader;
import java.io.FileReader;

/*
 * @author hongwei
 */
public class VenueFileHandler {

    static final String Vfile = "Venues.txt";

    // Venues ------------------------------------------------------------------------
    // will retrieve from Venues.txt, full version will require connection to an online database
    static public ArrayList<Venue> Retrieve_Venues() throws Exception {
        ArrayList<Venue> Vlist = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(Vfile));
            for (int i = 0; i < 11; i++) // skip instructions
            {
                reader.readLine();
            }

            String name = reader.readLine();
            String type = "";
            String facu = "";
            String capa = "";

            while (!name.equals("END")) {

                // retrieve values
                name = name.substring(6);
                type = reader.readLine().substring(6);
                facu = reader.readLine().substring(6);
                capa = reader.readLine().substring(6);
                Vlist.add(new Venue(name, type, facu, Integer.parseInt(capa)));

                reader.readLine();  // ignore space
                name = reader.readLine();
            }
        } catch (Exception obj) {
            throw new Exception("Venue file is corrupted");
        }

        return Vlist;
    }
}
