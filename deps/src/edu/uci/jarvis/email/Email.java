package edu.uci.jarvis.email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Email
{
	private String to ="";
	private String subjectline="";
	private String filename="";

public Email(String to, String subjectline, String filename) {
	this.to = to;
	this.subjectline = subjectline;
	this.filename = filename;
}

public String getTo() {
	return to;
}

public String getSubjectline() {
	return subjectline;
}

public String getFilename() {
	return filename;
}

public void sendEmail()
   {
      
     
      // Sender's email ID needs to be mentioned
      String from = "chethana259@gmail.com";

      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(to));

         // Set Subject: header field
         message.setSubject(subjectline);

         // Create the message part 
         BodyPart messageBodyPart = new MimeBodyPart();

         // Fill the message
         messageBodyPart.setText("This is message body");

         // Part two is attachment
         messageBodyPart = new MimeBodyPart();
      
         DataSource source = new FileDataSource(filename);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(filename);
       
         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }
}