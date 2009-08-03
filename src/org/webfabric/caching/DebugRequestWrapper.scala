package org.webfabric.caching


import java.lang.String
import javax.servlet.http.{HttpServletRequestWrapper, HttpServletRequest}

class DebugRequestWrapper(request: HttpServletRequest) extends HttpServletRequestWrapper(request){
  override def getDateHeader(name: String) = {
    System.out.println("getDateHeader('" + name + "')")
    super.getDateHeader(name)
  }

  override def getHeader(name: String) = {
    System.out.println("getHeader('" + name + "')")
    super.getHeader(name)
  }

  override def getHeaders(name: String) = {
    System.out.println("getHeaders('" + name + "')")
    super.getHeaders(name)
  }

  override def getHeaderNames = {
    System.out.println("getHeaderNames()")
    super.getHeaderNames
  }
}