package org.webfabric.rest

import java.lang.reflect.Method
import javax.ws.rs._
import com.googlecode.yadic.{Resolver}

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) extends Matcher[Request]{
  lazy val extractors = method.getParameterAnnotations.map(_(0) match {
      case query: QueryParam => new QueryParameterExtractor(query)
      case form: FormParam => new FormParameterExtractor(form)
      case path: PathParam => new PathParameterExtractor(path, pathTemplate)
      case _ => null
    }).filter(_ != null)

  lazy val pathTemplate: UriTemplate = {
    val paths = List(resource.getAnnotation(classOf[Path]), method.getAnnotation(classOf[Path]))
    new UriTemplate(paths.filter(_ != null).map(_.value).mkString("/"))
  }

  val matchers = List(new MethodMatcher(httpMethod), new MimeMatcher(resource, method),
    new Matcher[Request] { def isMatch(request: Request) = pathTemplate.isMatch(request.path)})

  def isMatch(request:Request): Boolean = matchers.forall(_.isMatch(request)) && extractors.forall(_.isMatch(request))
  
  def activate(container: Resolver, request:Request): Object = {
    val resourceInstance = container.resolve(resource)
    method.invoke(resourceInstance, getParameters(request): _*)
  }

  def getParameters(request:Request): Array[Object] = extractors.map(extractor => extractor.extract(request))
}