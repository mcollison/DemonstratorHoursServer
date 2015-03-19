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

    public static void main(String[] args) {

        final String username = "nmc91";
        final String password = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtpauth.ncl.ac.uk");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("matthew.collison@ncl.ac.uk"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("m.g.collison@ncl.ac.uk"));
            message.setSubject("Testing Subject");
            message.setText("Test message");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
