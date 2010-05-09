package org.webfabric.properties

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.google.appengine.api.datastore.DatastoreServiceFactory

class PropertiesServlet extends HttpServlet{
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
    val repository = new PropertiesRepository(DatastoreServiceFactory.getDatastoreService)
    val presenter = new PropertiesPresenter(repository)
    presenter.present(request.getParameterMap.asInstanceOf[java.util.Map[String, Array[String]]], response.getWriter)
  }
}