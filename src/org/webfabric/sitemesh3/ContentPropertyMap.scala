package org.webfabric.sitemesh3

import org.sitemesh.content.ContentProperty
import java.util.{ArrayList, Map, Collection}

class ContentPropertyMap(property:ContentProperty) extends Map[String, Any] {
  def containsKey(key: Any) = property.hasChild(key.toString)

  def get(key: Any) = new ContentPropertyMap(property.getChild(key.toString))

  override def toString = property.getValue

  def values = {
    val list = new ArrayList[String]
    list.add(property.getValue)
    list.asInstanceOf[Collection[Any]]
  }

  def size = throw new UnsupportedOperationException

  def remove(key: Any) = throw new UnsupportedOperationException

  def putAll(m: Map[_ <: String, _ <: Any]) = throw new UnsupportedOperationException

  def put(key: String, value: Any) = throw new UnsupportedOperationException

  def keySet = throw new UnsupportedOperationException

  def isEmpty = throw new UnsupportedOperationException


  def entrySet = throw new UnsupportedOperationException

  def containsValue(value: Any) = throw new UnsupportedOperationException

  def clear = throw new UnsupportedOperationException
}