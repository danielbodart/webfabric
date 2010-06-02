package org.webfabric.rest

import javax.ws.rs._
import core.Response.Status
import core.{HttpHeaders, StreamingOutput}
import java.lang.reflect.{InvocationTargetException, Method}
import java.io.{InputStream}
import com.googlecode.yadic.{Container, SimpleContainer, Resolver}
import java.util.NoSuchElementException

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) extends Matcher[Request] {
  def getArgumentContainer: Container = {
    val container = new SimpleContainer
    container.add(classOf[UriTemplate], () => pathExtractor.pathTemplate)
    container.add(classOf[PathParameters], () => container.resolveType(classOf[UriTemplate]).extract(container.resolveType(classOf[Request]).path))
    container.add(classOf[HeaderParameters], () => container.resolveType(classOf[Request]).headers)
    container.add(classOf[QueryParameters], () => container.resolveType(classOf[Request]).query)
    container.add(classOf[FormParameters], () => container.resolveType(classOf[Request]).form)
    container.add(classOf[InputStream], () => container.resolveType(classOf[Request]).input)
    container
  }

  def getArguments(request: Request): Array[Object] = {
    val container = getArgumentContainer
    container.addInstance(request)
    method.getParameterTypes.zip(method.getParameterAnnotations).map(pair => {
      if (!container.contains(pair._1)) container.add(pair._1)
      container.remove(classOf[String])
      if (pair._2.length > 0) pair._2(0) match {
        case query: QueryParam => container.add(classOf[String], () => {
          val params = container.resolveType(classOf[QueryParameters])
          if(!params.contains(query.value)) throw new NoSuchElementException
          params.getValue(query.value)
        })
        case form: FormParam => container.add(classOf[String], () => {
          val params = container.resolveType(classOf[FormParameters])
          if(!params.contains(form.value)) throw new NoSuchElementException
          params.getValue(form.value)
        })
        case path: PathParam => container.add(classOf[String], () => {
          val params = container.resolveType(classOf[PathParameters])
          if(!params.contains(path.value)) throw new NoSuchElementException
          params.getValue(path.value)
        })
        case _ =>
      }
      container.resolve(pair._1)
    })
  }

  def argumentsMatch(request: Request): Boolean = {
    try {
      getArguments(request)
      true
    } catch {
      case _ => false
    }
  }

  val pathExtractor = new PathExtractor(resource, method)

  var producesMatcher = new ProducesMimeMatcher(resource, method)
  val matchers = List(new MethodMatcher(httpMethod), producesMatcher, new ConsumesMimeMatcher(resource, method), pathExtractor)

  def isMatch(request: Request): Boolean = {
    var allMatchers = matchers.forall(_.isMatch(request))
    var allExtractors = argumentsMatch(request)
    allMatchers && allExtractors
  }

  def matchQuality(request: Request): Float = producesMatcher.matchQuality(request)

  def numberOfArguments = method.getParameterTypes.size

  def activate(container: Resolver, request: Request, response: Response): Unit = {
    val instance = container.resolve(resource)
    val result =
    try {
      method.invoke(instance, getArguments(request): _*)
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