package org.webfabric.servlet



import io.{HierarchicalPath, Path}
import javax.servlet.http.HttpServletRequest;

class ContextPath(v: String) extends HierarchicalPath(v)

object ContextPath {
  def apply(request: HttpServletRequest):ContextPath = {
    return new ContextPath(request.getContextPath());
  }
}
