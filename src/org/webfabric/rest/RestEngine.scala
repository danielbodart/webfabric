package org.webfabric.rest

import org.webfabric.collections.Iterable.toMyIterable
import java.lang.reflect.Method
import com.googlecode.yadic.SimpleContainer
import org.webfabric.collections.List
import javax.ws.rs.{HttpMethod}
import java.lang.{String, Class}

class RestEngine {
  val activators = List[HttpMethodActivator]()
  val container = new SimpleContainer

  def getHttpMethod(method: Method): Option[HttpMethod] = {
    method.getAnnotations.tryPick(_.annotationType.getAnnotations.tryPick(_ match {
      case httpMethod: HttpMethod => Some(httpMethod)
      case _ => None
    }))
  }

  def add(resource: Class[_]): Unit = {
    container.add(resource)
    resource.getMethods.foreach(method => getHttpMethod(method) match {
      case Some(httpMethod) => activators.add(new HttpMethodActivator(httpMethod.value, resource, method))
      case _ =>
    })
  }

  def get(path: String): String = get(path, new QueryParameters)

  def get(path: String, query: QueryParameters): String = {
    activate(HttpMethod.GET, path, query, FormParameters())
  }

  def post(path: String, query: QueryParameters, form:FormParameters): String = {
    activate(HttpMethod.POST, path, query, form)
  }

  def activate(httpMethod: String, path: String, query: QueryParameters, form:FormParameters): String = {
    findActivator(httpMethod, path, query, form) match {
      case Some(activator) => activator.activate(container, query, form).asInstanceOf[String]
      case _ => error("No match found")
    }
  }

  def findActivator(httpMethod: String, path: String, query: QueryParameters, form:FormParameters): Option[HttpMethodActivator] = {
    activators.filter(activator => activator.isMatch(httpMethod, path, query, form)).headOption
  }
}