package org.webfabric.sitemesh

import org.webfabric.io.{Url}

import org.webfabric.io.{Converter, Url}
import java.io.IOException
import java.lang.String

class UrlPageLoader(parser: PropertyMapParser) extends PageLoader {
  def this() = this(new AutoDetectingPropertyMapParser)

  def load(path: String): Option[PropertyMap] = {
    try {
      val html: String = Converter.asString(new Url(path).inputStream)
      Some(parser.parse(html))
    } catch {
      case ex: IOException => None;
      case ex: NullPointerException => None;
    }
  }
}