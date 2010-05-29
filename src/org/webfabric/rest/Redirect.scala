package org.webfabric.rest


import java.io.{OutputStreamWriter, OutputStream, ByteArrayOutputStream}
import java.lang.reflect.Method
import net.sf.cglib.proxy.{MethodProxy, MethodInterceptor, Enhancer}
import javax.ws.rs.core.{HttpHeaders, StreamingOutput}

case class Redirect(location: String) {
  def applyTo(response:Response) {
    response.setHeader(HttpHeaders.LOCATION, location)
    response.setCode(303)
  }
}

object Redirect {
  def apply(path: StreamingOutput): Redirect = {
    val output = new ByteArrayOutputStream
    path.write(output)
    new Redirect(output.toString)
  }

  def resource[T <: Object](o: Object): T = resource(o.getClass)

  def resource[T <: Object](aClass: Class[T]): T = {
    val enhancer = new Enhancer
    enhancer.setSuperclass(aClass)
    enhancer.setCallback(new ResourcePath)
    val argumentTypes = aClass.getConstructors.first.getParameterTypes
    val arguments:Array[Object] = argumentTypes.map(_ => null)
    val value: Any = enhancer.create(argumentTypes, arguments)
    value.asInstanceOf[T]
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

  class ResourcePath extends MethodInterceptor {
    def intercept(proxy: Any, method: Method, arguments: Array[Object], methodProxy: MethodProxy): Object = {
      createReturnType(method.getReturnType, getPath(method, arguments))
    }
  }
}