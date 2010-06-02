package org.webfabric.rest

import com.googlecode.yadic.{SimpleContainer, Container}
import java.lang.reflect.Method
import java.io.{ByteArrayInputStream, InputStream}
import javax.ws.rs.{HeaderParam, QueryParam, FormParam, PathParam}

class ArgumentsExtractor(pathExtractor: PathExtractor, resource: Class[_], method: Method) extends RequestExtractor[Array[Object]] {
  def isMatch(request: Request) = {
    try {
      extract(request)
      true
    } catch {
      case _ => false
    }
  }

  def extract(request: Request): Array[Object] = {
    val container = getArgumentContainer
    container.addInstance(request)
    method.getParameterTypes.zip(method.getParameterAnnotations).map(pair => {
      val aClass = pair._1
      val annotations = pair._2
      if (!container.contains(aClass)) container.add(aClass)
      container.remove(classOf[String])
      if (annotations.length > 0) annotations(0) match {
        case query: QueryParam => container.add(classOf[String], () => {
          val params = container.resolveType(classOf[QueryParameters])
          if (!params.contains(query.value)) throw new NoSuchElementException
          params.getValue(query.value)
        })
        case form: FormParam => container.add(classOf[String], () => {
          val params = container.resolveType(classOf[FormParameters])
          if (!params.contains(form.value)) throw new NoSuchElementException
          params.getValue(form.value)
        })
        case path: PathParam => container.add(classOf[String], () => {
          val params = container.resolveType(classOf[PathParameters])
          if (!params.contains(path.value)) throw new NoSuchElementException
          params.getValue(path.value)
        })
        case header: HeaderParam => container.add(classOf[String], () => {
          val params = container.resolveType(classOf[HeaderParameters])
          if (!params.contains(header.value)) throw new NoSuchElementException
          params.getValue(header.value)
        })
        case _ =>
      }
      container.resolve(aClass)
    })
  }

  def getArgumentContainer: Container = {
    val container = new SimpleContainer
    container.add(classOf[UriTemplate], () => pathExtractor.pathTemplate)
    container.add(classOf[PathParameters], () => container.resolveType(classOf[UriTemplate]).extract(container.resolveType(classOf[Request]).path))
    container.add(classOf[HeaderParameters], () => container.resolveType(classOf[Request]).headers)
    container.add(classOf[QueryParameters], () => container.resolveType(classOf[Request]).query)
    container.add(classOf[FormParameters], () => container.resolveType(classOf[Request]).form)
    container.add(classOf[InputStream], () => container.resolveType(classOf[Request]).input)
    container
  }

  def generate(arguments: Array[Object]): Request = {
    val pathParams = PathParameters()
    val headers = HeaderParameters()
    val formParams = FormParameters()
    val queryParams = QueryParameters()
    var input: InputStream = new ByteArrayInputStream(new Array[Byte](0))

    arguments.zip(method.getParameterAnnotations).map(pair => {
      val value = pair._1
      val annotations = pair._2

      if (annotations.length > 0) annotations(0) match {
        case path: PathParam => pathParams.add(path.value, value.toString)
        case form: FormParam => formParams.add(form.value, value.toString)
        case query: QueryParam => queryParams.add(query.value, value.toString)
        case header: HeaderParam => headers.add(header.value, value.toString)
        case _ =>
      }
    })
    Request(null, pathExtractor.pathTemplate.generate(pathParams), headers, queryParams, formParams, input)
  }
}