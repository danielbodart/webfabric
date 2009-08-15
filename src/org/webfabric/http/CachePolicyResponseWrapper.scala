package org.webfabric.http


import javax.servlet.http.{HttpServletResponse, HttpServletResponseWrapper, Cookie}

class CachePolicyResponseWrapper(response:HttpServletResponse, cachePolicy: CachePolicy) extends HttpServletResponseWrapper(response) {
  override def addCookie(cookie: Cookie) = {
    cachePolicy.noCache
    super.addCookie(cookie)
  }
}