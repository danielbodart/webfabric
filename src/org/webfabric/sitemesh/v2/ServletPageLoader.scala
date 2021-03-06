package org.webfabric.sitemesh.v2

import com.opensymphony.module.sitemesh.filter.{PageResponseWrapper, PageRequestWrapper}
import com.opensymphony.module.sitemesh.{Config, HTMLPage, Factory}

import java.io.IOException
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import javax.servlet.{ServletException, ServletConfig}
import org.webfabric.sitemesh.{PageLoader, PropertyMap}

class ServletPageLoader(request: HttpServletRequest, response: HttpServletResponse, servletConfig: ServletConfig) extends PageLoader {
  def load(path: String): Option[PropertyMap] = {
    try {
      val dispatcher = request.getRequestDispatcher(path)
      val responseWrapper = new PageResponseWrapper(response, factory)
      responseWrapper.setContentType("text/html")
      dispatcher.include(new PageRequestWrapper(request), responseWrapper)
      Some(new PropertyMap(PageAdapter.parse(responseWrapper.getPage.asInstanceOf[HTMLPage])))
    } catch {
      case ex: ServletException => None
      case ex: IOException => None
    }
  }

  def factory = Factory.getInstance(new Config(servletConfig))
}