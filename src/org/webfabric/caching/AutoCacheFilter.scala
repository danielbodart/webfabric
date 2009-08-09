package org.webfabric.caching

import java.util.{Calendar}
import javax.servlet._
import http._

class AutoCacheFilter extends Filter {
  var seconds = 60

  def init(config: FilterConfig) = {
     try {
       seconds = config.getInitParameter("seconds").toInt
     } catch {
       case e:NumberFormatException => 
     }
  }

  def destroy = {}

  def doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, chain: FilterChain) = {
    val request = servletRequest.asInstanceOf[HttpServletRequest];
    val response = servletResponse.asInstanceOf[HttpServletResponse];

    val cachePolicy = new CachePolicy(seconds)
    chain.doFilter(new CachePolicyRequestWrapper(request, cachePolicy), new CachePolicyResponseWrapper(response, cachePolicy))

    cachePolicy.writeTo(response)
  }
}