package org.webfabric.rest

import java.lang.reflect.Method
import javax.ws.rs._
import com.googlecode.yadic.{Resolver}

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) extends Matcher[Request]{
  val pathExtractor = new PathExtractor(resource, method)

  lazy val extractors = method.getParameterAnnotations.map(_(0) match {
      case query: QueryParam => new QueryParameterExtractor(query)
      case form: FormParam => new FormParameterExtractor(form)
      case path: PathParam => new PathParameterExtractor(path, pathExtractor)
      case _ => null
    }).filter(_ != null)

  val matchers = List(new MethodMatcher(httpMethod), new MimeMatcher(resource, method), pathExtractor)

  def isMatch(request:Request): Boolean = matchers.forall(_.isMatch(request)) && extractors.forall(_.isMatch(request))
  
  def activate(container: Resolver, request:Request): Object = {
    val resourceInstance = container.resolve(resource)
    method.invoke(resourceInstance, getParameters(request): _*)
  }

  def getParameters(request:Request): Array[Object] = extractors.map(extractor => extractor.extract(request))
}