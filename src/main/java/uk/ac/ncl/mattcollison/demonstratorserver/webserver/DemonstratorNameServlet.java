/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ncl.mattcollison.demonstratorserver.webserver;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
public class DemonstratorNameServlet extends HttpServlet {
    public DemonstratorNameServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        SqlDAO db = new SqlDAO();
        try {
            ResultSet results = db.queryDatabase("Select FirstName LastName from demo_hours.Demonstrators;");
            while (results.next()) {
                response.getWriter().println(results.getString(1) 
                + " " + results.getString(2) + "\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
