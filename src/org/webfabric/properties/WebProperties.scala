package org.webfabric.properties

import java.util.Properties
import org.webfabric.io.Url

class WebProperties(url: Url) extends Properties {
  this.load(url.inputStream)

  def flush = url.put("text/plain", store(_, null)) match {
    case (201, _) =>
    case (code, message) => error(code + " " + message)
  }
}