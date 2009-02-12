package org.webfabric.io

import java.io.{InputStreamReader, Reader, BufferedReader, InputStream}
import java.net.{URI, URL, URLConnection}

class Url(val url: String) {
  def replacePath(path: Path): Url = {
    url match {
      case Url.JarUrl(jar, entry) => new Url("jar:" + jar + "!" + path.value)
      case _ => {
        val o: URI = Url.toURI(this)
        val n = new URI(o.getScheme, o.getUserInfo, o.getHost, o.getPort, path.value, o.getQuery, o.getFragment)
        new Url(n.toString)
      }
    }
  }

  def path: HierarchicalPath = {
    url match {
      case Url.JarUrl(jar, entry) => HierarchicalPath(entry)
      case _ => HierarchicalPath(Url.toURI(this).getRawPath)
    }
  }

  def reader: Reader = {
    return new InputStreamReader(inputStream)
  }

  def inputStream:InputStream = {
    val urlConnection:URLConnection = new URL(url.toString).openConnection()
    urlConnection.setUseCaches(true)
    urlConnection.getInputStream
  }

  override def toString = url

  override def hashCode = url.hashCode

  override def equals(other: Any) = other match {
    case that: Url => toString == that.toString
    case _ => false
  }
}

object Url {
  implicit def toURL(url: Url): URL = new URL(url.toString)

  implicit def toUrl(url: URL): Url = new Url(url.toString)

  implicit def toURI(url: Url): URI = new URI(url.toString)

  private val JarUrl = """jar:([^!]*)!(.*)""".r

  def apply(value: String): Url = {
    val o: Url = new Url(value)
    o.replacePath(o.path)
  }
}