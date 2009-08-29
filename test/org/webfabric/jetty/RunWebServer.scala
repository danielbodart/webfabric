package org.webfabric.jetty


object RunWebServer {
  def main(args: Array[String]) {
    val webServer = new WebServer(8080)
    webServer.start
  }
}