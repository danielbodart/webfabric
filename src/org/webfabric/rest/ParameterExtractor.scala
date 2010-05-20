package org.webfabric.rest


trait ParameterExtractor extends Matcher[Request] {
  def extract(request:Request):Object
}