package org.webfabric.io

import java.io.{OutputStream, InputStreamReader, Reader, InputStream}
import java.net.{HttpURLConnection, URI, URL, URLConnection}

class Url(val url: String) {
  def replacePath(path: Path): Url = {
    url match {
      case Url.JarUrl(jar, entry) => new Url("jar:" + jar + "!" + path.toString)
      case _ => {
        val o: URI = Url.toURI(this)
        val n = new URI(o.getScheme, o.getUserInfo, o.getHost, o.getPort, path.toString, o.getQuery, o.getFragment)
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

  def parent: Url = {
    replacePath(path.parent)
  }

  def reader: Reader = {
    return new InputStreamReader(inputStream)
  }

  def inputStream:InputStream = {
    val urlConnection:URLConnection = new URL(url.toString).openConnection()
    urlConnection.setUseCaches(true)
    urlConnection.getInputStream
  }

  def openConnection: HttpURLConnection = {
    new URL(url.toString).openConnection().asInstanceOf[HttpURLConnection]
  }

  def get( mimeType:String, handler: (InputStream) => Unit): (Int, String) = {
    val urlConnection:HttpURLConnection = this.openConnection
    urlConnection.setUseCaches(true)
    urlConnection.setRequestProperty("Accept", mimeType)
    val inputStream = urlConnection.getInputStream
    handler(inputStream)
    inputStream.close
    (urlConnection.getResponseCode, urlConnection.getResponseMessage)
  }

  def put( mimeType:String, handler: (OutputStream) => Unit): (Int, String) = {
    val urlConnection:HttpURLConnection = this.openConnection
    urlConnection.setDoOutput(true)
    urlConnection.setRequestMethod("PUT")
    urlConnection.setRequestProperty("Content-Type", mimeType)
    var outputStream = urlConnection.getOutputStream
    handler(outputStream)
    outputStream.close
    (urlConnection.getResponseCode, urlConnection.getResponseMessage)
  }

  def delete: (Int, String) = {
    val urlConnection:HttpURLConnection = this.openConnection
    urlConnection.setRequestMethod("DELETE")
    (urlConnection.getResponseCode, urlConnection.getResponseMessage)
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