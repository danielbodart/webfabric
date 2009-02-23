package org.webfabric.stringtemplate

import com.opensymphony.module.sitemesh.filter.{PageResponseWrapper, PageRequestWrapper}
import com.opensymphony.module.sitemesh.{Config, HTMLPage, Factory}

import java.io.IOException
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import javax.servlet.{ServletException, RequestDispatcher, ServletConfig}

class ServletPageLoader(request: HttpServletRequest, response: HttpServletResponse, servletConfig: ServletConfig) extends PageLoader {
  def load(path: String): Option[HTMLPage] = {
    val requestWrapper = new PageRequestWrapper(request)
    val responseWrapper = new PageResponseWrapper(response, factory)
    responseWrapper.setContentType("text/html")
    val dispatcher = request.getRequestDispatcher(path)
    try {
      dispatcher.include(requestWrapper, responseWrapper)
      Some(responseWrapper.getPage.asInstanceOf[HTMLPage])
    } catch {
      case ex: ServletException => None
      case ex: IOException => None;
    }
  }

  def factory = Factory.getInstance(new Config(servletConfig))
}