package org.webfabric.rest


class FormParameterExtractor(name:String) extends ParameterExtractor{
  def extractFrom(parameters:Parameters):Object = {
    parameters.getValue(name)
  }
}