package org.webfabric.web.servlet;

import io.HierarchicalPath
import javax.servlet.http.HttpServletRequest;

class OriginalServletPath(v: String) extends HierarchicalPath(v)

object OriginalServletPath {
  def apply(request: HttpServletRequest):OriginalServletPath = {
    return new OriginalServletPath(request.getAttribute("javax.servlet.include.servlet_path").asInstanceOf[String]);
  }
}
