package org.webfabric.properties

import java.util.Properties
import org.webfabric.io.Url

class WebProperties(url: Url) extends Properties {
  this.load(url.inputStream)

  def flush {
    var (code, message) = url.put(store(_, null))
    Console.println(code + " " + message)
  }
}