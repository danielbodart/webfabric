package org.webfabric.stringtemplate

import com.opensymphony.module.sitemesh.HTMLPage
import org.webfabric.io.{Converter, Url}
import java.io.IOException
import org.webfabric.sitemesh.DivCapturingPageParser

class UrlPageLoader extends PageLoader{
  def load(path:String): Option[HTMLPage] = {
    try{
      val url = new Url(path);
      val html = Converter.asString(url.inputStream)
      val parser = new DivCapturingPageParser()
      Some(parser.parse(html))
    } catch {
      case ex: IOException => None;
      case ex: NullPointerException => None;
    }
  }
}