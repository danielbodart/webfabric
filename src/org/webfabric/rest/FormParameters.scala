package org.webfabric.rest

class FormParameters extends Parameters

object FormParameters{
  def apply(pairs: (String, String)*): FormParameters = {
    val result = new FormParameters
    pairs.foreach(pair => result.add(pair._1, pair._2))
    result
  }
}