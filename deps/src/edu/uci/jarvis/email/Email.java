package edu.uci.jarvis.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * This is a utility class that sends e-mail messages.
 * 
 * @author Ha Minh Nam
 * 
 */
public class Email {

	/**
	 * Sends an e-mail with attachments.
	 * 
	 * @param host
	 *            address of the server
	 * @param port
	 *            port number of the server
	 * @param userName
	 *            email address used to send mails
	 * @param password
	 *            password of the email account
	 * @param toAddress
	 *            email address to send
	 * @param subject
	 *            title of the email
	 * @param message
	 *            content of the email
	 * @param attachFiles
	 *            an array of file paths
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendEmail(String message, String[] attachFiles)
			throws AddressException, MessagingException {
		// TODO: Read Email properties from file
		// sets SMTP properties
		String host = "smtp.gmail.com";
		String port = "587";
		String mailFrom = "nisha.aram@gmail.com";
		String mailTo = "cs237ss@gmail.com";
		String password = "kryptonite08!!";
		String subject = "New email";
		// String bodyMessage =
		// "<html><b>Hello</b><br/><i>This is an HTML email with an attachment</i></html>";
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", mailFrom);
		properties.put("mail.password", password);

		// creates a new session with an authenticator
		Authenticator auth = new SMTPAuthenticator(mailFrom, password);
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		MimeMessage msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(mailFrom));
		InternetAddress[] toAddresses = { new InternetAddress(mailTo) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachFiles != null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				addAttachment(multipart, filePath);
			}
		}

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);

	}

	/**
	 * Adds a file as an attachment to the email's content
	 * 
	 * @param multipart
	 * @param filePath
	 * @throws MessagingException
	 */
	private void addAttachment(Multipart multipart, String filePath)
			throws MessagingException {
		MimeBodyPart attachPart = new MimeBodyPart();
		DataSource source = new FileDataSource(filePath);
		attachPart.setDataHandler(new DataHandler(source));
		attachPart.setFileName(new File(filePath).getName());

		multipart.addBodyPart(attachPart);
	}

	/**
	 * This class provides authentication information.
	 * 
	 * @author Ha Minh Nam
	 * 
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		private String userName;
		private String password;

		public SMTPAuthenticator(String userName, String password) {
			this.userName = userName;
			this.password = password;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(userName, password);
		}
	}
}
