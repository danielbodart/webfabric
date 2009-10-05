package org.webfabric.sitemesh.v2

import com.opensymphony.module.sitemesh.parser.HTMLPageParser
import org.webfabric.sitemesh.PropertyMap
import com.opensymphony.module.sitemesh.HTMLPage

trait HtmlParser extends HTMLPageParser {
  def parse(html:String): PropertyMap = {
    new PagePropertyMap(parse(html.toCharArray).asInstanceOf[HTMLPage])
  }
}