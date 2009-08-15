package org.webfabric.caching

import java.util.logging.Logger
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
          NoSessionFilter.log.warning("Attempt to create Http Session denide")
        }
        null
      }

      override def getSession = null
    }

    chain.doFilter(requestWrapper, servletResponse)
  }
}

object NoSessionFilter{
  val log: Logger = Logger.getLogger(NoSessionFilter.getClass.getName);
}