package org.webfabric.rest

class QueryParameters extends Parameters

object QueryParameters{
  def apply(pairs: (String, String)*): QueryParameters = {
    val result = new QueryParameters
    pairs.foreach(pair => result.add(pair._1, pair._2))
    result
  }
}