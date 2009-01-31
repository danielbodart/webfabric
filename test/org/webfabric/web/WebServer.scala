package org.webfabric.web

import mortbay.jetty.nio.SelectChannelConnector
import mortbay.jetty.webapp.WebAppContext
import mortbay.jetty.{Connector, Server}

class WebServer(port: Int) {
  val server = new Server()
  server.addConnector(createConnector())
  server.setHandler(createAppContext())
  server.setStopAtShutdown(true)

  def createAppContext(): WebAppContext = {
    val appContext = new WebAppContext()
    appContext.setContextPath("/")
    appContext.setWar("./web")
    return appContext
  }

  def createConnector(): Connector = {
    val connector = new SelectChannelConnector()
    connector.setPort(port)
    return connector
  }

  def start = {
    server.start()
  }
}

