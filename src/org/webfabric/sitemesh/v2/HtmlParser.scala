package org.webfabric.sitemesh.v2

import com.opensymphony.module.sitemesh.parser.HTMLPageParser
import com.opensymphony.module.sitemesh.HTMLPage
import org.webfabric.sitemesh.{PropertyMapParser, PropertyMap}

trait HtmlParser extends HTMLPageParser with PropertyMapParser {
  def parse(html:String): PropertyMap = {
    new PagePropertyMap(parse(html.toCharArray).asInstanceOf[HTMLPage])
  }
}