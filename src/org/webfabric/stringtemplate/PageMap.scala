package org.webfabric.stringtemplate

import com.opensymphony.module.sitemesh.{HTMLPage}
import java.util.{HashMap, Map}

class PageMap(pageLoader:PageLoader) extends Map[String, HTMLPage]{
  def this() = this(new UrlPageLoader)

  var cache:Map[String, HTMLPage] = new HashMap()

  def containsKey(key: Any) = key match{
    case path:String => {
      if(!cache.containsKey(path)){
        loadPage(path)
      }
      cache.containsKey(path)
    }
    case _ => false
  }

  def get(key: Any) = key match{
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

  def keySet = throw new UnsupportedOperationException

  def put(key: String, value: HTMLPage) = throw new UnsupportedOperationException

  def isEmpty = throw new UnsupportedOperationException

  def remove(key: Any) = throw new UnsupportedOperationException

  def size = throw new UnsupportedOperationException

  def clear = throw new UnsupportedOperationException

  def values = throw new UnsupportedOperationException

  def entrySet = throw new UnsupportedOperationException

  def containsValue(value: Any) = throw new UnsupportedOperationException

  def putAll(m: Map[_ <: String, _ <: HTMLPage]) = throw new UnsupportedOperationException
}