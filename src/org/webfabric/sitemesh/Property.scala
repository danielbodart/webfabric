package org.webfabric.sitemesh

trait Property {
  def hasChild(name: String): Boolean;
  def getChild(name: String): Property;
  def getValue: String
}