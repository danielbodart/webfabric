package org.webfabric.sitemesh3

import org.sitemesh.webapp.WebAppContext
import org.sitemesh.DecoratorSelector
import org.sitemesh.content.Content

class ListOfDecoratorSelectors(selectors:DecoratorSelector[WebAppContext]*) extends DecoratorSelector[WebAppContext] {
  def selectDecoratorPaths(content: Content, context: WebAppContext): Array[String] = {
    for(selector:DecoratorSelector[WebAppContext] <- selectors) {
      val decoratorPaths = selector.selectDecoratorPaths(content, context)
      if(decoratorPaths.size > 0) {
        return decoratorPaths
      }
    }
    new Array[String](0)
  }
}