package org.webfabric;

import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;

public class WebServer {
    private final int port;
    private final Server server;

    public WebServer(int portNumber) {
        this.port = portNumber;
        this.server = new Server();
        server.addConnector(CreateConnector());
        server.setHandler(CreateAppContext());
        server.setStopAtShutdown(true);
    }

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer(8080);
        webServer.start();
    }

    private WebAppContext CreateAppContext() {
        WebAppContext appContext = new WebAppContext();
        appContext.setContextPath("/");
        appContext.setWar("./web");
        return appContext;
    }

    private Connector CreateConnector() {
        Connector connector = new SelectChannelConnector();
        connector.setPort(port);
        return connector;
    }

    public void start() throws Exception {
        server.start();
    }
}