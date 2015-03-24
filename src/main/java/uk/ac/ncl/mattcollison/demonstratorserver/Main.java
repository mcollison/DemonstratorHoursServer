/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ncl.mattcollison.demonstratorserver;

import java.util.Calendar;

/**
 *
 * @author Matt2
 */
public class Main {

    public static void main(String[] args) {

        SendEmail emails;
        SqlDAO db;
        int month = Calendar.MONTH;
        int day = Calendar.DAY_OF_YEAR;

        //take command-line arguments for testing
        if (args.length != 0) {
            if (args[0].equals("-test")) {
                emails = new SendEmail(true);
            } else {
                emails = new SendEmail();
            }
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-parse")) {
                    SpreadsheetParser demoSheet = new SpreadsheetParser(args[i + 1]);
                    demoSheet.parse();
                }
            }
        } else {
            emails = new SendEmail();
        }
        db = new SqlDAO();// Possibly update this for non local databases command-line options later

        //start web server 
        WebServer.startServer();

        //start email scheduler 
        try {
            while (true) {
                if (Calendar.MONTH != month) {
                    emails.sendMails();
                    month = Calendar.MONTH;
                }
                if (Calendar.DAY_OF_YEAR != day) {
                    db.backup(day);
                    day = Calendar.DAY_OF_YEAR;
                }
                Thread.sleep(10000);
                System.out.println("Month: " + Calendar.MONTH 
                        + "\nDay: " + Calendar.DAY_OF_YEAR);
            }
        } catch (InterruptedException intEx) {
            //send notification server is down
            intEx.printStackTrace();
        }

    }
}
