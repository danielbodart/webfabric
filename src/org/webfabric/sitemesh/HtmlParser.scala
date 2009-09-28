package org.webfabric.sitemesh

import com.opensymphony.module.sitemesh.HTMLPage
import com.opensymphony.module.sitemesh.parser.HTMLPageParser

trait HtmlParser extends HTMLPageParser {
  def parse(html:String): HTMLPage = {
    parse(html.toCharArray).asInstanceOf[HTMLPage]
  }
}