package org.webfabric.rest

import java.lang.reflect.Method
import org.webfabric.collections.List
import com.googlecode.yadic.SimpleContainer
import javax.ws.rs.{PathParam, Path, FormParam, QueryParam}

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) {
  lazy val extractors: List[ParameterExtractor] = {
    val extractors = List[ParameterExtractor]()
    method.getParameterAnnotations.foreach(_(0) match {
      case query: QueryParam => extractors.add(new QueryParameterExtractor(query.value))
      case form: FormParam => extractors.add(new FormParameterExtractor(form.value))
      case path: PathParam => extractors.add(new PathParameterExtractor(path.value))
      case _ =>
    })
    extractors
  }

  lazy val pathTemplate: UriTemplate = {
    val paths = List(resource.getAnnotation(classOf[Path]), method.getAnnotation(classOf[Path]))
    new UriTemplate(paths.filter(_ != null).map(_.value).mkString("/"))
  }

  def isMatch(httpMethodToCheck: String, pathToCheck: String, query: QueryParameters, form: FormParameters): Boolean = {
    httpMethod.equals(httpMethodToCheck) && pathTemplate.isMatch(pathToCheck) && extractorsMatch(query, form, pathTemplate.extract(pathToCheck))
  }

  def extractorsMatch(query: QueryParameters, form: FormParameters, path: PathParameters):Boolean = {
    extractors.foldLeft(true, (isMatch:Boolean, extractor) => extractor match {
      case queryExtractor: QueryParameterExtractor => isMatch && queryExtractor.matches(query)
      case formExtractor: FormParameterExtractor => isMatch && formExtractor.matches(form)
      case pathExtractor: PathParameterExtractor => isMatch && pathExtractor.matches(path)
    })
  }

  def activate(container: SimpleContainer, pathToCheck:String, query: QueryParameters, form: FormParameters): Object = {
    val resourceInstance = container.resolve(resource)
    method.invoke(resourceInstance, getParameters(pathTemplate.extract(pathToCheck), query, form): _*)
  }

  def getParameters(path: PathParameters, query: QueryParameters, form: FormParameters): Array[Object] = {
    val results = List[Object]()
    extractors.foreach(_ match {
      case queryExtractor: QueryParameterExtractor => results.add(queryExtractor.extractFrom(query))
      case formExtractor: FormParameterExtractor => results.add(formExtractor.extractFrom(form))
      case pathExtractor: PathParameterExtractor => results.add(pathExtractor.extractFrom(path))
    })
    results.toArray
  }
}