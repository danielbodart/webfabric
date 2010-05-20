package org.webfabric.rest

import javax.ws.rs.PathParam

class PathParameterExtractor(param:PathParam, extractor:PathExtractor) extends ParameterExtractor{
  def isMatch(request:Request):Boolean = extractor.extract(request).contains(param.value)
  def extract(request:Request):Object = extractor.extract(request).getValue(param.value)
}