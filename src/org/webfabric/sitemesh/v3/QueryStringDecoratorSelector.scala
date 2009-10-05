package org.webfabric.sitemesh.v3

import org.sitemesh.DecoratorSelector
import org.sitemesh.webapp.WebAppContext
import org.sitemesh.content.Content
import org.webfabric.collections.List

class QueryStringDecoratorSelector extends DecoratorSelector[WebAppContext] {
  def selectDecoratorPaths(content: Content, context: WebAppContext): Array[String] = {
    val result = List.empty[String]
    context.getRequest.getParameter("decorator") match {
      case value:String => result.add("/decorators/" + value + ".st")
      case null =>
    }
    result.toArray(new Array[String](result.size))
  }
}
