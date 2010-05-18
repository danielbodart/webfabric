package org.webfabric.rest

import java.lang.reflect.Method
import org.webfabric.collections.List
import com.googlecode.yadic.SimpleContainer
import javax.ws.rs.{PathParam, Path, FormParam, QueryParam}
import java.lang.annotation.Annotation

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) {
  lazy val parameters: List[Annotation] = {
    val result = List[Annotation]()
    method.getParameterAnnotations.foreach(_(0) match {
      case query: QueryParam => result.add(query)
      case form: FormParam => result.add(form)
      case path: PathParam => result.add(path)
      case _ =>
    })
    result
  }

  lazy val pathTemplate: UriTemplate = {
    val paths = List(resource.getAnnotation(classOf[Path]), method.getAnnotation(classOf[Path]))
    new UriTemplate(paths.filter(_ != null).map(_.value).mkString("/"))
  }

  def isMatch(httpMethodToCheck: String, pathToCheck: String, query: QueryParameters, form: FormParameters): Boolean = {
    httpMethod.equals(httpMethodToCheck) && pathTemplate.isMatch(pathToCheck) &&
            parametersMatch(query, form, pathTemplate.extract(pathToCheck))
  }

  def parametersMatch(queryParameters: QueryParameters, formParameters: FormParameters, pathParameters: PathParameters):Boolean = {
    parameters.foldLeft(true, (isMatch:Boolean, parameter) => parameter match {
      case query: QueryParam => isMatch && queryParameters.contains(query.value)
      case form: FormParam => isMatch && formParameters.contains(form.value)
      case path: PathParam => isMatch && pathParameters.contains(path.value)
    })
  }

  def activate(container: SimpleContainer, pathToCheck:String, query: QueryParameters, form: FormParameters): Object = {
    val resourceInstance = container.resolve(resource)
    method.invoke(resourceInstance, getParameters(pathTemplate.extract(pathToCheck), query, form): _*)
  }

  def getParameters(pathParameters: PathParameters, queryParameters: QueryParameters, formParameters: FormParameters): Array[Object] = {
    val results = List[Object]()
    parameters.foreach(_ match {
      case query: QueryParam  => results.add(queryParameters.getValue(query.value))
      case form: FormParam  => results.add(formParameters.getValue(form.value))
      case path: PathParam => results.add(pathParameters.getValue(path.value))
    })
    results.toArray
  }
}