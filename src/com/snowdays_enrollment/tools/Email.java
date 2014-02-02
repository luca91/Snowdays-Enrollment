//Code inspired from: http://zetcode.com/tutorials/jeetutorials/sendingemail/

package com.snowdays_enrollment.tools;

import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
* Class used to send an email
* 
* @author Luca Barazzuol
*/
public class Email {
	
	// commons logging references
	static Logger log = Logger.getLogger(Email.class.getName());
	
	/**
	 * @uml.property  name="from"
	 */
	String from = "ems2013.staff@gmail.com";
    /**
	 * @uml.property  name="login"
	 */
    String login = "ems2013.staff@gmail.com";
    /**
	 * @uml.property  name="password"
	 */
    String password = "PaSsWoRd";
	
    /**
	 * @uml.property  name="props"
	 * @uml.associationEnd  qualifier="constant:java.lang.String java.lang.String"
	 */
    Properties props = new Properties();
    
    /**
     * Get the properties for the configuration
     * 
     */
	public Properties getProps() {
		return props;
	}
	
	public boolean sendEmail(String to, String subject, String text){
//		final String username = "username@gmail.com";
//		final String password = "password";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(login, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(text);
 
			Transport.send(message);
 
			System.out.println("Done");
			return true;
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

//    /**
//     * Constructor
//     * 
//     */
//	public Email(){
//        props.put("mail.smtp.starttls.enable", "true"); // added this line
//        props.put("mail.smtp.host", "smtp.googlemail.com");
//        props.put("mail.smtp.user", from);
//        props.put("mail.smtp.password", password);
////        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//	}
//	
//    /**
//     * Send an email
//     * 
//     * @param to
//     * @param subject of the message
//     * @param the message
//     * @return a boolean (true if the message has been sent, false otherwise)
//     */
//    public boolean sendEmail(String to, String subject, String message){ 
//    	log.debug("address: " + to);
//
//        Authenticator auth = new SMTPAuthenticator(login, password);
//
//        Session session = Session.getInstance(props, auth);
//
//        try {
////        	Message msg = new MimeMessage(session);
////			msg.setText(message);
////            msg.setSubject(subject);
////            msg.setFrom(new InternetAddress(from));
////            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            Transport tr = session.getTransport("smtp");
//            tr.connect(session.getProperty("mail.smtp.host"),  session.getProperty("mail.smtp.user"), session.getProperty("mail.smtp.password"));
//            Message msg = new MimeMessage(session);
//            msg.saveChanges();
//            tr.sendMessage(msg, msg.getAllRecipients());
//            tr.close();
//            return true;   
//        } catch (AuthenticationFailedException ex) {
//        	log.debug("AuthenticationFailedException");
//        	log.debug(ex);
//        	return false;
//        } catch (AddressException ex) {
//        	log.debug("AddressException");
//        	log.debug(ex);
//        	return false;
//
//        } catch (MessagingException ex) {
//        	log.debug("MessagingException");
//        	log.debug(ex);
//        	return false;
//        }
//    }	

    
//    /**
//    * Class used to perform authentication for the mail provider
//    * 
//    * @author http://zetcode.com/tutorials/jeetutorials/sendingemail/
//    */
//    private class SMTPAuthenticator extends Authenticator {
//
//        private PasswordAuthentication authentication;
//
//        public SMTPAuthenticator(String login, String password) {
//            authentication = new PasswordAuthentication(login, password);
//        }
//
//        protected PasswordAuthentication getPasswordAuthentication() {
//            return authentication;
//        }
//    }
    
	
//	/**
//	 * Main to test
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		Email e = new Email();
//		e.sendEmail("ems2013.staff@gmail.com", "subject", "message");
//
//	}
}
