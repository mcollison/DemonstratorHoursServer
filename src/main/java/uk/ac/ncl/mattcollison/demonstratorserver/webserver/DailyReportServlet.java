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
                if (formElement.startsWith("demo_name")){
                    int number = Integer.parseInt(formElement.replace("demo_name", ""));
                    WebServer.database.queryDatabase(
                            "INSERT INTO demo_hours ("
                                    + ", " + request.getParameter("module_code")
                                    + ", " + request.getParameter("demo_date")
                                    + ", " + request.getParameter(formElement)
                                    + ", " + request.getParameter("no_hours"+number));
                }
            }
                
        

        //add daily summary 
        dailyResponseTable(response);
    }

    private void dailyResponseTable(HttpServletResponse response) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
            String query = "SELECT * FROM demo_hours WHERE demo_date = " + dateFormat.format(date);
            response.getWriter().print(WebServer.database.queryToHTML(query));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
