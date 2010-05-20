package org.webfabric.rest


trait ParameterExtractor {
  def isMatch(request:Request, path:PathParameters):Boolean
  def extract(request:Request, path:PathParameters):Object
}