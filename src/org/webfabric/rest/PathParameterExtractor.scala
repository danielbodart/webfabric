package org.webfabric.rest

import javax.ws.rs.PathParam

class PathParameterExtractor(param:PathParam) extends ParameterExtractor{
  def isMatch(request:Request, path:PathParameters):Boolean = path.contains(param.value)
  def extract(request:Request, path:PathParameters):Object = path.getValue(param.value)
}