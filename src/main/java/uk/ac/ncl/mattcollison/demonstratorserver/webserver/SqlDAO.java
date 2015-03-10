/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ncl.mattcollison.demonstratorserver.webserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Matt2
 */
public class SqlDAO {

    String dbClass = "com.mysql.jdbc.Driver";
    Properties dbProperties;
    static Connection connection = null;

    public Connection getConnection() {
        if (connection == null) {
            return openDatabaseConnection();
        } else {
            return connection;
        }
    }

    public Connection openDatabaseConnection() {
        try {
            //load properties
            dbProperties = new Properties();
            dbProperties.load(new FileInputStream("./main/config/database.properties"));

            //initialise database driver 
            Class.forName(dbClass);

            //start connection 
            connection = DriverManager.getConnection(
                    dbProperties.getProperty("database"),
                    dbProperties.getProperty("dbuser"),
                    dbProperties.getProperty("dbpassword"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.print("connection not initialised...");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.print("Database properties file not found..."
                    + "\nIt should be created in the config driectory.");
        }
        return connection;

    }

    public ResultSet queryDatabase(String query) {
        try {
            Statement statement = getConnection().createStatement();
            return statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String queryToHTML(String query) {
        String htmlTable = "";
        try {
            ResultSet results = queryDatabase(query);
            ResultSetMetaData md = results.getMetaData();
            int count = md.getColumnCount();

            htmlTable += "<table>\n<tr>\n";
            for (int i = 1; i <= count; i++) {
                htmlTable += "<td>";
                htmlTable += md.getColumnLabel(i);
                htmlTable += "</td>\n";

            }
            htmlTable += "</tr>";
            while (results.next()) {
                htmlTable += "<tr>";
                for (int i = 1; i <= count; i++) {
                    htmlTable += "<td>";
                    htmlTable += results.getString(i);
                    htmlTable += "</td>";
                }
                htmlTable += "</tr>";
            }
            htmlTable += "</table>";
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return htmlTable;
    }
}
