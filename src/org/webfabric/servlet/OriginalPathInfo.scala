package org.webfabric.servlet;

import org.webfabric.io.{HierarchicalPath}
import javax.servlet.http.HttpServletRequest;

class OriginalPathInfo(v: String) extends HierarchicalPath(v)

object OriginalPathInfo {
  def apply(request: HttpServletRequest):OriginalPathInfo = {
    return new OriginalPathInfo(request.getAttribute("javax.servlet.include.servlet_info").asInstanceOf[String]);
  }
}
