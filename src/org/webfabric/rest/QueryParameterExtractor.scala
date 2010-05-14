package org.webfabric.rest


class QueryParameterExtractor(name:String) extends ParameterExtractor{
  def extractFrom(query:Parameters):Object = {
    query.getValue(name)
  }
}