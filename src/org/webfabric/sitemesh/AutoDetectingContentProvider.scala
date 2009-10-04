package org.webfabric.sitemesh

import javax.servlet.http.HttpServletRequest
import v2.PagePropertiesProvider

class AutoDetectingContentProvider extends ContentProvider{
  def getContent(request: HttpServletRequest) = {
    new PagePropertiesProvider().getContent(request)
  }
}