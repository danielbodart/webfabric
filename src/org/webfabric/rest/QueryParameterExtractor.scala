package org.webfabric.rest

import javax.ws.rs.QueryParam

class QueryParameterExtractor(param:QueryParam) extends ParameterExtractor{
  def isMatch(request:Request):Boolean = request.query.contains(param.value)
  def extract(request:Request):Object = request.query.getValue(param.value)
  def generate(parameters:QueryParameters, value:String) = parameters.add(param.value, value)
}
