package Export;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import Logic.*;
import Logic.Event.*;
import Logic.Budget.*;
import Logic.PersonnelList.*;
import Logic.Itinerary.*;
import Logic.Memo.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class Exporter
{
	static private BufferedWriter writer;
	
	public static void Export(String filename, Event event,
			boolean Event_Description,
			boolean Budget,
			boolean Participant,
			boolean Helper,
                        boolean Sponsor,
                        boolean Vendor,
                        boolean Task,
			ArrayList<EventDay> EventDays) {
		
		createFile(filename);
		
		writeCSS("header", "<b>" + event.getTitle() + "</b>");

		if(Event_Description) appendDescription(event);
		if(Budget) appendBudgetCategory(event.getBudgetCategoryList(), event.getUncategorizedBudget(), event.getTotalBudget(), event.getTotalExpense());
		if(Participant) appendPersonnels(event.getParticipantList(), 1);
		if(Helper) appendPersonnels(event.getHelperList(), 2);
		if(Sponsor) appendPersonnels(event.getSponsorList(), 3);
		if(Vendor) appendPersonnels(event.getVendorList(), 4);
                if(Task) appendTasks(event.getTaskList());
		
		for(int i=0; i<EventDays.size(); i++)
			appendEventDays(EventDays.get(i));
		
		closeFile();
	}
	
	static private void createFile(String filename) {
		filename = filename + ".html";
		File file = new File(filename);
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(filename);
			BufferedWriter print = new BufferedWriter(fw);
			writer = print;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		write("<html>");
		write("<head>");
		write("<style type=\"text/css\">");
		
		writeCSSFont("header", "\"Trebuchet MS\", Helvetica, sans-serif", 50, 10, "");
		writeCSSFont("text3", "\"Trebuchet MS\", Helvetica, sans-serif", 23, 10, "");
		writeCSSFont("text2", "\"Trebuchet MS\", Helvetica, sans-serif", 18, 18, "");
		writeCSSFont("text1", "\"Trebuchet MS\", Helvetica, sans-serif", 15, 10, "");
		
		writeCSSHeader("table", "border: 1px solid black; border-collapse:collapse; position:relative; right:-25px");
		writeCSSHeader("tr.odd", "background-color:#EEEEEE;");
		writeCSSHeader("tr.even", "background-color:#DDDDDD;");
		writeCSSHeader("tr.special", "background-color:#BBBBBB;");
		writeCSSHeader("td", "border: 1px solid black; padding:5px;");
		
		write("</style>");
		write("</head>");
		write("");
		write("<body>");
	}
	
	static private void write(String str) {
		try {
			writer.write(str.replaceAll("\n", "<br/>") + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static private void writeNoLine(String str) {
		try {
			writer.write(str.replaceAll("\n", "<br/>"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static private void writeCSS(String classname, String str) {
		write("<div class=\"" + classname + "\">" + str.replaceAll("\n", "<br/>") + "</div>");
	}
	
	static private void writeCSSFont(String classname, String fontfamily, int size, int paddingleft, String others) {
		write("." + classname + "{");
		write("font-family: " + fontfamily + ";");
		write("font-size: " + size + "px;");
		write("padding-left: " + paddingleft + "px;");
		write(others + "}");
	}
	
	static private void writeCSSHeader(String classname, String str) {
		write(classname + "{");
		write(str + "}");
	}
	
	static private void openTable() {
		write("<table width=\"50%\">");
	}
	
	static private void closeTable() {
		write("</table>");
	}
	
	static private void openRowHeader() {
		write("\t<tr>");
	}
	
	static private void openRow(int rownum) {
		if(rownum%2==0) write("\t<tr class=\"odd\">");
		else write("\t<tr class=\"even\">");
	}
	
	static private void openRow() {
		write("\t<tr class=\"special\">");
	}
	
	static private void closeRow() {
		write("\t</tr>");
	}
	
	static private void writeCell(String classname, int widthpercent, String str) {
		writeNoLine("\t\t<td width=\"" + widthpercent + "%\">");
		writeCSS(classname, str);
		write("\t\t</td>");
	}
	
	static private void writeCell(String classname, String str) {
		writeNoLine("\t\t<td>");
		writeCSS(classname, str);
		write("\t\t</td>");
	}
	
	static private void writeCellSpan(String classname, int colspan, String str) {
		writeNoLine("\t\t<td colspan=" + colspan + ">");
		writeCSS(classname, str);
		write("\t\t</td>");
	}
	
	static private void closeFile() {
		write("</body>");
		write("</html>");
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static private void appendDescription(Event event) {
		writeCSS("text2", event.getDescription());
		write("<br/>");
	}
	
	static private void appendBudgetCategory(ArrayList<BudgetCategory> BudgetCategories,
                                                BudgetCategory Uncat,
						long total, long totalexpense) {
		
		writeCSS("text3", "Budget");
		
		write("<br>");
		writeCSS("text2", "Total Budget : $" + (new BigDecimal(total).divide(new BigDecimal("100"))));
		
		for(int i=0; i<BudgetCategories.size(); i++) {
			openTable();
			
			openRowHeader();
				writeCell("text1", "<b>" + BudgetCategories.get(i).getName() + "</b>");
                                writeCell("text1", "<b>$" + (new BigDecimal(BudgetCategories.get(i).getAmount()).divide(new BigDecimal("100"))) + "</b>");
			closeRow();
                        
                        
                        
                        double budget = BudgetCategories.get(i).getAmount();
		
			for(int j=0; j<BudgetCategories.get(i).getExpenseList().size(); j++) {
				openRow(j);
					writeCell("text1", 80, (j+1) + ". " + BudgetCategories.get(i).getExpenseList().get(j).getTitle());
					writeCell("text1", "$" + (new BigDecimal(BudgetCategories.get(i).getExpenseList().get(j).getCost())).divide(new BigDecimal("100")));
				closeRow();
                                budget -= BudgetCategories.get(i).getExpenseList().get(j).getCost();
			}
		
			openRow();
				writeCell("text1", 80, "<b>Balance</b>");
				writeCell("text1", "<b>$" + (new BigDecimal(budget)).divide(new BigDecimal("100")) + "</b>");
			closeRow();
			
			write("<br/>");
		
			closeTable();
		}
                
                openTable();
			
			for(int j=0; j<Uncat.getExpenseList().size(); j++) {
				openRow(j);
					writeCell("text1", 80, (j+1) + ". " + Uncat.getExpenseList().get(j).getTitle());
					writeCell("text1", "$" + (new BigDecimal(Uncat.getExpenseList().get(j).getCost())).divide(new BigDecimal("100")));
				closeRow();
                                
                                
			}
		
		closeTable();
                
                write("<br/>");
                writeCSS("text2", "Total Expense : $" + (new BigDecimal(totalexpense).divide(new BigDecimal("100"))));
		
		write("<br/></br>");
	}
	
	static private void appendPersonnels(PersonnelList contacts, int listtype) {
		
		switch(listtype) {
		
                    case 1:
                        writeCSS("text3", "Participants");
                        break;
                    case 2:
			writeCSS("text3", "Helpers");
                        break;
                    case 3:
			writeCSS("text3", "Sponsors");
                        break;
                    case 4:
			writeCSS("text3", "Vendors");
                }
		
		write("<br>");
		
		openTable();
			
		openRowHeader();
			int size = contacts.getPersonnelHeadersList().size();
                        writeCell("text1", 100/(size+2), "<b>Name</b>");
                        writeCell("text1", 100/(size+2), "<b>Email</b>");
			for(int i=0; i<size; i++)
				writeCell("text1", 100/(size+2), "<b>" + contacts.getPersonnelHeadersList().get(i) + "</b>");
		closeRow();
			
		for(int i=0; i<contacts.getPersonnelList().size(); i++) {
			openRow(i);
			writeCell("text1", (i+1) + ". " + contacts.getPersonnelList().get(i).getName());
			writeCell("text1", contacts.getPersonnelList().get(i).getEmail());
			for(int j=0; j<size; j++)
				writeCell("text1", contacts.getPersonnelList().get(i).getAttributes().get(j));
			closeRow();
		}
		
		closeTable();
	
		write("<br/></br>");
	}
	
	static private void appendEventDays(EventDay eventday) {
		
		writeCSS("text3", eventday.getDescription() + " Programme Listing");
		
                SimpleDateFormat formatterED = new SimpleDateFormat ("d MMMM y, hh:mma");
		write("<br>");
		writeCSS("text2", "Notes : " + eventday.getNotes());
		writeCSS("text2", "Time : " + formatterED.format(eventday.getDate()) + " - " + formatterED.format(eventday.getEndDate()));
		
		String venues = "";
		for(int i=0; i<eventday.getTaggedVenueBookingList().size(); i++) {
			venues = venues + eventday.getTaggedVenueBookingList().get(i).getVenue().getName();
			if(i != eventday.getTaggedVenueBookingList().size()-1)
				venues = venues + ", ";
		}
		
		writeCSS("text2", "Venue(s) : " + venues);
			
		write("<br>");
		
		openTable();
                
                    SimpleDateFormat formatter = new SimpleDateFormat ("hh:mma");
                    for(int i=0; i<eventday.getItinerary().getSortedItineraryItemList().size(); i++) {
			openRow(i);
			ItineraryItem PLI = eventday.getItinerary().getSortedItineraryItemList().get(i);
			writeCell("text1", 30, (i+1) + ". " + formatter.format(PLI.getDate()).toLowerCase() + " - " +
                                formatter.format(PLI.getEndDate()).toLowerCase());
			writeCell("text1", PLI.getName());
			closeRow();
                    }
		
		closeTable();
	
		write("<br/></br>");
	}
        
        static private void appendTasks(ArrayList<Task> Tasks) {
		
		writeCSS("text3", "Tasks to Do");
		write("<br>");
		
		openTable();
                
                    openRowHeader();
                        writeCell("text1", 30, "<b>Deadline</b>");
                        writeCell("text1", 30, "<b>Description</b>");
                        writeCell("text1", 8, "<b>Priority</b>");
                        writeCell("text1", "<b>Venues</b>");
                    closeRow();
                
                    SimpleDateFormat formatter = new SimpleDateFormat ("d MMMM, hh:mma");
                    for(int i=0; i<Tasks.size(); i++) {
			openRow(i);
                        Task task = Tasks.get(i);
			writeCell("text1", 30, (i+1) + ". " + formatter.format(task.getDate()).toLowerCase());
			writeCell("text1", 30, task.getDescription());
                        switch(task.getPriority()) {
                            case 0: writeCell("text1", 8, "High"); break;
                            case 1: writeCell("text1", 8, "Med"); break;
                            case 2: writeCell("text1", 8, "Low");
                        }
                        String venues = "";
                        for(int j=0; j<task.getTaggedVenueBookingList().size(); j++) {
                            venues = venues + task.getTaggedVenueBookingList().get(j).getVenue().getName();
                            if(j != task.getTaggedVenueBookingList().size()-1)
				venues = venues + ", ";
                        }
                        writeCell("text1", venues);
			closeRow();
                    }
		
		closeTable();
	
		write("<br/></br>");
                
	}
}
