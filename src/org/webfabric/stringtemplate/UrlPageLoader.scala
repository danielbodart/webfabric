package org.webfabric.stringtemplate

import org.webfabric.io.{Converter, Url}
import java.io.IOException
import org.webfabric.sitemesh.v2.DivCapturingPageParser
import org.webfabric.sitemesh.PropertyMap

class UrlPageLoader extends PageLoader{
  def load(path:String): Option[PropertyMap] = {
    try{
      val html = Converter.asString(new Url(path).inputStream)
      val parser = new DivCapturingPageParser()
      Some(parser.parse(html))
    } catch {
      case ex: IOException => None;
      case ex: NullPointerException => None;
    }
  }
}