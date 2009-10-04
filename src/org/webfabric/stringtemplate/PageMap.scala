package org.webfabric.stringtemplate

import com.opensymphony.module.sitemesh.{HTMLPage}
import java.util.{HashMap, Map}
import org.webfabric.collections.UnsupportedMap

class PageMap(pageLoader:PageLoader) extends UnsupportedMap[String, HTMLPage]{
  def this() = this(new UrlPageLoader)

  var cache:Map[String, HTMLPage] = new HashMap()

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