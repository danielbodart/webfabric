package org.webfabric.sitemesh.v3

import org.sitemesh.content.ContentProperty
import java.util.{Collection}
import org.webfabric.collections.{List}
import org.webfabric.sitemesh.PropertyMap

class ContentPropertyMap(property:ContentProperty) extends PropertyMap {
  override def containsKey(key: Any) = property.hasChild(key.toString)

  override def get(key: Any) = new ContentPropertyMap(property.getChild(key.toString))

  override def toString = property.getValue

  override def values:Collection[Any] = {
    List(property.getValue)
  }
}