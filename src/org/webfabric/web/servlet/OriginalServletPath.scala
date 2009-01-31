package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

class OriginalServletPath(v: String) extends Path {
  def value(): String = v

  override def toString = v
}

object OriginalServletPath {
  def apply(request: HttpServletRequest):OriginalServletPath = {
    return new OriginalServletPath(request.getAttribute("javax.servlet.include.servlet_path").asInstanceOf[String]);
  }
}
