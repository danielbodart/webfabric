package org.webfabric.stringtemplate

import java.util.{HashMap, Map}
import org.webfabric.collections.UnsupportedMap
import org.webfabric.sitemesh.PropertyMap

class PageMap(pageLoader:PageLoader) extends UnsupportedMap[String, PropertyMap]{
  def this() = this(new UrlPageLoader)

  var cache:Map[String, PropertyMap] = new HashMap()

  override def containsKey(key: Any) = key match{
    case path:String => {
      if(!cache.containsKey(path)){
        loadPage(path)
      }
      cache.containsKey(path)
    }
    case _ => false
  }

  override def get(key: Any) = key match{
    case path:String => {
      cache.get(path)
    }
    case _ => error("No page available for '{0}'".format(key))
  }

  def loadPage(path:String){
    pageLoader.load(path) match {
      case Some(page) => cache.put(path, page)
      case _ =>
    }
  }
}