package org.webfabric.caching

import com.opensymphony.module.sitemesh.filter.DebugResponseWrapper
import java.lang.String
import javax.servlet._
import http.{HttpServletResponse, HttpServletRequest}

class StrongEtagFilter extends Filter {
  def destroy = {}

  def init(config: FilterConfig) = {}

  def doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, chain: FilterChain) = {
    val request = servletRequest.asInstanceOf[HttpServletRequest];
    val response = servletResponse.asInstanceOf[HttpServletResponse];

    val wrappedResponse = new EtagResponseWrapper(response)
    chain.doFilter(new EtagRequestWrapper(request), wrappedResponse)

    wrappedResponse.getWriter.flush
    if (wrappedResponse.buffer.size > 0) {
      if (request.getHeader("If-None-Match") == wrappedResponse.etag) {
        response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
      } else {
        wrappedResponse.writeToUnderlyingResponse
      }
    }
  }
}