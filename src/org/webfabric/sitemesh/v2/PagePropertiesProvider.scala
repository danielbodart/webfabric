package org.webfabric.sitemesh.v2

import javax.servlet.http.HttpServletRequest
import com.opensymphony.module.sitemesh.{RequestConstants, HTMLPage}
import org.webfabric.sitemesh.{PropertyMap, ContentProvider}

class PagePropertiesProvider extends ContentProvider{
  def getContent(request: HttpServletRequest): Option[PropertyMap] = {
    request.getAttribute(RequestConstants.PAGE) match {
      case page: HTMLPage => Some(new PagePropertyMap(page))
      case _ => None
    }
  }
}