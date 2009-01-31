package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

class ServletPath(v: String) extends Path {
  def value(): String = v

  override def toString = v
}

object ServletPath {
  def apply(request: HttpServletRequest):ServletPath = {
    return new ServletPath(request.getServletPath());
  }
}