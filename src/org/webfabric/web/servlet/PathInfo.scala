package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

class PathInfo(v: String) extends Path {
  def value(): String = v

  override def toString = v
}

object PathInfo {
  def apply(request: HttpServletRequest):PathInfo = {
    return new PathInfo(request.getPathInfo());
  }
}
