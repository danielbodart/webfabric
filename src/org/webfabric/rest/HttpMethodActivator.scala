package org.webfabric.rest

import java.lang.reflect.Method
import org.webfabric.collections.{List}
import javax.ws.rs._
import com.googlecode.yadic.{Resolver}

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) extends Matcher[Request]{
  lazy val extractors: List[ParameterExtractor] = {
    val result = List[ParameterExtractor]()
    method.getParameterAnnotations.foreach(_(0) match {
      case query: QueryParam => result.add(new QueryParameterExtractor(query))
      case form: FormParam => result.add(new FormParameterExtractor(form))
      case path: PathParam => result.add(new PathParameterExtractor(path, pathTemplate))
      case _ =>
    })
    result
  }

  lazy val pathTemplate: UriTemplate = {
    val paths = List(resource.getAnnotation(classOf[Path]), method.getAnnotation(classOf[Path]))
    new UriTemplate(paths.filter(_ != null).map(_.value).mkString("/"))
  }

  val mimeMatcher = new MimeMatcher(resource, method)

  def isMatch(request:Request): Boolean = {
    httpMethod.equals(request.method) && pathTemplate.isMatch(request.path) &&
            mimeMatcher.isMatch(request) && parametersMatch(request)
  }
  
  def parametersMatch(request:Request): Boolean = {
    extractors.foldLeft(true, (isMatch: Boolean, extractor) => isMatch && extractor.isMatch(request))
  }

  def activate(container: Resolver, request:Request): Object = {
    val resourceInstance = container.resolve(resource)
    method.invoke(resourceInstance, getParameters(request): _*)
  }

  def getParameters(request:Request): Array[Object] = {
    val results = List[Object]()
    extractors.foreach(extractor => results.add(extractor.extract(request)))
    results.toArray
  }
}