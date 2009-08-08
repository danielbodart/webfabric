package org.webfabric.caching

import com.opensymphony.module.sitemesh.filter.DebugResponseWrapper
import java.util.{Calendar, Date}
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

    val calendar = Calendar.getInstance
    responseWrapper.setDateHeader("Date", calendar.getTimeInMillis)
    noCache match {
      case true => {
        responseWrapper.setHeader("Cache-Control", "no-cache")
        responseWrapper.setDateHeader("Expires", 0)
      }
      case false => {
        val seconds = 60
        responseWrapper.setHeader("Cache-Control", "public, max-age=" + seconds)
        calendar.add(Calendar.SECOND, seconds)
        responseWrapper.setDateHeader("Expires", calendar.getTimeInMillis)
      }
    }
  }
}