package TextUI;

import Export.Exporter;
import Logic.Budget.Expense;
import Logic.Event.Event;
import Logic.Itinerary.Itinerary;
import Logic.LogicController;
import Logic.Memo.Day;
import Logic.Memo.EventDay;
import Logic.Memo.MemoItem;
import Logic.Memo.Memoable;
import Logic.Memo.Task;
import Logic.Venue.Venue;
import Logic.Venue.VenueBooking;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;


public class TextUI {
    static boolean batching = false;
    static boolean batchcommandssuccess = true;
    static boolean print = true;
    static Scanner scans;
    static SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmm");
    static FileOutputStream out;
    static Scanner batchscans;
    
    static Event gete(String index){
        return LogicController.getEventList().get(Integer.parseInt(index));
    }
    
    
    public static boolean deleteDir(File dir) {

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        if (!dir.isDirectory())
            return dir.delete();
        return true;
    }
    
    public static boolean initialiseData(File dir) throws FileNotFoundException, IOException {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i < children.length; i++) {                  
                FileReader in = new FileReader(new File(dir, children[i]));
                FileWriter out = new FileWriter(new File ("Data/"+children[i]));
                int c;

                while ((c = in.read()) != -1)
                  out.write(c);

                in.close();
                out.close();
            }
        }      
        return true;
    }
    
    public static boolean compareOutput(String inputfile) throws FileNotFoundException{
        Scanner expectedscans = new Scanner(new File(inputfile+"/expectedoutput.txt"));
        Scanner loggedscans = new Scanner(new File(inputfile+"/pepotest_output.txt"));
        int line = 1;
        while(expectedscans.hasNext()){
            String ecur = expectedscans.nextLine();
            String lcur = loggedscans.nextLine();
            if (!ecur.equals(lcur)){
                System.out.println("Test fail at line "+line);
                System.out.println("Expected: "+ecur);
                System.out.println("Actual: "+lcur);
                expectedscans.close();
                loggedscans.close();
                return false;
            }
            line++;
        }
        if (loggedscans.hasNext()){
            System.out.println("Test fail: Actual output is longer than expected output.");
            expectedscans.close();
            loggedscans.close();
            return false;
        }
        return true;
    }
    
    public static boolean compareXMLs(String inputfile) throws FileNotFoundException{
        String[] filename = {"ArchiveMap","BudgetCategory","Event","EventDay","Expense","Itinerary","ItineraryItem",
        "Personnel","PersonnelList","Task","VenueBooking"};
        
        boolean same = true;
        for (int i = 0; i < filename.length; i++){
            Scanner expectedscans = new Scanner(new File(inputfile+"/ExpectedData/"+filename[i]+".xml"));
            Scanner loggedscans = new Scanner(new File("Data/"+filename[i]+".xml"));         
            int line = 1;
            boolean pass = true;
            while(expectedscans.hasNext()){
                String ecur = expectedscans.nextLine();
                String lcur = loggedscans.nextLine();
                if (!ecur.equals(lcur)){
                    System.out.println(filename[i]+".xml test fail at line "+line);
                    System.out.println("Expected: "+ecur);
                    System.out.println("Actual: "+lcur);
                    expectedscans.close();
                    loggedscans.close();
                    same = false;
                    break;
                }
                line++;
            }
            if (!pass)
                continue;
            if (loggedscans.hasNext()){
                System.out.println(filename[i]+".xml test fail: Actual output is longer than expected output.");
                expectedscans.close();
                loggedscans.close();
                same = false;
                continue;
            }    
            expectedscans.close();
            loggedscans.close();
        }
        return same;
    }
    
    static void textMain() throws IOException{

        // to exit, write "exit"
        if (!scans.hasNext())
            return;
        
        String input = scans.nextLine();
        String oldinput = input;
        if (input.equals("exit") || input.equals("ENDOFTEST"))
            return;
        
        if (!input.contains(" ")){
            System.out.println("Invalid command");
            textMain();
            return;
        }
        
        String function = input.substring(0,input.indexOf(" ")).toLowerCase(); 
        input = input.substring(input.indexOf(" ")+1);
        String[] preparameters = input.split(";");
        String[] parameters = new String[10];
        for (int i = 0; i < preparameters.length; i++)
            parameters[i] = preparameters[i];
        Task mytask;
        try {
            switch(function)
            {
                //  Event-General
                case "addevent":
                    LogicController.addEvent(parameters[0], parameters[1]);
                    break;
                
                case "archiveevent":
                    LogicController.archiveEvent(gete(parameters[0]));
                    break;
                
                case "unarchiveevent":
                    LogicController.unArchiveEvent(LogicController.getArchivedEventList().get(Integer.parseInt(parameters[0])));
                    break;    
                    
                case "deleteevent":
                    LogicController.deleteEvent(gete(parameters[0]));
                    break;
                    
                case "seteventtitle":
                    LogicController.setEventTitle(gete(parameters[0]), parameters[1]);
                    break; 
                    
                case "seteventdescription":
                    LogicController.setEventDescription(gete(parameters[0]), parameters[1]);
                    break;      
                    
                    
                //  Event-Budget
                case "seteventtotalbudget":
                    LogicController.setEventTotalBudget(gete(parameters[0]), Long.parseLong(parameters[1]));
                    break;
                    
                case "addbudgetcategory":
                    // command line to call - addbudgetcategory 0;bcnameanyhow;32 (note use comma and no space for parameters)
                    LogicController.addBudgetCategory(
                            LogicController.getEventList().get(Integer.parseInt(parameters[0])),
                                parameters[1], Long.parseLong(parameters[2]));
                    break; 
                    
               
                //  Event-Task
                case "addtask":
                    if (!parameters[0].equals("none"))
                        LogicController.addTask(gete(parameters[0]), parameters[1], date.parse(parameters[2]), Integer.parseInt(parameters[3]));
                    else 
                        LogicController.addTask(null, parameters[1], date.parse(parameters[2]), Integer.parseInt(parameters[3]));
                    break;
                    
                case "deletetask":
                    mytask = LogicController.getTaskList().get(Integer.parseInt(parameters[0]));
                    LogicController.deleteTask(mytask);
                    break;
                    
                                     
                //  Event-EventDay
                case "addeventdaywithitinerary":
                    LogicController.addEventDayWithItinerary(gete(parameters[0]), parameters[1], parameters[2], date.parse(parameters[3]), date.parse(parameters[4]), new Itinerary()); //how to get itenary <- use empty one.
                    break;  
                
                case "deleteeventday":
                    LogicController.deleteEventDay(gete(parameters[0]).getEventDayList().get(Integer.parseInt(parameters[1])));
                    break;
                    
                //  Event-Participant
                case "setparticipantlist":  //  setparticipantlist <event#>;age;gender;address
                    ArrayList<String> attributes = new ArrayList<>();
                    for (int i = 1; i < parameters.length; i++){
                        if (parameters[i] == null)
                            break;
                        attributes.add(parameters[i]);
                    }
                    LogicController.setParticipantList(gete(parameters[0]), attributes);
                    break;
                    
                case "deleteparticipantlist":
                    LogicController.deleteParticipantList(gete(parameters[0]));
                    break;
                    
                    
                    
                //  Budget and expense    
                case "addexpense":
                    if (parameters[1].equals("none"))
                        LogicController.addExpense(gete(parameters[0]).getUncategorizedBudget(),parameters[2], Long.parseLong(parameters[3]));
                    else
                        LogicController.addExpense(gete(parameters[0]).getBudgetCategoryList().get(Integer.parseInt(parameters[1])),parameters[2], Long.parseLong(parameters[3]));
                    break;
                    
                case "deleteexpense": 
                    LogicController.deleteExpense(gete(parameters[0]).getBudgetCategoryList().get(Integer.parseInt(parameters[1])).getExpenseList().get(Integer.parseInt(parameters[2])));
                    break;
                    
                case "setbudgetcategoryname": 
                    LogicController.setBudgetCategoryName(gete(parameters[0]).getBudgetCategoryList().get(Integer.parseInt(parameters[1])), parameters[2]);
                    break;
                    
                case "setbudgetcategoryamount": 
                    LogicController.setBudgetCategoryAmount(gete(parameters[0]).getBudgetCategoryList().get(Integer.parseInt(parameters[1])), Long.parseLong(parameters[2]));
                    break;
                 
                case "setexpensetitle":  
                    LogicController.setExpenseTitle(gete(parameters[0]).getBudgetCategoryList().get(Integer.parseInt(parameters[1])).getExpenseList().get(Integer.parseInt(parameters[2])), parameters[3]);
                    break;
                
                case "setexpensecost":  
                    LogicController.setExpenseCost(gete(parameters[0]).getBudgetCategoryList().get(Integer.parseInt(parameters[1])).getExpenseList().get(Integer.parseInt(parameters[2])), Long.parseLong(parameters[3]));
                    break;
                
                case "setexpensebudgetcategory":    //  setexpensebudgetcategory <event#>;<originalCategory#>;<Expense#>;<NewCategory#>
                    Expense from;
                    if (parameters[1].equals("none"))
                        from = gete(parameters[0]).getUncategorizedBudget().getExpenseList().get(Integer.parseInt(parameters[2]));
                    else
                        from = gete(parameters[0]).getBudgetCategoryList().get(Integer.parseInt(parameters[1])).getExpenseList().get(Integer.parseInt(parameters[2]));
                    if (parameters[3].equals("none"))
                        LogicController.setExpenseBudgetCategory(from,gete(parameters[0]).getUncategorizedBudget());
                    else
                        LogicController.setExpenseBudgetCategory(from,gete(parameters[0]).getBudgetCategoryList().get(Integer.parseInt(parameters[3])));
                    break;
                
                    
                    
                    
                //  Event Day
                case "seteventdaynotes":   //  seteventdaynotes <event#>;<eventday#>;notes of event day
                    LogicController.setEventDayNotes(gete(parameters[0]).getEventDayList().get(Integer.parseInt(parameters[1])), parameters[2]);
                    break;
                
                case "seteventdaydescription":   
                    LogicController.setEventDayDescription(gete(parameters[0]).getEventDayList().get(Integer.parseInt(parameters[1])), parameters[2]);
                    break;
                
                case "seteventdaydate":
                    LogicController.setEventDayDate(gete(parameters[0]).getEventDayList().get(Integer.parseInt(parameters[1])), date.parse(parameters[2]) , date.parse(parameters[3]));
                    break;
                    
                case "tageventdayvenuebooking":   //    tageventdayvenuebooking <Event#>;<EventDay#>;<venuebooking#>
                    LogicController.tagEventDayVenueBooking(LogicController.getEventList().get(Integer.parseInt(parameters[0])).getEventDayList().get(Integer.parseInt(parameters[1])), LogicController.getVenueBookingList().get(Integer.parseInt(parameters[2])));
                    break;  
                
                case "untageventdayvenuebooking":
                    LogicController.untagEventDayVenueBooking(LogicController.getEventList().get(Integer.parseInt(parameters[0])).getEventDayList().get(Integer.parseInt(parameters[1])), LogicController.getVenueBookingList().get(Integer.parseInt(parameters[2])));
                    break;  
                    
                    
                    
                    
                //  Contacts (Participants only)   <- ignore setPersonnelAttribute                
                case "addparticipant":
                    ArrayList<String> personnelattributes = new ArrayList<>();
                    for (int i = 3; i < parameters.length; i++){
                        if (parameters[i] == null)
                            break;
                        personnelattributes.add(parameters[i]);
                    }
                    LogicController.addPersonnel(gete(parameters[0]).getParticipantList(), parameters[1], parameters[2], personnelattributes);
                    break;
                
                case "deleteparticipant":
                    LogicController.deleteParticipantList(gete(parameters[0]));
                    break;
                    
                
       
                    
                //  Task
                case "settaskevent":    //  settaskevent <task#>;<event#>           
                    Task mytaskset = LogicController.getTaskList().get(Integer.parseInt(parameters[0]));
                    if (parameters[1].equals("none"))   //  for task without event
                        LogicController.setTaskEvent(mytaskset, null);
                    else
                        LogicController.setTaskEvent(mytaskset, gete(parameters[1]));
                    break;
                
                case "settaskpriority":  
                    mytask = LogicController.getTaskList().get(Integer.parseInt(parameters[0]));
                    LogicController.setTaskPriority(mytask, Integer.parseInt(parameters[1]));
                    break;
                    
                case "setcompleted": 
                    mytask = LogicController.getTaskList().get(Integer.parseInt(parameters[0]));
                    LogicController.setCompleted(mytask, Boolean.parseBoolean(parameters[1]));
                    break;
                
                case "settaskdescription": 
                    mytask = LogicController.getTaskList().get(Integer.parseInt(parameters[0]));
                    LogicController.setTaskDescription(mytask, parameters[1]);
                    break;
                    
                case "settaskdate": 
                    mytask = LogicController.getTaskList().get(Integer.parseInt(parameters[0]));
                    LogicController.setTaskDate(mytask, date.parse(parameters[1]));
                    break;
                    
                case "tagtaskvenuebooking":   //    tagtaskvenuebooking <task#>;<venuebooking#>
                    LogicController.tagTaskVenueBooking(LogicController.getTaskList().get(Integer.parseInt(parameters[0])), LogicController.getVenueBookingList().get(Integer.parseInt(parameters[1])));
                    break;
                
                case "untagtaskvenuebooking": 
                    LogicController.untagTaskVenueBooking(LogicController.getTaskList().get(Integer.parseInt(parameters[0])), LogicController.getVenueBookingList().get(Integer.parseInt(parameters[1])));
                    break;                    
                    
                    
                    
                    
                //  VenueBooking
                case "addvenuebooking":   //  addvenuebooking <venue#>;<start>;<end>
                    LogicController.addVenueBooking(LogicController.getVenueList().get(Integer.parseInt(parameters[0])), date.parse(parameters[1]), date.parse(parameters[2]));
                    break;
                
                case "deletevenuebooking":
                    LogicController.deleteVenueBooking(LogicController.getVenueBookingList().get(Integer.parseInt(parameters[0])));
                    break;   
                    
                
                    
                    
                //  Itinerary
                case "additineraryitem":   //  additinereraryitem <event#>;<everyday#>;description;0800;0900
                    GregorianCalendar start = new GregorianCalendar(2005,1,1);
                    start.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(parameters[3].substring(0, 2)));   
                    start.set(GregorianCalendar.MINUTE, Integer.parseInt(parameters[3].substring(2)));   
                    GregorianCalendar end = new GregorianCalendar(2005,1,1);
                    end.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(parameters[4].substring(0, 2)));   
                    end.set(GregorianCalendar.MINUTE, Integer.parseInt(parameters[4].substring(2)));   
                    
                    LogicController.addItineraryItem(gete(parameters[0]).getEventDayList().get(Integer.parseInt(parameters[1])).getItinerary(), parameters[2],start.getTime() , end.getTime());
                    break;
                
                case "deleteitineraryitem":   // index of itineraryitems sorted based on starttime
                    LogicController.deleteItineraryItem(gete(parameters[0]).getEventDayList().get(Integer.parseInt(parameters[1])).getItinerary().getSortedItineraryItemList().get(Integer.parseInt(parameters[2])));
                    break;    
                    
                    
                    
                //  Exit    
                case "exit":
                    LogicController.exit();
                    return;
                    
                
                   
                    
                //  Testing
                /*
                     *  Test folder contains:
                     *  Data Folder: xml of expectefd output
                     *  testcommands.txt: txt file containing commands
                     *  expectedoutput.txt: txt file containing expected output from testing
                     * 
                     */
                case "pepotest":    //  pepotest testfolder
                    if (!parameters[0].equals("system") && !parameters[0].equals("unit"))
                        throw new Exception("Invalid test");
                    if (!batching)
                        System.out.printf("PEPOTEST needs to replace any data in Data folder before testing. Press '1' to replace: ");
                    String option = scans.nextLine();
                    System.out.println("");
                    if (!option.equals("1")){
                        batchcommandssuccess = false; 
                        throw new Exception("Unable to replace data: "+parameters[1]);
                    }
                    LogicController.exit();
                    File datafolder = new File("Data");
                    deleteDir(datafolder);
                    initialiseData(new File("PEPOTest/"+parameters[1]+"/initialData"));
                    LogicController.start();
                    Scanner oldscans = scans;
                    scans = new Scanner(new File("PEPOTest/"+parameters[1]+"/testcommands.txt"));
                    out.close();
                    out = new FileOutputStream("PEPOTest/"+parameters[1]+"/pepotest_output.txt");
                    print = false;
                    textMain();
                    print = true;
                    out.close();
                    out = new FileOutputStream("logger.txt",true);
                    scans = oldscans;
                    
                    boolean somefailed = false;
                    //  Compare output file
                    if (compareOutput("PEPOTest/"+parameters[1]))
                        System.out.println(parameters[1].replace(".txt", "")+ ": Output test success!");
                    else{
                        somefailed = true;
                        System.out.println(parameters[1].replace(".txt", "")+ ": Output test failed.");
                    }
                    
                    LogicController.exit();
                    LogicController.start();
                    if (parameters[0].equals("system")){
                        if (compareXMLs("PEPOTest/"+parameters[1]))
                            System.out.println(parameters[1].replace(".txt", "")+ ": Data test success!");
                        else {
                            System.out.println(parameters[1].replace(".txt", "")+ ": Data test failed.");
                            somefailed = true;
                        }
                    }
                    
                    if (somefailed)
                        throw new Exception(parameters[1]);
                    break;
                    
                
                case "batchcommands":

                    Scanner old = scans;
                    scans = new Scanner(new File("PEPOTest/"+parameters[0]));
                    batchscans = scans;
                    batching = true;  
                    batchcommandssuccess = true;
                    textMain();
                    batching = false;
                    scans = old;
                    System.out.println("");
                    if (!batchcommandssuccess)
                        throw new Exception("Some command(s) failed");
                    input = parameters[0].replace(".txt", "");
                    break;
                    
                default:
                    throw new Exception("Invalid command");
            } 
            
            String output = "Success! - " + oldinput;
            if (print)
                System.out.println(output);
            out.write(output.getBytes());
            out.write("\r\n".getBytes());
        
        } catch (Exception ex){
            String output = "Fail! - " + ex.getMessage();
            if (scans == batchscans)
                batchcommandssuccess = false;
            if (print)
                System.out.println(output);
            out.write(output.getBytes());
            out.write("\r\n".getBytes());
        }

        textMain();
    }
  
    
    public static void run() throws Exception{
        LogicController.start();
        scans = new Scanner(System.in);
        out = new FileOutputStream("logger.txt",true);
        textMain();
        exit();
    }
    
    public static void exit() throws Exception{
        LogicController.exit();
    }
}