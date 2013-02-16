package Logic.PersonnelList;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * @author kuan
 */
public class MailClient {
    //  Variables of mail

    private static String fromEmail;
    private static String[] toEmail;
    private static String mailSubject;
    private static String mailBody;
    private static String smtp;
    private static String mailUsername;
    private static String mailPassword;
    private static Socket s;
    private static DataOutputStream serverWriter;
    private static BufferedReader serverReader;

    //  Send email from client
    public static void send(String from, String[] to, String subject, String body) throws Exception {
        if (to.length == 0)
            throw new Exception("No valid recipient");
        //  Initialise inputs
        if (from.indexOf("/") >= 0) {
            fromEmail = from.substring(from.indexOf("/") + 1);
        } else {
            fromEmail = from.substring(from.indexOf("\\") + 1);
        }
        toEmail = to;
        mailSubject = subject;
        mailBody = body;
        //  Start sending of mail
        serverWriter.writeBytes("MAIL FROM:" + fromEmail + "\r\n");
        String buffer = serverReader.readLine();

        //  Check if FROM address is valid
        if (buffer.equals("501 5.5.4 Invalid Address")) {
            throw new Exception("Invalid sender address");
        }

        //  Sending receipent's email
        for (int i = 0; i < to.length; i++) {
            serverWriter.writeBytes("RCPT TO:" + toEmail[i] + "\r\n");
            buffer = serverReader.readLine();
            //System.out.println("adding " + toEmail[i]);
            //System.out.println(buffer);
        }


        //  Sending DATA of mail
        serverWriter.writeBytes("DATA" + "\r\n");
        serverReader.readLine();        //  DATA response   
/**/ serverWriter.writeBytes("From:" + fromEmail + "\r\n" + "To:\r\n" + "Subject:" + mailSubject + "\r\n" + mailBody + "\r\n" + "." + "\r\n");
        String response = serverReader.readLine();
        //System.out.println(response);
        //  Check if mail is successfully queued
        //  if (!response.contains("Queued mail for delivery"))
        //        throw new Exception("Unknown sending error.");
    }

    //  Creating MailClient with login info
    public static void login(String username, String password) throws Exception {
        //  initialise variables
        smtp = "smtp.nus.edu.sg";
        mailUsername = username;
        mailPassword = password;

        //  Open socket
        try {
            s = new Socket(smtp, 25);
        } catch (UnknownHostException ex) {  //  Unknown host found
            throw new Exception("Unknown SMTP server");
        } catch (SecurityException ex) {  //  Unknown host found
            throw new Exception("Security error");
        } catch (IOException ex) {  //  I/O error
            throw new Exception("I/O error");
        }

        // Open stream to write to server
        OutputStream os = s.getOutputStream();
        serverWriter = new DataOutputStream(os); //to write string,otherwise, need to write byte array

        // Open input stream to read from server
        InputStreamReader isrServer = new InputStreamReader(s.getInputStream());
        serverReader = new BufferedReader(isrServer);

        if (!serverReader.readLine().substring(0, 3).equals("220")) //  connected message
        {
            throw new Exception("Error connecting");
        }

        //  Hello command to server      
        serverWriter.writeBytes("EHLO\r\n");
        while (true) {   //  reading hello reply
            serverReader.readLine();    //  hello reply
            if (!serverReader.ready()) {
                break;
            }
        }

        //  Logs in with username and password
        serverWriter.writeBytes("AUTH LOGIN " + mailUsername + "\r\n");
        if (!serverReader.readLine().substring(0, 3).equals("334")) //  334 ask for password
        {
            throw new Exception("Error while logging in");
        }
        serverWriter.writeBytes(mailPassword + "\r\n");
        String buffer = serverReader.readLine();

        //  Check if login is successful
        if (buffer.equals("535 5.7.3 Authentication unsuccessful")) {
            throw new Exception("Authentication unsuccessful");
        }
    }

    public static void logout() throws Exception {
        if (!s.isConnected()) //  make sure s is connected
        {
            throw new Exception("Socket not connected");
        }
        try {
            serverWriter.writeBytes("QUIT" + "\r\n");
            serverReader.readLine();    //  QUIT response

            // Close socket
            s.close();
        } catch (IOException ex) {
        }  //  buffer remaining when close
    }
}
