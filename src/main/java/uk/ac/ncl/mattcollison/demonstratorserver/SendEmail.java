package uk.ac.ncl.mattcollison.demonstratorserver;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Matt2
 */
public class SendEmail {

    final String username = "nmc91";
    final String password = "";
    Session session;
    Properties props = new Properties();
    private boolean test = false;
    SqlDAO db = new SqlDAO();

    public SendEmail(boolean test) {
        this.test = test;
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "SMTPAUTH.NCL.AC.UK");
        props.put("mail.smtp.port", "465");
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public SendEmail() {
        this.test = test;
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "SMTPAUTH.NCL.AC.UK");
        props.put("mail.smtp.port", "465");
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public void sendMails() {

        //itterate through demonstrators, module leaders and stage coordinators
        //demonstrators
        try {
            ResultSet results = db.queryDatabase("Select ID, FirstName, LastName, Email FROM demo_hours.demonstrators;");
            while (results.next()) {
                if (test) {
                    ResultSet individualResults = db.queryDatabase(
                            "Select * FROM demo_hours.demo_hours WHERE ID = " + results.getString(1) + ";");
                    String conts = "\"Hi \n"
                            + "You're hours to be submited are listed below. \n";
                    while (individualResults.next()) {
                        conts = conts + results.toString() + "\n";
                    }

                    sendMail(username + "@ncl.ac.uk", username + "@ncl.ac.uk",
                            Calendar.MONTH + "/" + Calendar.YEAR
                            + " Demonstrator hours", conts);
                }
            }

            //module leaders
            results = db.queryDatabase("Select ModuleLeaderFirstName, Email, ModuleCode FROM demo_hours.modules;");
            while (results.next()) {
                if (test) {
                    ResultSet individualResults = db.queryDatabase(
                            "Select * FROM demo_hours.modules WHERE Code = " + results.getString(4) + ";");
                    String conts = "\"Hi \n"
                            + "You're hours to be submited for module " + results.getString(4) + " are listed below. \n";
                    while (individualResults.next()) {
                        conts = conts + results.toString() + "\n";
                    }

                    sendMail(username + "@ncl.ac.uk", username + "@ncl.ac.uk",
                            Calendar.MONTH + "/" + Calendar.YEAR
                            + " Demonstrator hours", conts);
                } else {
                    ResultSet individualResults = db.queryDatabase(
                            "Select * FROM demo_hours.modules WHERE Code = " + results.getString(4) + ";");
                    String conts = "\"Hi \n"
                            + "You're hours to be submited for module " + results.getString(4) + " are listed below. \n";
                    while (individualResults.next()) {
                        conts = conts + results.toString() + "\n";
                    }

                    sendMail(username + "@ncl.ac.uk", individualResults.getString(3),
                            Calendar.MONTH + "/" + Calendar.YEAR
                            + " Demonstrator hours", conts);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void sendMail(String from, String to, String subject, String content) {

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
