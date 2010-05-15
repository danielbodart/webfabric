package org.webfabric.rest

class ParameterExtractor(name:String) {
  def extractFrom(parameters:Parameters):Object = parameters.getValue(name)

  def matches(parameters:Parameters) = parameters.contains(name)
}