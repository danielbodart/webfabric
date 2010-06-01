package org.webfabric.rest

import javax.ws.rs.PathParam

class PathParameterExtractor(param:PathParam, extractor:PathExtractor) extends RequestExtractor[String]{
  def isMatch(request:Request):Boolean = extractor.extract(request).contains(param.value)
  def extract(request:Request):String = extractor.extract(request).getValue(param.value)
  def generate(parameters:PathParameters, value:String) = parameters.add(param.value, value)
}