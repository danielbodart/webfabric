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

  def handle(request:Request): String = {
    activate(request) match {
      case value: String => value
      case streaming: StreamingOutput => {
        val out = new ByteArrayOutputStream
        streaming.write(out)
        out.toString
      }
      case null => null
    }
  }

  def handle(request:Request, output: OutputStream): Unit = {
    activate(request) match {
      case value: String => {
        var streamWriter = new OutputStreamWriter(output)
        streamWriter.write(value)
        streamWriter.flush
      }
      case streaming: StreamingOutput => streaming.write(output)
      case null => null
    }
  }

  def activate(request:Request): Object = {
    findActivator(request) match {
      case Some(activator) => activator.activate(container, request)
      case _ => error("No match found")
    }
  }

  def findActivator(request:Request): Option[HttpMethodActivator] = {
    activators.filter(activator => activator.isMatch(request)).headOption
  }

}