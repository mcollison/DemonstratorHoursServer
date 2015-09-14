/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ncl.mattcollison.demonstratorserver;

/**
 *
 * @author matt
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
public class IndexServlet extends HttpServlet {

    public IndexServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        Scanner sc = null;
        try {
            sc = new Scanner(new File("./classes/webapp/index.html"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Relative file path for index.html is incorrect!");
        }
        try {
            while (sc.hasNextLine()) {
                String str = sc.nextLine();

                if (str.contains("<!--insert demonstrator names-->")) {
                    response.getWriter().println("<!-- comment from java interception-->");
                    SqlDAO db = new SqlDAO();
                    try {
                        ResultSet results = db.queryDatabase("Select FirstName, LastName FROM demo_hours.demonstrators;");
                        while (results.next()) {
                            response.getWriter().println("<option value=\""
                                    + results.getString(1) + " " + results.getString(2)
                                    + "\">"
                                    + results.getString(1) + " " + results.getString(2)
                                    + "</option>");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
                response.getWriter().println(str);
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

    }
}
