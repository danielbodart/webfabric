package org.webfabric.rest

import javax.ws.rs.HttpMethod
import java.io.{ByteArrayInputStream, InputStream}
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.HttpHeaders
import org.webfabric.servlet.{BasePath, ContextPath}

case class Request(method:String, base:BasePath, path:String, headers:HeaderParameters, query:QueryParameters, form:FormParameters, input:InputStream)

object Request{
  def emptyInput = new ByteArrayInputStream(new Array[Byte](0))

  def get(path: String): Request = get(path, QueryParameters())

  def get(path: String, query: QueryParameters): Request = Request(HttpMethod.GET, path, HeaderParameters(), query, FormParameters(), emptyInput )

  def post(path: String, form: FormParameters): Request = Request(HttpMethod.POST, path, HeaderParameters(HttpHeaders.CONTENT_TYPE -> "application/x-www-form-urlencoded"), QueryParameters(), form, emptyInput)

  def delete(path: String): Request = Request(HttpMethod.DELETE, path, HeaderParameters(), QueryParameters(), FormParameters(), emptyInput)

  def put(path: String, input:InputStream): Request = Request(HttpMethod.PUT, path, HeaderParameters(), QueryParameters(), FormParameters(), input)

  def apply(request: HttpServletRequest):Request = {
    Request(request.getMethod, BasePath(request), request.getPathInfo, HeaderParameters(request), QueryParameters(request), FormParameters(request), request.getInputStream)
  }

  def apply(method:String, path:String, headers:HeaderParameters, query:QueryParameters, form:FormParameters, input:InputStream):Request = {
    Request(method, BasePath(""), path, headers, query, form, input)
  }
}