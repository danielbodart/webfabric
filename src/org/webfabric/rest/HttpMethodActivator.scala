package org.webfabric.rest

import java.lang.reflect.Method
import org.webfabric.collections.{List,Map}
import javax.ws.rs._
import com.googlecode.yadic.{Resolver}

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) {
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

  lazy val producesMimetype: String = {
    val result = List(method.getAnnotation(classOf[Produces]), resource.getAnnotation(classOf[Produces])).filter(_ != null)
    result.headOption match {
      case Some(produces) => produces.value.first
      case None => "*/*"
    }
  }

  def isMatch(request:Request): Boolean = {
    httpMethod.equals(request.method) && pathTemplate.isMatch(request.path) &&
            mimeTypesMatch(request.headers) && parametersMatch(request)
  }
  
  def mimeTypesMatch(headers: HeaderParameters):Boolean = {
    val expected = if(headers.contains("Accept")) headers.getValue("Accept") else "*/*"
    expected.equals(producesMimetype)
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