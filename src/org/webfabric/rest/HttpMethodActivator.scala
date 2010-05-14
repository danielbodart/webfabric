package org.webfabric.rest

import java.lang.reflect.Method
import java.lang.annotation.Annotation
import org.webfabric.collections.List
import com.googlecode.yadic.SimpleContainer
import javax.ws.rs.QueryParam

class HttpMethodActivator(httpMethod:String, path:String, resource:Class[_], method:Method){
  val extractors = createParamterExtractors(method.getParameterAnnotations)

  def createParamterExtractors(annotationsPerParameter: Array[Array[Annotation]]): List[ParameterExtractor] = {
    val extractors = List[ParameterExtractor]()
    annotationsPerParameter.foreach((annotations: Array[Annotation]) => {
      extractors.add(new ParameterExtractor(annotations(0).asInstanceOf[QueryParam].value))
    })
    extractors
  }

  def isMatch(httpMethodToCheck:String, pathToCheck:String, query:QueryParameters):Boolean = {
    httpMethod.equals(httpMethodToCheck) && path.equals(pathToCheck)
  }

  def activate(container:SimpleContainer, query:QueryParameters):Object = {
    val resourceInstance = container.resolve(resource)
    method.invoke(resourceInstance, getParameters(query): _*)
  }

  def getParameters( query: QueryParameters): Array[Object] = {
    val result = new Array[Object](query.size)
    var index = 0
    extractors.foreach(extractor => {
      result(index) = extractor.extractFrom(query)
      index = index + 1
    })
    result
  }
}