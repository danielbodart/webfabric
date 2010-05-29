package org.webfabric.servlet

import org.webfabric.io.HierarchicalPath
import javax.servlet.http.HttpServletRequest

case class BasePath(v: String) extends HierarchicalPath(v)

object BasePath{
  def apply(request: HttpServletRequest):BasePath = {
    return new BasePath(request.getContextPath() + request.getServletPath);
  }
}