package org.webfabric.http

import javax.servlet._
import http.{HttpServletResponse, HttpServletRequest}

class StrongEtagFilter extends GetFilter {

  def doGet(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) = {
    val wrappedResponse = new EtagResponseWrapper(response)
    chain.doFilter(new EtagRequestWrapper(request), wrappedResponse)

    wrappedResponse.getWriter.flush
    if (wrappedResponse.buffer.size > 0) {
      if (request.getHeader("If-None-Match") == wrappedResponse.etag) {
        response.reset
        response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
      } else {
        wrappedResponse.writeToUnderlyingResponse
      }
    }
  }
}