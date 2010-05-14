package org.webfabric.rest


class ParameterExtractor(name:String){
  def extractFrom(query:QueryParameters):Object = {
    query.getValue(name)
  }
}