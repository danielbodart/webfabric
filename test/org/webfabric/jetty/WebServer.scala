package org.webfabric.jetty

import org.mortbay.jetty.nio.SelectChannelConnector
import org.mortbay.jetty.webapp.WebAppContext
import org.mortbay.jetty.{Server}

class WebServer(port: Int) {
  val server = new Server()
  createConnector()
  createAppContext()
  server.setStopAtShutdown(true)

  def createAppContext() {
    val appContext = new WebAppContext()
    appContext.setContextPath("/")
    appContext.setWar("./web")
    server.setHandler(appContext)
  }

  def createConnector() {
    val connector = new SelectChannelConnector()
    connector.setPort(port)
    server.addConnector(connector)
  }

  def start = {
    server.start()
  }
}

