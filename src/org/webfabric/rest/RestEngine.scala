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

  def add(resource: Class[_]): Unit = {
    container.add(resource)
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

  def handle(httpMethod: String, path: String, headers: HeaderParameters, query: QueryParameters, form: FormParameters): String = {
    activate(httpMethod, path, headers, query, form) match {
      case value: String => value
      case streaming: StreamingOutput => {
        val out = new ByteArrayOutputStream
        streaming.write(out)
        out.toString
      }
      case null => null
    }
  }

  def handle(httpMethod: String, path: String, headers: HeaderParameters, query: QueryParameters, form: FormParameters, output: OutputStream): Unit = {
    activate(httpMethod, path, headers, query, form) match {
      case value: String => {
        var streamWriter = new OutputStreamWriter(output)
        streamWriter.write(value)
        streamWriter.flush
      }
      case streaming: StreamingOutput => streaming.write(output)
      case null => null
    }
  }

  def activate(httpMethod: String, path: String,  headers: HeaderParameters, query: QueryParameters, form: FormParameters): Object = {
    findActivator(httpMethod, path, headers, query, form) match {
      case Some(activator) => activator.activate(container, path, headers, query, form)
      case _ => error("No match found")
    }
  }

  def findActivator(httpMethod: String, path: String, headers: HeaderParameters, query: QueryParameters, form: FormParameters): Option[HttpMethodActivator] = {
    activators.filter(activator => activator.isMatch(httpMethod, path, headers, query, form)).headOption
  }

  def get(path: String): String = get(path, new QueryParameters)

  def get(path: String, query: QueryParameters): String = {
    handle(HttpMethod.GET, path, HeaderParameters(), query, FormParameters())
  }

  def post(path: String, query: QueryParameters, form: FormParameters): String = {
    handle(HttpMethod.POST, path, HeaderParameters(), query, form)
  }

}