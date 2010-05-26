package org.webfabric.http

import javax.servlet._
import http._

class CacheControlFilter extends GetFilter {
  var seconds = 60

  override def init(config: FilterConfig) = {
    try {
      seconds = config.getInitParameter("seconds").toInt
    } catch {
      case e: NumberFormatException =>
    }
  }

  def doGet(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) = {
    val cachePolicy = new CachePolicy(seconds)
    chain.doFilter(new CachePolicyRequestWrapper(request, cachePolicy), new CachePolicyResponseWrapper(response, cachePolicy))
    cachePolicy.writeTo(response)
  }
}