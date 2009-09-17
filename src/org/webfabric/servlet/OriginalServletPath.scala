package org.webfabric.servlet;

import javax.servlet.http.HttpServletRequest
import org.webfabric.io.HierarchicalPath;

class OriginalServletPath(v: String) extends HierarchicalPath(v)

object OriginalServletPath {
  def apply(request: HttpServletRequest):OriginalServletPath = {
    return new OriginalServletPath(request.getAttribute("javax.servlet.include.servlet_path").asInstanceOf[String]);
  }
}
