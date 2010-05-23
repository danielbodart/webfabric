package org.webfabric.rest

import org.webfabric.collections.Iterable.toMyIterable
import java.lang.reflect.Method
import org.webfabric.collections.List
import javax.ws.rs.{HttpMethod}
import java.lang.{Class}
import com.googlecode.yadic.{Resolver}

class RestEngine {
  val activators = List[HttpMethodActivator]()

  def add(resource: Class[_]): Unit = {
    resource.getMethods.foreach(method => getHttpMethod(method) match {
      case Some(httpMethod) => activators.add(new HttpMethodActivator(httpMethod.value, resource, method))
      case _ =>
    })
  }

  def getHttpMethod(method: Method): Option[HttpMethod] = {
    method.getAnnotations.tryPick(_.annotationType.getAnnotations.tryPick(_ match {
      case httpMethod: HttpMethod => Some(httpMethod)
      case _ => None
    }))
  }

  def handle(container:Resolver, request:Request, response:Response): Unit = {
    findActivator(request) match {
      case Some(activator) => activator.activate(container, request, response)
      case _ => error("No match found")
    }
  }

  def findActivator(request:Request): Option[HttpMethodActivator] = {
    activators.filter(activator => activator.isMatch(request)).headOption
  }

}