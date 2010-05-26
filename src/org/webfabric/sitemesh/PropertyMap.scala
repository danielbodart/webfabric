package org.webfabric.sitemesh

import org.webfabric.collections.{List,UnsupportedMap}
import java.util.Collection

class PropertyMap(property: Property) extends UnsupportedMap[String, Any] {
  override def containsKey(key: Any) = property.hasChild(key.toString)

  override def get(key: Any) = new PropertyMap(property.getChild(key.toString))

  override def toString = property.getValue

  override def values: Collection[Any] = {
    List(property.getValue)
  }
}