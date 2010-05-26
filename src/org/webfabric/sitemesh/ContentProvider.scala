package org.webfabric.sitemesh

import javax.servlet.http.HttpServletRequest

trait ContentProvider {
  def getContent(request: HttpServletRequest): Option[PropertyMap]
}