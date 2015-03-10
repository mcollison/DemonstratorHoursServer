/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ncl.mattcollison.demonstratorserver.webserver;

import java.net.URL;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import java.awt.Desktop;
import javax.servlet.MultipartConfigElement;

/**
 *
 * @author Matt Collison
 */
public class WebServer implements Runnable {

    public static SqlDAO database;
    public static void main(String[] args) {
        //start web server in new thread
        try {
            (new Thread(new WebServer())).start();
            Desktop.getDesktop().browse(new URL("http://localhost/").toURI());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        Server server = new Server(80);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{"index.html"});
        resource_handler.setResourceBase("./src/main/webapp/");

        //initialise servlet context handler
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        //add servlets with holders to context handler
        ServletHolder demoNameAjaxServletHolder = new ServletHolder(new DemonstratorNameServlet());
        context.addServlet(demoNameAjaxServletHolder, "/html-elements.html");

        ServletHolder dailyreportServletHolder = new ServletHolder(new DailyReportServlet());
        dailyreportServletHolder.getRegistration().setMultipartConfig(
                new MultipartConfigElement("data/tmp"));
        context.addServlet(dailyreportServletHolder, "/dailyreport.html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context, new DefaultHandler()});
        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
