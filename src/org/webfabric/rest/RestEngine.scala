package org.webfabric.rest

import org.webfabric.collections.Iterable.toMyIterable
import java.lang.reflect.Method
import com.googlecode.yadic.SimpleContainer
import org.webfabric.collections.List
import javax.ws.rs.{HttpMethod}
import java.lang.{String, Class}
import javax.ws.rs.core.StreamingOutput
import java.io.{OutputStreamWriter, ByteArrayOutputStream, OutputStream}

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
    handle(HttpMethod.GET, path, query, FormParameters())
  }

  def post(path: String, query: QueryParameters, form: FormParameters): String = {
    handle(HttpMethod.POST, path, query, form)
  }

  def handle(httpMethod: String, path: String, query: QueryParameters, form: FormParameters): String = {
    val out = new ByteArrayOutputStream
    handle(httpMethod, path, query, form, out)
    out.toString
  }

  def handle(httpMethod: String, path: String, query: QueryParameters, form: FormParameters, output:OutputStream) {
    findActivator(httpMethod, path, query, form) match {
      case Some(activator) => activator.activate(container, query, form) match {
          case value: String => {
            var streamWriter = new OutputStreamWriter(output)
            streamWriter.write(value)
            streamWriter.flush
          }
          case streaming: StreamingOutput => streaming.write(output)
      }
      case _ => error("No match found")
    }
  }

  def findActivator(httpMethod: String, path: String, query: QueryParameters, form: FormParameters): Option[HttpMethodActivator] = {
    activators.filter(activator => activator.isMatch(httpMethod, path, query, form)).headOption
  }
}