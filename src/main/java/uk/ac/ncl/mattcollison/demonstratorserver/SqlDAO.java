/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ncl.mattcollison.demonstratorserver;

import com.mysql.jdbc.Driver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author Matt2
 *
 * This class should establish access with a local database. It should also take
 * a daily backup of the database.
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
            dbProperties.load(SqlDAO.class.getResourceAsStream("/config/database.properties"));

            //initialise database driver 
            Class.forName(dbClass);

            System.out.println(dbProperties.getProperty("database"));

            //start connection 
            connection = DriverManager.getConnection(
                    dbProperties.getProperty("long_db"),
                    dbProperties.getProperty("dbuser"),
                    dbProperties.getProperty("dbpassword"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("connection not initialised...");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Database properties file not found..."
                    + "\nIt should be created in the config driectory.");
        } catch (NullPointerException ex) {
            System.err.println("This may indicate the \"config/database.properties\" file was not created.");
            ex.printStackTrace();
        }
        return connection;

    }

    public void setupDatabase() {
        //if database doesn't already exist it should be created 
        if (!dbExists("demo_hours")) {
            InputStream sqlScript = SqlDAO.class.getResourceAsStream("/scripts/setup.sql");
            runSQLScript(sqlScript);
        }
    }

    public boolean dbExists(String dbName) {
        try {
            ResultSet databaseSet = getConnection().createStatement().executeQuery("SHOW DATABASES;");
            while (databaseSet.next()) {
                if (databaseSet.toString().contains(dbName)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void runSQLScript(InputStream sqlScript) {
        Scanner sc = new Scanner(sqlScript);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            //consider batch inserts here if performance becomes a problem
            try {
                getConnection().createStatement().executeUpdate(line);
            } catch (SQLException sqlEx) {
                System.err.println("SQL failed for statement: " + line);
                sqlEx.printStackTrace();
            }
        }
    }

    public int executeQuery(String query) {
        try {
            if (connection == null) {
                openDatabaseConnection();
            }
            Statement statement = getConnection().createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet queryDatabase(String query) {
        try {
            if (connection == null) {
                openDatabaseConnection();
            }
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
//            System.out.println(htmlTable);
            int count = md.getColumnCount();

            htmlTable += "<table class=\"tableWithFloatingHeader nasdaq\">\n<tr>\n";
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

    public void backup(int day) {
        String execCmd = "";
        execCmd = "mysqldump -u " + dbProperties.getProperty("dbuser")
                + " -p " + dbProperties.getProperty("dbpassword")
                + " " + dbProperties.getProperty("database")
                + " -r backup" + day + ".sql";
        try {
            Process p = Runtime.getRuntime().exec(execCmd);
            int exitCode = p.waitFor();
            if (exitCode == 0) {
                System.out.println("Backup successful");
            } else {
                System.out.println("Backup failed");
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (InterruptedException intEx) {
            intEx.printStackTrace();
        }

    }
}
