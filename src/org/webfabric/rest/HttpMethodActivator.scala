package org.webfabric.rest

import javax.ws.rs._
import core.Response.Status
import core.{HttpHeaders, StreamingOutput}
import java.lang.reflect.{InvocationTargetException, Method}
import com.googlecode.yadic.{Resolver}

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) extends Matcher[Request] {
  val pathExtractor = new PathExtractor(resource, method)
  val argumentsExtractor = new ArgumentsExtractor(pathExtractor, resource, method)
  var producesMatcher = new ProducesMimeMatcher(resource, method)
  val matchers = List(new MethodMatcher(httpMethod), producesMatcher, new ConsumesMimeMatcher(resource, method), pathExtractor, argumentsExtractor)

  def isMatch(request: Request): Boolean = matchers.forall(_.isMatch(request))

  def matchQuality(request: Request): Float = producesMatcher.matchQuality(request)

  def numberOfArguments = method.getParameterTypes.size

  def activate(container: Resolver, request: Request, response: Response): Unit = {
    val instance = container.resolve(resource)
    val result =
    try {
      method.invoke(instance, argumentsExtractor.extract(request): _*)
    } catch {
      case e: InvocationTargetException => throw e.getCause
    }
    response.setHeader(HttpHeaders.CONTENT_TYPE, producesMatcher.mimeType)
    result match {
      case redirect: Redirect => redirect.applyTo(request.base, response)
      case body: String => response.write(body)
      case streaming: StreamingOutput => streaming.write(response.output)
      case null => response.setCode(Status.NO_CONTENT)
    }
  }

}