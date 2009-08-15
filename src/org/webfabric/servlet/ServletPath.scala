package org.webfabric.servlet;

import io.{HierarchicalPath, Path}
import javax.servlet.http.HttpServletRequest;

class ServletPath(v: String) extends HierarchicalPath(v)

object ServletPath {
  def apply(request: HttpServletRequest):ServletPath = {
    return new ServletPath(request.getServletPath());
  }
}