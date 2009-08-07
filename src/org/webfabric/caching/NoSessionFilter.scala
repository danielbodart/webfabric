package org.webfabric.caching

import javax.servlet._
import http.{HttpServletRequestWrapper, HttpServletRequest}

class NoSessionFilter extends Filter {
  def init(config: FilterConfig) = {}

  def destroy = {}

  def doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, chain: FilterChain) = {
    val request = servletRequest.asInstanceOf[HttpServletRequest];

    val requestWrapper = new HttpServletRequestWrapper(request) {
      override def getSession(create: Boolean) = {
        if(create){
          System.out.println("NoSessionFilter: warning someone is trying to create a session!")
        }
        null
      }

      override def getSession = null
    }

    chain.doFilter(requestWrapper, servletResponse)
  }
}