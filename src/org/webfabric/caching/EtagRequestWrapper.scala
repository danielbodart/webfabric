package org.webfabric.caching


import java.lang.String
import javax.servlet.http.{HttpServletRequestWrapper, HttpServletRequest}

class EtagRequestWrapper(request: HttpServletRequest) extends HttpServletRequestWrapper(request){
  override def getDateHeader(name: String) = {
    super.getDateHeader(name)
  }

  override def getHeader(name: String): String = {
    if(name == "If-Modified-Since"){
      return null;
    }
    super.getHeader(name)
  }

  override def getHeaders(name: String) = {
    super.getHeaders(name)
  }

  override def getHeaderNames = {
    super.getHeaderNames
  }
}