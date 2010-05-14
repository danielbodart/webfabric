package org.webfabric.rest

import java.lang.reflect.Method
import java.lang.annotation.Annotation
import org.webfabric.collections.List
import com.googlecode.yadic.SimpleContainer
import javax.ws.rs.{FormParam, QueryParam}

class HttpMethodActivator(httpMethod:String, path:String, resource:Class[_], method:Method){
  val extractors = createParamterExtractors(method.getParameterAnnotations)

  def createParamterExtractors(annotationsPerParameter: Array[Array[Annotation]]): List[ParameterExtractor] = {
    val extractors = List[ParameterExtractor]()
    annotationsPerParameter.foreach((annotations: Array[Annotation]) => {
      annotations(0) match {
        case query:QueryParam => extractors.add(new QueryParameterExtractor(query.value))
        case form:FormParam => extractors.add(new FormParameterExtractor(form.value))
        case _ =>
      }
    })
    extractors
  }

  def isMatch(httpMethodToCheck:String, pathToCheck:String, query:QueryParameters, form:FormParameters):Boolean = {
    httpMethod.equals(httpMethodToCheck) && path.equals(pathToCheck)
  }

  def activate(container:SimpleContainer, query:QueryParameters, form:FormParameters):Object = {
    val resourceInstance = container.resolve(resource)
    method.invoke(resourceInstance, getParameters(query, form): _*)
  }

  def getParameters( query: QueryParameters, form:FormParameters): Array[Object] = {
    val results = List[Object]()
    extractors.foreach(_ match {
      case queryExtractor:QueryParameterExtractor => results.add(queryExtractor.extractFrom(query))
      case formExtractor:FormParameterExtractor => results.add(formExtractor.extractFrom(form))
    })
    results.toArray
  }
}