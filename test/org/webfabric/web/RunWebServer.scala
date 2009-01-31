package org.webfabric.web


object RunWebServer {
  def main(args: Array[String]) {
    val webServer = new WebServer(8080)
    webServer.start
  }
}