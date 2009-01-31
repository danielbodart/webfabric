package org.webfabric.web.servlet;

import org.webfabric.io.Path;

import javax.servlet.http.HttpServletRequest;

class OriginalPathInfo(v: String) extends Path {
  def value(): String = v

  override def toString = v
}

object OriginalPathInfo {
  def apply(request: HttpServletRequest):OriginalPathInfo = {
    return new OriginalPathInfo(request.getAttribute("javax.servlet.include.servlet_info").asInstanceOf[String]);
  }
}
