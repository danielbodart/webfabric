package org.webfabric.rest

import java.lang.reflect.Method
import org.webfabric.collections.List
import com.googlecode.yadic.SimpleContainer
import javax.ws.rs.{Path, FormParam, QueryParam}

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) {
  lazy val extractors: List[ParameterExtractor] = {
    val extractors = List[ParameterExtractor]()
    method.getParameterAnnotations.foreach(_(0) match {
      case query: QueryParam => extractors.add(new QueryParameterExtractor(query.value))
      case form: FormParam => extractors.add(new FormParameterExtractor(form.value))
      case _ =>
    })
    extractors
  }

  lazy val path: String = {
    val paths = List(resource.getAnnotation(classOf[Path]), method.getAnnotation(classOf[Path]))
    paths.filter(_ != null).map(_.value).mkString("/")
  }

  def isMatch(httpMethodToCheck: String, pathToCheck: String, query: QueryParameters, form: FormParameters): Boolean = {
    httpMethod.equals(httpMethodToCheck) && path.equals(pathToCheck) && extractorsMatch(query, form)
  }

  def extractorsMatch(query: QueryParameters, form: FormParameters):Boolean = {
    extractors.foldLeft(true, (isMatch:Boolean, extractor) => extractor match {
      case queryExtractor: QueryParameterExtractor => isMatch && queryExtractor.matches(query)
      case formExtractor: FormParameterExtractor => isMatch && formExtractor.matches(form)
    })
  }

  def activate(container: SimpleContainer, query: QueryParameters, form: FormParameters): Object = {
    val resourceInstance = container.resolve(resource)
    method.invoke(resourceInstance, getParameters(query, form): _*)
  }

  def getParameters(query: QueryParameters, form: FormParameters): Array[Object] = {
    val results = List[Object]()
    extractors.foreach(_ match {
      case queryExtractor: QueryParameterExtractor => results.add(queryExtractor.extractFrom(query))
      case formExtractor: FormParameterExtractor => results.add(formExtractor.extractFrom(form))
    })
    results.toArray
  }
}