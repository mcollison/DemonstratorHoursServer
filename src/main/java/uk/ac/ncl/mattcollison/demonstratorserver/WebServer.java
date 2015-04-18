/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ncl.mattcollison.demonstratorserver;

import java.net.URL;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import java.io.File;
import javax.servlet.MultipartConfigElement;

/**
 *
 * @author Matt Collison
 * 
 * This web server should allow individual sessions to be logged (added to 
 * the database). It should also enable spreadsheet uploading. 
 */
public class WebServer implements Runnable{

    public static SqlDAO database;

    public void run() {

        String resourcePath ="";
        Server server = new Server(80);

        try{
            resourcePath = new File("./").getCanonicalPath() + "/classes/webapp/";
        } catch (Exception ex){
            ex.printStackTrace();
        }
        
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{"index.html"});
        resource_handler.setResourceBase(resourcePath);
//                WebServer.class.getResource("/webapp/").getPath());
        System.out.println(resourcePath);

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
