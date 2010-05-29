package org.webfabric.servlet

import org.webfabric.io.{HierarchicalPath}
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletContext

case class ContextPath(v: String) extends HierarchicalPath(v)

object ContextPath {
  def apply(request: HttpServletRequest):ContextPath = {
    return new ContextPath(request.getContextPath());
  }

  def apply(context: ServletContext):ContextPath = {
    return new ContextPath(context.getContextPath());
  }
}
