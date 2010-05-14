package org.webfabric.rest

import org.webfabric.collections.Iterable.toMyIterable
import java.lang.reflect.Method
import com.googlecode.yadic.SimpleContainer
import java.lang.Class
import org.webfabric.collections.List
import java.lang.annotation.Annotation
import javax.ws.rs.{HttpMethod, QueryParam, GET, Path}

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
    val path = resource.getAnnotation(classOf[Path])
    resource.getMethods.foreach((method: Method) => getHttpMethod(method) match {
      case Some(httpMethod) => activators.add(new HttpMethodActivator(httpMethod.value, path.value, resource, method))
      case _ =>
    })
  }

  def get(path: String): String = get(path, new QueryParameters)

  def get(path: String, query: QueryParameters): String = {
    findActivator(HttpMethod.GET, path, query) match {
      case Some(activator) => activator.activate(container, query).asInstanceOf[String]
      case _ => error("No match found")
    }

  }

  def findActivator(httpMethod: String, path: String, query: QueryParameters): Option[HttpMethodActivator] = {
    activators.filter(activator => activator.isMatch(httpMethod, path, query)).headOption
  }
}