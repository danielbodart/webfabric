package org.webfabric.sitemesh.v3

import org.sitemesh.content.ContentProperty
import java.util.{ArrayList, Collection}
import org.webfabric.collections.UnsupportedMap

class ContentPropertyMap(property:ContentProperty) extends UnsupportedMap[String, Any] {
  override def containsKey(key: Any) = property.hasChild(key.toString)

  override def get(key: Any) = new ContentPropertyMap(property.getChild(key.toString))

  override def toString = property.getValue

  override def values = {
    val list = new ArrayList[String]
    list.add(property.getValue)
    list.asInstanceOf[Collection[Any]]
  }
}