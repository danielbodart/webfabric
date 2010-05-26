package org.webfabric.servlet;

import org.webfabric.io.{HierarchicalPath}
import javax.servlet.http.HttpServletRequest;

class PathInfo(v: String) extends HierarchicalPath(v)

object PathInfo {
  def apply(request: HttpServletRequest):PathInfo = {
    return new PathInfo(request.getPathInfo());
  }
}
