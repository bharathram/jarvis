package edu.uci.jarvis.email;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
 
public class Sedit {
    public static void main(String[] args) throws AddressException, MessagingException {
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "nisha.aram@gmail.com";
        String mailTo = "cs237ss@gmail.com";
        String password = "kryptonite08!!";
        String subject = "New email";
        String bodyMessage = "<html><b>Hello</b><br/><i>This is an HTML email with an attachment</i></html>";
        
        Email sender = new Email();
        String[] fileAttachment = {"C:\\Users\\Nisha\\hi.png"};
        sender.sendEmail(host, port, mailFrom, password, mailTo, subject, bodyMessage, fileAttachment);      
        System.out.println("Done");
    }
}