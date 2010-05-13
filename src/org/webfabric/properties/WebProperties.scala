package org.webfabric.properties

import java.util.Properties
import org.webfabric.io.Url
import java.lang.String

class WebProperties(url: Url) extends Properties {
  val mimeType: String = "text/plain"
  url.get(mimeType, load(_))

  def flush = url.put(mimeType, store(_, null)) match {
    case (200, _) =>
    case (201, _) =>
    case (204, _) =>
    case (code, message) => error(code + " " + message)
  }
}