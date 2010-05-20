package org.webfabric.rest

import javax.ws.rs.QueryParam

class QueryParameterExtractor(param:QueryParam) extends ParameterExtractor{
  def isMatch(request:Request, path:PathParameters):Boolean = request.query.contains(param.value)
def extract(request:Request, path:PathParameters):Object = request.query.getValue(param.value)
}
