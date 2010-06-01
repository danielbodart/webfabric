package org.webfabric.rest

import javax.ws.rs.QueryParam

class QueryParameterExtractor(param:QueryParam) extends RequestExtractor[String]{
  def isMatch(request:Request):Boolean = request.query.contains(param.value)
  def extract(request:Request):String = request.query.getValue(param.value)
  def generate(parameters:QueryParameters, value:String) = parameters.add(param.value, value)
}
