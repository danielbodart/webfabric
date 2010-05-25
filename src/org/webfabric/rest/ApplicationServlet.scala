package org.webfabric.rest

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import javax.servlet.ServletConfig

class ApplicationServlet extends HttpServlet{
  var application:Application = null

  override def init(config: ServletConfig) = {
    application = config.getServletContext.getAttribute(classOf[Application].getCanonicalName).asInstanceOf[Application]
  }

  override def service(req: HttpServletRequest, resp: HttpServletResponse) = {
    application.handle(Request(req), Response(resp))
  }
}