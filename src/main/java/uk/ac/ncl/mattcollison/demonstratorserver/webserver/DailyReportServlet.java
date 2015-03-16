/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ncl.mattcollison.demonstratorserver.webserver;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Matt2
 */
public class DailyReportServlet extends HttpServlet {

    private SqlDAO db = new SqlDAO();
    public DailyReportServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        dailyResponseTable(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

            //insert form information into database
            Enumeration<String> formParts = request.getParameterNames();
            while(formParts.hasMoreElements()){
                String formElement = formParts.nextElement();
//                System.out.println(formElement);
                if (formElement.startsWith("demo_name")){
//                    System.out.println("CHECKPOINT");
                    int number = Integer.parseInt(formElement.replace("demo_name", ""));
                    String dbquery = "INSERT INTO demo_hours.demo_hours (Date, ModuleCode, DemonstratorID) VALUES (\""
                            + request.getParameter("demo_date")
                            + "\", \"" + request.getParameter("modules")
                            + "\", (SELECT ID FROM demo_hours.demonstrators WHERE demonstrators.LastName = \"" + request.getParameter(formElement).split(" ")[1] +"\"));";
//                                    + "), " + request.getParameter("no_hours"+number);
//                    System.out.println(dbquery);
                    db.executeQuery(dbquery);
                }
            }
                
        

        //add daily summary 
        dailyResponseTable(response);
    }

    private void dailyResponseTable(HttpServletResponse response) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
//            System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
            String query = "SELECT * FROM demo_hours WHERE demo_hours.Date = \"" + dateFormat.format(date) + "\";";
//            System.out.println(query);
            String dbResponse = db.queryToHTML(query);
//            System.out.println(dbResponse);
            response.getWriter().print(dbResponse);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
