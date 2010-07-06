package org.webfabric.jetty

import org.mortbay.jetty.nio.SelectChannelConnector
import org.mortbay.jetty.webapp.WebAppContext
import org.mortbay.jetty.{Server}
import com.google.appengine.tools.development.LocalApiProxyServletFilter

class WebServer(port: Int) {
  val server = new Server()
  createConnector()
  createAppContext()

  def createAppContext() {
    val appContext = new WebAppContext()
    appContext.setContextPath("/")
    appContext.setWar("./web")
    appContext.addFilter(classOf[LocalApiProxyServletFilter], "/*", 1)
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

  def stop = {
    server.stop()
  }
}

