package org.webfabric.rest

import java.lang.reflect.Method
import org.webfabric.collections.{List}
import com.googlecode.yadic.SimpleContainer
import javax.ws.rs._

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) {
  type Param = {def value(): String}
  lazy val parameters: List[Param] = {
    val result = List[Param]()
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
    parameters.foldLeft(true, (isMatch: Boolean, parameter) => parameter match {
      case query: QueryParam => isMatch && request.query.contains(query.value)
      case form: FormParam => isMatch && request.form.contains(form.value)
      case path: PathParam => isMatch && pathTemplate.extract(request.path).contains(path.value)
    })
  }

  def activate(container: SimpleContainer, request:Request): Object = {
    val resourceInstance = container.resolve(resource)
    method.invoke(resourceInstance, getParameters(request): _*)
  }

  def getParameters(request:Request): Array[Object] = {
    val results = List[Object]()
    parameters.foreach(_ match {
      case query: QueryParam => results.add(request.query.getValue(query.value))
      case form: FormParam => results.add(request.form.getValue(form.value))
      case path: PathParam => results.add(pathTemplate.extract(request.path).getValue(path.value))
    })
    results.toArray
  }
}