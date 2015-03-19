package emailtests;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Matt2
 */
public class SendEmail {

    public static void mainmethodstub(String[] args) {
//        System.out.println(System.getProperties());
//        String from = "m.g.collison@gmail.com";
//        String to = "matthew.collison@ncl.ac.uk";
//        String host = "smtp.gmail.com";
//        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.host", host);
//        properties.setProperty("mail.smtp.port", "465");
//        properties.setProperty("mail.smtp.password", "465");
//        Session session = Session.getDefaultInstance(properties);

        final String username = "nmc91";
        final String password = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "SMTPAUTH.NCL.AC.UK");
        props.put("mail.smtp.port", "465");
        System.out.println("checkpoint 1");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        System.out.println("checkpoint 2");

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("matthew.collison@ncl.ac.uk"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("matthew.collison@ncl.ac.uk"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");
        System.out.println("checkpoint 3");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO,
//                    new InternetAddress(to));
//
//            // Set Subject: header field
//            message.setSubject("This is the Subject Line!");
//
//            // Now set the actual message
//            message.setText("This is actual message");
//
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//
    }
}
