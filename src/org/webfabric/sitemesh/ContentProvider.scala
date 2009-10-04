package org.webfabric.sitemesh

import javax.servlet.http.HttpServletRequest
import java.util.Map

trait ContentProvider {
  def getContent(request: HttpServletRequest): Option[Map[_,_]]
}