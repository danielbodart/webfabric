package org.webfabric.sitemesh.v2

import org.webfabric.collections.UnsupportedMap
import com.opensymphony.module.sitemesh.HTMLPage

class PagePropertyMap(page: HTMLPage) extends UnsupportedMap[String, Any] {
  override def containsKey(key: Any):Boolean = key match {
    case "head" => true
    case "body" => true
    case _ => page.getProperties.containsKey(key)
  }

  override def get(key: Any):Any = key match {
    case "head" => page.getHead
    case "body" => page.getBody
    case _ => page.getProperties.get(key)
  }
}