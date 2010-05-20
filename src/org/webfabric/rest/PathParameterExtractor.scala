package org.webfabric.rest

import javax.ws.rs.PathParam

class PathParameterExtractor(param:PathParam, pathTemplate:UriTemplate) extends ParameterExtractor{
  def isMatch(request:Request):Boolean = pathTemplate.extract(request.path).contains(param.value)
  def extract(request:Request):Object = pathTemplate.extract(request.path).getValue(param.value)
}