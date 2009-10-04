package org.webfabric.sitemesh.v2

import org.webfabric.sitemesh.ContentProvider
import javax.servlet.http.HttpServletRequest
import com.opensymphony.module.sitemesh.{RequestConstants, HTMLPage}
import java.util.Map

class PagePropertiesProvider extends ContentProvider{
  def getContent(request: HttpServletRequest): Option[Map[_,_]] = {
    request.getAttribute(RequestConstants.PAGE) match {
      case page: HTMLPage => Some(new PagePropertyMap(page))
      case _ => None
    }
  }
}