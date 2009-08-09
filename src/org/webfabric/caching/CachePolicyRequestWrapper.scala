package org.webfabric.caching


import javax.servlet.http.{HttpServletRequest, HttpServletRequestWrapper}

class CachePolicyRequestWrapper(request: HttpServletRequest, cachePolicy: CachePolicy) extends HttpServletRequestWrapper(request) {
  override def getCookies = {
    val cookies = super.getCookies
    if (cookies != null) {
      cachePolicy.noCache
    }
    cookies
  }

  override def getSession(create: Boolean) = {
    val session = super.getSession(create)
    if (session != null) {
      cachePolicy.noCache
    }
    session
  }

  override def getSession = {
    val session = super.getSession
    if (session != null) {
      cachePolicy.noCache
    }
    session
  }
}