package org.webfabric.web.servlet

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

class ContextPath(v: String) extends Path {
  def value(): String = v

  override def toString = v
}

object ContextPath {
  def apply(request: HttpServletRequest):ContextPath = {
    return new ContextPath(request.getContextPath());
  }
}
