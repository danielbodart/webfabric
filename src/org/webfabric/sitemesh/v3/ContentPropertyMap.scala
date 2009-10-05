package org.webfabric.sitemesh.v3

import org.sitemesh.content.ContentProperty
import java.util.{Collection}
import org.webfabric.collections.{List,UnsupportedMap}

class ContentPropertyMap(property:ContentProperty) extends UnsupportedMap[String, Any] {
  override def containsKey(key: Any) = property.hasChild(key.toString)

  override def get(key: Any) = new ContentPropertyMap(property.getChild(key.toString))

  override def toString = property.getValue

  override def values:Collection[Any] = {
    List(property.getValue)
  }
}