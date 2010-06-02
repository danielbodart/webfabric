package org.webfabric.rest


import java.io.{OutputStreamWriter, OutputStream, ByteArrayOutputStream}
import java.lang.reflect.Method
import net.sf.cglib.proxy.{MethodProxy, MethodInterceptor, Enhancer}
import javax.ws.rs.core.{HttpHeaders, StreamingOutput}
import org.webfabric.servlet.{BasePath}
import javax.ws.rs.core.Response.Status

case class Redirect(location: String) {
  def applyTo(base:BasePath, response:Response) {
    response.setHeader(HttpHeaders.LOCATION, base + "/" + location)
    response.setCode(Status.SEE_OTHER)
  }
}

object Redirect {
  def apply(path: StreamingOutput): Redirect = {
    val output = new ByteArrayOutputStream
    path.write(output)
    new Redirect(output.toString)
  }

  def resource[T <: Object](aClass: Class[T]): T = {
    val enhancer = new Enhancer
    enhancer.setSuperclass(aClass)
    enhancer.setCallback(new ResourcePath)
    val constructorWithLeastArguments = aClass.getConstructors.toList.sort(_.getParameterTypes.size < _.getParameterTypes.size).first
    val argumentTypes = constructorWithLeastArguments.getParameterTypes
    val arguments:Array[Object] = argumentTypes.map(_ => null)
    val value: Any = enhancer.create(argumentTypes, arguments)
    value.asInstanceOf[T]
  }

  def getPath(method: Method, arguments: Array[Object]): String = {
    var pathExtractor = new PathMatcher(method)
    var argumentsGenerator = new ArgumentsGenerator(pathExtractor, method)
    argumentsGenerator.generate(arguments).path
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

  class ResourcePath extends MethodInterceptor {
    def intercept(proxy: Any, method: Method, arguments: Array[Object], methodProxy: MethodProxy): Object = {
      createReturnType(method.getReturnType, getPath(method, arguments))
    }
  }
}