package org.webfabric.rest

import org.webfabric.collections._


class QueryParameters{
  val values = Map[String, List[String]]()

  def add(name:String, value:String):Unit = {
    if(!values.containsKey(name)) {
      values.put(name, List[String]())
    }
    values.get(name).add(value)
  }

  def size = values.size

  def getValue(name:String):String = {
    values.get(name).get(0)
  }
}

object QueryParameters{
  def apply(pairs: (String, String)*): QueryParameters = {
    val result = new QueryParameters
    pairs.foreach(pair => result.add(pair._1, pair._2))
    result
  }
}