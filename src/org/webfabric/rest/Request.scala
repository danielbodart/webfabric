package org.webfabric.rest

import javax.ws.rs.HttpMethod

case class Request(method:String, path:String, headers:HeaderParameters, query:QueryParameters, form:FormParameters)

object Request{
  def get(path: String): Request = get(path, QueryParameters())

  def get(path: String, query: QueryParameters): Request = {
    Request(HttpMethod.GET, path, HeaderParameters(), query, FormParameters())
  }

  def post(path: String, form: FormParameters): Request = {
    Request(HttpMethod.POST, path, HeaderParameters(), QueryParameters(), form)
  }
}