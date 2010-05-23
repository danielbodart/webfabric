package org.webfabric.rest

import javax.ws.rs.HttpMethod
import java.io.{ByteArrayInputStream, InputStream}
import javax.servlet.http.HttpServletRequest

case class Request(method:String, path:String, headers:HeaderParameters, query:QueryParameters, form:FormParameters, input:InputStream)

object Request{
  def emptyInput = new ByteArrayInputStream(new Array[Byte](0))

  def get(path: String): Request = get(path, QueryParameters())

  def get(path: String, query: QueryParameters): Request = Request(HttpMethod.GET, path, HeaderParameters(), query, FormParameters(), emptyInput )

  def post(path: String, form: FormParameters): Request = Request(HttpMethod.POST, path, HeaderParameters(), QueryParameters(), form, emptyInput) // FIX ME

  def delete(path: String): Request = Request(HttpMethod.DELETE, path, HeaderParameters(), QueryParameters(), FormParameters(), emptyInput)

  def put(path: String, input:InputStream): Request = Request(HttpMethod.PUT, path, HeaderParameters(), QueryParameters(), FormParameters(), input)

  def apply(request: HttpServletRequest):Request = {
    Request(request.getMethod, request.getPathInfo, HeaderParameters(request), QueryParameters(), FormParameters(), request.getInputStream)
  }
}