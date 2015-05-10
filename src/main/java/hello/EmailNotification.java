package hello;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Adwait on 5/9/2015.
 */
public class EmailNotification {

    final Properties configProp = new Properties();

    public void sendEmail(String UserEmail, String albumName) {

        try {
            configProp.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String NotificationEmail = new String(configProp.getProperty("NOTIFICATION_EMAIL"));
        String NotificationPwd = new String(configProp.getProperty("NOTIFICATION_PWD"));
        String NotificationHost = new String(configProp.getProperty("NOTIFICATION_HOST"));
        String NotificationPort = new String(configProp.getProperty("NOTIFICATION_PORT"));

        // Recipient's email ID needs to be mentioned.
        String to = UserEmail;

        // Sender's email ID needs to be mentioned
        String from = NotificationEmail;


        // Assuming you are sending email from localhost
        String host = NotificationHost;

        // Get system properties
        Properties properties = System.getProperties();


        // Setup mail server
        properties.put("mail.smtp.from", NotificationEmail);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", NotificationPort);
        properties.put("mail.smtp.socketFactory.port", NotificationPort);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new GmailAuthenticator(NotificationEmail, NotificationPwd));

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(" Facebook Time Machine - Photo Album Successfully Backed Up", "UTF-8");

            // Now set the actual message
            message.setText("Dear User,\n\nGreetings !!!\n\n Your Album " + albumName + "  has been successfully backed up. \n\nBest Regards,\nTeam Alpha");

            // Send message
            Transport.send(message);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
