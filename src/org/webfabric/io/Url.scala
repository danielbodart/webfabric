package org.webfabric.io


import java.net.{URI, URL}

class Url(url: String) {
  def this(url: URL) = this (url.toString)

  def toURL = new URL(url)

  def toURI = new URI(url)

  def replacePath(path: Path): Url = {
    val o = toURI
    val n = new URI(o.getScheme, o.getUserInfo, o.getHost, o.getPort, path.value, o.getQuery, o.getFragment)
    new Url(n.toURL)
  }

  def path: HierarchicalPath = {
    new HierarchicalPath(toURI.getRawPath)
  }

  override def toString = url

  override def hashCode = url.hashCode

  override def equals(obj: Any) = if(obj == null) false else toString == obj.toString
}