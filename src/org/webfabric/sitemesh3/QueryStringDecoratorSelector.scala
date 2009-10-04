package org.webfabric.sitemesh3

import org.sitemesh.DecoratorSelector
import org.sitemesh.webapp.WebAppContext
import org.sitemesh.content.Content

class QueryStringDecoratorSelector extends DecoratorSelector[WebAppContext] {
  def selectDecoratorPaths(content: Content, context: WebAppContext): Array[String] = {
    val result = new java.util.ArrayList[String]
    context.getRequest.getParameter("decorator") match {
      case value:String => result.add("/decorators/" + value + ".st")
      case null =>
    }
    result.toArray(new Array[String](result.size))
  }
}
