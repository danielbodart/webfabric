package org.webfabric.stringtemplate


import com.opensymphony.module.sitemesh.{Page, HTMLPage}
import io.Converter
import java.io.IOException
import java.net.URL
import java.util.{Map => JavaMap}
import web.sitemesh.DivCapturingPageParser

class PageMap(pageLoader:PageLoader) extends JavaMap[String, HTMLPage]{
  def this() = this(new UrlPageLoader)

  var cache:Map[String, HTMLPage] = Map()

  def containsKey(key: Any) = key match{
    case path:String => {
      if(!cache.contains(path)){
        loadPage(path)
      }
      cache.contains(path)
    }
    case _ => false
  }

  def get(key: Any) = key match{
    case path:String => {
      cache(path)
    }
    case _ => error("No page available for '{0}'".format(key))
  }

  def loadPage(path:String){
    pageLoader.load(path) match {
      case Some(page) => cache += (path -> page)
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

  def putAll(m: JavaMap[_ <: String, _ <: HTMLPage]) = throw new UnsupportedOperationException
}