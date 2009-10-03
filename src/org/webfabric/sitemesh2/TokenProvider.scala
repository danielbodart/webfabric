package org.webfabric.sitemesh2

trait TokenProvider {
  def getToken(path: String): String
}