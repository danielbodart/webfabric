package org.webfabric.stringtemplate

import com.opensymphony.module.sitemesh.HTMLPage
import org.webfabric.io.{Converter, Url}
import java.io.IOException
import org.webfabric.sitemesh.v2.DivCapturingPageParser

class UrlPageLoader extends PageLoader{
  def load(path:String): Option[HTMLPage] = {
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