package org.webfabric.sitemesh

import java.lang.String

class TreeProperty(name:String, var value: String) extends Property {
  def this(name:String) = this (name, null)

  val children = new java.util.HashMap[String, TreeProperty]

  def getValue = value

  def setValue(v:String):Unit = value = v

  def getChild(name: String) = {
    if (!children.containsKey(name)) {
      children.put(name, new TreeProperty(name))
    }
    children.get(name)
  }

  def hasChild(name: String) = children.containsKey(name)
}