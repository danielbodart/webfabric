package org.webfabric.sitemesh.v3

import java.io.IOException
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import javax.servlet.{ServletException, ServletConfig}
import org.webfabric.sitemesh.{PageLoader, PropertyMap}
import org.sitemesh.webapp.contentfilter.{ResponseMetaData, BasicSelector, HttpServletResponseBuffer}

class ServletPageLoader(request: HttpServletRequest, response: HttpServletResponse, servletConfig: ServletConfig) extends PageLoader {
  def load(path: String): Option[PropertyMap] = {
    try {
      val dispatcher = request.getRequestDispatcher(path)
      val responseWrapper = new HttpServletResponseBuffer(response, new ResponseMetaData, new BasicSelector)
      responseWrapper.setContentType("text/html")
      dispatcher.include(request, responseWrapper)
      Some(new ContentPropertyMapParser().parse(responseWrapper.getBuffer))
    } catch {
      case ex: ServletException => None
      case ex: IOException => None
    }
  }
}