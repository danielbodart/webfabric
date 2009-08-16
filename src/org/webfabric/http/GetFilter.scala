package org.webfabric.http

import javax.servlet._
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

trait GetFilter extends Filter {
  def init(config: FilterConfig) = {}

  def destroy = {}

  def doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, chain: FilterChain) = {
    (servletRequest, servletResponse) match {
      case (request:HttpServletRequest, response:HttpServletResponse ) => {
        request.getMethod match {
          case "GET" => doGet(request, response, chain)
          case "HEAD" => doGet(request, response, chain)
          case _ => chain.doFilter(servletRequest, servletResponse)
        }
      }
      case _ => chain.doFilter(servletRequest, servletResponse)
    }
  }

  def doGet(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain)

}