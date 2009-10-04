package org.webfabric.sitemesh

trait TokenProvider {
  def getToken(path: String): String
}