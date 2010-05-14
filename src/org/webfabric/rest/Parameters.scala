package org.webfabric.rest

import org.webfabric.collections.{Map, List}

class Parameters{
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