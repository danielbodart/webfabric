package org.webfabric.rest

import java.lang.reflect.{Method, InvocationHandler, Proxy}
import javax.ws.rs.core.StreamingOutput
import java.io.{OutputStreamWriter, OutputStream, ByteArrayOutputStream}

class Redirect(path:String){
  override def toString = path.toString
}


object Redirect {
  def apply(path: String): Redirect = {
    new Redirect(path)
  }

  def apply(path: StreamingOutput): Redirect = {
    val output = new ByteArrayOutputStream
    path.write(output)
    new Redirect(output.toString)
  }

  def resource[T <: Object](aClass: Class[T]): T = {
    var loader = getClass.getClassLoader
    var classes = Array[Class[_]](aClass)
    var proxyClass = Proxy.getProxyClass(loader, classes: _*)
    Proxy.newProxyInstance(loader, classes, new ResourcePath).asInstanceOf[T]
  }

  def getPath(method: Method, arguments: Array[Object]): String = {
    var pathExtractor = new PathExtractor(method.getDeclaringClass, method)
    val params = PathParameters()
    var extractors = Extractors.generateExtractors(method, pathExtractor)
    extractors.zip(arguments).foreach(pair => {
      pair._1 match {
        case pathExtractor: PathParameterExtractor => pathExtractor.generate(params, pair._2.toString)
        case _ =>
      }
    })
    pathExtractor.generate(params)
  }

  def createReturnType(returnType: Class[_], path: String): Object = {
    if (returnType == classOf[StreamingOutput]) {
      new StreamingOutput {
        def write(output: OutputStream) = {
          var writer = new OutputStreamWriter(output)
          writer.write(path)
          writer.flush
        }
      }
    } else {
      path
    }
  }

  class ResourcePath extends InvocationHandler {
    def invoke(proxy: Any, method: Method, arguments: Array[Object]): Object = {
      createReturnType(method.getReturnType, getPath(method, arguments))
    }
  }

}