package org.webfabric.rest

class HeaderParameters extends Parameters

object HeaderParameters{
  def apply(pairs: (String, String)*): HeaderParameters = {
    val result = new HeaderParameters
    pairs.foreach(pair => result.add(pair._1, pair._2))
    result
  }
}