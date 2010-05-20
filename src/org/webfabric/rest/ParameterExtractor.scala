package org.webfabric.rest


trait ParameterExtractor {
  def isMatch(request:Request):Boolean
  def extract(request:Request):Object
}