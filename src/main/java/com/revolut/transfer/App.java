package com.revolut.transfer;

import com.revolut.transfer.controllers.TransferController;
import com.revolut.transfer.di.AppConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.h2.server.web.DbStarter;

public class App {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(9998);
        jettyServer.setHandler(context);

        context.addEventListener(new DbStarter());

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                TransferController.class.getCanonicalName());
        jerseyServlet.setInitParameter(
                "javax.ws.rs.Application",
                AppConfig.class.getCanonicalName());
        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
