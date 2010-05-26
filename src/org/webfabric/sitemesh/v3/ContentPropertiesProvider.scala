package org.webfabric.sitemesh.v3

import javax.servlet.http.HttpServletRequest
import org.sitemesh.webapp.WebAppContext
import org.sitemesh.content.Content
import org.webfabric.sitemesh.{PropertyMap, ContentProvider}

class ContentPropertiesProvider extends ContentProvider{
  def getContent(request: HttpServletRequest): Option[PropertyMap] = {
    request.getAttribute(WebAppContext.CONTENT_KEY) match {
      case content: Content => Some(new PropertyMap(new ContentPropertyAdapter(content.getExtractedProperties)))
      case _ => None
    }
  }
}