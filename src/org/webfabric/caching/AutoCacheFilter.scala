package org.webfabric.caching

import com.opensymphony.module.sitemesh.filter.DebugResponseWrapper
import javax.servlet._
import http._
class AutoCacheFilter extends Filter {
  def init(config: FilterConfig) = {}

  def destroy = {}

  def doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, chain: FilterChain) = {
    val request = servletRequest.asInstanceOf[HttpServletRequest];
    val response = servletResponse.asInstanceOf[HttpServletResponse];

    var noCache = false
    val requestWrapper = new HttpServletRequestWrapper(request) {
      override def getCookies = {
        val cookies = super.getCookies
        if( cookies !=  null) {
          noCache = true
        }
        cookies
      }

      override def getSession(create: Boolean) = {
        val session  = super.getSession(create)
        if( session !=  null){
          noCache = true
        }
        session
      }

      override def getSession = {
        val session  = super.getSession
        if( session !=  null){
          noCache = true
        }
        session
      }
    }

    val responseWrapper = new HttpServletResponseWrapper(response) {
      override def addCookie(cookie: Cookie) = {
        noCache = true
        super.addCookie(cookie)
      }
    }

    chain.doFilter(requestWrapper, responseWrapper)

    responseWrapper.setHeader("Cache-Control", noCache match {
      case true => "no-cache"
      case false => "public, max-age=60"
    })
  }
}