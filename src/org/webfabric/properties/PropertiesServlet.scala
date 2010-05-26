package org.webfabric.properties

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.google.appengine.api.datastore.DatastoreServiceFactory
import java.util.{Properties, UUID}

class PropertiesServlet extends HttpServlet{
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
    val repository = new PropertiesRepository(DatastoreServiceFactory.getDatastoreService)
    val presenter = new PropertiesPresenter(repository)
    response.setHeader("Content-Type", "text/plain")
    presenter.present(request.getParameterMap.asInstanceOf[java.util.Map[String, Array[String]]], response.getWriter)
  }

  override def doPut(request: HttpServletRequest, response: HttpServletResponse) = {
    val repository = new PropertiesRepository(DatastoreServiceFactory.getDatastoreService)
    var uuid = UUID.fromString(request.getParameter("uuid"))
    val properties = new Properties
    properties.load(request.getReader)
    repository.set(uuid, properties)
    response.setStatus(201, "Created")
  }

  override def doDelete(request: HttpServletRequest, response: HttpServletResponse) = {
    val repository = new PropertiesRepository(DatastoreServiceFactory.getDatastoreService)
    var uuid = UUID.fromString(request.getParameter("uuid"))
    repository.remove(uuid)
    response.setStatus(204, "Deleted")
  }
}