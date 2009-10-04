package org.webfabric.sitemesh.v3

import org.webfabric.sitemesh.ContentProvider
import javax.servlet.http.HttpServletRequest
import org.sitemesh.webapp.WebAppContext
import org.sitemesh.content.Content
import java.util.Map

class ContentPropertiesProvider extends ContentProvider{
  def getContent(request: HttpServletRequest): Option[Map[_,_]] = {
    request.getAttribute(WebAppContext.CONTENT_KEY) match {
      case content: Content => Some(new ContentPropertyMap(content.getExtractedProperties))
      case _ => None
    }
  }
}