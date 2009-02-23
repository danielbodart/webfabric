package org.webfabric.stringtemplate

import com.opensymphony.module.sitemesh.HTMLPage

class PageLoaders(list:PageLoader*) extends PageLoader{
  def load(path:String): Option[HTMLPage] = {
    for( loader <- list ) {
      loader.load(path) match {
        case Some(page) => return Some(page)
        case _ =>
      }
    }
    None
  }
}